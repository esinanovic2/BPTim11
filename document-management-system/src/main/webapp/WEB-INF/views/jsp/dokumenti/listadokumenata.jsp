<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">

<jsp:include page="../fragments/header.jsp" />

<body>

	<div class="container">

		<c:if test="${not empty msg}">
			<div class="alert alert-${css} alert-dismissible" role="alert">
				<button type="button" class="close" data-dismiss="alert"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<strong>${msg}</strong>
			</div>
		</c:if>

		<h1>Svi dokumenti</h1>

		<table class="table table-striped">
			<thead>
				<tr>
					<th>#ID</th>
					<th>Naziv</th>
					<th>Vlasnik</th>
					<th>Akcija</th>
				</tr>
			</thead>

			<c:forEach var="dokument" items="${dokumenti}">
				<tr>
					<td>${dokument.id}</td>
					<td>${dokument.naziv}</td>
					<td>${dokument.vlasnik}</td>
					<td><spring:url value="/dokumenti/${dokument.id}"
							var="dokumentUrl" /> <spring:url
							value="/dokumenti/${dokument.id}/obrisi" var="obrisiUrl" /> <spring:url
							value="/dokumenti/${dokument.id}/promijeni" var="promijeniUrl" />

						<button class="btn btn-info"
							onclick="location.href='${dokumentUrl}'">Detaljno</button>
						<button class="btn btn-primary"
							onclick="location.href='${promijeniUrl}'">Izmijeni</button>
						<button class="btn btn-danger"
							onclick="this.disabled=true;post('${obrisiUrl}')">Obrisi</button></td>
				</tr>
			</c:forEach>

		</table>

	</div>

	<jsp:include page="../fragments/footer.jsp" />

</body>
</html>