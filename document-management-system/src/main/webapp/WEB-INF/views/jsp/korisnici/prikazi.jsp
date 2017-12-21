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
			<div class="col-sm-10">${uloga}</div>
		</div>
	</div>
	<jsp:include page="../fragments/footer.jsp" />

</body>
  </c:when>
  <c:when test="${loggedRole==3}">
	<jsp:include page="../fragments/headerStudent.jsp" />
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
	
		<h1>Detalji o Vasem korisnickom racunu</h1>
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
			<div class="col-sm-10">${uloga}</div>
		</div>
	</div>
	<jsp:include page="../fragments/footer.jsp" />
	</body>
  </c:when>
  <c:otherwise>
	<jsp:include page="../fragments/header.jsp" />
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
			<div class="col-sm-10">${uloga}</div>
		</div>
	</div>
	<jsp:include page="../fragments/footer.jsp" />

</body>
  </c:otherwise>
</c:choose>


</html>