<?php
if (! empty ( $_POST )) {
	
	$string = $_POST ["json"];
	$jsonFileName = $_POST ["jsonFileName"];
	$menuName = $_POST ["menuName"];
	$configFlag = $_POST ["configFlag"];
	// $script = "";
	// if ($configFlag == "true") {
	// Datei neu erstellen
	// unlink ( $configfile );
	// array_map ( 'unlink', glob ( "tables/*.html" ) );
	// array_map ( 'unlink', glob ( "jsonFiles/*.json" ) );
	// $script .= '<?php' . "\n";
	// $script .= ' $menuName = "' . $menuName . '";' . "\n";
	// $script .= ' $jsonFiles[] = array();' . "\n";
	// }
	$file = htmlspecialchars ( 'jsonFiles/' . $jsonFileName );
	$bodytag = html_entity_decode ( $string, ENT_QUOTES );
	file_put_contents ( $file, $bodytag );
	// $script .= ' $jsonFiles[] = "' . $file . '";' . "\n";
	
	// file_put_contents ( $configfile, $script, FILE_APPEND );
	
	echo "Ok";
}

?>
