<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<head>
<title>Sistem za upravljanje dokumentima</title>

<spring:url value="/resources/core/css/hello.css" var="coreCss" />
<spring:url value="/resources/core/css/bootstrap.min.css"	var="bootstrapCss" />
<spring:url value="/resources/core/css/style.css"	var="styleCss" />
<link href="${bootstrapCss}" rel="stylesheet" />
<link href="${coreCss}" rel="stylesheet" />
<link href="${styleCss}" rel="stylesheet" />
</head>

<spring:url value="/" var="urlHome" />
<spring:url value="/korisnici" var="urlKorisnici" />
<spring:url value="/navigation/login" var="urlLogin" />
<spring:url value="/navigation/logout" var="urlLogout" />
<spring:url value="/uloge" var="urlUloge" />
<spring:url value="/vidljivosti" var="urlVidljivosti" />

<spring:url value="/dokumenti" var="urlDokumenti" />


<nav class="navbar navbar-inverse ">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand">Dokumet menadzment sistem</a>
		</div>
		<div id="navbar">
			<ul class="nav navbar-nav navbar-right">
				<li class="active"><a href="${urlLogout}">Logout</a></li>
			</ul>
			
			<ul class="nav navbar-nav navbar-right">
				<li class="active"><a href="${urlUloge}">Uloge</a></li>
			</ul>
			
			<ul class="nav navbar-nav navbar-right">
				<li class="active"><a href="${urlVidljivosti}">Vidljivosti</a></li>
			</ul>
					
			<ul class="nav navbar-nav navbar-right">
				<li class="active"><a href="${urlKorisnici}">Korisnici</a></li>
			</ul>
			
			<ul class="nav navbar-nav navbar-right">
				<li class="active"><a href="${urlDokumenti}">Dokumenti</a></li>
			</ul>
		</div>
	</div>
</nav>