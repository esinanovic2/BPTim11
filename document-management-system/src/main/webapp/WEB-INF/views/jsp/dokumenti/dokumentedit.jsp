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

	<c:choose>
		<c:when test="${dokumentForm['new']}">
			<h1>Dodaj dokument</h1>
		</c:when>
		<c:otherwise>
			<h1>Izmijeni dokument</h1>
		</c:otherwise>
	</c:choose>
	<br/>

	<c:choose>
		<c:when test="${showTextArea}">
			<spring:url value="/edit" var="dokumentActionUrl" />
		</c:when>
		<c:otherwise>
			<spring:url value="/editnocontent" var="dokumentActionUrl" />
		</c:otherwise>
	</c:choose>


	<form:form class="form-horizontal" method="post" modelAttribute="dokumentForm" action="${dokumentActionUrl}" enctype="multipart/form-data">

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

		<div class="form-group ${status.error ? 'has-error' : ''}">
			<label class="col-sm-2 control-label">Vlasnik</label>
			<div class="col-sm-10">
				<div >${vlasnik}</div>
			</div>
		</div>
	
		<spring:bind path="vidljivost">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Vidljivost</label>
				<div class="col-sm-10">
					<form:select path = "vidljivost">
                    	<form:options items = "${vidljivosti}" itemValue="id"/>
                  	</form:select>
				</div>
			</div>
		</spring:bind>


		<c:choose>
			<c:when test="${showTextArea}">
				<label class="col-sm-2 control-label">Fajl</label>
				<div class="col-sm-10">
					<textarea name='fajlcontent' rows="20" class="form-control"
						id='fajlcontent'>${dokumetContent} </textarea>
						<input id="xwpf" name="xwpf" type="hidden" value="${xdoc}"/>
				</div>
			</c:when>
			<c:otherwise>
			</c:otherwise>
		</c:choose>

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