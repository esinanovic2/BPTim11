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
		<c:when test="${vidljivostForm['new']}">
			<h1>Dodaj vidljivost</h1>
		</c:when>
		<c:otherwise>
			<h1>Izmijeni vidljivost</h1>
		</c:otherwise>
	</c:choose>
	<br />

	<spring:url value="/vidljivosti" var="vidljivostActionUrl" />

	<form:form class="form-horizontal" method="post" modelAttribute="vidljivostForm" action="${vidljivostActionUrl}">

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

		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<c:choose>
					<c:when test="${vidljivostForm['new']}">
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