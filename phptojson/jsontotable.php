<?php
session_start ();
if (! empty ( $_GET ['param'] ) && ! empty ( $_SESSION ['content'] )) {
	showMenu ();
} else {
	createHTMLTables ();
	showMenu ();
}
function showMenu() {
	$param = $_GET ['param'];
	$content = $_SESSION ['content'];
	$files = $_SESSION ['files'];
	
	$table = file_get_contents ( $files [$param] );
	$content .= $table;
	echo $content;
	session_destroy ();
}
function createHTMLTables() {
	include 'config.php';
	$file [] = array ();
	$content = '';
	
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
          <p class="navbar-brand">JKlubTV Frontend</p>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav">			
			<li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">' . $menuName . '<span class="caret"></span></a>
              <ul class="dropdown-menu">';
	foreach ( $jsonFiles as $filename ) {
		
		// liest den Inhalt einer Datei in einen String
		$handle = fopen ( $filename, "r" );
		$json = fread ( $handle, filesize ( $filename ) );
		fclose ( $handle );
		$data = json_decode ( $json, true );
		// var_dump ( $data );
		
		// CROSSTABLE
		$table = '';
		$table .= '	<div class="panel panel-default"><div class="panel-heading">' . "\n";
		$table .= '		<h1 class="panel-title"> ' . $data ["tournamentName"] . ' - ' . $data ["groupName"] . '</h1>' . "\n";
		$table .= '	</div>' . "\n";
		
		$table .= '<div class="panel-body"><div class="panel panel-success">
            <div class="panel-heading">
              <h3 class="panel-title"> ' . $data [jsonCrossTitle] . "</h3>\n";
		
		$table .= '</div>
            <div class="panel-body"><div class="row"><div class="col-md-6"><table class="table table-bordered lightPro">' . "\n";
		$counter = 0;
		$crosstable = $data ["crossTable"];
		foreach ( $crosstable as $jsons ) {
			$table .= '  <tr>' . "\n";
			
			foreach ( $jsons as $key => $rvalue ) {
				if ($counter == 0) {
					$table .= '    <th>' . $rvalue . '</th>' . "\n";
				} else {
					$table .= '    <td>' . $rvalue . '</td>' . "\n";
				}
			}
			$table .= '  </tr>' . "\n";
			$counter ++;
		}
		$table .= '</table></div></div></div> </div> </div>' . "\n";
		// MEETINGTABLE
		
		$table .= '<div class="panel-body"><div class="panel panel-success">
            <div class="panel-heading">
              <h3 class="panel-title"> ' . $data [jsonMeetingtitle] . "</h3>\n";
		
		$table .= '</div>
            <div class="panel-body"><div class="row"><div class="col-md-6"><table class="table table-bordered lightPro">' . "\n";
		$counter = 0;
		$meetingtable = $data ["meetingTable"];
		foreach ( $meetingtable as $jsons ) {
			$table .= '  <tr>' . "\n";
			
			foreach ( $jsons as $key => $rvalue ) {
				if ($counter == 0) {
					$table .= '    <th>' . $rvalue . '</th>' . "\n";
				} else {
					$table .= '    <td>' . $rvalue . '</td>' . "\n";
				}
			}
			$table .= '  </tr>' . "\n";
			$counter ++;
		}
		$table .= '</table></div></div></div> </div> </div>
          </div>' . "\n";
		$fileName = 'tables/' . $data [tournamentName] . '-' . $data [groupName] . '.html';
		
		file_put_contents ( $fileName, $table );
		$file [$index] = $fileName;
		$content .= '<li><a href="index.php?param=' . $index . '" >' . $data [groupName] . '</a></li>';
		$index ++;
	}
	$content .= '    </ul>
            </li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>';
	$content .= '<div class="container theme-showcase" role="main">' . "\n" . '<!-- Main jumbotron for a primary marketing message or call to action -->' . "\n";
	$_SESSION ['content'] = $content;
	$_SESSION ['files'] = $file;
}
?>
	