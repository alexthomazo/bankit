<?php 
/**
 * File containing some utilities functions
 */

/**
 * Get a property from the git.properties file.
 * @param string $property Property name
 * @return string Value of the property
 */
function get_git_property($property, $channel = 'stable') {
	$prop_val = "";
	if (!is_channel_valid($channel)) return $prop_val;
	
	$props = file("download/$channel/git.properties");
	foreach ($props as $prop) {
		if (preg_match("/^$property=(.+)$/", $prop, $res)) {
			$prop_val = $res[1];
			break;
		}
	}
	
	return $prop_val;
}

/**
 * Search in download dir the zip file and return it's name.
 * @return string name of the first zip file found in download dir
 */
function get_download_file($channel = 'stable') {
	$file = "";
	if (!is_channel_valid($channel)) return $file;
	
	if ($handle = opendir("download/$channel/")) {	
		/* loop over the directory. */
		while (false !== ($entry = readdir($handle))) {
			if (substr($entry, -4) == ".zip") {
				$file = $entry;
				break;
			}
		}
		
		closedir($handle);
	}
	
	return $channel .'/' . $file;
}

/**
 * Check if a channel exists (stable, dev...)
 * @param string $channel Channel to test
 * @return bool if the channel is valid
 */
function is_channel_valid($channel) {
	switch ($channel) {
		case 'stable':
		case 'dev':
			return true;
			
		default:
			return false;
	}
}
?>