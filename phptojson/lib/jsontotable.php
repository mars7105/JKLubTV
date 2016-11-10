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
	<div class="col-sm-8 blog-main">';
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
	$wrapper = new Wrap ();
	include 'config.php';
	$file [] = array ();
	$content = '';
	$menuLinks = '';
	$index = 0;
	
	foreach ( $jsonFiles as $filename ) {
		
		// liest den Inhalt einer Datei in einen String
		$handle = fopen ( $filename, "r" );
		$json = fread ( $handle, filesize ( $filename ) );
		fclose ( $handle );
		$data = json_decode ( $json, true );
		
		// CROSSTABLE
		$allContent = '';
		$greyContent = '';
		$greenCrossContent = '';
		$greenMeetingContent = '';
		$greyH1 = $data ["tournamentName"] . ' - ' . $data ["groupName"];
		$greenCrossH1 = $data ["jsonCrossTitle"];
		$greenMeetingH1 = $data ["jsonMeetingtitle"];
		
		$cTable = '';
		$cTable .= '<table class="table table-bordered well">' . "\n";
		$counter = 0;
		$crosstable = $data ["crossTable"];
		foreach ( $crosstable as $jsons ) {
			$cTable .= '  <tr>' . "\n";
			
			foreach ( $jsons as $key => $rvalue ) {
				if ($counter == 0) {
					$cTable .= '    <th class="alert-warning">' . $rvalue . '</th>' . "\n";
				} else {
					$cTable .= '    <td>' . $rvalue . '</td>' . "\n";
				}
			}
			$cTable .= '  </tr>' . "\n";
			$counter ++;
		}
		$cTable .= '</table>' . "\n";
		$crossTableText = $data ["crossTableText"];
		$cTable .= $crossTableText [$index];
		$greenCrossContent = $wrapper->wrapGreyContent ( $greenCrossH1, $cTable );
		
		// MEETINGTABLE
		
		$mTable = '';
		$mTable .= '<table class="table table-bordered well">' . "\n";
		$counter = 0;
		$meetingtable = $data ["meetingTable"];
		foreach ( $meetingtable as $jsons ) {
			$mTable .= '  <tr>' . "\n";
			
			foreach ( $jsons as $key => $rvalue ) {
				if ($counter == 0) {
					$mTable .= '    <th class="alert-warning">' . $rvalue . '</th>' . "\n";
				} else {
					$mTable .= '    <td>' . $rvalue . '</td>' . "\n";
				}
			}
			$mTable .= '  </tr>' . "\n";
			$counter ++;
		}
		$mTable .= '</table>' . "\n";
		$meetingTableText = $data ["meetingTableText"];
		$mTable .= $meetingTableText [$index];
		$greenMeetingContent = $wrapper->wrapGreyContent ( $greenMeetingH1, $mTable );
		
		$allContent = '<h1 class="well">' . $greyH1 . '</h1>';
		$allContent .= $greenCrossContent . $greenMeetingContent;
		$fileName = 'tables/' . $data ["tournamentName"] . '-' . $data ["groupName"] . '.html';
		
		file_put_contents ( $fileName, $allContent );
		$file [$index] = $fileName;
		$menuLinks .= '<li><a href="index.php?param=' . $index . '" >' . $data ["groupName"] . '</a></li>' . "\n";
		$index ++;
	}
	$menuName = $data ["menuName"];
	$content = $wrapper->wrapNavigation ( $data ["siteName"], $menuName, $menuLinks );
	;
	$_SESSION ['sidePanels'] = $data ["sidePanels"];
	$_SESSION ['content'] = $content;
	$_SESSION ['files'] = $file;
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
	$wrapper = new Wrap ();
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
	