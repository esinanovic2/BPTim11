<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">
<style type="text/css">
body {
	background-image: url('http://crunchify.com/bg.png');
}
</style>

<jsp:include page="../fragments/header.jsp" />

<div class="container">

	<c:if test="${not empty msg}">
		<div class="alert alert-${css} alert-dismissible" role="alert">
			<button type="button" class="close" data-dismiss="alert" aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
			<strong>${msg}</strong>
		</div>
	</c:if>

	<h1>Detalji o korisniku</h1>
	<br />

	<div class="row">
		<label class="col-sm-2">ID</label>
		<div class="col-sm-10">${korisnik.id}</div>
	</div>

	<div class="row">
		<label class="col-sm-2">Ime</label>
		<div class="col-sm-10">${korisnik.ime}</div>
	</div>

	<div class="row">
		<label class="col-sm-2">Prezime</label>
		<div class="col-sm-10">${korisnik.prezime}</div>
	</div>

	<div class="row">
		<label class="col-sm-2">Uloga</label>
		<div class="col-sm-10">${korisnik.uloga}</div>
	</div>
</div>

<jsp:include page="../fragments/footer.jsp" />

</body>
</html>