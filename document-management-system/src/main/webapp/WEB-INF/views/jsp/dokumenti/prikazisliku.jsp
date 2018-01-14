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
	</c:when>
	<c:when test="${loggedRole==3}">
		<jsp:include page="../fragments/headerStudentska.jsp" />
	</c:when>
	<c:when test="${loggedRole==2}">
		<jsp:include page="../fragments/headerStudent.jsp" />
	</c:when>
	<c:otherwise>
		<jsp:include page="../fragments/header.jsp" />
	</c:otherwise>
</c:choose>


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

	<h1>${naziv}</h1>
	<br />



	<div class="row">
		<div class="col-sm-10">
			<img src="data:image/jpeg;charset=utf-8;base64,${image}" width="1024px"
				height="576px" />
		</div>
	</div>


</div>

<jsp:include page="../fragments/footer.jsp" />

</body>
</html>