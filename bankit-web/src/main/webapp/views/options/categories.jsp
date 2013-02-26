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
<html>
<head>
	<title>Catégories</title>
</head>

<body>
<h4>Catégories</h4>

<%-- ACTIONS ALERT --%>
<div class="row">
	<div class="span4">
		<c:if test="${added != null}">
			<div class="alert alert-success">
				<button type="button" class="close" data-dismiss="alert">×</button>
				<strong>Catégorie ajoutée.</strong>&nbsp;&nbsp;&nbsp;<small><a href="<c:url value='/options/category/del/${added}'/>">Annuler</a></small>
			</div>
		</c:if>
		<c:if test="${deleted}">
			<div class="alert alert-success">
				<button type="button" class="close" data-dismiss="alert">×</button>
				<strong>Catégorie supprimée.</strong>
			</div>
		</c:if>
	</div>
</div>

<%-- CATEGORIES LIST --%>
<div class="row">
	<div class="span4">
		<table class="table table-striped table-hover table-condensed">
			<thead>
			<tr>
				<th>Nom</th>
				<th></th>
			</tr>
			</thead>

			<tbody>
			<c:forEach var="cat" items="${categories}">
				<tr>
					<td>${cat.name}</td>
					<td></td>
				</tr>
			</c:forEach>
			</tbody>

		</table>

		<button
			class="btn btn-primary visible-desktop"
			data-remote="<c:url value='/options/category/add'/>?js=t"
			data-toggle="modal" data-target="#add-modal">Ajouter</button>

		<a class="btn btn-primary hidden-desktop" href="<c:url value='/options/category/add'/>">Ajouter</a>

	</div>
</div>

<%-- ADD MODAL BOX --%>
<div id="add-modal" class="modal hide fade">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal">×</button>
		<h2>Ajouter une catégorie</h2>
	</div>
	<div class="modal-body">Chargement du formulaire...</div>
</div>
<%-- /ADD MODAL BOX --%>

</body>

</html>