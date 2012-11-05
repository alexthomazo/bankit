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
package org.alexlg.bankit.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.alexlg.bankit.dao.AbstractDaoTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Test class for OptionsService
 * 
 * @author Alexandre Thomazo
 */
public class OptionsServiceTest extends AbstractDaoTest {

	@Autowired
	private OptionsService optionsService;
	
	/** Retrieve an existing String option */
	@Test
	public void testGetStringExists() throws Exception {
		assertEquals("string exists", "test string", optionsService.getString("testString"));
	}
	
	/** Retrieve a nonexistent String option */
	@Test
	public void testGetStringNoExists() throws Exception {
		assertNull("string not exists", optionsService.getString("notExists"));
	}
	
	/** Set a string */
	@Test
	public void testSetString() throws Exception {
		optionsService.set("testSetString", "test");
		assertEquals("string exists", "test", optionsService.getString("testSetString"));
		optionsService.set("testSetString", (String) null);
		assertNull(optionsService.getString("testSetString"));
	}
	
	/** Retrieve an existing Date option */
	@Test
	public void testGetDateExists() throws Exception {
		Date date = optionsService.getDate("testDate");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		assertEquals("date exists", "22/12/1984", sdf.format(date));
	}
	
	/** Retrieve a nonexistent Date option */
	@Test
	public void testGetDateNoExists() throws Exception {
		assertNull("date not exists", optionsService.getDate("notExists"));
	}
	
	/** Set a date */
	@Test
	public void testSetDate() throws Exception {
		optionsService.set("testSetDate", new Date());
		assertNotNull("date exists", optionsService.getDate("testSetDate"));
		optionsService.set("testSetDate", (Date) null);
		assertNull(optionsService.getDate("testSetDate"));
	}
	
	/** Retrieve an existing Integer option */
	@Test
	public void testGetIntegerExists() throws Exception {
		assertEquals("integer exists", new Integer(22), optionsService.getInteger("testInteger"));
	}
	
	/** Retrieve a nonexistent String option */
	@Test
	public void testGetIntegerNoExists() throws Exception {
		assertNull("integer not exists", optionsService.getInteger("notExists"));
	}
	
	/** Set a string */
	@Test
	public void testSetInteger() throws Exception {
		optionsService.set("testSetInteger", new Integer(22));
		assertEquals("string exists", new Integer(22), optionsService.getInteger("testSetInteger"));
		optionsService.set("testSetInteger", (Integer) null);
		assertNull(optionsService.getInteger("testSetInteger"));
	}
}
