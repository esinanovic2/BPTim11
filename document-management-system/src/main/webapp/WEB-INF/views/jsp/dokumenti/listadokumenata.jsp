<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">

<c:choose>
  <c:when test="${loggedRole==0}">
	<jsp:include page="../fragments/main_header.jsp" />
  </c:when>
  <c:when test="${loggedRole==4}">
	<jsp:include page="../fragments/headerStudentska.jsp" />
  </c:when>
  <c:when test="${loggedRole==3}">
	<jsp:include page="../fragments/headerStudent.jsp" />
  </c:when>
  <c:otherwise>
	<jsp:include page="../fragments/header.jsp" />
  </c:otherwise>
</c:choose>

<spring:url value="/dokumenti/dodaj" var="urlDodajDokument" />

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
		
		<button class="btn btn-primary btn-lg pull-right" 	onclick="location.href='${urlDodajDokument}'">Dodaj dokument</button>
				
		<table class="table table-striped">
			<thead>
				<tr>
					<th>#ID</th>
					<th>Naziv</th>
					<th>Vlasnik</th>
					<th></th>
				</tr>
			</thead>
			<c:forEach var="dokument" items="${dokumenti}" varStatus = "status">
				<tr>
					<td>${dokument.id}</td>
					<td>${dokument.naziv}</td>
					<td>${vlasnici[status.index].ime}</td>
					<td  class="pull-right">
							<spring:url value="/dokumenti/${dokument.id}/prikazi/" var="dokumentPrikazi" /> 
							<spring:url	value="/dokumenti/${dokument.id}/obrisi" var="obrisiUrl" /> 
							<spring:url	value="/dokumenti/${dokument.id}/promijeni" var="promijeniUrl" />
						<button class="btn btn-info"
							onclick="location.href='${dokumentPrikazi}'">Prikazi</button>
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