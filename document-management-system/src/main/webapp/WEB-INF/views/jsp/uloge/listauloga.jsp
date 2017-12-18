<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">
<style type="text/css">
body {
	background-image: url('http://crunchify.com/bg.png');
}
</style>
<jsp:include page="../fragments/header.jsp" />
<spring:url value="/uloge/dodaj" var="urlDodajUlogu" />


<body>

<div class="container">

		<c:if test="${not empty msg}">
			<div class="alert alert-${css} alert-dismissible" role="alert">
				<button type="button" class="close" data-dismiss="alert" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<strong>${msg}</strong>
			</div>
		</c:if>

		<h1>Sve uloge</h1>
		
		<button class="btn btn-primary btn-lg pull-right" 	onclick="location.href='${urlDodajUlogu}'">Dodaj ulogu</button>

		<table class="table table-striped">
			<thead>
				<tr>
					<th>#ID</th>
					<th>Naziv</th>
				</tr>
			</thead>

			<c:forEach var="uloga" items="${uloge}">
				<tr>
					<td>
						${uloga.id}
					</td>
					<td>${uloga.naziv}</td>
					<td>
						<spring:url value="/uloge/${uloga.id}" var="ulogaUrl" />
						<spring:url value="/uloge/${uloga.id}/obrisi" var="obrisiUrl" /> 
						<spring:url value="/uloge/${uloga.id}/promijeni" var="promijeniUrl" />

						<button class="btn btn-info" onclick="location.href='${ulogaUrl}'">Detaljno</button>
						<button class="btn btn-primary" onclick="location.href='${promijeniUrl}'">Izmijeni</button>
						<button class="btn btn-danger" onclick="this.disabled=true;post('${obrisiUrl}')">Obrisi</button></td>
				</tr>
			</c:forEach>
		</table>

	</div>

	<jsp:include page="../fragments/footer.jsp" />

</body>
</html>