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
package org.alexlg.bankit.controllers.options;

import org.alexlg.bankit.services.OptionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller which handles all operations for
 * BankIt updates options.
 * 
 * @author Alexandre Thomazo
 */
@Controller
@RequestMapping("/options/updates")
public class UpdateController {

	@Autowired
	private OptionsService optionsService;
	
	/**
	 * Show updates options (automatic updates and channel)
	 * @return View name
	 */
	@RequestMapping(value="", method=RequestMethod.GET)
	public String showUpdatesOption(ModelMap map) {
		Integer checkUpdates = optionsService.getInteger("checkUpdates");
		if (checkUpdates == null) checkUpdates = 1;
		
		String updateChannel = optionsService.getString("updateChannel");
		if (updateChannel == null) updateChannel = "stable";

		map.addAttribute("page", "updates");
		map.addAttribute("checkUpdates", checkUpdates);
		map.addAttribute("updateChannel", updateChannel);
		return "options/updates";
	}
	
	/**
	 * Save options for updates
	 * @param checkUpdates Do we check for updates (0/1) ?
	 * @param updateChannel Channel to check for updates ?
	 * @return redirect to display form
	 */
	@RequestMapping(value="", method=RequestMethod.POST)
	public String saveUpdatesOption(@RequestParam String checkUpdates, @RequestParam String updateChannel,
			RedirectAttributes redirectAttributes) {
		if ("1".equals(checkUpdates)) {
			optionsService.set("checkUpdates", 1);
		} else {
			optionsService.set("checkUpdates", 0);
		}
		optionsService.set("updateChannel", updateChannel);
		
		redirectAttributes.addFlashAttribute("saved", "ok");
		return "redirect:/options/updates";
	}
}
