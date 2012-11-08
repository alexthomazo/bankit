<?php
$subpage = "first";
if (isset($_GET['s'])) {
	switch ($_GET['s']) {
		case "first.htm": $subpage = "first"; break;
		case "costs.htm": $subpage = "costs"; break;
		case "operations.htm": $subpage = "operations"; break;
		case "sync.htm": $subpage = "sync"; break;
	}
}
?>
<div class="row">
	<div class="span2 well" style="padding: 8px 0;">
		<ul class="nav nav-list">
			<li class="nav-header">Utilisation</li>
			<li <?php if ($subpage=="first") echo 'class="active"' ?>><a href="?p=use&s=first">Premier lancement</a></li>
			<li <?php if ($subpage=="costs") echo 'class="active"' ?>><a href="?p=use&s=costs">Charges/Revenus</a></li>
			<li <?php if ($subpage=="operations") echo 'class="active"' ?>><a href="?p=use&s=operations">Operations</a></li>
			<li <?php if ($subpage=="sync") echo 'class="active"' ?>><a ?p=use&s=sync">Synchronisation</a></li>
		</ul>
	</div>
	
	<div class="span9">
		<?php 
			include("views/use/" . $subpage . ".php");
		?>
	</div>
</div>