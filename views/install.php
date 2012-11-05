<div class="row">
	<div class="offset1 span10">
		<div class="well" style="text-align: center; font-size: 120%;">
		Avant tout, veuillez installer la dernière version de Java : <a href="http://www.java.com/download/" target="_blank">http://www.java.com/download/</a>
		</div>
		
		<section id="win">
			<h3>Windows</h3>
			<p>Extraire le fichier zip téléchargé. Si vous n'avez pas de logiciel d'extraction comme <a href="http://www.spiroo.be/7zip/" target="_blank">7-Zip</a>, vous pouvez utiliser celui intégré dans Windows.</p>
			<p>Cliquer du bouton droit sur le fichier puis sélectionner l'option <strong>Extraire tout...</strong>&nbsp;:</p>
			<p style="text-align: center;"><img src="static/img/bankit-win1.png" class="img-polaroid"></p>
			<p>Valider le chemin proposé par défaut (il s'agit du répertoire courant) et laisser la case cochée, puis cliquer sur <strong>Extraire</strong>.</p>
			<p>Le répertoire contenant les fichiers extraits s'affiche. Il suffit de double cliquer sur l'icône <strong>bankit</strong> ou <strong>bankit.bat</strong> pour lancer l'application&nbsp;:</p>
			<p style="text-align: center;"><img src="static/img/bankit-win2.png" class="img-polaroid"></p>
		</section>
		
		<section id="mac">
			<h3>Mac OS X</h3>
			<p>Extraire le fichier zip téléchargé en double cliquant dessus depuis le finder&nbsp;:</p>
			<p style="text-align: center;"><img src="static/img/bankit-mac1.png" class="img-polaroid"></p>
			<p>Entrer dans le dossier ainsi créé puis double cliquer sur le fichier <strong>bankit-standalone.jar</strong>, l'application se lance&nbsp;:</p>
			<p style="text-align: center;"><img src="static/img/bankit-mac2.png" class="img-polaroid"></p>
		</section>
		
		<section id="nux">
			<h3>Linux</h3>
			<h4>KDE</h4>
				<p>Extraire le fichier zip téléchargé en cliquant du bouton droit sur celui-ci puis en sélectionnant l'option <strong>Extraire -&gt; Extraire l'archive ici, auto-détecter les sous-dossiers</strong>&nbsp;:</p>
				<p style="text-align: center;"><img src="static/img/bankit-kde1.png" class="img-polaroid"></p>
				<p>Entrer dans le dossier ainsi créé puis rendre le fichier <strong>bankit.sh</strong> exécutable. Pour cela, cliquer du bouton droit sur celui-ci puis sélectionner <strong>Propriétés</strong>.</p>
				<p>Dans l'onglet <strong>Droits d'accès</strong>, cocher la case <strong>est exécutable</strong> puis cliquer sur OK.</p>
				<p style="text-align: center;"><img src="static/img/bankit-kde2.png" class="img-polaroid"></p>
				<p>Double cliquer sur le fichier <strong>bankit.sh</strong> pour lancer l'application.</p>
				
			<h4>Ligne de commande</h4>
			<p>Exécuter les commandes suivantes pour extraire et assigner les bons droits au fichier&nbsp;:</p>
<pre>
$ unzip <?php echo get_download_file() . "\n"; ?>
$ chmod a+x bankit.sh
</pre>
			<p>Exécuter ensuite la commande suivante pour lancer l'application</p>
<pre>
$ ./bankit.sh
</pre>
		</section>
	</div>
</div>

<script type="text/javascript">
//put the section on top according to user OS
document.addEvent('domready', function() {
	if (Browser.Platform.mac) {
		document.id('mac').inject(document.id('win'), 'before');
	} else if (Browser.Platform.linux) {
		document.id('nux').inject(document.id('win'), 'before');
	}
});
</script>