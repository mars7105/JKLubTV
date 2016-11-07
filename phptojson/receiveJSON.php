<?php
$configfile = "config.php";
if (! empty ( $_POST )) {
	
	$string = $_POST ["json"];
	$name = $_POST ["name"];
	$title = $_POST ["title"];
	$groupname = $_POST ["groupname"];
	$tabletype = $_POST ["tabletype"];
	$configFlag = $_POST ["configFlag"];
	$script = "";
	if ($configFlag == "true") {
		// Datei neu erstellen
		unlink ( $configfile );
		array_map ( 'unlink', glob ( "tables/*.html" ) );
		array_map ( 'unlink', glob ( "jsonFiles/*.json" ) );
		$script .= '<?php' . "\n";
		$script .= '  $jsonFiles[] = array();' . "\n";
		$script .= '  $title[] = array();' . "\n";
		$script .= '  $groupname[] = array();' . "\n";
		$script .= '  $tabletype[] = array();' . "\n";
	}
	$file = htmlspecialchars ( 'jsonFiles/' . $name . '.json' );
	$bodytag = html_entity_decode ( $string, ENT_QUOTES );
	file_put_contents ( $file, $bodytag );
	$script .= '  $jsonFiles[] = "' . $file . '";' . "\n";
	$script .= '  $title[] = "' . $title . '";' . "\n";
	$script .= '  $groupname[] = "' . $groupname . '";' . "\n";
	$script .= '  $tabletype[] = "' . $tabletype . '";' . "\n";
	
	file_put_contents ( $configfile, $script, FILE_APPEND );
	
	echo "Ok";
} else {
	echo "ERROR!";
}
// delete all files in tables/ and jsonFiles/
function deleteAllTempFiles() {
	$files1 = glob ( 'tables/*' ); // get all file names
	$files2 = glob ( 'jsonFiles/*' ); // get all file names
	foreach ( $files1 as $file ) { // iterate files
		if (is_file ( $file )) {
			unlink ( $file ); // delete file
		}
	}
	foreach ( $files2 as $file ) { // iterate files
		if (is_file ( $file )) {
			unlink ( $file ); // delete file
		}
	}
}
?>
 
