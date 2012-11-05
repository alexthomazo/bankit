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
 * Extract short (dd/mm) or full (dd/mm/yyyy) date from string value.
 * The date is an existing and validate date otherwise the return is null.
 */
var $getDate = function(value) {
	var re = /^\d{1,2}\/\d{1,2}(\/\d{4})?$/;
	if( re.test(value)){
		var adata = value.split('/');
		var gg = parseInt(adata[0],10);
		var mm = parseInt(adata[1],10);
		var aaaa = (adata.length > 2 ? parseInt(adata[2],10) : new Date().getFullYear());
		var xdata = new Date(aaaa,mm-1,gg);
		if ( ( xdata.getFullYear() == aaaa ) && ( xdata.getMonth () == mm - 1 ) && ( xdata.getDate() == gg ) ) {
			return xdata;
		}
	}
	return null;
};

/** Check if the string is a short or full date */
jQuery.validator.addMethod("dateShort", function(value, element) {
	return this.optional(element) || ($getDate(value) != null);
});

/** Check if the date supplied is after the current date */
jQuery.validator.addMethod("dateFuture", function(value, element) {
	var date = $getDate(value);
	return this.optional(element) || (date == null ? true : date > new Date());
});

$(document).ready(function() {
	$("#operation").validate({
	    errorElement: 'span', 
	    errorClass:'help-inline',
	    errorPlacement: function(error, element) {
	    	var p = element.closest('.controls');
	    	if (p.length == 0) p = element.parent().parent().get(0);
	    	error.appendTo(p);
	    },
	    highlight: function (element, errorClass) {
	        $(element).closest('.control-group').addClass('error');
	    },
	    unhighlight: function (element, errorClass) {
	        $(element).closest('.control-group').removeClass('error');
	    }
	});
});