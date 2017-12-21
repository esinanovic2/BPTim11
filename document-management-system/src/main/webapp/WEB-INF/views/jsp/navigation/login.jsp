<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html lang="en">

<jsp:include page="../fragments/login_header.jsp" />
<style type="text/css">
body {
	background-image: url('http://crunchify.com/bg.png');
}
</style>

<c:choose>
  <c:when test="${loggedRole==0}">
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

		<h1>Login</h1>
		<spring:url value="/navigation/login" var="loginActionUrl" />

		<form:form class="form-horizontal" method="post"
			modelAttribute="loginForm" action="${loginActionUrl}">
			<spring:bind path="korisnickoIme">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-sm-2 control-label">Korisnicko Ime</label>
					<div class="col-sm-10">
						<form:input path="korisnickoIme" type="text" class="form-control"
							id="korisnickoIme" placeholder="Korisnicko Ime" />
						<form:errors path="korisnickoIme" class="control-label" />
					</div>
				</div>
			</spring:bind>

			<spring:bind path="sifra">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-sm-2 control-label">Sifra</label>
					<div class="col-sm-10">
						<form:password path="sifra" class="form-control" id="sifra"
							placeholder="Sifra" />
						<form:errors path="sifra" class="control-label" />
					</div>
				</div>
			</spring:bind>

			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<strong class="text-danger">${loginError}</strong>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button type="submit" class="btn-lg btn-primary pull-right">Login</button>
				</div>
			</div>
		</form:form>
	</div>
	<jsp:include page="../fragments/footer.jsp" />

</body>
  </c:when>
  <c:otherwise>
  <body>

	<div class="container">
		<c:if test="${not empty msg}">
			<div class="alert alert-${css} alert-dismissible" role="alert">
				<button type="button" class="close" data-dismiss="alert" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h1>
					Dobrodosli u DMS: <strong class="text-success">${msg2}</strong> 
				</h1>
				<br/>
				<h1>
					Vase korisnicko ime: <strong class="text-success">${msg}</strong> 
				</h1>
			</div>
		</c:if>
	</div>
	<jsp:include page="../fragments/footer.jsp" />

</body>
	
	
  </c:otherwise>
</c:choose>




</html>