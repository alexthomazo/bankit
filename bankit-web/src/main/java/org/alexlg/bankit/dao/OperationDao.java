/*
 * Copyright (C) 2012-2013 Alexandre Thomazo
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
import org.alexlg.bankit.db.Operation_;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Controller;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

/**
 * Dao for account operations.
 * 
 * @author Alexandre Thomazo
 */
@Controller
public class OperationDao extends AbstractDao<Operation, Integer> {
	
	/**
	 * Get operations from startDay to endDay.
	 * 
	 * @param startDay Day from which retrieve operations.
	 * @param endDay Day to which retrieve operations
	 * @return Operation list history
	 * 		sorted by operation date and id.
	 */
	public List<Operation> getHistory(LocalDate startDay, LocalDate endDay) {
		CriteriaBuilder b = getBuilder();
		
		//creating criteria
		CriteriaQuery<Operation> q = b.createQuery(Operation.class);
		Root<Operation> op = q.from(Operation.class);
		q.select(op);

		//adding restriction
		// - every operation between the start and end date
		// - every planned operation not sync before start date
		q.where(
			b.or(
				b.between(op.get(Operation_.operationDate), startDay.toDate(), endDay.toDate()),
				b.and(
					b.lessThan(op.get(Operation_.operationDate), startDay.toDate()),
					b.isNull(op.get(Operation_.amount))
				)
			)
		);
		
		//ordering
		q.orderBy(
			b.asc(op.get(Operation_.operationDate)),
			b.asc(op.get(Operation_.operationId))
		);
		
		return getEm().createQuery(q).getResultList();
	}

	/**
	 * Get balance of the account for previous operations.
	 * @param day Get the balance for operation before this day
	 *            to the opening of the account
	 * @return Balance of the account
	 */
	public BigDecimal getBalanceHistory(LocalDate day) {
		CriteriaBuilder b = getBuilder();
		
		//creating criteria
		CriteriaQuery<BigDecimal> q = b.createQuery(BigDecimal.class);
		Root<Operation> op = q.from(Operation.class);
		q.select(b.sum(op.get(Operation_.amount)));
		
		//adding restriction
		q.where(b.lessThan(op.get(Operation_.operationDate), day.toDate()));
		
		return getEm().createQuery(q).getSingleResult();
	}
	
	/**
	 * Get all future planned operation beyond a day.
	 * @param day Day from which get future operation
	 * @return Future operation ordered by operation date and id
	 */
	public List<Operation> getFuture(LocalDate day) {
		CriteriaBuilder b = getBuilder();
		
		//creating criteria
		CriteriaQuery<Operation> q = b.createQuery(Operation.class);
		Root<Operation> op = q.from(Operation.class);
		q.select(op);

		//adding restriction
		q.where(b.greaterThan(op.get(Operation_.operationDate), day.toDate()));
		
		//ordering
		q.orderBy(
			b.asc(op.get(Operation_.operationDate)),
			b.asc(op.get(Operation_.operationId))
		);
		
		return getEm().createQuery(q).getResultList();
	}

	/**
	 * Retrieve all planned operations with no amount before a date (included).
	 * @param day Date from which retrieve the old operations.
	 * @return List of old operations
	 */
	public List<Operation> getOldPlannedOps(LocalDate day) {
		CriteriaBuilder b = getBuilder();
		
		//creating criteria
		CriteriaQuery<Operation> q = b.createQuery(Operation.class);
		Root<Operation> op = q.from(Operation.class);
		q.select(op);

		//adding restriction
		q.where(b.and(
			b.isNull(op.get(Operation_.amount)),	
			b.lessThanOrEqualTo(op.get(Operation_.operationDate), day.toDate())
		));
		
		return getEm().createQuery(q).getResultList();
	}
	
	/**
	 * Take a planned operation and check if a real operation exists
	 * for this planned operation.
	 * An operation matches if it's between 2 days before or after the planned date,
	 * the planned amount matches exactly or the planned label is contained in the
	 * real operation.
	 * @param plannedOp Planned operation to check
	 * @return The operation that match or null if any operation matching is found.
	 */
	public Operation matchRealOp(Operation plannedOp) {
		CriteriaBuilder b = getBuilder();
		
		//creating criteria
		CriteriaQuery<Operation> q = b.createQuery(Operation.class);
		Root<Operation> op = q.from(Operation.class);
		q.select(op);
		
		//start and end date
		Calendar startDate = Calendar.getInstance();
		startDate.setTime(plannedOp.getOperationDate());
		startDate.set(startDate.get(Calendar.YEAR), startDate.get(Calendar.MONTH),
				startDate.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		startDate.add(Calendar.DAY_OF_MONTH, -2);
		
		Calendar endDate = Calendar.getInstance();
		endDate.setTime(plannedOp.getOperationDate());
		endDate.set(endDate.get(Calendar.YEAR), endDate.get(Calendar.MONTH),
				endDate.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		endDate.add(Calendar.DAY_OF_MONTH, 2);
		
		//adding restriction
		q.where(b.and(
			b.between(op.get(Operation_.operationDate), startDate.getTime(), endDate.getTime()),
			b.isNull(op.get(Operation_.planned)),
			b.or(
				b.equal(op.get(Operation_.amount), plannedOp.getPlanned()),
				b.like(op.get(Operation_.label), "%" + plannedOp.getLabel() + "%")
			)
		));
		
		List<Operation> ops = getEm().createQuery(q).getResultList();
		if (ops.size() > 0) {
			return ops.get(0);
		} else {
			return null;
		}
	}

}
