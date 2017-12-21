<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
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
			<h1>Nemate pristup!</h1>
		</div>
	</body>
  </c:when>
  <c:when test="${loggedRole==3}">
	<jsp:include page="../fragments/headerStudent.jsp" />
	<body>
		<div class="container">
	
		<c:choose>
			<c:when test="${korisnikForm['new']}">
				<h1>Dodaj korisnika</h1>
			</c:when>
			<c:otherwise>
				<h1>Izmijeni svoje podatke</h1>
			</c:otherwise>
		</c:choose>
		<br />
	
		<spring:url value="/korisnici" var="korisnikActionUrl" />
	
		<form:form class="form-horizontal" method="post" modelAttribute="korisnikForm" action="${korisnikActionUrl}">
	
			<form:hidden path="id" />
	
			<spring:bind path="ime">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-sm-2 control-label">Ime</label>
					<div class="col-sm-10">
						<form:input path="ime" type="text" class="form-control " id="ime" placeholder="Ime" />
						<form:errors path="ime" class="control-label" />
					</div>
				</div>
			</spring:bind>
	
			<spring:bind path="prezime">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-sm-2 control-label">Prezime</label>
					<div class="col-sm-10">
						<form:input path="prezime" class="form-control" id="prezime" placeholder="Prezime" />
						<form:errors path="prezime" class="control-label" />
					</div>
				</div>
			</spring:bind>
	
			<spring:bind path="korisnickoIme">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-sm-2 control-label">Korisnicko ime</label>
					<div class="col-sm-10">
						<form:input path="korisnickoIme" type="text" class="form-control" id="korisnickoIme" placeholder="korisnickoIme" />
						<form:errors path="korisnickoIme" class="control-label" />
					</div>
				</div>
			</spring:bind>
	
			<spring:bind path="sifra">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-sm-2 control-label">Sifra</label>
					<div class="col-sm-10">
						<form:password path="sifra" class="form-control" id="sifra" placeholder="sifra" />
						<form:errors path="sifra" class="control-label" />
					</div>
				</div>
			</spring:bind>
	
			<spring:bind path="potvrdisifru">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-sm-2 control-label">Potvrdi sifru</label>
					<div class="col-sm-10">
						<form:password path="potvrdisifru" class="form-control" id="potvrdisifru" placeholder="potvrdisifru" />
						<form:errors path="potvrdisifru" class="control-label" />
					</div>
				</div>
			</spring:bind>
	
			<spring:bind path="uloga">
				<form:input type="hidden" path="uloga" class="form-control" id="uloga" value="${uloga}" />
			</spring:bind>
	
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<c:choose>
						<c:when test="${korisnikForm['new']}">
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
	
	
	
	
	
  </c:when>
  <c:otherwise>
	<jsp:include page="../fragments/header.jsp" />
	<body>
		<div class="container">
	
		<c:choose>
			<c:when test="${korisnikForm['new']}">
				<h1>Dodaj korisnika</h1>
			</c:when>
			<c:otherwise>
				<h1>Izmijeni korisnika</h1>
			</c:otherwise>
		</c:choose>
		<br />
	
		<spring:url value="/korisnici" var="korisnikActionUrl" />
	
		<form:form class="form-horizontal" method="post" modelAttribute="korisnikForm" action="${korisnikActionUrl}">
	
			<form:hidden path="id" />
	
			<spring:bind path="ime">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-sm-2 control-label">Ime</label>
					<div class="col-sm-10">
						<form:input path="ime" type="text" class="form-control " id="ime" placeholder="Ime" />
						<form:errors path="ime" class="control-label" />
					</div>
				</div>
			</spring:bind>
	
			<spring:bind path="prezime">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-sm-2 control-label">Prezime</label>
					<div class="col-sm-10">
						<form:input path="prezime" class="form-control" id="prezime" placeholder="Prezime" />
						<form:errors path="prezime" class="control-label" />
					</div>
				</div>
			</spring:bind>
	
			<spring:bind path="korisnickoIme">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-sm-2 control-label">Korisnicko ime</label>
					<div class="col-sm-10">
						<form:input path="korisnickoIme" type="text" class="form-control" id="korisnickoIme" placeholder="korisnickoIme" />
						<form:errors path="korisnickoIme" class="control-label" />
					</div>
				</div>
			</spring:bind>
	
			<spring:bind path="sifra">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-sm-2 control-label">Sifra</label>
					<div class="col-sm-10">
						<form:password path="sifra" class="form-control" id="sifra" placeholder="sifra" />
						<form:errors path="sifra" class="control-label" />
					</div>
				</div>
			</spring:bind>
	
			<spring:bind path="potvrdisifru">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-sm-2 control-label">Potvrdi sifru</label>
					<div class="col-sm-10">
						<form:password path="potvrdisifru" class="form-control" id="potvrdisifru" placeholder="potvrdisifru" />
						<form:errors path="potvrdisifru" class="control-label" />
					</div>
				</div>
			</spring:bind>
	
			<spring:bind path="uloga">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-sm-2 control-label">Uloga</label>
					<div class="col-sm-10">
					<form:select path = "uloga">
	                      	<form:options items = "${uloge}" itemValue="id"/>
	                  	</form:select>
					</div>
				</div>
			</spring:bind>
	
	
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<c:choose>
						<c:when test="${korisnikForm['new']}">
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