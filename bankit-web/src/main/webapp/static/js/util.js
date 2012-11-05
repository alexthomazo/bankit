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
 * Affiche l'indicateur de chargement.
 * @return Tween permettant de chainer l'effet.
 */
function showLoading() {
	return document.id('loading').fade('in').get('tween');
}

/**
 * Fait disparaitre et cache l'indicateur de chargement.
 * @return Tween permettant de chainer l'effet.
 */
function hideLoading() {
	document.id('loading').fade('out').get('tween').chain(function() {
		return document.id('loading_bar').fade('hide').get('tween');
	});
}

/**
 * Affiche ou cache l'indicateur de chargement
 * @param state True pour afficher, false pour cacher
 */
function $loading(state) {
	state ? showLoading() : hideLoading();
}

/**
 * Affiche la progression d'un chargement.
 * @param {Number} nb Nombre d'item chargés
 * @param {Number} total Nombre d'item total
 */
function showProgress(nb, total) {
	var bar = document.id('loading_bar');
	if (bar.getStyle('visibility') == 'hidden') {
		bar.setStyle('width', '0%');
		bar.show();
	}
	bar.tween('width', ((nb/total)*100)-1 + '%');
}

/**
 * Affiche un message d'erreur.
 * @param message Message d'erreur à afficher
 */
function showError(message) {
	showMessage('error', message);
}

/**
 * Affiche un message de confirmation.
 * @param message Message de confirmation à afficher
 */
function showConfirm(message) {
	showMessage('confirm', message);
}

/**
 * Affiche un message et le cache après 5 secondes.
 * @param divname Id de la div "Message" à afficher
 * @param message Message a afficher
 */
function showMessage(divname, message) {
	var div = document.id(divname);
	div.empty();
	div.appendText(message);
	
	div.fade('in');
	window.setTimeout(function() {
		div.fade('out');
	}, 5000);
}

//Ajoute 4 méthodes pour afficher/cacher un élément
window.addEvent('domready', function() {
	//time to implement basic show / hide  
	Element.implement({
		//implement show  
		fadeIn : function() {
			return this.fade('in').get('tween');
		},
		//implement hide  
		fadeOut : function() {
			return this.fade('out').get('tween');
		},
		
		show : function() {
			this.setStyle('display', '');
		}, 
		
		hide : function() {
			this.setStyle('display', 'none');
		},
		
		isHided: function() {
			return this.getStyle('display') == 'none';
		}
	});
	
	//ajout de la comparaison des tableaux
	Array.implement({
		equals : function(arr) {
			if (this.length !== arr.length) {
				return false;
			}
			for ( var i = this.length - 1; i >= 0; i--) {
				if (this[i] !== arr[i]) {
					return false;
				}
			}
			return true;
		}
	});
	
});

function error(message, exception) {
	showError(message);
	if (console && console.firebug) {
		console.log(exception);
	}

}

//convertie une date en dd/mm/YYYY
function displayDate(date) {
	return date.format("%d/%m/%Y");
}

//converti une date dd/mm/yyyy en date
function toDate(str) {
	return new Date(str.substr(6, 4), str.substr(3, 2)-1, str.substr(0, 2));
}

/**
 * follow a link (anchor tag) with linkId
 * @param linkId Id of the anchor tag to follow
 * @param event if true, launch onclick event
 * 		instead of reloading the page
 */
function followLink(linkId, event) {
	var link = document.id(linkId);
	if (link && link.get('href')) {
		if (event) {
			link.fireEvent('click', link);
		} else {
			window.location.href = link.get('href');
		}
	}
}