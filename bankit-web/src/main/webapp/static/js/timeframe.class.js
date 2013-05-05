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

/**
 * File creating the time frame to select date interval
 */
var Timeframe = new Class({
	Implements: [Options, Events],

	month_div: null, //containing all the month element
	first_date: null, //first date displayed in the time frame
	last_date: null, //last date displayed in the time frame

	clicked: null, //element which have been clicked, null if mouse button is up
	start_date: null, //first date of the selection
	end_date: null, //last date of the selection

	options: {
		base_div: null, //div to inject the time frame
		start_date: null, //start date of the range to select on the init
		end_date: null //end date of the range to select on the init
	},

	initialize: function(options) {
		if (options.start_date != null && options.end_date != null) {
			//set start/end date of option to first of the month
			options.start_date = this.getFirstDayMonth(options.start_date);
			options.end_date = this.getFirstDayMonth(options.end_date);

			//check the order of start/end
			if (options.start_date.getTime() > options.end_date.getTime()) {
				var tmp = options.start_date;
				options.start_date = options.end_date;
				options.end_date = tmp;
			}
		}

		this.setOptions(options);
		this.buildStruct();

		//select range if start/end selected
		if (this.options.start_date != null && this.options.end_date != null) {
			this.selectRange(this.options.start_date, this.options.end_date);
		}
	},

	/**
	 * Build the base HTML structure
	 */
	buildStruct: function() {
		//applying custom style to the div
		this.options.base_div.addClass('timeframe');

		//creating the div containing the months
		this.months_div = new Element('div', {'class': 'months'}).inject(this.options.base_div);

		//create the months
		var end_date, cur_date = this.getFirstDayMonth(new Date());
		//check if the initial selection is not after the current date
		if (this.options.end_date != null && this.options.end_date.getTime() > cur_date.getTime()) {
			end_date = this.options.end_date;
		} else {
			end_date = cur_date;
		}
		this.last_date = end_date;

		for (var i = 0 ; i < 24 ; i++) {
			this.buildMonthEl(end_date.clone().decrement('month', i)).inject(this.months_div, 'top');
		}
		this.first_date = end_date.clone().decrement('month', 23);

		//check the initial first date is not after first displayed date
		if (this.options.start_date != null && this.options.start_date.getTime() < this.first_date.getTime()) {
			this.options.start_date = this.first_date;
		}

		//adding mouseup on document if the user release the click outside month div
		document.addEvent('mouseup', function(e) {
			if (e.event.button == 0) {
				this.clicked = null;

				//raise the event notify the date has been selected
				this.fireEvent("selected", [this.start_date, this.end_date]);
			}
		}.bind(this));
	},

	/**
	 * Build an element for a month
	 * @param date Date of the month to build
	 * @return {Element} element built
	 */
	buildMonthEl: function(date) {
		var monthTxt = date.format('%b'),
			monthEl = new Element('div', {'class': 'month'});

		//save the date into the element
		monthEl.store('date', date);

		//element grey when unselected, blue when selected
		new Element('div', {'class':'switch'}).inject(monthEl);

		//short month name
		new Element('div', {'class':'txt', text: monthTxt}).inject(monthEl);

		//if we are on january month, display also the year
		if (date.get('mo') == 0) {
			new Element('div', {'class':'year', text: date.get('year')}).inject(monthEl);
		}

		monthEl.addEvents({
			//mouse button pressed
			mousedown: function(e) {
				//if left button pressed
				if (e.event.button == 0) {
					this.removeSelection();

					//get the month element (can be click on switch or txt)
					var el = e.target;
					if (!el.hasClass('month')) el = el.getParent('.month');

					//save start date and set to clicked
					this.start_date = el.retrieve('date');
					this.end_date = this.start_date;
					this.clicked = el;
					el.addClass('selected').addClass('left').addClass('right');
				}
			}.bind(this),

			//entering the element
			mouseenter: function(e) {
				//if the left mouse button is pressed
				if (this.clicked) {
					//get the month element (can be click on switch or txt)
					var el = e.target;
					if (!el.hasClass('month')) el = el.getParent('.month');

					//check if we are after or before
					var el_date = el.retrieve('date'),
						orig_date = this.clicked.retrieve('date');

					//set start_date / end_date in right order
					if (orig_date <= el_date) {
						this.start_date = orig_date;
						this.end_date = el_date;
					} else {
						this.start_date = el_date;
						this.end_date = orig_date;
					}

					//select the elements
					this.selectRange(this.start_date, this.end_date);
				}
			}.bind(this)
		});

		return  monthEl;
	},

	/**
	 * Remove selected class of all month elements
	 */
	removeSelection: function() {
		this.months_div.getChildren().removeClass('selected').removeClass('left').removeClass('right');
	},

	/**
	 * Select the element between start_date and end_date
	 * @param start_date First element to select
	 * @param end_date Last element to select
	 */
	selectRange: function(start_date, end_date) {
		this.months_div.getChildren().each(function(el) {
			var el_time = el.retrieve('date').getTime(),
				start_time = start_date.getTime(),
				end_time = end_date.getTime();

			//remove old selected style
			el.removeClass('left').removeClass('right');

			//check if before start or after end, so deselect
			if (el_time < start_time || el_time > end_time) {
				el.removeClass('selected');

			} else {
				//the element is in the range
				el.addClass('selected');

				if (el_time == start_time) {
					el.addClass('left');
				}
				if (el_time == end_time) {
					el.addClass('right');
				}
			}

		}.bind(this));
	},

	/**
	 * Create a date at the first day of the month based on a date
	 * @param date Date to retrieve year and month
	 * @returns {Date} new date at the 1st of the month
	 */
	getFirstDayMonth: function(date) {
		return new Date(date.getFullYear(), date.getMonth(), 1);
	}

});