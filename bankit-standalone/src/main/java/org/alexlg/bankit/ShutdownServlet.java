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

	/** Number of seconds before shutdown the application if ping is not received */
	public static final int SHUTDOWN_TIMEOUT = 30;

	/** Jetty server */
	private Server jettyServer;

	/** Thread waiting for the ping */
	private Thread keepAlive;

	/** if the countdown reach zero and keepAlive thread is running, the application stops */
	private int countdown;

	public ShutdownServlet(Server jettyServer) {
		super();
		this.jettyServer = jettyServer;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		resp.setContentType("text/plain");

		String uri = req.getRequestURI();
		//remove any trailing slash
		if (uri.endsWith("/")) uri = uri.substring(0, uri.length() - 1);

		if (uri.equals("/shutdown")) {
			shutdown();
		} else if (uri.equals("/shutdown/ping")) {
			ping();
		}

		resp.setStatus(HttpServletResponse.SC_OK);
		resp.getWriter().println("done");
	}

	/**
	 * Shutdown the jetty server
	 */
	private void shutdown() {
		new Thread() {
			public void run() {
				try {
					// shutdown the server
					countdown = -1;
					jettyServer.stop();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	/**
	 * Handle a ping request either by starting the countdown
	 * or by reset it to SHUTDOWN_TIMEOUT
	 */
	private void ping() {
		if (keepAlive == null) {
			keepAlive = new Thread() {
				@Override
				public void run() {
					while (countdown >= 0) {
						if (countdown == 0) {
							shutdown();
						}
						countdown--;
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							//if we can't sleep, let's shutdown
							shutdown();
						}
					}
				}
			};
			keepAlive.start();
		}

		countdown = SHUTDOWN_TIMEOUT;
	}
}
