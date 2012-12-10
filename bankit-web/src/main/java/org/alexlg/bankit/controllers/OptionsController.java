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
package org.alexlg.bankit.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller which handles all operations for
 * BankIt options.
 * 
 * @author Alexandre Thomazo
 */
@Controller
@RequestMapping("/options")
public class OptionsController {

	@RequestMapping("/")
	public String index() {
		return "redirect:/options/updates";
	}
	
	/**
	 * Show updates options (automatic updates and channel)
	 * @return View name
	 */
	@RequestMapping(value="/updates", method=RequestMethod.GET)
	public String showUpdatesOption(ModelMap map) {
		map.addAttribute("page", "updates");
		return "options/updates";
	}
}
