<?php
$configfile = "config.php";
if (! empty ( $_POST )) {
	
	$string = $_POST ["json"];
	$jsonFileName = $_POST ["jsonFileName"];
	$configFlag = $_POST ["configFlag"];
	$script = "";
	if ($configFlag == "true") {
		// Datei neu erstellen
		unlink ( $configfile );
		array_map ( 'unlink', glob ( "tables/*.html" ) );
		array_map ( 'unlink', glob ( "jsonFiles/*.json" ) );
		$script .= '<?php' . "\n";
		
	}
	$file = htmlspecialchars ( 'jsonFiles/' . $jsonFileName . '.json' );
	$bodytag = html_entity_decode ( $string, ENT_QUOTES );
	file_put_contents ( $file, $bodytag );
	$script .= '  $jsonFiles[] = "' . $file . '";' . "\n";
	
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
 
