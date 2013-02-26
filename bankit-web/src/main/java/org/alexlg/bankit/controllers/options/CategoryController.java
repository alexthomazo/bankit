/*
 * Copyright (C) 2013 Alexandre Thomazo
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

import org.alexlg.bankit.dao.CategoryDao;
import org.alexlg.bankit.db.Category;
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

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Handle calls for category managing.
 *
 * @author Alexandre Thomazo
 */
@Controller
@RequestMapping("/options/category")
public class CategoryController {

	@Autowired
	private CategoryDao categoryDao;

	/**
	 * List all categories.
	 * @param model Model
	 * @return View name
	 */
	@RequestMapping(value="", method= RequestMethod.GET)
	@Transactional(readOnly=true)
	public String list(ModelMap model) {

		List<Category> categories = categoryDao.getList();

		model.put("page", "category");
		model.put("categories", categories);
		return "options/categories";
	}

	/**
	 * Show form to add a category
	 * @param model Model to fill with a new Category
	 * @return View name
	 */
	@RequestMapping(value="/add", method=RequestMethod.GET)
	public String showAddCategoryForm(ModelMap model) {
		model.put("page", "category");
		model.put("category", new Category());
		return "options/category-form";
	}

	/**
	 * Add a category into the database
	 * @param category Category to add
	 * @param result Result of the validation
	 * @param redirectAttributes Redirect attributes to send to redirect view
	 * @return View name
	 */
	@RequestMapping(value="/add", method=RequestMethod.POST)
	@Transactional
	public String addCategory(@ModelAttribute @Validated Category category,
			BindingResult result,
			RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {
			return "options/category-form";
		} else {
			categoryDao.insert(category);
			redirectAttributes.addFlashAttribute("added", category.getCategoryId());
			return "redirect:/options/category";
		}
	}

	/**
	 * Show the edit category form
	 * @param catId Id of the category to edit
	 * @param model Model to fill with category to edit
	 * @return View name
	 * @throws FileNotFoundException If the category id doesn't exists
	 */
	@RequestMapping(value = "/edit/{catId}", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public String showEditCategory(@PathVariable int catId, ModelMap model) throws FileNotFoundException {
		Category category = categoryDao.get(catId);
		if (category == null) {
			throw new FileNotFoundException("Category [" + catId + "] not found");
		}

		model.put("page", "category");
		model.put("category", category);
		return "options/category-form";
	}

	/**
	 * Update a category
	 * @param category Category to edit
	 * @param result Validation result
	 * @param redirectAttributes Redirect attributes to send to redirect view
	 * @return View name
	 */
	@RequestMapping(value="/edit", method=RequestMethod.POST)
	@Transactional
	public String editCategory(@ModelAttribute @Validated Category category,
							  BindingResult result,
							  RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {
			return "options/category-form";
		} else {
			categoryDao.save(category);
			redirectAttributes.addFlashAttribute("edited", category.getCategoryId());
			return "redirect:/options/category";
		}
	}

	/**
	 * Delete a category
	 * @param catId Id of the category to delete
	 * @param redirectAttributes Redirect attributes to send to redirect view
	 * @return View name
	 * @throws FileNotFoundException If the category to delete doesn't exist
	 */
	@RequestMapping(value = "/del/{catId}", method = RequestMethod.GET)
	@Transactional
	public String deleteCategory(@PathVariable int catId, RedirectAttributes redirectAttributes) throws FileNotFoundException {
		Category category = categoryDao.get(catId);
		if (category == null) {
			throw new FileNotFoundException("Category [" + catId + "] not found");
		}

		categoryDao.delete(category);

		redirectAttributes.addFlashAttribute("deleted", true);
		return "redirect:/options/category";
	}
}
