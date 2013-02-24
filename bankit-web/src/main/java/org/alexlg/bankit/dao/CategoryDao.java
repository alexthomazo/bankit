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
package org.alexlg.bankit.dao;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import org.alexlg.bankit.db.Category;
import org.alexlg.bankit.db.Category_;
import org.alexlg.bankit.db.Operation;
import org.alexlg.bankit.db.Operation_;
import org.joda.time.LocalDate;
import org.joda.time.YearMonth;
import org.springframework.stereotype.Controller;

/**
 * DAO for categories
 * 
 * @author Alexandre Thomazo
 */
@Controller
public class CategoryDao extends AbstractDao<Category, Integer> {

	/**
	 * Calculate the amount of operations for all categories on a specific month
	 * @param yearMonth Year and month of the summary to calculate
	 * @return Map containing the Category and the amount for the month
	 */
	public Map<Category, BigDecimal> getMonthSummary(YearMonth yearMonth) {
		CriteriaBuilder b = getBuilder();
		
		//create criteria and join
		CriteriaQuery<Tuple> q = b.createTupleQuery();
		Root<Operation> operation = q.from(Operation.class);
		Join<Operation, Category> category = operation.join(Operation_.category);
		
		//select
		Expression<BigDecimal> sum = b.sum(operation.get(Operation_.amount));
		q.select(b.tuple(category, sum));
		
		//groupby
		q.groupBy(category.get(Category_.categoryId));
		
		//where clause
		LocalDate startDate = yearMonth.toLocalDate(1);
		LocalDate endDate = startDate.plusMonths(1);
		q.where(b.between(operation.get(Operation_.operationDate), startDate.toDate(), endDate.toDate()));
		
		//execute query
		List<Tuple> results = getEm().createQuery(q).getResultList();
		
		//put in map
		Map<Category, BigDecimal> resMap = new LinkedHashMap<Category, BigDecimal>(results.size());
		for (Tuple res : results) {
			BigDecimal sumVal = res.get(sum);
			if (!sumVal.equals(BigDecimal.ZERO)) {
				resMap.put(res.get(category), sumVal);
			}
		}
		
		return resMap;
	}
}
