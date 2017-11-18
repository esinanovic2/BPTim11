<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">

<jsp:include page="../fragments/header.jsp" />

<div class="container">

	<c:choose>
		<c:when test="${dokumentForm['new']}">
			<h1>Dodaj dokument</h1>
		</c:when>
		<c:otherwise>
			<h1>Izmijeni dokument</h1>
		</c:otherwise>
	</c:choose>
	<br />

	<spring:url value="/dokumenti" var="dokumentActionUrl" />

	<form:form class="form-horizontal" method="post" modelAttribute="dokumentForm" action="${dokumentActionUrl}">

		<form:hidden path="id" />

		<spring:bind path="naziv">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Naziv</label>
				<div class="col-sm-10">
					<form:input path="naziv" type="text" class="form-control " id="naziv" placeholder="Naziv" />
					<form:errors path="naziv" class="control-label" />
				</div>
			</div>
		</spring:bind>

		<spring:bind path="fajl">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Fajl</label>
				<div class="col-sm-10">
					<form:input path="fajl" class="form-control" id="fajl" placeholder="Fajl" />
					<form:errors path="fajl" class="control-label" />
				</div>
			</div>
		</spring:bind>

		<spring:bind path="vlasnik">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Vlasnik</label>
				<div class="col-sm-10">
					<form:input path="vlasnik" type="text" class="form-control" id="vlasnik" placeholder="Vlasnik" />
					<form:errors path="vlasnik" class="control-label" />
				</div>
			</div>
		</spring:bind>

		<spring:bind path="vidljivost">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Vidljivost</label>
				<div class="col-sm-10">
					<form:password path="vidljivost" class="form-control" id="vidljivost" placeholder="Vidljivost" />
					<form:errors path="vidljivost" class="control-label" />
				</div>
			</div>
		</spring:bind>

		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<c:choose>
					<c:when test="${dokumentForm['new']}">
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


</html>