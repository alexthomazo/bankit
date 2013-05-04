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

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * A cost is a recurring operation on credit or debt.
 * It will be generated to a planned operation each month.
 * 
 * @author Alexandre Thomazo
 */
@Entity
@Table(name="costs")
public class Cost {

	private int costId;
	/** Day in month of the cost is planned */
	private int day;
	/** Label displayed in dashboard until sync */
	private String label;
	/** Amount planned for this cost */
	private BigDecimal amount;

	//-- FOREIGN KEYS
	/** Category attached to this operation */
	private Category category;
	
	//-- TRANSIENT / NOT PERSISTENT
	/** Is this operation is a cost or an income */
	private boolean cost;
	/** Id of the category, used for form selection */
	private int categoryId;
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="cost_id")
	public int getCostId() {
		return costId;
	}
	
	@NotNull
	@Min(1) @Max(31)
	public int getDay() {
		return day;
	}
	
	@NotNull
	@NotBlank
	public String getLabel() {
		return label;
	}
	
	@NotNull
	public BigDecimal getAmount() {
		return amount;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="category_id")
	public Category getCategory() {
		return category;
	}

	@Transient
	public boolean isCost() {
		return cost;
	}

	@Transient
	public int getCategoryId() {
		if (category != null) categoryId = category.getCategoryId();
		return categoryId;
	}

	public void setCostId(int costId) {
		this.costId = costId;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public void setCost(boolean cost) {
		this.cost = cost;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	@Override
	public String toString() {
		return "Cost [costId=" + costId + ", day=" + day + ", label=" + label
				+ ", amount=" + amount + "]";
	}
}
