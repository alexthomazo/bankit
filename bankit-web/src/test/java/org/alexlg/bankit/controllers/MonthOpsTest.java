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

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Calendar;

import org.alexlg.bankit.db.Operation;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for MonthOps
 * 
 * @author Alexandre Thomazo
 */
public class MonthOpsTest {

	private MonthOps monthOps;
	
	@Before
	public void setUp() {
		LocalDate date = new LocalDate(2012, 8, 26);
		monthOps = new MonthOps(date, new BigDecimal("22.12"));
	}
	
	/** Test adding a operation with op date null => Exception */
	@Test(expected=IllegalArgumentException.class)
	public void testAddOpNullDate() throws Exception {
		Operation op = new Operation();
		op.setPlanned(new BigDecimal("12.24"));
		monthOps.addOp(op);
	}
	
	/** Test adding a operation with op date in bad month => Exception */
	@Test(expected=IllegalArgumentException.class)
	public void testAddOpBadMonth() throws Exception {
		Operation op = new Operation();
		op.setPlanned(new BigDecimal("12.24"));
		op.setOperationDate(new LocalDate(2012, 9, 26).toDate());
		monthOps.addOp(op);
	}
	
	/** Test adding a operation with no planned amount => Exception */
	@Test(expected=IllegalArgumentException.class)
	public void testAddOpPlannedNull() throws Exception {
		Operation op = new Operation();
		op.setOperationDate(new LocalDate(2012, 8, 27).toDate());
		monthOps.addOp(op);
	}
	
	/** Test adding of valid operation => OK */
	@Test
	public void testAddOpOk() throws Exception {
		Operation op = new Operation();
		op.setPlanned(new BigDecimal("12.24"));
		op.setOperationDate(new LocalDate(2012, 8, 28).toDate());
		monthOps.addOp(op);
		
		Operation op2 = new Operation();
		op2.setPlanned(new BigDecimal("-27.21"));
		op2.setOperationDate(new LocalDate(2012, 8, 29).toDate());
		monthOps.addOp(op2);
		
		assertEquals("Ops size", 2, monthOps.getOps().size());
		assertEquals("Amount", new BigDecimal("-14.97"), monthOps.getAmount());
		assertEquals("Balance", new BigDecimal("7.15"), monthOps.getBalance());
		//checking totals
		assertEquals("total", new BigDecimal("34.36"), op.getTotal());
		assertEquals("total", new BigDecimal("7.15"), op2.getTotal());
	}

	/** Checking if last day is correct */
	@Test
	public void testGetLastDay() throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.setTime(monthOps.getLastDay());
		assertEquals("year", 2012, cal.get(Calendar.YEAR));
		assertEquals("month", Calendar.AUGUST, cal.get(Calendar.MONTH));
		assertEquals("day", 31, cal.get(Calendar.DAY_OF_MONTH));
	}
}
