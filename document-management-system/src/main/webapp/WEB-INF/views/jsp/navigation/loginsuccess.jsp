<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">

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
				<h1>
					Dobrodosli u DMS: <strong class="text-success">${msg}</strong> 
				</h1>
			</div>
		</c:if>
	</div>

	<jsp:include page="../fragments/footer.jsp" />

</body>
</html>