<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%-- 
 * 작성일 : 2021. 9. 8.
 * 작성자 : 박용복
 * 설명 : 아이디 찾기 결과 페이지
 * 수정일 : 2021. 9. 8.
--%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
<link rel = "stylesheet" href= "${pageContext.request.contextPath}/css/style.css">
<title>아이디 찾기 결과</title>
</head>
<jsp:include page = "/WEB-INF/views/common/header.jsp" />
<body>
<div class = "container-fluid contents-wrap" style="width:90%">
	<div class="text-center col-sm-30 my-5">
		<div align = "left">
			<h3>아이디 찾기 결과</h3>
		</div>
		<c:if test = "${member.id != null}">
			<ul class = "list-group">
				<li class = "list-group-item">입력하신 ${member.name}님의 아이디는 ${member.id} 입니다.</li>
			</ul>
			<br>
			<div>
				<input type = "button" class = "btn btn-outline-dark" value = "로그인 하기" onclick = "location.href='loginForm.do'">
				<input type = "button" class = "btn btn-outline-dark" value = "비밀번호 찾기" onclick = "location.href='passwdSearchForm.do'">
			</div>
		</c:if>
		<c:if test = "${member.id == null}">
			<ul class = "list-group">
				<li class = "list-group-item">해당되는 정보가 존재하지 않습니다.</li>
			</ul>
			<br>
			<div>
				<input type = "button" class = "btn btn-outline-dark" value = "뒤로가기" onclick = "location.href='idSearchForm.do'">
				<input type = "button" class = "btn btn-outline-dark" value = "홈으로" onclick = "location.href='${pageContext.request.contextPath}/main/main.do'">
			</div>
		</c:if>
	</div>
</div>
<footer>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</footer>
</body>
</html>