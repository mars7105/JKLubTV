<?php
session_start ();
include 'wrap.php';
if (! empty ( $_GET ['param'] ) && ! empty ( $_SESSION ['content'] )) {
	showMenu ();
} else {
	createHTMLTables ();
	showMenu ();
}
function showMenu() {
	$wrapper = new Wrap ();
	$param = $_GET ['param'];
	$content = $_SESSION ['content'];
	$files = $_SESSION ['files'];
	
	$table = file_get_contents ( $files [$param] );
	$content .= '<div class="container theme-showcase" role="main">
	<!-- Main jumbotron for a primary marketing message or call to action -->
	<div class="col-md-8">';
	$content .= $table;
	$content .= '</div> <!-- col-sm-8 blog-main -->';
	$sidePanels = $_SESSION ['sidePanels'];
	$sidebar .= createSidebarPanel ( $sidePanels );
	$content .= $wrapper->wrapSidebar ( $sidebar ) . '</div> <!--container -->';
	$content .= createFooter ();
	
	echo $content;
	session_destroy ();
}
function createHTMLTables() {
	$timeStampFilename = 'tables/timestamp.json';
	$timeStamp = "0";
	if (file_exists ( $timeStampFilename )) {
		$timeStamphandle = fopen ( $timeStampFilename, "r" );
		
		$timeStampjson = fread ( $timeStamphandle, filesize ( $timeStampFilename ) );
		fclose ( $handle );
		$timeStamp = json_decode ( $timeStampjson, true );
	}
	
	$wrapper = new Wrap ();
	// include 'config.php';
	$configJSON = 'config.json';
	$handle = fopen ( $configJSON, "r" );
	$json = fread ( $handle, filesize ( $configJSON ) );
	fclose ( $handle );
	$jsonFiles = json_decode ( $json, true );
	
	$file [] = array ();
	$content = '';
	$menuLinks = '';
	$index = 0;
	
	foreach ( $jsonFiles['filename'] as $filename ) {
		if (file_exists ( $filename )) {
			// liest den Inhalt einer Datei in einen String
			$handle = fopen ( $filename, "r" );
			$json = fread ( $handle, filesize ( $filename ) );
			fclose ( $handle );
			$data = json_decode ( $json, true );
			$timeStampJSON [$index] = $data ["timeStamp"];
			
			// CROSSTABLE
			$allContent = '';
			$greyContent = '';
			$greenCrossContent = '';
			$greenMeetingContent = '';
			$greyH1 = $data ["tournamentName"] . ' - ' . $data ["groupName"];
			$greenCrossH1 = $data ["jsonCrossTitle"];
			$greenMeetingH1 = $data ["jsonMeetingtitle"];
			
			$crosstable = $data ["crossTable"];
			$cTable = createTable ( $crosstable );
			
			$crossTableText = $data ["crossTableText"];
			$cTable .= $crossTableText [$index];
			$greenCrossContent = $wrapper->wrapGreyContent ( $greenCrossH1, $cTable );
			
			// MEETINGTABLE
			$meetingtable = $data ["meetingTable"];
			$mTable = createTable ( $meetingtable );
			
			$meetingTableText = $data ["meetingTableText"];
			$mTable .= $meetingTableText [$index];
			$greenMeetingContent = $wrapper->wrapGreyContent ( $greenMeetingH1, $mTable );
			
			$allContent = '<h1 class="well">' . $greyH1 . '</h1>';
			$allContent .= $greenCrossContent . $greenMeetingContent;
			$fileName = 'tables/' . $data ["tournamentName"] . '-' . $data ["groupName"] . '.html';
			$file [$index] = $fileName;
			if (strcmp ( $timeStampJSON [$index], $timeStamp [$index] ) != 0) {
				
				file_put_contents ( $fileName, $allContent );
			}
			$menuLinks .= '<li><a href="index.php?param=' . $index . '" >' . $data ["groupName"] . '</a></li>' . "\n";
			$index ++;
		}
	}
	file_put_contents ( $timeStampFilename, json_encode ( $timeStampJSON ) );
	
	$menuName = $data ["menuName"];
	$content = $wrapper->wrapNavigation ( $data ["siteName"], $menuName, $menuLinks );
	
	$_SESSION ['sidePanels'] = $data ["sidePanels"];
	$_SESSION ['content'] = $content;
	$_SESSION ['files'] = $file;
}
function createTable($table) {
	$tempTable = '';
	$tempTable .= '<table class="table table-bordered well">' . "\n";
	$counter = 0;
	// $crosstable = $data ["crossTable"];
	foreach ( $table as $jsons ) {
		$tempTable .= '  <tr>' . "\n";
		
		foreach ( $jsons as $key => $rvalue ) {
			if ($counter == 0) {
				$tempTable .= '    <th class="alert-warning">' . $rvalue . '</th>' . "\n";
			} else {
				$tempTable .= '    <td>' . $rvalue . '</td>' . "\n";
			}
		}
		$tempTable .= '  </tr>' . "\n";
		$counter ++;
	}
	$tempTable .= '</table>' . "\n";
	return $tempTable;
}
function createSidebarPanel($panelText) {
	$wrapper = new Wrap ();
	$sidebar = '';
	for($i = 0; $i < count ( $panelText ); $i = $i + 2) {
		$sidebarModule = $wrapper->wrapGreyContent ( $panelText [$i], '<p>' . $panelText [$i + 1] . '</p>' );
		$sidebar .= $wrapper->wrapSidebarModule ( $sidebarModule );
	}
	return $sidebar;
}
function createFooter() {
	$wrap = '
	<footer class="blog-footer">
		<p>
			<a href="http://getbootstrap.com">Bootstrap</a>
		</p>
		<p>
			<a href="#">Back to top</a>
		</p>
	</footer>';
	return $wrap;
}
?>
	
