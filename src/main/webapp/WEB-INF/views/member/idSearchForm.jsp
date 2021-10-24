<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- 
 * 작성일 : 2021. 9. 8.
 * 작성자 : 박용복
 * 설명 : 아이디 찾기 form
 * 수정일 : 2021. 9. 8.
--%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<script type = "text/javascript" src = "${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<title>아이디 찾기</title>
<script type="text/javascript">
	$(document).ready(function() {
		
		$('#idsearch_form').submit(function() {
			if($('#name').val().trim() == '') {
				alert('회원 가입시 입력한 이름을 입력하세요!');
				$('#name').focus();
				$('#name').val('');
				return false;
			}
			if($('#phone').val().trim() == '') {
				alert('회원 가입시 입력한 전화번호를 입력하세요!');
				$('#phone').focus();
				$('#phone').val('');
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
			<h3>아이디 찾기</h3>
		</div>
	<form action = "idSearch.do" method = "post" id = "idsearch_form">
		<div align = "center">
		<table>
			<tr>
				<td height = "40px"><label for = "name" class = "col-sm-12 col-form-label">회원 가입시 입력한 이름</label></td>
				<td><input type = "text" id = "name" class = "col-sm-12 form-control" name = "name" maxlength = "8"></td>
			</tr>
			<tr>
				<td height = "80px"><label for = "phone" class = "col-sm-12 col-form-label">회원 가입시 입력한 전화번호</label></td>
				<td><input type = "text" id = "phone" name = "phone" class = "col-sm-12 form-control" maxlength = "15"></td>
			</tr>
			<tr>
				<td colspan = "2" align = "center">
					<input type = "submit" class = "btn btn-outline-dark" value = "아이디 찾기">
					<input type = "button" class = "btn btn-outline-dark" value = "뒤로 가기" onclick = "location.href='loginForm.do'">
				</td>
			</tr>
		</table>
		</div>
	</form>
	</div>
</div>
<footer>
<jsp:include page = "/WEB-INF/views/common/footer.jsp" />
</footer>
</body>
</html>