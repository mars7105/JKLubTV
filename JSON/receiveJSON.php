<?php
$configfile = "config.js";
if (! empty ( $_POST )) {
	$string = $_POST ["json"];
	$name = $_POST ["name"];
	$configFlag = $_POST ["configFlag"];
	$file = htmlspecialchars ( 'jsonFiles/' . $name . '.json' );
	$bodytag = html_entity_decode ( $string, ENT_QUOTES );
	file_put_contents ( $file, $bodytag );
	$script = '';
	if ($configFlag == "true") {
		// Datei neu erstellen
		unlink ( $configfile );
		$script = 'var jsonFiles = [];' . "\n";
	}
	$script .= "jsonFiles.push('" . $file . "');" . "\n";
	
	// Datei nicht neu erstellen
	file_put_contents ( $configfile, $script, FILE_APPEND );
	
	echo "Ok";
} else {
	echo "ERROR!";
}

?>
 
