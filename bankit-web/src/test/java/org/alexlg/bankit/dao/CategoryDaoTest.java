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
		YearMonth yearMonth = new YearMonth(2012, 8);
		
		Map<Category, BigDecimal> categories = categoryDao.getMonthSummary(yearMonth);
		
		assertEquals(2, categories.size());

		int i = 0;
		for (Map.Entry<Category, BigDecimal> entry : categories.entrySet()) {
			Category category = entry.getKey();
			
			//testing amount and order
			if (category.getName().equals("Alimentation")) {
				assertEquals("Alimentation amount", new BigDecimal("-140.39"), entry.getValue());
				assertEquals("Alimentation order", 0, i);

			} else if (category.getName().equals("Communications")) {
				assertEquals("Communication amount", new BigDecimal("-49.98"), entry.getValue());
				assertEquals("Communcation order", 1, i);
			}
			i++;
		}
	}
	
}
