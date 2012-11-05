/*
 * Copyright (C) 2012 Alexandre Thomazo
 *
 * This file is part of BankIt.
 *
 * BankIt is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * BankIt is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with BankIt. If not, see <http://www.gnu.org/licenses/>.
 */
package org.alexlg.bankit;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;

import org.apache.commons.dbcp.BasicDataSource;
import org.eclipse.jetty.plus.jndi.Resource;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * Main class which launch the Jetty Server.
 * 
 * It search in lib dir for a WAR to deploy,
 * bind a H2 database JNDI resource, search
 * for an open port, start the Jetty server on it
 * then open the user default browser.
 * 
 * @author Alexandre Thomazo
 */
public class Launcher {
	
	public static void main(String[] args) throws Exception {
		LoadingFrame loadingFrame = new LoadingFrame();
		loadingFrame.display();
		
		File warFile = null;
		if (!(args.length > 0 && args[0].equals("nowar"))) { //can be deactivated for tests
			//searching war file in lib dir
			File libDir = new File("lib");
			if (!libDir.isDirectory()) throw new Exception("Directory [lib] doesn't exists in [" + libDir.getAbsolutePath() + "]");
			
			for (File file : libDir.listFiles()) {
				if (file.getName().endsWith(".war")) {
					warFile = file;
					break;
				}
			}
			if (warFile == null) throw new Exception("Cant find any .war file in [" + libDir.getAbsolutePath() + "]");
		}

		final int port = findAvailablePort(8080);
		
		//server definition
		Server server = new Server();
		server.setGracefulShutdown(1000);
		server.setStopAtShutdown(true);
		
		//http connector
		Connector connector = new SelectChannelConnector();
		connector.setHost("localhost");
		connector.setPort(port);
		server.addConnector(connector);
		
		//handlers context
		ContextHandlerCollection contexts = new ContextHandlerCollection();
		
		//shutdown servlet
		ServletContextHandler shutdownServletCtx = new ServletContextHandler();
		shutdownServletCtx.setContextPath("/shutdown");
		shutdownServletCtx.addServlet(new ServletHolder(new ShutdownServlet(server)), "/*");
		contexts.addHandler(shutdownServletCtx);
		
		//bankit war
		if (warFile != null) {
			WebAppContext webAppContext = new WebAppContext();
			webAppContext.setContextPath("/bankit");
			webAppContext.setWar(warFile.getAbsolutePath());
			webAppContext.setTempDirectory(new File("tmp/"));
			
			//prevent slf4j api to be loaded by the webapp
			//in order to keep the jetty logback configuration
			webAppContext.setClassLoader(new WebAppClassLoaderNoSlf4J(webAppContext));
			
			contexts.addHandler(webAppContext);
		}
		
		//database datasource
		BasicDataSource datasource = new BasicDataSource();
		datasource.setDriverClassName("org.h2.Driver");
		datasource.setUrl("jdbc:h2:bankit");
		new Resource("jdbc/bankit", datasource);
		
		//standalone jndi resource
		Boolean standalone = true;
		new Resource("java:comp/env/standalone", standalone);
		
		//adding Handlers for servlet and war
		server.setHandler(contexts);
		server.start();
		
		//start the user browser after the server started
		startBrowser("http://localhost:" + port + "/bankit/");
		loadingFrame.close();
		
		server.join();
	}
	
	/**
	 * Find an available TCP port for listening
	 * @param startPort base port to start check
	 * @return Port number or 0 if any port is found on the interval
	 */
	private static int findAvailablePort(int startPort) {
		for (int i = startPort ; i < 65535 ; i++) {
			if (isPortOpen(i)) return i;
		}
		return 0;
	}
	
	/**
	 * Check if a tcp port is open on localhost interface.
	 * @param port Port to check
	 * @return True if port is open, false otherwise
	 */
	private static boolean isPortOpen(int port) {
		ServerSocket socket = null;
		try {
		    socket = new ServerSocket(port);
		    socket.setReuseAddress(true);
		} catch (IOException e) {
		    return false;
		} finally { 
		    // Clean up
			try {
				if (socket != null) socket.close();
			} catch (IOException e) { } 
		}
		
		return true;
	}
	
	/**
	 * Start the local browser
	 * @param url URL to start with
	 */
	private static void startBrowser(String url) {
		String os = System.getProperty("os.name").toLowerCase();
		String[] commands = null;
		
		//start browser depending the operating system
		if (os.indexOf("win") >= 0) {
			commands = new String[]{"cmd", "/c", "start", url};
			
		} else if (os.indexOf("nux") >= 0) {
			commands = new String[]{"xdg-open", url};
			
		} else if (os.indexOf("mac") >= 0) {
			commands = new String[]{"open", url};
		}
		
		if (commands != null) {
			try {
				Runtime.getRuntime().exec(commands);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
