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
/**
 * Script used to shutdown the embedded Jetty when standalone application is running
 */
window.addEvent('load', function() {
	new Request({
		url: '/shutdown/',
		
		onComplete: function() {
			document.id('stopped').setStyle('display', '');
			document.id('loading-stop').setStyle('display', 'none');
		}
	}).get();
});

//hiding navbar to avoid click on links
window.addEvent('domready', function() {
	$$('.navbar').setStyle('display', 'none');
});