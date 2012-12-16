<%--

    Copyright (C) 2012 Alexandre Thomazo

    This file is part of BankIt.

    BankIt is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    BankIt is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with BankIt. If not, see <http://www.gnu.org/licenses/>.

--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
	<head>
		<title>Mises à jour</title>
		<style type="text/css">
			.form-horizontal .control-label { width: 160px; }
			.form-horizontal .controls { margin-left: 180px; }
		</style>
	</head>
	
	<body>
		<h4>Mises à jour</h4>
		<form class="form-horizontal" method="post">
		
			<%-- Check updates --%>
			<div class="control-group">
				<label class="control-label">Vérifier les mises à jour :</label>
				<div class="controls">
					<label class="radio inline">
						<input type="radio" name="checkUpdates" value="1" <c:if test='${checkUpdates == 1}'>checked</c:if>> Oui
					</label>
					<label class="radio inline">
						<input type="radio" name="checkUpdates" value="0" <c:if test='${checkUpdates == 0}'>checked</c:if>> Non
					</label>
				</div>
			</div>
			
			<%-- Channel --%>
			<div class="control-group">
				<label class="control-label">Version :</label>
				<div class="controls">
					<label class="radio">
						<input type="radio" name="updateChannel" value="stable" <c:if test='${updateChannel == "stable"}'>checked</c:if>> Stable
					</label>
					<label class="radio">
						<input type="radio" name="updateChannel" value="dev" <c:if test='${updateChannel == "dev"}'>checked</c:if>> Développement
					</label>
				</div>
			</div>
			
			<%-- Save button --%>
			<div class="control-group">
				<div class="controls">
					<button type="submit" class="btn btn-primary">Enregistrer</button>
				</div>
			</div>
		</form>
	</body>
</html>