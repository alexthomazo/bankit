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

import org.alexlg.bankit.dao.CategoryDao;
import org.alexlg.bankit.dao.CostDao;
import org.alexlg.bankit.db.Category;
import org.alexlg.bankit.db.Cost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller which handles all operations for
 * displaying or editing the recurring costs.
 * 
 * @author Alexandre Thomazo
 */
@Controller
@RequestMapping("/cost")
public class CostController {

	@Autowired
	private CostDao costDao;

	@Autowired
	private CategoryDao categoryDao;

	@RequestMapping("/")
	public String index() {
		return "redirect:/cost/list";
	}
	
	/**
	 * List all costs.
	 * @param model Model to add costs.
	 * @return View name
	 */
	@RequestMapping("/list")
	@Transactional(readOnly=true)
	public String list(ModelMap model) {
		List<Cost> costs = costDao.getList();
		model.addAttribute("costs", costs);
		
		return "cost/list";
	}
	
	/**
	 * Show adding cost form.
	 * @param model Model to fill with current cost object
	 * @return View
	 */
	@RequestMapping(value="/add", method=RequestMethod.GET)
	public String showAddCostForm(ModelMap model) {
		Cost cost = new Cost();
		cost.setCost(true);
		model.addAttribute("cost", cost);
		//inject categories list
		model.put("categories", buildCategoriesList());
		return "cost/form";
	}
	
	/**
	 * Add a cost
	 * @param cost Cost to add
	 * @param result Result of the binding between the POST and the object
	 * @param redirectAttributes Use to add some data into the model after the redirect
	 * @return View name
	 */
	@RequestMapping(value="/add", method=RequestMethod.POST)
	@Transactional
	public String addCost(@ModelAttribute @Valid Cost cost, 
			BindingResult result,
			RedirectAttributes redirectAttributes) {
		
		if (result.hasErrors()) {
			return "cost/form";
		} else {
			if (cost.isCost()) {
				//negate the amount if needed
				cost.setAmount(cost.getAmount().negate());
				cost.setCost(false);
			}
			costDao.insert(cost);
			redirectAttributes.addFlashAttribute("added", cost.getCostId());
			return "redirect:/cost/list";
		}
	}

	/**
	 * Show the edit cost form
	 * @param costId Id of the cost to edit
	 * @param model Model to fill with the cost to edit
	 * @return View name
	 * @throws FileNotFoundException If the cost doesn't exist
	 */
	@RequestMapping(value = "/edit/{costId}", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public String showEditCostForm(@PathVariable int costId, ModelMap model) throws FileNotFoundException {
		Cost cost = costDao.get(costId);
		if (cost == null) {
			throw new FileNotFoundException("Cost [" + costId + "] not found");
		}
		//reverting amount if it's a cost to display the cost/income option correctly
		if (cost.getAmount().signum() == -1) {
			cost.setAmount(cost.getAmount().negate());
			cost.setCost(true);
		}
		model.put("cost", cost);

		//inject categories list
		model.put("categories", buildCategoriesList());

		return "cost/form";
	}

	/**
	 * Update a cost
	 * @param cost Cost to update
	 * @param result Validation result
	 * @param redirectAttributes Redirect attributes to send to redirect view
	 * @return View name
	 */
	@RequestMapping(value="/edit", method=RequestMethod.POST)
	@Transactional
	public String editCost(@ModelAttribute @Validated Cost cost,
						   BindingResult result,
						   RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {
			return "cost/form";
		} else {
			if (cost.isCost()) {
				//negate the amount if needed
				cost.setAmount(cost.getAmount().negate());
				cost.setCost(false);
			}

			//inject category by category id
			if (cost.getCategoryId() < 0) {
				cost.setCategory(null);
			} else {
				cost.setCategory(categoryDao.load(cost.getCategoryId()));
			}

			costDao.save(cost);
			redirectAttributes.addFlashAttribute("edited", cost.getCostId());
			return "redirect:/cost/list";
		}
	}

	/**
	 * Delete a cost.
	 * @param costId Id of the cost to delete
	 * @param redirectAttributes Use to add some data into the model after the redirect
	 * @return View name
	 */
	@RequestMapping("/del/{costId}")
	@Transactional
	public String del(@PathVariable int costId,  RedirectAttributes redirectAttributes) {
		Cost cost = costDao.get(costId);
		if (cost != null) {
			costDao.delete(cost);
			redirectAttributes.addFlashAttribute("deleted", true);
		}
		return "redirect:/cost/list";
	}

	/**
	 * Build a Id/Name map of the categories
	 * @return Map Id/Name of the categories with -1/"" at the top
	 */
	private Map<Integer, String> buildCategoriesList() {
		List<Category> categories = categoryDao.getList();
		Map<Integer, String> res = new LinkedHashMap<Integer, String>(categories.size());
		res.put(-1, "");

		for (Category category : categories) {
			res.put(category.getCategoryId(), category.getName());
		}
		return res;
	}

}
