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

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.alexlg.bankit.db.Cost;
import org.alexlg.bankit.db.Cost_;
import org.springframework.stereotype.Controller;

/**
 * Dao for costs operations.
 * 
 * @author Alexandre Thomazo
 */
@Controller
public class CostDao extends AbstractDao<Cost, Integer> {

	@Override
	public List<Cost> getList() {
		CriteriaBuilder b = getBuilder();
		
		//creating criteria
		CriteriaQuery<Cost> q = b.createQuery(Cost.class);
		Root<Cost> cost = q.from(Cost.class);
		q.select(cost);
		
		//ordering
		q.orderBy(b.asc(cost.get(Cost_.day)));
		
		return getEm().createQuery(q).getResultList();
	}
	
	/**
	 * Get all costs between startDay (excluded) and endDay (included)
	 * @param startDay Get operations from this day excluded
	 * @param endDay Get operations to this day included
	 * @return Costs list
	 */
	public List<Cost> getList(int startDay, int endDay) {
		CriteriaBuilder b = getBuilder();
		
		//creating criteria
		CriteriaQuery<Cost> q = b.createQuery(Cost.class);
		Root<Cost> cost = q.from(Cost.class);
		q.select(cost);
		
		//restricting
		q.where(b.and(
			b.greaterThan(cost.get(Cost_.day), startDay),
			b.lessThanOrEqualTo(cost.get(Cost_.day), endDay)
		));
		
		//ordering
		q.orderBy(b.asc(cost.get(Cost_.day)));
		
		return getEm().createQuery(q).getResultList();
	}
}
