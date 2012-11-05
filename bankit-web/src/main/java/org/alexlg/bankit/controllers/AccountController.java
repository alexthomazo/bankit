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

import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import javax.validation.groups.Default;

import org.alexlg.bankit.dao.CostDao;
import org.alexlg.bankit.dao.OperationDao;
import org.alexlg.bankit.db.Cost;
import org.alexlg.bankit.db.Operation;
import org.alexlg.bankit.services.OptionsService;
import org.alexlg.bankit.services.SyncService;
import org.alexlg.bankit.validgroup.AddPlannedOp;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller which handles all operations for
 * displaying or editing account operations.
 * 
 * @author Alexandre Thomazo
 */
@Controller
@RequestMapping("/account")
public class AccountController {

	/** Number of future month to display */
	public static final int NB_FUTURE_MONTH = 1;
	
	@Autowired
	private OperationDao operationDao;
	
	@Autowired
	private CostDao costDao;
	
	@Autowired
	private SyncService syncService;
	
	@Autowired
	private OptionsService optionsService;
	
	@RequestMapping("/")
	public String index() {
		return "redirect:/account/list";
	}
	
	/**
	 * Initialize binder to handle specific type.
	 * @param binder Binder to initialize.
	 */
	@InitBinder
	public void binder(WebDataBinder binder) {
		final SimpleDateFormat dateFormatFull = new SimpleDateFormat("dd/MM/yyyy");
		final SimpleDateFormat dateFormatShort = new SimpleDateFormat("dd/MM");
		
		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
			@Override
			public String getAsText() {
				if (getValue() == null) return "";
				return dateFormatFull.format((Date) getValue());
			}

			@Override
			public void setAsText(String text) throws IllegalArgumentException {
				//short format
				try {
					Date date = dateFormatShort.parse(text);
					//setting current year
					LocalDate lDate = new LocalDate(date).withYear(new LocalDate().getYear());
					setValue(lDate.toDate());
					return;
				} catch (ParseException e) {
					setValue(null);
				}
				//full format
				try {
					setValue(dateFormatFull.parse(text));
					return;
				} catch (ParseException e) {
					setValue(null);
				}
			}
		});
	}
	
	/**
	 * Display operations list for history and future.
	 * @param model Model to fill with operations list
	 * @return view name
	 */
	@RequestMapping("/list")
	@Transactional(readOnly=true)
	public String list(ModelMap model) {
		Calendar calDay = Calendar.getInstance();
		LocalDate day = new LocalDate(calDay);

		//getting history operations
		List<Operation> ops = operationDao.getHistory(calDay);
		
		//calculating balance for history
		//current balance
		BigDecimal current = operationDao.getBalanceHistory(calDay);
		//difference between planned and real
		BigDecimal currentDiff = new BigDecimal("0");
		//balance for planned op but not debited
		BigDecimal plannedWaiting = new BigDecimal("0");
		
		//checking if a balance exists or init the account
		if (current == null && ops.size() == 0) {
			return "redirect:/account/init";
		}
		
		if (current == null) current = new BigDecimal("0");
		BigDecimal initialBalance = current;
		
		//calculating total for old operations
		for (Operation op : ops) {
			BigDecimal amount = op.getAmount();
			BigDecimal planned = op.getPlanned();
			
			if (amount != null) {
				//operation done
				current = current.add(amount);
				op.setTotal(current);
				if (planned != null) {
					currentDiff = currentDiff.add(amount).subtract(planned);
				}
			}
		}
		
		//calculating total for planned undebit operations
		for (Operation op : ops) {
			if (op.getAmount() == null) {
				plannedWaiting = plannedWaiting.add(op.getPlanned());
				op.setTotal(current.add(plannedWaiting));
			}
		}
		
		//getting future operations
		Set<MonthOps> futureOps = buildFutureOps(day, 
				operationDao.getFuture(calDay), costDao.getList(),
				current.add(plannedWaiting), NB_FUTURE_MONTH);
		
		model.put("curDate", calDay.getTime());
		model.put("ops", ops);
		model.put("current", current);
		model.put("currentDiff", currentDiff);
		model.put("periodBalance", current.subtract(initialBalance));
		model.put("plannedWaiting", plannedWaiting);
		model.put("currentWaiting", current.add(plannedWaiting));
		model.put("futureOps", futureOps);
		model.put("lastSyncDate", optionsService.getDate(SyncService.OP_SYNC_OPT));
		
		return "account/list";
	}
	
	/**
	 * Show adding operation form.
	 * @param model Model to fill with current operation object
	 * @return View
	 */
	@RequestMapping(value="/add", method=RequestMethod.GET)
	public String showAddOperationForm(ModelMap model) {
		Operation op = new Operation();
		//setting default values
		op.setDebit(true);
		model.addAttribute("operation", op);
		return "account/add";
	}
	
	/**
	 * Add an future planned operation
	 * @param op Operation to add
	 * @param result Result of the binding between the POST and the object
	 * @param redirectAttributes Use to add some data into the model after the redirect
	 * @return View name
	 */
	@RequestMapping(value="/add", method=RequestMethod.POST)
	@Transactional
	public String addOperation(@ModelAttribute @Validated({ Default.class, AddPlannedOp.class }) Operation op, 
			BindingResult result,
			RedirectAttributes redirectAttributes) {
		
		if (op.getPlanned() == null) {
			result.rejectValue("planned", "javax.validation.constraints.NotNull.message");
		}
		
		if (result.hasErrors()) {
			return "account/add";
		} else {
			if (op.isDebit()) {
				//negate the planned amount
				op.setPlanned(op.getPlanned().negate());
				op.setDebit(false);
			}
			operationDao.insert(op);
			redirectAttributes.addFlashAttribute("added", op.getOperationId());
			return "redirect:/account/list";
		}
	}
	
	/**
	 * Delete an operation
	 * @param opId Id of the operation to delete
	 * @param redirectAttributes Use to add some data into the model after the redirect
	 * @return View name
	 */
	@RequestMapping("/del/{opId}")
	@Transactional
	public String delOperation(@PathVariable int opId, RedirectAttributes redirectAttributes) {
		Operation op = operationDao.get(opId);
		if (op != null) {
			operationDao.delete(op);
			redirectAttributes.addFlashAttribute("deleted", true);
		}
		return "redirect:/account/list";
	}
	
	/**
	 * "Unmerge" an operation by deleting the planned
	 * amount of the operation.
	 * @param opId Id of the operation which delete the planned amount
	 * @return View name
	 */
	@RequestMapping("/unmerge/{opId}")
	@Transactional
	public String unmerge(@PathVariable int opId) {
		Operation op = operationDao.get(opId);
		if (op != null) {
			op.setPlanned(null);
			operationDao.save(op);
		}
		return "redirect:/account/list";
	}
	
	/**
	 * Sync a file for the bank with the account.
	 * @param file File to sync.
	 * @return View name
	 * @throws IOException If an error occurs when reading the bank file
	 */
	@RequestMapping(value="/sync", method=RequestMethod.POST)
	@Transactional(rollbackFor=IOException.class)
	public String sync(@RequestParam("file") MultipartFile file) throws IOException {
		if (!file.isEmpty()) {
			syncService.readQifAndInsertOp(file.getInputStream());
			syncService.mergeOldPlannedOps();
		}
		
		return "redirect:/account/list";
	}
	
	/**
	 * Init account by adding initial amount operation
	 * @param model Model to fill with current operation object
	 * @return View
	 */
	@RequestMapping(value="/init", method=RequestMethod.GET)
	public String initAccount(ModelMap model) {
		//creating the init operation, one month before
		Operation op = new Operation();
		LocalDate date = new LocalDate();
		op.setOperationDate(date.toDate());
		op.setLabel("Solde initial");
		
		model.addAttribute("operation", op);
		return "account/init";
	}
	
	/**
	 * Create the init operation in the account
	 * @param op Operation to add
	 * @param result Result of the binding between the POST and the object
	 * @return View name
	 */
	@RequestMapping(value="/init", method=RequestMethod.POST)
	@Transactional
	public String initAccount(@ModelAttribute @Valid Operation op, BindingResult result) {
		
		if (op.getAmount() == null) {
			result.rejectValue("amount", "javax.validation.constraints.NotNull.message");
		}
		
		if (result.hasErrors()) {
			return "account/init";
		} else {
			operationDao.insert(op);
			syncService.materializeCostsIntoOperation();
			optionsService.set(SyncService.OP_SYNC_OPT, op.getOperationDate());
			return "redirect:/account/list";
		}
	}
	
	/**
	 * Handles exception which happens in controller
	 * @param e Exception raised
	 * @return View name
	 */
	@ExceptionHandler(Exception.class)
	public ModelAndView handleException(Exception e) {
		ModelAndView modelView = new ModelAndView("error");
		
		modelView.addObject("errorName", e.getMessage());
		
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		
		modelView.addObject("errorTrace", sw.toString());
		
		return modelView;
	}
	
	/**
	 * Run materializeCostsIntoOperation at the start of the application.
	 */
	@PostConstruct
	public void scheduleMaterializeCostsIntoOperation() {
		syncService.materializeCostsIntoOperation();
	}
	
	/**
	 * Build a set of MonthOps for all future ops : "manual" or costs (beyond 2 days of current)
	 * @param day Current day
	 * @param futurePlannedOps List of manual future operations
	 * @param costs List of costs
	 * @param balance Start balance
	 * @param nbMonth Number of month to build in addition to the current month.
	 * @return A set of MonthOps for each month planned
	 */
	protected Set<MonthOps> buildFutureOps(LocalDate day, List<Operation> futurePlannedOps,
			List<Cost> costs, BigDecimal balance, int nbMonth) {
		
		Set<MonthOps> futureOps = new TreeSet<MonthOps>();
		//going through all months
		for (int i = 0 ; i < nbMonth+1 ; i++) {
			LocalDate monthDate = day.monthOfYear().addToCopy(i);
			int lastDayOfMonth = monthDate.dayOfMonth().getMaximumValue();
			
			MonthOps monthOps = new MonthOps(monthDate, balance);
			futureOps.add(monthOps);
			
			//adding "manual" operation of the current month
			if (futurePlannedOps.size() > 0) {
				//loop an add all operation of the month
				for (Operation op : futurePlannedOps) {
					if (new LocalDate(op.getOperationDate()).getMonthOfYear() == monthDate.getMonthOfYear()) {
						op.setAuto(false);
						monthOps.addOp(op);
					}
				}
			}
			
			//adding costs of the current month
			LocalDate costStartDay = day.plusDays(2);
			for (Cost cost : costs) {
				int costDay = cost.getDay();
				
				//if the operation is planned after the last day of month
				//set it to the last day
				if (costDay > lastDayOfMonth) costDay = lastDayOfMonth;
				
				LocalDate opDate = new LocalDate(monthDate.getYear(), monthDate.getMonthOfYear(), costDay);
				//checking if we add the cost (the date is after current+2)
				if (opDate.isAfter(costStartDay)) {
					Operation op = new Operation();
					//setting a fake id for comparison (as we put the operation in the set)
					op.setOperationId(cost.getCostId() + i);
					op.setOperationDate(opDate.toDate());
					op.setPlanned(cost.getAmount());
					op.setLabel(cost.getLabel());
					op.setAuto(true);
					monthOps.addOp(op);
				}
			}
			
			//saving current balance for next monthOp
			balance = monthOps.getBalance();
		}
		return futureOps;
	}
}
