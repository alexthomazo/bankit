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
package org.alexlg.bankit.db;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import org.alexlg.bankit.validgroup.AddPlannedOp;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Bank operation on the account, past or future.
 * The past operation can't be edited if the amount is set
 * because it's be imported from the bank website.
 * 
 * @author Alexandre Thomazo
 */
@Entity
@Table(name="operations")
public class Operation implements Comparable<Operation> {

	private int operationId;
	private Date valueDate;
	private Date operationDate;
	private String label;
	private BigDecimal planned;
	private BigDecimal amount;

	//-- FOREIGN KEYS
	/** Category attached to this operation */
	private Category category;

	//-- TRANSIENT / NOT PERSISTENT
	/** Is this operation is a future planned added automatically */
	private boolean auto;
	/** Is this operation is a debit (amount or planned) */
	private boolean debit;
	/** balance of the account added to the operation amount */
	private BigDecimal total;
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="operation_id")
	public int getOperationId() {
		return operationId;
	}
	
	@Column(name="value_date")
	public Date getValueDate() {
		return valueDate;
	}
	
	@NotNull
	@Future(groups = { AddPlannedOp.class })
	@Column(name="operation_date")
	public Date getOperationDate() {
		return operationDate;
	}
	
	@NotNull
	@NotBlank
	public String getLabel() {
		return label;
	}
	
	public BigDecimal getPlanned() {
		return planned;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}

	@ManyToOne
	@JoinColumn(name="category_id")
	public Category getCategory() {
		return category;
	}

	@Transient
	public boolean isAuto() {
		return auto;
	}

	@Transient
	public boolean isDebit() {
		return debit;
	}
	
	@Transient
	public BigDecimal getTotal() {
		return total;
	}

	public void setOperationId(int operationId) {
		this.operationId = operationId;
	}
	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}
	public void setOperationDate(Date operationDate) {
		this.operationDate = operationDate;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public void setPlanned(BigDecimal planned) {
		this.planned = planned;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public void setAuto(boolean auto) {
		this.auto = auto;
	}
	public void setDebit(boolean debit) {
		this.debit = debit;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	
	@Override
	public String toString() {
		return "Operation [operationId=" + operationId + ", operationDate="
				+ operationDate + ", label=" + label + ", planned=" + planned
				+ ", amount=" + amount + "]";
	}

	@Override
	public int compareTo(Operation o) {
		//compare on date, then on id and finally on label
		int comp = operationDate == null ? -1 : operationDate.compareTo(o.operationDate);
		if (comp != 0) return comp;
		comp = operationId - o.operationId;
		if (comp != 0) return comp;
		return label == null ? -1 : label.compareTo(o.label);
	}
	
	
}
