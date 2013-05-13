<%--

    Copyright (C) 2012-2013 Alexandre Thomazo

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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
	<head>
		<title>Liste des opérations</title>
		<link href="<c:url value='/static/css/account.css'/>" type="text/css" rel="stylesheet" />
		<link href="<c:url value='/static/css/timeframe.css'/>" type="text/css" rel="stylesheet" />
		<script type="text/javascript">
			var $start_day = new Date(${startDay.time}),
				$end_day = new Date(${endDay.time});
		</script>
		<script src="<c:url value='/static/js/timeframe.class.js'/>" type="text/javascript"></script>
		<script src="<c:url value='/static/js/operation-list.js'/>" type="text/javascript"></script>
	</head>

	<body>
		<%-- ACTIONS ALERT --%>
		<div class="row">
			<div class="span8 offset1">
				<c:if test="${added != null}">
			    <div class="alert alert-success">
					<button type="button" class="close" data-dismiss="alert">×</button>
					<strong>Opération ajoutée.</strong>&nbsp;&nbsp;&nbsp;<small><a href="<c:url value='/account/del/${added}'/>">Annuler</a></small>
				</div>
				</c:if>
				<c:if test="${deleted}">
				<div class="alert alert-success">
					<button type="button" class="close" data-dismiss="alert">×</button>
					<strong>Opération supprimée.</strong>
				</div>
				</c:if>
			</div>
		</div>


		<div class="row">
			<%-- TIMEFRAME --%>
			<div id="timeframe" class="span10"></div>

			<%-- TITLE --%>
			<div class="span5">
				<h2>Compte chèque</h2>			
			</div>
			<div class="span5">
				<h2 class="visible-desktop" style="text-align: right">
					<button 
						class="btn btn-primary"
						data-remote="<c:url value='/account/add'/>?js=t" 
						data-toggle="modal" data-target="#add-modal">Ajouter</button>
					<button 
						class="btn btn-primary"
						data-toggle="modal" data-target="#sync-modal">Synchroniser</button>
				</h2>
			</div>
			
			<%-- OPERATION TABLE --%>
			<div class="span10">
				<table class="table table-striped table-hover table-condensed table-account">
					<thead>
						<tr>
							<th>Date</th>
							<th>Libellé</th>
							<th>Catégorie</th>
							<th>Montant</th>
							<th>Cumul</th>
							<th></th>
						</tr>
					</thead>
				
					<tbody>
						<%-- Display past operations --%>
						<c:forEach items="${ops}" var="op">
						<c:if test="${op.amount != null}">
						<tr>
							<c:set var="diff" value="${op.amount-op.planned}"/>
							<c:set var="diffClass">
								<c:choose>
									<c:when test="${diff > 0}">plus</c:when>
									<c:when test="${diff < 0}">minus</c:when>
								</c:choose>
							</c:set>
							
							<%-- Date column --%>
							<td><fmt:formatDate pattern="dd/MM/yyyy" value="${op.operationDate}" /></td>
							
							<%-- Label column --%>
							<td>${op.label}</td>
							
							<%-- Category column --%>
							<td>
								<select id="cat_${op.operationId}" class="cat_select">
									<option value="-1"></option>
									<c:forEach var="cat" items="${categories}">
									<option
										value="${cat.categoryId}"
										<c:if test="${op.category.categoryId == cat.categoryId}">selected</c:if>
										>${cat.name}</option>
									</c:forEach>
								</select>
							</td>
							
							<%-- Amount column --%>
							<td <c:if test="${op.amount > 0}">class="income"</c:if>>
								<span<c:if test="${op.planned != null}"> class="planned ${diffClass}" title="Diff : ${diff}<br>Prévu : ${op.planned}"</c:if>>${op.amount}</span>
							</td>
							
							<%-- Total column --%>
							<td <c:if test="${op.total < 0}">class="red"</c:if>>${op.total}</td>
							
							<%-- Action column --%>
							<td>
								<%-- Offer to delete the planned amount on a sync operation --%>
								<c:if test="${op.planned != null}">
								<a href="<c:url value='/account/unmerge/${op.operationId}'/>" title="Supprimer le montant prévu"><i class="icon-remove"></i></a>
								</c:if>
							</td>
						</tr>
						</c:if>
						</c:forEach>
					
						<%-- Past operation balance --%>
						<tr class="success balance">
							<td><fmt:formatDate pattern="dd/MM/yyyy" value="${endDay}" /></td>
							<td>solde courant</td>
							<td></td>
							<td>
								<c:choose>
									<c:when test="${currentDiff > 0}"><span class="planned plus" title="Gains sur prévisions : +${currentDiff}">${periodBalance}</span></c:when>
									<c:when test="${currentDiff < 0}"><span class="planned minus" title="Pertes sur prévisions : ${currentDiff}">${periodBalance}</span></c:when>
									<c:otherwise>${periodBalance}</c:otherwise>
								</c:choose>
							</td>
							<td>${current}</td>
							<td></td>
						</tr>
					</tbody>
					
					<%-- Display past planned operation not synced with real one --%>
					<c:if test="${plannedWaiting != 0}">
					<tbody class="waiting">
						<c:forEach items="${ops}" var="op">
						<c:if test="${op.amount == null}">
						<tr>
							<td><fmt:formatDate pattern="dd/MM/yyyy" value="${op.operationDate}" /></td>
							<td>${op.label}</td>
							
							<td>
								<select id="cat_${op.operationId}" class="cat_select">
									<option value="-1"></option>
									<c:forEach var="cat" items="${categories}">
									<option
										value="${cat.categoryId}"
										<c:if test="${op.category.categoryId == cat.categoryId}">selected</c:if>
										>${cat.name}</option>
									</c:forEach>
								</select>
							</td>
							
							<td <c:if test="${op.planned > 0}">class="income"</c:if>>${op.planned}</td>
							<td <c:if test="${op.total < 0}">class="red"</c:if>>${op.total}</td>
							<td><a href="<c:url value='/account/del/${op.operationId}'/>" title="Supprimer l'opération planifiée"><i class="icon-trash"></i></a></td>
						</tr>
						</c:if>
						</c:forEach>
						<tr class="error balance">
							<td></td>
							<td>solde prévu non débité</td>
							<td></td>
							<td>${plannedWaiting}</td>
							<td>${currentWaiting}</td>
							<td></td>
						</tr>
					</tbody>
					</c:if>
					
					<!-- Displaying future operations -->
					<c:forEach items="${futureOps}" var="monthOp">
					<tbody class="future">
						<c:forEach items="${monthOp.ops}" var="op">
						<tr>
							<td><fmt:formatDate pattern="dd/MM/yyyy" value="${op.operationDate}" /></td>
							<td>${op.label}</td>
							
							<td>
							<%-- Category --%>
							<c:if test="${not op.auto}">
								<select id="cat_${op.operationId}" class="cat_select">
									<option value="-1"></option>
									<c:forEach var="cat" items="${categories}">
									<option
										value="${cat.categoryId}"
										<c:if test="${op.category.categoryId == cat.categoryId}">selected</c:if>
										>${cat.name}</option>
									</c:forEach>
								</select>
							</c:if>
							<c:if test="${op.auto}">
								${op.category.name}
							</c:if>
							</td>
							
							<td <c:if test="${op.planned > 0}">class="income"</c:if>>${op.planned}</td>
							<td <c:if test="${op.total < 0}">class="red"</c:if>>${op.total}</td>
							<td>
								<c:if test="${op.auto}">
								<i class="icon-time" title="Opération planifiée automatiquement"></i>
								</c:if>
								<c:if test="${not op.auto}">
								<a href="<c:url value='/account/del/${op.operationId}'/>" title="Supprimer l'opération planifiée"><i class="icon-trash"></i></a>
								</c:if>
							</td>
						</tr>
						</c:forEach>
						<tr class="info balance">
							<td></td>
							<td>solde <fmt:formatDate pattern="MMMMM" value="${monthOp.lastDay}" /></td>
							<td></td>
							<td><c:if test="${monthOp.amount != 0}">${monthOp.amount}</c:if></td>
							<td>${monthOp.balance}</td>
							<td></td>
						</tr>
					</tbody>
					</c:forEach>
				</table>
				
				<%-- link for mobile version --%>
				<div class="span2 hidden-desktop">
					<a class="btn btn-primary" href="<c:url value='/account/add'/>">Ajouter une opération</a>
				</div>
			</div>
			<%-- /OPERATION TABLE --%>
			
			<%-- SUMMARY --%>
			<div class="offset10">
				<h4>Balance</h4>
				<table class="table table-striped table-hover table-condensed table-summary">
					<tbody>
						<tr>
							<td>courant</td>
							<td>${current}</td>
						</tr>
						<c:if test="${plannedWaiting != 0}">
						<tr>
							<td>prévu en attente</td>
							<td>${plannedWaiting}</td>
						</tr>
						</c:if>
						<c:forEach items="${futureOps}" var="monthOp">
						<tr>
							<td><fmt:formatDate pattern="MMMMM" value="${monthOp.lastDay}" /></td>
							<td>${monthOp.balance}</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
				
				<c:if test="${fn:length(categoriesSummary) > 0}">
				<h4>Catégories</h4>
				
				<c:forEach var="catMonth" items="${categoriesSummary}">
					<h5><fmt:formatDate pattern="MMMMM" value="${catMonth.key}" /></h5>
					
					<table class="table table-striped table-hover table-condensed table-summary">
						<tbody>
							<c:forEach var="cat" items="${catMonth.value}">
							<tr>
								<td>
									<c:choose>
										<c:when test="${cat.key.categoryId == -1}">Non catégorisé</c:when>
										<c:otherwise>${cat.key.name}</c:otherwise>
									</c:choose>
								</td>
								<td>${cat.value}</td>
							</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:forEach>
				</c:if>
			</div>
			<%-- /SUMMARY --%>
			
			<%-- ADD MODAL BOX --%>
			<div id="add-modal" class="modal hide fade">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">×</button>
					<h2>Ajouter une opération à venir</h2>
				</div>
				<div class="modal-body">Chargement du formulaire...</div>
			</div>
			<%-- /ADD MODAL BOX --%>
			
			<%-- SYNC MODAL BOX --%>
			<div id="sync-modal" class="modal hide fade">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">×</button>
					<h2>Synchronisation</h2>
				</div>
				<div class="modal-body">
					<form method="post" action="<c:url value='/account/sync'/>" enctype="multipart/form-data">
						<h5>Fichier QIF de la banque :</h5>
						<input type="file" name="file"/>
						<button type="submit" class="btn btn-primary">Synchroniser</button>
					</form>
					<small>Date de la dernière synchronisation : <fmt:formatDate pattern="dd/MM/yyyy" value="${lastSyncDate}" /></small>
				</div>
			</div>
			<%-- /SYNC MODAL BOX --%>
		</div>
	</body>

</html>