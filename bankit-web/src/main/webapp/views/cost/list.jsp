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
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
	<head>
		<title>Liste des charges/revenus</title>
		<link href="<c:url value='/static/css/cost.css'/>" type="text/css" rel="stylesheet" />
		<script type="text/javascript" src="<c:url value='/static/js/costs.js' />"></script>
	</head>

	<body>
		<%-- ACTIONS ALERT --%>
		<div class="row">
			<div class="span8 offset1">
				<c:if test="${added != null}">
			    <div class="alert alert-success">
					<button type="button" class="close" data-dismiss="alert">×</button>
					<strong>Charge ajoutée.</strong>&nbsp;&nbsp;&nbsp;<small><a href="<c:url value='/cost/del/${added}'/>">Annuler</a></small>
				</div>
				</c:if>
				<c:if test="${edited != null}">
					<div class="alert alert-success">
						<button type="button" class="close" data-dismiss="alert">×</button>
						<strong>Charge mise à jour.</strong>
					</div>
				</c:if>
				<c:if test="${deleted}">
				<div class="alert alert-success">
					<button type="button" class="close" data-dismiss="alert">×</button>
					<strong>Charge supprimée.</strong>
				</div>
				</c:if>
			</div>
		</div>
	
		<%-- TITLE --%>
		<div class="row">
			<div class="span9">
				<h2>Charges et revenus</h2>			
			</div>
			<div class="span2">
				<h2>
					<button 
						class="btn btn-primary visible-desktop"
						style="float:right"
						data-remote="<c:url value='/cost/add'/>?js=t" 
						data-toggle="modal" data-target="#add-modal">Ajouter</button>
				</h2>
			</div>
		
			<%-- OPERATION TABLE --%>
			<div class="span10 offset1">
				<table class="table table-striped table-hover table-condensed table-cost">
					<thead>
						<tr>
							<th>Jour</th>
							<th>Libellé</th>
							<th>Montant</th>
							<th></th>
						</tr>
					</thead>
					
					<tbody>
						<c:forEach var="c" items="${costs}">
						<tr>
							<td>${c.day}</td>
							<td>${c.label}</td>
							<td>${c.amount}</td>
							<td>
								<a href="<c:url value='/cost/edit/${c.costId}'/>?js=t"
								   title="Modifier la charge"
								   data-toggle="modal" data-target="#edit-modal">
									<i class="icon-pencil"></i>
								</a>
								<a href="<c:url value='/cost/del/${c.costId}'/>" title="Supprimer la charge"><i class="icon-trash"></i></a>
							</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
				
				<%-- link for mobile version --%>
				<div class="span2 hidden-desktop">
					<a class="btn btn-primary" href="<c:url value='/cost/add'/>">Ajouter une charge</a>
				</div>
			</div>
			<%-- /OPERATION TABLE --%>
			
			<%-- ADD MODAL BOX --%>
			<div id="add-modal" class="modal hide fade">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">×</button>
					<h2>Ajouter une charge ou un revenu</h2>
				</div>
				<div class="modal-body">Chargement du formulaire...</div>
			</div>
			<%-- /ADD MODAL BOX --%>

			<%-- EDIT MODAL BOX --%>
			<div id="edit-modal" class="modal hide fade">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">×</button>
					<h2>Modifier une charge ou un revenu</h2>
				</div>
				<div class="modal-body">Chargement du formulaire...</div>
			</div>
			<%-- /EDIT MODAL BOX --%>
		</div>
	</body>
</html>