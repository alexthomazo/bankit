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

import org.eclipse.jetty.webapp.WebAppClassLoader;

/**
 * This class deactivate the loading of SLF4J API inside the WebApp
 * in order to use the one provided by the Jetty Classloader.
 * The use of the Jetty SLF4J implementation also avoid to load the logging
 * configuration of the WebApp.
 * 
 * @author Alexandre Thomazo
 */
public class WebAppClassLoaderNoSlf4J extends WebAppClassLoader {

	public WebAppClassLoaderNoSlf4J(Context context)
			throws IOException {
		super(context);
	}

	@Override
	public void addClassPath(String classPath) throws IOException {
		//deactivate loading of slf4j
		if (classPath.matches(".*slf4j-api-.*\\.jar")) return;
		super.addClassPath(classPath);
	}
}
