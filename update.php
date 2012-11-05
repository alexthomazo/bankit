<?php 
require('lib.php');

if (isset($_GET['callback'])) {
	echo $_GET['callback'] . "(";
}
echo "{'commit':'" . get_git_property("git.commit.id") . "'}";

if (isset($_GET['callback'])) {
	echo ");";
}
?>