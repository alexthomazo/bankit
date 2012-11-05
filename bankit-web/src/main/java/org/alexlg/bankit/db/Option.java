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

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * The option can store a String, an int or a date.
 * 
 * @author Alexandre Thomazo
 */
@Entity
@Table(name="options")
public class Option {

	private int optionId;
	private String name;
	private String stringVal;
	private Date dateVal;
	private Integer intVal;
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="option_id")
	public int getOptionId() {
		return optionId;
	}
	
	@NotNull
	public String getName() {
		return name;
	}
	
	@Column(name="string_val")
	public String getStringVal() {
		return stringVal;
	}
	
	@Column(name="date_val")
	public Date getDateVal() {
		return dateVal;
	}
	
	@Column(name="int_val")
	public Integer getIntVal() {
		return intVal;
	}
	
	
	public void setOptionId(int optionId) {
		this.optionId = optionId;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setStringVal(String stringVal) {
		this.stringVal = stringVal;
	}
	public void setDateVal(Date dateVal) {
		this.dateVal = dateVal;
	}
	public void setIntVal(Integer intVal) {
		this.intVal = intVal;
	}
	
}
