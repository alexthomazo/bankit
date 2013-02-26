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
package org.alexlg.bankit.db;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * A category contains several operations of the same type.
 *
 * @author Alexandre Thomazo
 */
@Entity
@Table(name="categories")
public class Category {

	/** Id of the category */
	private int categoryId;
	/** Name of the category */
	private String name;

	//FOREIGN COLLECTIONS
	private Collection<Operation> operations = new HashSet<Operation>();

	@Id	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="category_id")
	public int getCategoryId() {
		return categoryId;
	}

	@NotNull
	@NotBlank
	public String getName() {
		return name;
	}

	@OneToMany(mappedBy = "category", orphanRemoval = true)
	public Collection<Operation> getOperations() {
		return operations;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOperations(Collection<Operation> operations) {
		this.operations = operations;
	}
}
