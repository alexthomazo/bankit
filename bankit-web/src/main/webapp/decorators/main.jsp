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
<!DOCTYPE html>
<html lang="fr">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

	<head>
		<title>BankIt | <decorator:title default="Index" /></title>
		<meta http-equiv="Content-Type" content="text/HTML; charset=UTF-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		
		<link href="<c:url value='/static/css/bootstrap.css'/>" type="text/css" rel="stylesheet" />
		<link href="<c:url value='/static/css/style.css'/>" type="text/css" rel="stylesheet" />
		<link href="<c:url value='/static/css/bootstrap-responsive.css'/>" type="text/css" rel="stylesheet" />

		<script type="text/javascript">var $ctx_path="<c:url value='/'/>";</script>
		<script src="<c:url value='/static/js/jquery-1.8.1.js'/>" type="text/javascript"></script>
		<script src="<c:url value='/static/js/bootstrap.js'/>" type="text/javascript"></script>
		<script src="<c:url value='/static/js/mootools.js'/>" type="text/javascript"></script>
		<script src="<c:url value='/static/js/mootools-more.js'/>" type="text/javascript"></script>
		<script src="<c:url value='/static/js/util.js'/>" type="text/javascript"></script>
		<script src="<c:url value='/static/js/update.js'/>" type="text/javascript"></script>
		<decorator:head />
	</head>
	<body>
		<div class="navbar navbar-fixed-top navbar-inverse">
			<div class="navbar-inner">
				<div class="container">
					<a class="brand" href="<c:url value='/'/>">
						BankIt
						<small>${gitDescribe}</small>
					</a>

					<ul class="nav">
						<li class="divider-vertical"></li>
						<li><a href="<c:url value='/account/'/>">Compte</a></li>
						<li class="divider-vertical"></li>
						<li><a href="<c:url value='/cost/'/>">Charges</a></li>
						<li class="divider-vertical"></li>
						<li><a href="<c:url value='/options/'/>">Options</a></li>
					</ul>
					
					<c:if test="${standalone}">
					<div class="btn-group pull-right">
						<a class="btn" href="<c:url value='/shutdown'/>">
							<i class="icon-off"></i> Quitter
						</a>
					</div>
					</c:if>
				</div>
			</div>
  		</div>

		<div id="message">
			<div class="loading" id="loading" style="visibility: hidden;">
				Chargement...<br /> 
				<img id="loading_bar" src="<c:url value='/static/img/loading_bar.png'/>" />
			</div>
	
			<div class="error" id="error" style="visibility: hidden;">
				Erreur
			</div>
	
			<div class="confirm" id="confirm" style="visibility: hidden;"></div>
	
			<div class="info" id="info" style="visibility: hidden;"></div>
		</div>

	<div class="container" id="container">
		<decorator:body />
	</div>
  </body>
</html>