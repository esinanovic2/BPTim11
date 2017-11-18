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
<c:if test="${loginAcces==true}">
	
</c:if>


<c:choose>
  <c:when test="${loginAcces==true}">
	<jsp:include page="../fragments/header.jsp" />
  </c:when>
  <c:otherwise>
	<jsp:include page="../fragments/main_header.jsp" />
  </c:otherwise>
</c:choose>

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

		<h1>Sistem za upravljanje dokumentima</h1>
	</div>

	<jsp:include page="../fragments/footer.jsp" />

</body>
</html>