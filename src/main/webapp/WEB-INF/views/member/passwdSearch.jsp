<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>    
<%-- 
 * 작성일 : 2021. 9. 9.
 * 작성자 : 박용복
 * 설명 : 비밀번호 찾기 결과
 * 수정일 : 2021. 9. 9.
--%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>비밀번호 찾기 결과</title>
<script type="text/javascript" src = "${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
<link rel = "stylesheet" href= "${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript">
$(document).ready(function() {
	// 이벤트 연결
	$('#password_form').submit(function() {
		
		if($('#passwd').val().trim() == '') {
			alert('변경할 비밀번호를 입력하세요!');
			$('#passwd').focus();
			$('#passwd').val('');
			return false;
		}
		if($('#passwd2').val().trim() == '') {
			alert('변경할 비밀번호 확인을 입력하세요!');
			$('#passwd2').focus();
			$('#passwd2').val('');
			return false;
		}
		if($('#passwd').val() != $('#passwd2').val()) {
			alert('새 비밀번호와 새 비밀번호 확인이 불일치');
			$('#passwd2').val('').focus();
			return false;
		}
	});		// end of submit
	
	// 비밀번호 수정 및 비밀번호 일치 여부 체크
	// 새 비밀번호 확인까지 한 후 다시 새 비밀번호를 수정하려고 하면 새 비밀번호 확인을 초기화
	$('#passwd').keyup(function() {
		$('#passwd2').val('');
		$('#message_passwd').text('');
	});
	
	// 새 비밀번호와 새 비밀번호 확인 일치 여부 체크
	$('#passwd2').keyup(function() {
		if($('#passwd').val() == $('#passwd2').val()) {
			$('#message_passwd').text('새 비밀번호 일치');
		}else {
			$('#message_passwd').text('');
		}
	});
});
</script>
<jsp:include page = "/WEB-INF/views/common/header.jsp" />
</head>
<body>
<div class = "container-fluid contents-wrap" style="width:90%">
	<div class="text-left col-sm-12 my-5">
		<div align = "left">
			<h3>비밀번호 찾기 결과</h3>
		</div>
	<c:if test = "${member.id != null && member.name != null && member.passkey != null}">
		<form action = "modifyPasswd.do" method = "post" id = "password_form">
			<ul class = "list-group">
				<li class = "list-group-item"><label for = "id" class = "col-sm-2 col-form-label">아이디</label>
				 ${member.id}<input type = "hidden" name = "id" id = "id" value = "${member.id}"></li>
				<li class = "list-group-item"><label for = "passwd" class = "col-sm-2 col-form-label">변경 할 비밀번호</label>
				<input type = "password" name = "passwd" id = "passwd" maxlength = "12"></li>
				<li class = "list-group-item"><label for = "passwd2" class = "col-sm-2 col-form-label">변경 할 비밀번호 확인</label>
				<input type = "password" name = "passwd2" id = "passwd2" maxlength = "12">
				<span id = "message_passwd"></span></li>
			</ul>
			<br>
			<div class = "align-left">
				<input type = "submit" class = "btn btn-outline-dark" value = "비밀번호 수정">
			</div>
		</form>
	</c:if>
	<c:if test = "${member.id == null || member.name == null || member.passkey || null }">
		<div class = "text-center">
			<ul class = "list-group">
				<li class = "list-group-item">해당되는 정보가 존재하지 않습니다.</li>
			</ul>
			<br>
			<input type = "button" class = "btn btn-outline-dark" value = "뒤로가기" onclick = "location.href='passwdSearchForm.do'">
			<input type = "button" class = "btn btn-outline-dark" value = "홈으로" onclick = "location.href='${pageContext.request.contextPath}/main/main.do'">
		</div>
	</c:if>
	</div>
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>