<?php
session_start ();
if (! empty ( $_GET ['param'] ) && ! empty ( $_SESSION ['content'] )) {
	showMenu ();
} else {
	createHTMLTables ();
}
function showMenu() {
	$param = $_GET ['param'];
	$content = $_SESSION ['content'];
	$files = $_SESSION ['files'];
	
	$table = file_get_contents ( $files [$param - 1] );
	$content .= $table;
	echo $content;
}
function createHTMLTables() {
	include 'config.php';
	$file [] = array ();
	$content = '';
	$index = 0;
	
	foreach ( $jsonFiles as $filename ) {
		if ($index > 0) {
			$table = '';
			// liest den Inhalt einer Datei in einen String
			$handle = fopen ( $filename, "r" );
			$json = fread ( $handle, filesize ( $filename ) );
			fclose ( $handle );
			$data = json_decode ( $json, true );
			// var_dump($data);
			$table .= '<h1> ' . $title [$index] . ' </h1>' . "\n";
			
			$table .= '<h2> ' . $groupname [$index] . ' </h2>' . "\n";
			
			$table .= '<h2> ' . $tabletype [$index] . ' </h2>' . "\n";
			
			$table .= '<table class="lightPro" border="1">' . "\n";
			foreach ( $data as $jsons ) {
				$table .= '  <tr>' . "\n";
				foreach ( $jsons as $rvalue ) {
					$table .= '    <td>' . $rvalue . '</td>' . "\n";
				}
				$table .= '  </tr>' . "\n";
			}
			$table .= '</table>' . "\n";
			$file [$index - 1] = 'tables/' . $groupname [$index] . '-' . $tabletype [$index] . '.html';
			$linkname [$index - 1] = $groupname [$index] . '-' . $tabletype [$index];
			file_put_contents ( $file [$index - 1], $table );
			$content .= '<a href="index.php?param=' . $index . '" >' . $linkname [$index - 1] . '</a>' . "\n";
		}
		
		$index ++;
	}
	$_SESSION ['content'] = $content;
	$_SESSION ['files'] = $file;
	echo $content;
}
?>
	