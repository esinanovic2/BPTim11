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
		<jsp:include page="../fragments/footer.jsp" />
	</body>
  </c:when>
  <c:when test="${loggedRole==4}">
	<jsp:include page="../fragments/headerStudentska.jsp" />
	<body>
		<div class="container">
			<h1>Nemate pristup!</h1>
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
		<jsp:include page="../fragments/footer.jsp" />
	</body>
  </c:when>
  <c:otherwise>
	<jsp:include page="../fragments/header.jsp" />
	<body>
		<div class="container">
			<c:choose>
				<c:when test="${ulogaForm['new']}">
					<h1>Dodaj ulogu</h1>
				</c:when>
				<c:otherwise>
					<h1>Izmijeni ulogu</h1>
				</c:otherwise>
			</c:choose>
			<br/>
		
			<spring:url value="/uloge" var="ulogaActionUrl" />
		
			<form:form class="form-horizontal" method="post" modelAttribute="ulogaForm" action="${ulogaActionUrl}">
		
				<form:hidden path="id" />
		
				<spring:bind path="naziv">
					<div class="form-group ${status.error ? 'has-error' : ''}">
						<label class="col-sm-2 control-label">Naziv uloge</label>
						<div class="col-sm-10">
							<form:input path="naziv" type="text" class="form-control" id="naziv" placeholder="Naziv" />
							<form:errors path="naziv" class="control-label" />
						</div>
					</div>
				</spring:bind>
		
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<c:choose>
							<c:when test="${ulogaForm['new']}">
								<button type="submit" class="btn-lg btn-primary pull-right">Dodaj</button>
							</c:when>
							<c:otherwise>
								<button type="submit" class="btn-lg btn-primary pull-right">Promijeni</button>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</form:form>
	</div>
	<jsp:include page="../fragments/footer.jsp" />
	</body>
  </c:otherwise>
</c:choose>






</html>