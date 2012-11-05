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
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
	<head>
		<title>Ajout d'une opération à venir</title>
		<c:if test="${not empty param.js}">
		<meta name="decorator" content="empty">
		</c:if>
	</head>

	<body>
		<c:url var="formUrl" value="/account/add"/>
		<form:form method="post" commandName="operation" action="${formUrl}" cssClass="form-horizontal">
			
			<spring:bind path="operationDate">
			<div class="control-group ${status.error ? 'error' : '' }">
				<form:label cssClass="control-label" path="operationDate">Date:</form:label>
				<div class="controls">
					<form:input path="operationDate" placeholder="dd/mm ou dd/mm/yyyy" cssClass="required dateShort dateFuture" />
					<c:if test="${status.error}">
						<span class="help-inline">${status.errorMessage}</span>
					</c:if>
				</div>
			</div>
			</spring:bind>
			
			<spring:bind path="label">
			<div class="control-group ${status.error ? 'error' : '' }">
				<form:label cssClass="control-label" path="label">Libellé:</form:label>
				<div class="controls">
					<form:input path="label" placeholder="libellé" cssClass="required" minlength="1" />
					<c:if test="${status.error}">
						<span class="help-inline">${status.errorMessage}</span>
					</c:if>
				</div>
			</div>
			</spring:bind>
			
			<spring:bind path="planned">
			<div class="control-group ${status.error ? 'error' : '' }">
				<form:label cssClass="control-label" path="planned">Montant:</form:label>
				<div class="controls">
					<div class="input-append">
						<form:input path="planned" placeholder="montant" cssClass="required number" />
						<span class="add-on">&euro;</span>
					</div>
					<c:if test="${status.error}">
						<span class="help-inline">${status.errorMessage}</span>
					</c:if>
				</div>
			</div>
			</spring:bind>
			
			<spring:bind path="debit">
			<div class="control-group ${status.error ? 'error' : '' }">
				<div class="controls">
					<form:label cssClass="checkbox inline" path="debit">
						<form:radiobutton path="debit" value="true"/> Débit
					</form:label>
					<form:label cssClass="checkbox inline" path="debit">
						<form:radiobutton path="debit" value="false"/> Crédit
					</form:label>
					<c:if test="${status.error}">
						<span class="help-inline">${status.errorMessage}</span>
					</c:if>
				</div>
			</div>
			</spring:bind>
			
			<div class="control-group">
				<div class="controls">
					 <button type="submit" class="btn btn-primary">Ajouter</button>
				</div>
			</div>
		</form:form>
		
		<%-- Displaying JS Code for form validating in modal box --%>
		<c:if test="${not empty param.js}">
		<script src="<c:url value='/static/js/form-validate/jquery.validate.js'/>" type="text/javascript"></script>
		<script src="<c:url value='/static/js/form-validate/messages_fr.js'/>" type="text/javascript"></script>
		<script src="<c:url value='/static/js/operation-add.js'/>" type="text/javascript"></script>
		</c:if>
	</body>
</html>