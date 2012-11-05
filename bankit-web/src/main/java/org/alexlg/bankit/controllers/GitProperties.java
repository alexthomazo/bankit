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

/**
 * Contains all Git properties from the current build.
 * The properties are injected from the git.properties resource file.
 * The resource file is updated by the git-commit-id maven plugin.
 * 
 * @author Alexandre Thomazo
 */
public class GitProperties {

	private String branch;
	private String describe;
	private String commitId;
	private String commitIdAbbrev;
	private String buildUserName;
	private String buildUserEmail;
	private String buildTime;
	private String commitUserName;
	private String commitUserEmail;
	private String commitMessageFull;
	private String commitMessageShort;
	private String commitTime;
	
	/**
	 * @return Branch name (ex: master)
	 */
	public String getBranch() {
		return branch;
	}
	
	/**
	 * @return git describe result (ex: v.1.0-2-b2414721)
	 */
	public String getDescribe() {
		return describe;
	}
	
	/**
	 * @return Full git commit id 
	 * (ex: d49d6c9cb8e9237cc9b6d06d08d6be6850543d56)
	 */
	public String getCommitId() {
		return commitId;
	}
	
	/**
	 * @return Short git commit id (ex: d49d6c9)
	 */
	public String getCommitIdAbbrev() {
		return commitIdAbbrev;
	}
	
	/**
	 * @return User name who build this package (ex: John Doe)
	 */
	public String getBuildUserName() {
		return buildUserName;
	}
	
	/**
	 * @return Email of the user who build this package (ex: john@doe.net)
	 */
	public String getBuildUserEmail() {
		return buildUserEmail;
	}
	
	/**
	 * @return Build time (ex: 21.09.2012 @ 19:52:24 CEST)
	 */
	public String getBuildTime() {
		return buildTime;
	}
	
	/**
	 * @return User name of the current commit (ex: John Doe)
	 */
	public String getCommitUserName() {
		return commitUserName;
	}
	
	/**
	 * @return Email of the user who did the current commit (ex: john@doe.net)
	 */
	public String getCommitUserEmail() {
		return commitUserEmail;
	}
	
	/**
	 * @return Full message of the current commit (ex: This commit fixes all the bugs)
	 */
	public String getCommitMessageFull() {
		return commitMessageFull;
	}
	
	/**
	 * @return Short message of the current commit (ex: Fixed all bugs)
	 */
	public String getCommitMessageShort() {
		return commitMessageShort;
	}
	
	/**
	 * @return Time of the current commit (ex: 21.09.2012 @ 19:52:24 CEST)
	 */
	public String getCommitTime() {
		return commitTime;
	}
	
	
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public void setCommitId(String commitId) {
		this.commitId = commitId;
	}
	public void setCommitIdAbbrev(String commitIdAbbrev) {
		this.commitIdAbbrev = commitIdAbbrev;
	}
	public void setBuildUserName(String buildUserName) {
		this.buildUserName = buildUserName;
	}
	public void setBuildUserEmail(String buildUserEmail) {
		this.buildUserEmail = buildUserEmail;
	}
	public void setBuildTime(String buildTime) {
		this.buildTime = buildTime;
	}
	public void setCommitUserName(String commitUserName) {
		this.commitUserName = commitUserName;
	}
	public void setCommitUserEmail(String commitUserEmail) {
		this.commitUserEmail = commitUserEmail;
	}
	public void setCommitMessageFull(String commitMessageFull) {
		this.commitMessageFull = commitMessageFull;
	}
	public void setCommitMessageShort(String commitMessageShort) {
		this.commitMessageShort = commitMessageShort;
	}
	public void setCommitTime(String commitTime) {
		this.commitTime = commitTime;
	}
	
	
}
