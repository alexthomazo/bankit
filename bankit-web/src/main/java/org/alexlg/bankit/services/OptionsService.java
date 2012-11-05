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

import java.util.Date;

import org.alexlg.bankit.dao.OptionDao;
import org.alexlg.bankit.db.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service used to handles set or get operations on Option
 * 
 * @author Alexandre Thomazo
 */
@Controller
@Transactional(readOnly=true)
public class OptionsService {

	@Autowired
	private OptionDao optionDao;
	
	/**
	 * Returns the value of the String option.
	 * @param optionName Name of the option
	 * @return String value or null if option doesn't exists.
	 */
	public String getString(String optionName) {
		Option option = optionDao.get(optionName);
		if (option != null) return option.getStringVal();
		return null;
	}
	
	/**
	 * Returns the value of the Date option.
	 * @param optionName Name of the option
	 * @return Date value or null if option doesn't exists.
	 */
	public Date getDate(String optionName) {
		Option option = optionDao.get(optionName);
		if (option != null) return option.getDateVal();
		return null;
	}
	
	/**
	 * Returns the value of the Integer option.
	 * @param optionName Name of the option
	 * @return Integer value or null if option doesn't exists.
	 */
	public Integer getInteger(String optionName) {
		Option option = optionDao.get(optionName);
		if (option != null) return option.getIntVal();
		return null;
	}
	
	/**
	 * Set a value to the option. If the option does not exist,
	 * the option is created. If the value is null, the option is deleted.
	 * @param optionName Name of the option.
	 * @param value Value to set, null to delete the option.
	 */
	@Transactional(readOnly=false)
	public void set(String optionName, String value) {
		Option option = getCommonValue(optionName, value);
		if (option == null) return;
		option.setStringVal(value);
		optionDao.save(option);
	}

	/**
	 * Set a value to the option. If the option does not exist,
	 * the option is created. If the value is null, the option is deleted.
	 * @param optionName Name of the option.
	 * @param value Value to set, null to delete the option.
	 */
	@Transactional(readOnly=false)
	public void set(String optionName, Date value) {
		Option option = getCommonValue(optionName, value);
		if (option == null) return;
		option.setDateVal(value);
		optionDao.save(option);
	}

	/**
	 * Set a value to the option. If the option does not exist,
	 * the option is created. If the value is null, the option is deleted.
	 * @param optionName Name of the option.
	 * @param value Value to set, null to delete the option.
	 */
	@Transactional(readOnly=false)
	public void set(String optionName, Integer value) {
		Option option = getCommonValue(optionName, value);
		if (option == null) return;
		option.setIntVal(value);
		optionDao.save(option);
	}
	
	/**
	 * Retrieve an option from the database. If the value is null, 
	 * the option is deleted and null is returned.
	 * @param optionName Name of the option to get
	 * @param value If value is null and the option exists, the option is deleted
	 * @return The option or null if deleted
	 */
	protected Option getCommonValue(String optionName, Object value) {
		Option option = optionDao.get(optionName);
		if (option == null && value == null) return null;
		if (value == null) {
			optionDao.delete(option);
			return null;
		}
		if (option == null) {
			option = new Option();
			option.setName(optionName);
		}
		return option;
	}
}
