<?php 
/**
 * File containing some utilities functions
 */

/**
 * Get a property from the git.properties file.
 * @param string $property Property name
 * @return string Value of the property
 */
function get_git_property($property) {
	$prop_val = "";
	
	$props = file("download/git.properties");
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
function get_download_file() {
	$file = "";
	
	if ($handle = opendir('download/')) {	
		/* loop over the directory. */
		while (false !== ($entry = readdir($handle))) {
			if (substr($entry, -4) == ".zip") {
				$file = $entry;
				break;
			}
		}
		
		closedir($handle);
	}
	
	return $file;
}
?>