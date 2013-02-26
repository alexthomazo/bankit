<%--
  Copyright (C) 2013 Alexandre Thomazo

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
		<title>Ajout d'une catégorie</title>
		<c:if test="${not empty param.js}">
		<meta name="decorator" content="empty">
		</c:if>
	</head>

	<body>
		<c:url var="formUrl" value="/options/category/add"/>
		<form:form method="post" commandName="category" action="${formUrl}" cssClass="form-validate form-horizontal">
			
			<spring:bind path="name">
			<div class="control-group ${status.error ? 'error' : '' }">
				<form:label cssClass="control-label" path="name">Nom:</form:label>
				<div class="controls">
					<form:input path="name" placeholder="nom" cssClass="required" />
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
		<script src="<c:url value='/static/js/form-validate-bootstrap.js'/>" type="text/javascript"></script>
		</c:if>
	</body>
</html>