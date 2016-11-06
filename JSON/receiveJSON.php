<?php
if (! empty ( $_POST )) {
	$string = $_POST ["json"];
	$name = $_POST ["name"];
	$file = htmlspecialchars ( 'jsonFiles/' . $name . '.json' );
	$bodytag = html_entity_decode ( $string, ENT_QUOTES );
	file_put_contents ( $file, $bodytag );
	echo "Ok";
} else {
	echo "ERROR!";
}

?>
 
