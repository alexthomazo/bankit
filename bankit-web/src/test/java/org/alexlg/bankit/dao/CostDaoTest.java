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
package org.alexlg.bankit.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.alexlg.bankit.db.Cost;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Cost Dao test class.
 * 
 * @author Alexandre Thomazo
 */
public class CostDaoTest extends AbstractDaoTest {

	@Autowired
	private CostDao costDao;
	
	@Test
	public void testGetList() throws Exception {
		List<Cost> costs = costDao.getList();
		
		assertEquals("size", 4, costs.size());
		int o = 0;
		assertEquals("order", 2, costs.get(o++).getCostId());
		assertEquals("order", 3, costs.get(o++).getCostId());
		assertEquals("order", 4, costs.get(o++).getCostId());
		assertEquals("order", 1, costs.get(o++).getCostId());
	}
	
}
