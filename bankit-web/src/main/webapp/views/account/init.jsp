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
		<title>Initialisation</title>
		<script src="<c:url value='/static/js/form-validate/jquery.validate.js'/>" type="text/javascript"></script>
		<script src="<c:url value='/static/js/form-validate/messages_fr.js'/>" type="text/javascript"></script>
		<script src="<c:url value='/static/js/operation-add.js'/>" type="text/javascript"></script>
	</head>

	<body>
		<div class="row">
			<div class="span8 offset2 well">
				<h1>Bienvenue dans BankIt</h1>
				
				<p>Ceci est votre première utilisation de BankIt.</p>
				
				<p>Avant de démarrer à utiliser BankIt, merci d'indiquer ci-après
					le solde courant de votre compte courant.
				</p>
				
				<div style="margin-top: 39px">
					<c:url var="formUrl" value="/account/init"/>
					<form:form method="post" commandName="operation" action="${formUrl}" cssClass="form-inline">
						<form:input path="operationDate" type="hidden"/>
						<form:input path="label" type="hidden"/>
						
						<spring:bind path="amount">
						<div class="control-group ${status.error ? 'error' : '' }">
							<form:label cssClass="control-label" path="amount">Solde :</form:label>
								<div class="input-append">
									<form:input path="amount" placeholder="montant" cssClass="required number input-small" />
									<span class="add-on">&euro;</span>
								</div>
								
								<button type="submit" class="btn btn-primary">C'est parti !</button>
								
								<c:if test="${status.error}">
									<span class="help-inline">${status.errorMessage}</span>
								</c:if>
						</div>
						</spring:bind>
						
					</form:form>
				</div>
			</div>		
		</div>
	</body>

</html>