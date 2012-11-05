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
$(document).ready(function() {
	$("#cost").validate({
	    rules: {
	    	day: {
	    		range: [1, 31]
	    	}
	    },
		
		errorElement: 'span', 
	    errorClass:'help-inline',
	    errorPlacement: function(error, element) {
	    	error.appendTo(element.closest('.controls'));
	    },
	    highlight: function (element, errorClass) {
	        $(element).closest('.control-group').addClass('error');
	    },
	    unhighlight: function (element, errorClass) {
	        $(element).closest('.control-group').removeClass('error');
	    }
	});
});