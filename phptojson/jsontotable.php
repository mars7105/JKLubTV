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
	$content = '<div class="container theme-showcase" role="main">' . "\n" . 

	'<!-- Main jumbotron for a primary marketing message or call to action -->' . "\n";
	
	$index = 0;
	$content .= '   <!-- Fixed navbar -->
    <nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="https://www.barmbeker-schachklub.de/">Barmbeker Schachklub</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav">';
	foreach ( $jsonFiles as $filename ) {
		if ($index > 0) {
			$table = '';
			// liest den Inhalt einer Datei in einen String
			$handle = fopen ( $filename, "r" );
			$json = fread ( $handle, filesize ( $filename ) );
			fclose ( $handle );
			$data = json_decode ( $json, true );
			// var_dump($data);
			$table .= '	<div class="page-header">' . "\n";
			$table .= '		<h1> ' . $title [$index] . ' </h1>' . "\n";
			$table .= '	</div>' . "\n";
			
			$table .= '	<div class="page-header"><h2> ' . $groupname [$index] . ' </h2></div>' . "\n";
			
			$table .= '	<div class="page-header"><h2> ' . $tabletype [$index] . ' </h2></div>' . "\n";
			
			$table .= '<div class="row"><div class="col-md-6"><table class="table table-bordered">' . "\n";
			$counter = 0;
			foreach ( $data as $jsons ) {
				$table .= '  <tr>' . "\n";
				
				foreach ( $jsons as $rvalue ) {
					if ($counter == 0) {
						$table .= '    <th>' . $rvalue . '</th>' . "\n";
					} else {
						$table .= '    <td>' . $rvalue . '</td>' . "\n";
					}
				}
				$table .= '  </tr>' . "\n";
				$counter ++;
			}
			$table .= '</table></div></div>' . "\n";
			$file [$index - 1] = 'tables/' . $groupname [$index] . '-' . $tabletype [$index] . '.html';
			$linkname [$index - 1] = $groupname [$index] . '-' . $tabletype [$index];
			file_put_contents ( $file [$index - 1], $table );
			// $content .= '<a href="index.php?param=' . $index . '" >' . $linkname [$index - 1] . '</a>' . "\n";
			
			$content .= '<li><a href="index.php?param=' . $index . '" >' . $linkname [$index - 1] . '</a></li>';
		}
		
		$index ++;
	}
	$content .= '</ul>
	</div><!--/.nav-collapse -->
	</div>
	</nav>';
	$_SESSION ['content'] = $content;
	$_SESSION ['files'] = $file;
	echo $content;
}
?>
	