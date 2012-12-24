<?php
header('Content-Type: application/javascript');
require('lib.php');

$channel = 'stable';
if (isset($_GET['channel']) && is_channel_valid($_GET['channel'])) {
	$channel = $_GET['channel'];
}
if (isset($_GET['callback'])) {
	echo $_GET['callback'] . "(";
}
echo "{'commit':'" . get_git_property("git.commit.id", $channel) . "'}";

if (isset($_GET['callback'])) {
	echo ");";
}
?>
