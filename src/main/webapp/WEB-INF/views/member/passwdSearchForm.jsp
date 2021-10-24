<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- 
 * 작성일 : 2021. 9. 9.
 * 작성자 : 박용복
 * 설명 : 비밀번호 찾기 form
 * 수정일 : 2021. 9. 9.
--%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>비밀번호 찾기</title>
<script type = "text/javascript" src = "${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
<link rel = "stylesheet" href= "${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript">
	$(document).ready(function() {
		
		$('#passwdsearch_form').submit(function() {
			if($('#id').val().trim() == '') {
				alert('아이디를 입력하세요!');
				$('#id').focus();
				$('#id').val('');
				return false;
			}
			if($('#name').val().trim() == '') {
				alert('회원 가입시 입력한 이름을 입력하세요!');
				$('#name').focus();
				$('#name').val('');
				return false;
			}
			if($('#passkey').val().trim() == '') {
				alert('회원 가입시 입력한 비밀번호 힌트 정답을 입력하세요!');
				$('#passkey').focus();
				$('#passkey').val('');
				return false;
			}
		});
	});
</script>
</head>
<jsp:include page = "/WEB-INF/views/common/header.jsp" />
<body>
<div class = "container-fluid contents-wrap" style = "width:90%">
	<div class="text-left col-sm-30 my-5">
		<div align = "left">
			<h3>비밀번호 찾기</h3>
		</div>
	<form action = "passwdSearch.do" method = "post" id = "passwdsearch_form">
		<div class = "align-center">
			<table>
				<tr>
					<td height = "80px"><label for = "id" class = "col-sm-12 col-form-label">아이디</label></td>
					<td><input type = "text" class = "col-sm-12 form-control" id = "id" name = "id" maxlength = "10"></td>
				</tr>
				<tr>
					<td height = "40px"><label for = "name" class = "col-sm-12 col-form-label">회원 가입시 입력한 이름</label></td>
					<td><input type = "text" class = "col-sm-12 form-control" id = "name" name = "name" maxlength = "8"></td>
				</tr>
				<tr>
					<td height = "80px"><label for = "passkey" class = "col-sm-12 col-form-label">비밀번호 힌트 : 가장 좋아하는 음식은?</label></td>
					<td><input type = "text" class = "col-sm-12 form-control" id = "passkey" name = "passkey" maxlength = "16"></td>
				</tr>
				<tr>
					<td colspan = "2" align = "center">
						<input type = "submit" class = "btn btn-outline-dark"  value = "비밀번호 찾기">
						<input type = "button" class = "btn btn-outline-dark"  value = "뒤로 가기" onclick = "location.href='loginForm.do'">
					</td>
				</tr>
			</table>
		</div>
	</form>
	</div>
</div>
<footer>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</footer>
</body>
</html>