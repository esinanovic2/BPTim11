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
		<jsp:include page="../fragments/footer.jsp" />
	</body>
  </c:when>
  <c:when test="${loggedRole==3}">
	<jsp:include page="../fragments/headerStudentska.jsp" />
	<body>
		<div class="container">
			<h1>Nemate pristup!</h1>
		</div>
		<jsp:include page="../fragments/footer.jsp" />
	</body>
  </c:when>
  <c:when test="${loggedRole==2}">
	<jsp:include page="../fragments/headerStudent.jsp" />
	<body>
		<div class="container">
			<h1>Nemate pristup!</h1>
		</div>
		<jsp:include page="../fragments/footer.jsp" />
	</body>
  </c:when>
  <c:otherwise>
	<jsp:include page="../fragments/header.jsp" />
	<spring:url value="/vidljivosti/dodaj" var="urlDodajVidljivost" />
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
			<h1>Sve vidljivosti</h1>
			<button class="btn btn-primary btn-lg pull-right" 	onclick="location.href='${urlDodajVidljivost}'">Dodaj vidljivost</button>
	
			<table class="table table-striped">
				<thead>
					<tr>
						<th>#ID</th>
						<th>Naziv</th>
					</tr>
				</thead>
	
				<c:forEach var="vidljivost" items="${vidljivosti}">
					<tr>
						<td>
							${vidljivost.id}
						</td>
						<td>${vidljivost.naziv}</td>
						<td  class="pull-right">
							<spring:url value="/vidljivosti/${vidljivost.id}/obrisi" var="obrisiUrl" /> 
							<spring:url value="/vidljivosti/${vidljivost.id}/promijeni" var="promijeniUrl" />
	
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