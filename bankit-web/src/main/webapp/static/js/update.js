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
 * File which handles the check of version update
 */
var Updater = new Class({
	initialize: function() {
		this.cookie = "up";
		this.localCommitId = "";
		this.remoteCommitId = "";
		this.channel = "";
	},
	
	update: function() {
		//if the cookie is present, don't check
		if (Cookie.read(this.cookie)) return;
		this.getLocal();
	},
	
	/**
	 * Get the local commit id with Ajax then call getRemote
	 */
	getLocal: function() {
		new Request.JSON({
			url: $ctx_path + 'api/update',
			onSuccess: function(up) {
				this.localCommitId = up.commitId;
				this.channel = up.updateChannel;

				if (up.checkUpdates == "1") {
					this.getRemote();
				} else {
					//put the cookie to avoid recheck on each page
					this.writeCookie();
				}
			}.bind(this)
		}).get();
	},
	
	/**
	 * Get the remote commit id with JSONP then call display
	 */
	getRemote: function() {
		new Request.JSONP({
			url: 'http://bankit.thomazo.info/update.php?channel=' + this.channel,
			onSuccess: function(res) {
				this.remoteCommitId = res.commit;
				this.display();
			}.bind(this)
		}).send();
	},
	
	/**
	 * Check if local and remote are different to detect a new
	 * version then put a notice to the user.
	 */
	display: function() {
		//put the cookie for 1 day to remind the notice has been displayed
		this.writeCookie();
		
		if (this.localCommitId != this.remoteCommitId) {
			//new version, display the notice to the user
			document.id('container').grab(
				new Element('div', {'class':'row'}).grab(
					new Element('div', {'class':'span8 offset1 alert alert-success'}).grab(
						new Element('button', {
							'type': 'button',
							'class': 'close',
							'data-dismiss': 'alert'
						}).appendText('×')
					).appendText('Une nouvelle version de BankIt est disponible sur ').grab(
						new Element('a', {'href':'http://bankit.thomazo.info/?channel=' + this.channel,'target':'blank'})
						.appendText('bankit.thomazo.info')
					)
				)
				
			,'top');
		}
	},

	/**
	 * Write the cookie to avoid check version during 24h
	 */
	writeCookie: function() {
		Cookie.write(this.cookie, 1, {duration: 1});
	}
});

window.addEvent('domready', function() {
	new Updater().update();
});