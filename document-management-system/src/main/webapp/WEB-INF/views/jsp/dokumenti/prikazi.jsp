<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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


<div class="container">

	<c:if test="${not empty msg}">
		<div class="alert alert-${css} alert-dismissible" role="alert">
			<button type="button" class="close" data-dismiss="alert" aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
			<strong>${msg}</strong>
		</div>
	</c:if>

	<h1>Detalji o dokumentu</h1>
	<br />

	<div class="row">
		<label class="col-sm-2">ID</label>
		<div class="col-sm-10">${dokument.id}</div>
	</div>

	<div class="row">
		<label class="col-sm-2">Naziv</label>
		<div class="col-sm-10">${dokument.naziv}</div>
	</div>

	<div class="row">
		<label class="col-sm-2">Vlasnik</label>
		<div class="col-sm-10">${dokument.vlasnik}</div>
	</div>
	

</div>

<jsp:include page="../fragments/footer.jsp" />

</body>
</html>