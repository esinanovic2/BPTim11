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
<c:choose>
  <c:when test="${loggedRole==0}">
	<jsp:include page="../fragments/main_header.jsp" />
	<body>
		<div class="container">
			<h1>Niste prijavljeni!</h1>
		</div>
	</body>
  </c:when>
  <c:when test="${loggedRole==4}">
	<jsp:include page="../fragments/headerStudentska.jsp" />
	<spring:url value="/korisnici/dodaj" var="urlDodajKorisnika" />

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
	
			<h1>Svi korisnici</h1>
			<table class="table table-striped">
				<thead>
					<tr>
						<th>#</th>
						<th>Ime</th>
						<th>Prezime</th>
						<th>Uloga</th>
						<th></th>
					</tr>
				</thead>
				<c:forEach var="korisnik" items="${korisnici}" varStatus = "status">
					<tr>
						<td>${status.index+1}</td>
						<td>${korisnik.ime}</td>
						<td>${korisnik.prezime}</td>
						<td>${uloge[status.index].naziv}</td>
						<td class="pull-right">
							
							<spring:url value="/korisnici/${korisnik.id}/dokumenti/" var="korisnikDokumentiPrikazi" /> 
							<spring:url value="/korisnici/${korisnik.id}/obrisi" var="obrisiUrl" /> 
							<spring:url value="/korisnici/${korisnik.id}/promijeni" var="promijeniUrl" />
	
							<button class="btn btn-info" onclick="location.href='${korisnikDokumentiPrikazi}'">Prikazi dokumente</button>
							<button class="btn btn-primary" onclick="location.href='${promijeniUrl}'">Izmijeni</button>
							<button class="btn btn-danger" onclick="this.disabled=true;post('${obrisiUrl}')">Obrisi</button></td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<jsp:include page="../fragments/footer.jsp" />
	</body>
  </c:when>
  <c:when test="${loggedRole==3}">
	<jsp:include page="../fragments/headerStudent.jsp" />
		<body>
			<div class="container">
				<h1>Nemate pristup!</h1>
			</div>
		</body>
  </c:when>
  <c:otherwise>
	<jsp:include page="../fragments/header.jsp" />
	<spring:url value="/korisnici/dodaj" var="urlDodajKorisnika" />
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
	
			<h1>Svi korisnici</h1>
	
			<button class="btn btn-primary btn-lg pull-right" 	onclick="location.href='${urlDodajKorisnika}'">Dodaj korisnika</button>
	
			<table class="table table-striped">
				<thead>
					<tr>
						<th>#</th>
						<th>Ime</th>
						<th>Prezime</th>
						<th>Uloga</th>
						<th></th>
					</tr>
				</thead>
	
				<c:forEach var="korisnik" items="${korisnici}" varStatus = "status">
					<tr>
						<td>${status.index+1}</td>
						<td>${korisnik.ime}</td>
						<td>${korisnik.prezime}</td>
						<td>${uloge[status.index].naziv}</td>
						<td class="pull-right">
				
							<spring:url value="/korisnici/${korisnik.id}/dokumenti/" var="korisnikDokumentiPrikazi" /> 
							<spring:url value="/korisnici/${korisnik.id}/obrisi" var="obrisiUrl" /> 
							<spring:url value="/korisnici/${korisnik.id}/promijeni" var="promijeniUrl" />
	
							<button class="btn btn-info" onclick="location.href='${korisnikDokumentiPrikazi}'">Prikazi dokumente</button>
							<button class="btn btn-primary" onclick="location.href='${promijeniUrl}'">Izmijeni</button>
							<button class="btn btn-danger" onclick="this.disabled=true;post('${obrisiUrl}')">Obrisi</button></td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<jsp:include page="../fragments/footer.jsp" />
	</body>
  </c:otherwise>
</c:choose>
</html>