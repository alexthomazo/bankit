<?php
$channel = 'stable'; 
if (isset($_GET['channel']) && is_channel_valid($_GET['channel'])) {
	$channel = $_GET['channel'];
}
?>
<div class="hero-unit" style="text-align: center;">
	<h1>BankIt</h1>
	<p>Un logiciel simple de prévision de vos dépenses</p>
	<p>
		<a href="download/<?php echo get_download_file($channel); ?>" class="btn btn-primary btn-large">Télécharger</a><br>
		<small style="color: #bbbbbb"><?php echo get_git_property("git.commit.id.describe", $channel); ?></small>
	</p>
</div>

<div style="text-align: center;">
	<h2>Fonctionnalités de BankIt</h2>
	<p>Un achat à prévoir ? Souvent à découvert ?<br>
		Utilisez BankIt pour anticiper vos revenus et vos dépenses !<br>
		BankIt vous permet de prévoir sur les deux prochains mois vos dépenses régulières.
	</p>

	<div class="row">
		<div class="span4">
			<h3>Opérations</h3>
			<div class="thumbnail">
				<img src="static/img/bankit-compte.png">
			</div>
			<p>Affichez la liste des opérations effectuée sur votre compte
				ansi que les futures opérations planifiées. Un cumul permet
				de connaître le solde de votre compte à tout moment.
			</p>
		</div>
		
		<div class="span4">
			<h3>Synchronisation</h3>
			<div class="thumbnail">
				<img src="static/img/bankit-sync.png">
			</div>
			<p>Ne vous embêtez pas à saisir les opérations effectuées sur votre compte,
				vous pouvez importer le fichier des opérations directement depuis votre banque.
			</p>
			<p>Les opérations planifiées seront automatiquement fusionnées avec les opérations importées.</p>
		</div>
		
		<div class="span4">
			<h3>Charges</h3>
			<div class="thumbnail">
				<img src="static/img/bankit-charges.png">
			</div>
			<p>Saisissez la liste des charges et revenus récurrents. Il seront ajoutés automatiquement
				à la liste de vos opérations plannifiées et vous permetterons de suivre votre solde
				prévisonnel.
			</p>
		</div>
	</div>
</div>