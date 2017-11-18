<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<head>
<title>Sistem za upravljanje dokumentima</title>

<spring:url value="/resources/core/css/hello.css" var="coreCss" />
<spring:url value="/resources/core/css/bootstrap.min.css"	var="bootstrapCss" />
<link href="${bootstrapCss}" rel="stylesheet" />
<link href="${coreCss}" rel="stylesheet" />
</head>

<spring:url value="/" var="urlHome" />
<spring:url value="/korisnici" var="urlKorisnici" />
<spring:url value="/korisnici/dodaj" var="urlDodajKorisnika" />
<spring:url value="/navigation/login" var="urlLogin" />
<spring:url value="/navigation/logout" var="urlLogout" />
<spring:url value="/uloge" var="urlUloge" />
<spring:url value="/uloge/dodaj" var="urlDodajUlogu" />
<spring:url value="/vidljivosti" var="urlVidljivosti" />
<spring:url value="/vidljivosti/dodaj" var="urlDodajVidljivost" />

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
				<li class="active"><a href="${urlKorisnici}">Korisnici</a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li class="active"><a href="${urlDodajKorisnika}">Dodaj Korisnika</a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li class="active"><a href="${urlUloge}">Uloge</a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li class="active"><a href="${urlDodajUlogu}">Dodaj uloge</a></li>
			</ul>		
			<ul class="nav navbar-nav navbar-right">
				<li class="active"><a href="${urlVidljivosti}">Vidljivosti</a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li class="active"><a href="${urlDodajVidljivost}">Dodaj Vidljivost</a></li>
			</ul>
		</div>
	</div>
</nav>