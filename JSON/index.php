<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>JSON to HTML Table Demo</title>
<script src="scripts/jquery-1.3.2.debug.js" type="text/javascript"></script>
<script src="scripts/json.htmTable.js" type="text/javascript"></script>
<script src="scripts/json.debug.js" type="text/javascript"></script>
<link href="styles/default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	$(document).ready(
			function() {
				jQuery.getJSON("jsonFiles/crosstable0.json", function(data) {
					$('#crossGrid').append(
							CreateTableView(data, "lightPro", false)).fadeIn();
				});
				jQuery.getJSON("jsonFiles/meetingtable0.json", function(data) {
					$('#meetingGrid').append(
							CreateTableView(data, "lightPro", false)).fadeIn();
				});

				$('#crossDynamicGridLoading').hide();
				$('#meetingDynamicGridLoading').hide();
			});
</script>

</head>
<body>
	<h2>Kreuztabelle</h2>
	<form id="form1">
		<div id="crossGrid">
			<div id="crossDynamicGridLoading">
				<img src="images/loading.gif" /><span> Loading Data... </span>
			</div>
		</div>
	</form>
	<h2>Termintabelle</h2>
	<form id="form2">
		<div id="meetingGrid">
			<div id="meetingDynamicGridLoading">
				<img src="images/loading.gif" /><span> Loading Data... </span>
			</div>
		</div>
	</form>
</body>
</html>
