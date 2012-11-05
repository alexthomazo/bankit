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

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.alexlg.bankit.db.Operation;
import org.alexlg.bankit.services.SyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handles all request for API calls
 * 
 * @author Alexandre Thomazo
 */
@Controller
@RequestMapping("/api")
public class ApiController {

	@Autowired
	private SyncService syncService;
	
	/**
	 * Sync a list of operation from the the bank with the account.
	 * @param operations List of operations to sync.
	 * @return Number of operations imported {nbOp: x}
	 */
	@RequestMapping(value="/sync", method=RequestMethod.POST, 
			consumes="application/json", produces="application/json")
	@Transactional
	@ResponseBody
	public Map<String, String> sync(@RequestBody OpList operations) {
		int nbOp = 0;
		if (operations != null) {
			syncService.syncOpList(operations);
			syncService.mergeOldPlannedOps();
			
			for (Operation op : operations) {
				if (op.getOperationId() != 0) nbOp++;
			}
		}
		
		Map<String, String> res = new HashMap<String, String>(1);
		res.put("nbOp", Integer.toString(nbOp));
		return res;
	}

	/**
	 * Handles exception which happens in controller
	 * @param e Exception raised
	 * @return View name
	 */
	@ExceptionHandler(Exception.class)
	public @ResponseBody Map<String, String> handleException(Exception e) {
		Map<String, String> res = new HashMap<String, String>(2);
		
		res.put("errorName", e.getMessage());
		
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		
		res.put("errorTrace", sw.toString());
		
		return res;
	}
	
	static class OpList extends LinkedList<Operation> {
		private static final long serialVersionUID = 1186461795395048834L;
	}
}
