<?php
$configfile = "config.php";
if (! empty ( $_POST )) {
	$string = $_POST ["json"];
	$name = $_POST ["name"];
	$title = $_POST ["title"];
	$groupname = $_POST ["groupname"];
	$tabletype = $_POST ["tabletype"];
	$configFlag = $_POST ["configFlag"];
	$file = htmlspecialchars ( 'jsonFiles/' . $name . '.json' );
	$bodytag = html_entity_decode ( $string, ENT_QUOTES );
	file_put_contents ( $file, $bodytag );
	$script = "";
	if ($configFlag == "true") {
		// Datei neu erstellen
		unlink ( $configfile );
		$script .= '<?php' . "\n";
		$script .= '  $jsonFiles[] = array();' . "\n";
		$script .= '  $title[] = array();' . "\n";
		$script .= '  $groupname[] = array();' . "\n";
		$script .= '  $tabletype[] = array();' . "\n";
	}
	$script .= '  $jsonFiles[] = "' . $file . '";' . "\n";
	$script .= '  $title[] = "' . $title . '";' . "\n";
	$script .= '  $groupname[] = "' . $groupname . '";' . "\n";
	$script .= '  $tabletype[] = "' . $tabletype . '";' . "\n";
	
	file_put_contents ( $configfile, $script, FILE_APPEND );
	
	echo "Ok";
} else {
	echo "ERROR!";
}

?>
 
