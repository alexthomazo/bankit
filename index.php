<!DOCTYPE html>
<html lang="fr">
<?php
require('lib.php');
$page = "home";
if (isset($_GET['p'])) {
	switch ($_GET['p']) {
		case "install.htm": $page = "install"; break;
		case "use.htm": $page = "use"; break;
		case "contribute.htm": $page = "contribute"; break;
	}
}
?>
	<head>
		<title>BankIt</title>
		<meta http-equiv="Content-Type" content="text/HTML; charset=UTF-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		
		<link href="static/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
		<link href="static/css/bootstrap-responsive.min.css" type="text/css" rel="stylesheet" />
		<link href="static/css/style.css" type="text/css" rel="stylesheet" />

		<script src="static/js/jquery-1.8.2.min.js" type="text/javascript"></script>
		<script src="static/js/bootstrap.min.js" type="text/javascript"></script>
		<script src="static/js/mootools-core-1.4.5.js" type="text/javascript"></script>
	</head>
	<body>
		<div class="navbar navbar-fixed-top navbar-inverse">
			<div class="navbar-inner">
				<div class="container">
					<a class="brand" href="./">
						BankIt
					</a>

					<ul class="nav">
						<li class="divider-vertical"></li>
						<li <?php if ($page=="install") echo 'class="active"' ?>>
							<a href="?p=install.htm">Installer</a>
						</li>
						
						<li class="divider-vertical"></li>
						<li <?php if ($page=="use") echo 'class="active"' ?>>
							<a href="?p=use.htm">Utiliser</a>
						</li>
						
						<li class="divider-vertical"></li>
						<li <?php if ($page=="contribute") echo 'class="active"' ?>>
							<a href="?p=contribute.htm">Contribuer</a>
						</li>
					</ul>
				</div>
			</div>
  		</div>

		<div class="container" id="container">
			<?php 
				include("views/" . $page . ".php");
			?>
		</div>
		
		<!-- Piwik -->
		<script type="text/javascript">
		var pkBaseURL = (("https:" == document.location.protocol) ? "https://stats.alexlg.eu.org/" : "http://stats.alexlg.eu.org/");
		document.write(unescape("%3Cscript src='" + pkBaseURL + "piwik.js' type='text/javascript'%3E%3C/script%3E"));
		</script><script type="text/javascript">
		try {
		var piwikTracker = Piwik.getTracker(pkBaseURL + "piwik.php", 4);
		piwikTracker.trackPageView();
		piwikTracker.enableLinkTracking();
		} catch( err ) {}
		</script><noscript><p><img src="http://stats.alexlg.eu.org/piwik.php?idsite=4" style="border:0" alt="" /></p></noscript>
		<!-- End Piwik Tracking Code -->
	</body>
</html>