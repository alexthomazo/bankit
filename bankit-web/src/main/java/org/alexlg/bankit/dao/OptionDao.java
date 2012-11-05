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

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.alexlg.bankit.db.Option;
import org.alexlg.bankit.db.Option_;
import org.springframework.stereotype.Controller;

/**
 * DAO used to store and retrieve options.
 * 
 * @author Alexandre Thomazo
 */
@Controller
public class OptionDao extends AbstractDao<Option, Integer> {

	/**
	 * Get an option by this name.
	 * @param name Name of the option
	 * @return Option or null if not exists.
	 */
	public Option get(String name) {
		CriteriaBuilder b = getBuilder();
		
		//creating criteria
		CriteriaQuery<Option> q = b.createQuery(Option.class);
		Root<Option> option = q.from(Option.class);
		q.select(option);
		
		//selecting
		q.where(b.equal(option.get(Option_.name), name));
		
		try {
			return getEm().createQuery(q).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
}
