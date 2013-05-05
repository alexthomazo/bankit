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

import org.alexlg.bankit.db.Operation;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Operation Dao test class
 * 
 * @author Alexandre Thomazo
 */
public class OperationDaoTest extends AbstractDaoTest {

	@Autowired
	OperationDao operationDao;
	
	/** Testing retrieval operation the 25th of the month  */
	@Test
	public void testGetHistoryEndMonth() throws Exception {
		LocalDate startDay = new LocalDate(2012, 8, 1);
		LocalDate endDay = new LocalDate(2012, 8, 24);
		
		List<Operation> ops = operationDao.getHistory(startDay, endDay);
		
		assertEquals("nb operation", 9, ops.size());
		//checking order => the old planned operation must be present
		int o = 0;
		assertEquals("order", 14, ops.get(o++).getOperationId());
		assertEquals("order", 11, ops.get(o++).getOperationId());
		assertEquals("order", 4,  ops.get(o++).getOperationId());
		assertEquals("order", 3,  ops.get(o++).getOperationId());
		assertEquals("order", 5,  ops.get(o++).getOperationId());
		assertEquals("order", 6,  ops.get(o++).getOperationId());
		assertEquals("order", 7,  ops.get(o++).getOperationId());
		assertEquals("order", 8,  ops.get(o++).getOperationId());
		assertEquals("order", 10, ops.get(o++).getOperationId());
	}

	/**
	 * Testing retrieval operation the 5th day of month
	 * to test previous month history
	 */
	@Test
	public void testGetHistoryStartMonth() throws Exception {
		LocalDate startDay = new LocalDate(2012, 7, 1);
		LocalDate endDay = new LocalDate(2012, 8, 5);
		
		List<Operation> ops = operationDao.getHistory(startDay, endDay);
		
		assertEquals("nb operation", 4, ops.size());
		//checking order => the old planned operation must be present
		int o = 0;
		assertEquals("order", 14, ops.get(o++).getOperationId());
		assertEquals("order", 1,  ops.get(o++).getOperationId());
		assertEquals("order", 2,  ops.get(o++).getOperationId());
		assertEquals("order", 11, ops.get(o++).getOperationId());
	}
	
	/** Test the sum of all previous history operations after the 7th of the month */
	@Test
	public void testGetBalanceHistoryEndMonth() throws Exception {
		LocalDate day = new LocalDate(2012, 8, 1);
		
		BigDecimal balance = operationDao.getBalanceHistory(day);
		assertEquals("histo balance", new BigDecimal("3747.32"), balance);
	}
	
	/** Test the sum of all previous history operations before the 7th of the month */
	@Test
	public void testGetBalanceHistoryStartMonth() throws Exception {
		LocalDate day = new LocalDate(2012, 7, 1);
		
		BigDecimal balance = operationDao.getBalanceHistory(day);
		assertEquals("histo balance", new BigDecimal("2117.25"), balance);
	}
	
	/** Testing retrieval future operation */
	@Test
	public void testGetFuture() throws Exception {
		LocalDate day = new LocalDate(2012, 8, 26);
		
		List<Operation> ops = operationDao.getFuture(day);
		
		assertEquals("nb operation", 2, ops.size());
		//checking order
		assertEquals("order", 12, ops.get(0).getOperationId());
		assertEquals("order", 9, ops.get(1).getOperationId());
	}
	
	/** Test retrieval of old ops */
	@Test
	public void testGetOldPlannedOps() throws Exception {
		LocalDate day = new LocalDate(2012, 8, 24);
		
		List<Operation> ops = operationDao.getOldPlannedOps(day);
		
		assertEquals("nb operation", 2, ops.size());
		
		Operation op = ops.remove(0);
		assertTrue("op id", op.getOperationId() == 14 || op.getOperationId() == 10);
		op = ops.remove(0);
		assertTrue("op id", op.getOperationId() == 14 || op.getOperationId() == 10);
	}
	
	/** Test the matching between planned op and real op */
	@Test
	public void testMatchRealOp() throws Exception {
		Calendar day = Calendar.getInstance();
		day.clear();
		Operation planOp = operationDao.get(14);
		Operation realOp = operationDao.matchRealOp(planOp);
		
		assertNull("real op matches with something", realOp);
		
		//adding an operation matching on amount
		Operation matchedOp = new Operation();
		matchedOp.setAmount(new BigDecimal("-200"));
		day.set(2012, Calendar.JUNE, 12);
		matchedOp.setOperationDate(day.getTime());
		matchedOp.setLabel("Test Planned Op");
		operationDao.insert(matchedOp);
		
		//retest the matching
		realOp = operationDao.matchRealOp(planOp);
		assertEquals("matched not the good operation", matchedOp, realOp);
		
		//testing with a label matching (on op 12)
		matchedOp = new Operation();
		matchedOp.setAmount(new BigDecimal("-72.24"));
		day.set(2012, Calendar.AUGUST, 29);
		matchedOp.setOperationDate(day.getTime());
		matchedOp.setLabel("Impots Revenu XYZ-AAA");
		operationDao.insert(matchedOp);
		
		//test the matching
		planOp = operationDao.get(12);
		realOp = operationDao.matchRealOp(planOp);
		assertEquals("matched not the good operation", matchedOp, realOp);
	}
}
