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

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import org.alexlg.bankit.db.Operation;
import org.joda.time.LocalDate;

/**
 * Container for all future operation on a month.
 * 
 * @author Alexandre THOMAZO
 */
public class MonthOps implements Comparable<MonthOps> {

	/** Last day of the month, used for display */
	private LocalDate lastDay;
	
	/** Operation set ordered by operation date and id */
	private Set<Operation> ops;
	
	/** Total amount of all operations */
	private BigDecimal amount;
	
	/** Balance for the month calculate */
	private BigDecimal balance;
	
	/** Keep the initial balance for totals calculation */
	private BigDecimal initialBalance;
	
	/** Do we have the totals to recalculate */
	private boolean dirtyTotals;
	
	/**
	 * Construct MonthOps with Calendar month number.
	 * Used for generate the lastDay of the month
	 * @param month One day in the month of any operation
	 * @param balance Current balance to add operation planned
	 */
	public MonthOps(LocalDate month, BigDecimal balance) {
		lastDay = month.dayOfMonth().withMaximumValue();
		ops = new TreeSet<Operation>();
		amount = new BigDecimal("0");
		this.balance = (balance != null ? balance : new BigDecimal("0"));
		this.initialBalance = this.balance;
	}
	
	/**
	 * Add an operation into the set.
	 * @param op Operation to add.
	 */
	public void addOp(Operation op) {
		//checking month
		if (op.getOperationDate() == null) throw new IllegalArgumentException("Operation date null");
		LocalDate opDate = new LocalDate(op.getOperationDate());
		if (opDate.getMonthOfYear() != lastDay.getMonthOfYear()) {
			throw new IllegalArgumentException("Invalid month in operation date");
		}
		if (op.getPlanned() == null) throw new IllegalArgumentException("Planned amount cannot be null");
		ops.add(op);
		amount = amount.add(op.getPlanned());
		balance = balance.add(op.getPlanned());
		dirtyTotals = true;
	}
	
	public Set<Operation> getOps() {
		if (dirtyTotals) calculateTotals();
		return ops;
	}
	
	public Date getLastDay() {
		return lastDay.toDate();
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	@Override
	public int compareTo(MonthOps o) {
		return lastDay.compareTo(o.lastDay);
	}
	
	/**
	 * Calculate the total of all the operations in the set.
	 */
	private void calculateTotals() {
		if (!dirtyTotals) return;
		dirtyTotals = false;
		
		BigDecimal total = this.initialBalance;
		for (Operation op : ops) {
			total = total.add(op.getPlanned());
			op.setTotal(total);
		}
	}
}
