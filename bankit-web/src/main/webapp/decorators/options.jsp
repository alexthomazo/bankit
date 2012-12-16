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
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<page:applyDecorator name="main">
<html lang="en">
	<head>
		<title><decorator:title default="Options" /></title>
		<decorator:head />
	</head>
	<body>
		
		<div class="row">
			<!-- Options list -->
			<div class="span2">
				<div class="well sidebar-nav">
					<ul class="nav nav-list">
						<li class="nav-header">Options</li>
						<li class="${page == 'updates' ? 'active' : ''}"><a href="<c:url value='/options/updates' />">Mises à jour</a></li>
					</ul>
				</div>
			</div>

			<!-- Container -->
			<div class="span10">
				<c:if test="${saved == 'ok'}">
			    <div class="alert alert-success">
					<button type="button" class="close" data-dismiss="alert">×</button>
					<strong>Options enregistrées.</strong>
				</div>
				</c:if>
			
				<!-- Content -->
				<decorator:body />				
			</div>
		</div>
	</body>
</html>
</page:applyDecorator>