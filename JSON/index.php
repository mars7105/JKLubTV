<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>JSON to HTML Table Demo</title>
<script src="scripts/jquery-1.3.2.debug.js" type="text/javascript"></script>

<script src="scripts/json.htmTable.js" type="text/javascript"></script>
<script src="scripts/json.debug.js" type="text/javascript"></script>
<script src="config.js" type="text/javascript"></script>

<link href="styles/default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	$(document).ready(
			function() {
				$.each( jsonFiles, function( index, value ){
					jQuery.getJSON(value, function(data) {
						$('#Grid').append('<div id=' + '"table' + index + '"' + '>' + CreateTableView(data, "lightPro", false)) + '</div>';
					});
				   
				});
			
				$('#DynamicGridLoading').hide();
			});
</script>

</head>
<body>
	<h2 class="tableCSS">Kreuztabelle</h2>
	<form id="form1">
		<div id="Grid">
			<div id="DynamicGridLoading">
				<img src="images/loading.gif" /><span> Loading Data... </span>
			</div>
		</div>
	</form>

</body>
</html>
