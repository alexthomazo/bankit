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
		<title>Arrêt</title>
		<script type="text/javascript">
			//hiding navbar to avoid click on links
			window.addEvent('domready', function() {
				$$('.navbar').setStyle('display', 'none');
			});
			window.addEvent('load', function() {
				shutdown();
			});
		</script>
	</head>

	<body>
		<div class="row">
			<div class="span6 offset3 well" style="text-align: center">
				<div id="loading-stop">
					<h2>Arrêt de BankIt en cours</h2>
					<img src="<c:url value='/static/img/stop-loader.gif'/>">
				</div>
				<div id="stopped" style="display: none">
					<h2>BankIt est arrêté</h2>
					<h4>Vous pouvez maintenant fermer cette fenêtre.</h4>
				</div>
			</div>
		</div>
	</body>

</html>