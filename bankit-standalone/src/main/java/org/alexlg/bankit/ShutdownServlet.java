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

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Server;

/**
 * Servlet which shutdown the Jetty server. It called
 * when the user click on the quit button in the application.
 * 
 * @author Alexandre Thomazo
 */
public class ShutdownServlet extends HttpServlet {
	private static final long serialVersionUID = -3857047472685595016L;

	/** Jetty server */
	private Server jettyServer;
		
	public ShutdownServlet(Server jettyServer) {
		super();
		this.jettyServer = jettyServer;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		resp.setContentType("text/plain");

		new Thread() {
			public void run() {
				try {
					// shutdown the server
					jettyServer.stop();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
			
		resp.setStatus(HttpServletResponse.SC_OK);
		resp.getWriter().println("done");
	}

}
