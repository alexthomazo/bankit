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
package org.alexlg.bankit.dao;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Map;

import org.alexlg.bankit.db.Category;
import org.joda.time.YearMonth;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * {@link CategoryDao} test class
 * 
 * @author Alexandre Thomazo
 */
public class CategoryDaoTest extends AbstractDaoTest {

	@Autowired
	private CategoryDao categoryDao;

	/** Test if each category has the correct amount */
	@Test
	public void testGetMonthSummary() throws Exception {
		//testing a first month
		YearMonth yearMonth = new YearMonth(2012, 7);
		Map<Category, BigDecimal> categories = categoryDao.getMonthSummary(yearMonth);

		assertEquals(1, categories.size());

		for (Map.Entry<Category, BigDecimal> entry : categories.entrySet()) {
			Category category = entry.getKey();

			//testing amount
			assertEquals("Carburant name", "Carburant", category.getName());
			assertEquals("Carburant amount", new BigDecimal("-73.07"), entry.getValue());
		}

		//testing next month
		yearMonth = new YearMonth(2012, 8);
		categories = categoryDao.getMonthSummary(yearMonth);

		assertEquals(3, categories.size());

		int i = 0;
		for (Map.Entry<Category, BigDecimal> entry : categories.entrySet()) {
			Category category = entry.getKey();
			
			//testing amount and order
			if (category.getName().equals("Alimentation")) {
				assertEquals("Alimentation amount", new BigDecimal("-140.39"), entry.getValue());
				assertEquals("Alimentation order", 0, i);
				i++;

			} else if (category.getName().equals("Communications")) {
				assertEquals("Communication amount", new BigDecimal("-49.98"), entry.getValue());
				assertEquals("Communcation order", 1, i);
				i++;

			} else if (category.getName().equals("Divers")) {
				assertEquals("Divers amount", new BigDecimal("-600.00"), entry.getValue());
				assertEquals("Divers order", 2, i);
				i++;
			}
		}

		//check if the "i" has been incremented by all if in the loop
		//and so every category has been tested
		assertEquals("Not checked all categories", 3, i);
	}
	
}
