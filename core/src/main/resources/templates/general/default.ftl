<#macro default_layout>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>windbag</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- css -->
    <link rel="stylesheet" href="/assets/css/bootstrap.css" media="screen">
    <link rel="stylesheet" href="/assets/css/main.css" media="screen">
</head>
<body>
<script type="text/x-handlebars">
<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
	<div class="container-fluid">
		<div class="navbar-header">
			{{#link-to 'index' class='navbar-brand'}}windbag{{/link-to}}
		</div>
	</div>
</div>

<div class="container-fluid">
	<div class="row">
		<div class="col-md-2">
			<h2>Servers</h2>
	</div>

		{{outlet}}
	</div>

	<hr>

	<footer>
		<p>zeroDi, 2014</p>
	</footer>
</div>
</script>

<script type="text/x-handlebars" id="index">
<div class="col-md-10">
	<ul class="nav nav-pills">
		<li class="dropdown">
			<a class="dropdown-toggle" data-toggle="dropdown" href="#">
				domain <span class="caret"></span>
			</a>
		</li>
	</ul>
</div>

<div class="col-md-10">
	{{#each}}
		<p>{{id}}: {{server_address}}:{{server_port}}</p>
	{{/each}}
</div>
</script>

<!-- /container -->
<!-- javascript -->
<script src="/assets/js/ember/jquery-1.10.2.js"></script>
<script src="/assets/js/ember/handlebars-1.1.2.js"></script>
<script src="/assets/js/ember/ember-1.4.0.js"></script>
<script src="/assets/js/ember/ember-data.prod.js"></script>
<script src="/assets/js/bootstrap.js"></script>
<script src="/assets/js/app/app.js"></script>
<script src="/assets/js/app/routes.js"></script>
</body>
</html>
</#macro>
