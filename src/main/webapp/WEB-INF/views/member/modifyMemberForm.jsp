<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- 
 * 작성일 : 2021. 9. 8.
 * 작성자 : 박용복
 * 설명 : 회원 정보 수정 form
 * 수정일 : 2021. 9. 8.
--%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>회원 정보 수정</title>
<script type="text/javascript" src = "${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
<script type="text/javascript">
	$(document).ready(function() {
		$('#modify_form').submit(function() {
			if($('#nowpasswd').val().trim() == '') {
				alert('개인정보를 수정하시려면 비밀번호를 입력하세요!');
				$('#nowpasswd').focus();
				$('#nowpasswd').val('');
				return false;
			}
			if($('#email').val().trim() == '') {
				alert('이메일을 입력하세요!');
				$('#email').focus();
				$('#email').val('');
				return false;
			}
			if($('#phone').val().trim() == '') {
				alert('전화번호를 입력하세요!');
				$('#phone').focus();
				$('#phone').val('');
				return false;
			}
			if($('#passkey').val().trim() == '') {
				alert('비밀번호 정답을 입력하세요!');
				$('#passkey').focus();
				$('#passkey').val();
				return false;
			}
			if($('#newpasswd').val() != $('#chepasswd').val()) {
				alert('새 비밀번호와 새 비밀번호 확인이 불일치 합니다!');
				$('#chepasswd').val('').focus();
				return false;
			}
		}); 		// end of submit
		
		// 새 비밀번호 확인까지 한 후 새 비밀번호를 수정하려고 하면 새 비밀번호를 초기화
		$('#newpasswd').keyup(function() {
			$('#chepasswd').val('');
			$('#message_chepasswd').text('');
		});
		
		// 새 비밀번호와 새 비밀번호 확인 일치 여부 체크
		$('#chepasswd').keyup(function() {
			if($('#newpasswd').val() == $('#chepasswd').val()) {
				$('#message_chepasswd').text('새 비밀번호 일치');
			}else {
				$('#message_chepasswd').text('');
			}
		});
	});
</script>
</head>
<jsp:include page = "/WEB-INF/views/common/header.jsp" />
<body>
<div class = "container-fluid" style = "width:90%">
	<div class="col-sm-12 my-5">
	<h3>회원 정보 수정</h3>
	<form action = "modifyMember.do" method = "post" id = "modify_form">
		<ul class = "list-group">
			<li class = "list-group-item">
				<label for = "id" class = "col-sm-2 col-form-label">아이디</label>
				${member.id}
				&emsp;
				<input type = "button" class = "btn btn-outline-dark" value = "회원 탈퇴" onclick = "location.href='deleteMemberForm.do'">
			</li>
			<li class = "list-group-item">
				<label for = "name" class = "col-sm-2 col-form-label">이름</label>
				${member.name}
			</li>
			<li class = "list-group-item">
				<label for = "nowpasswd" class = "col-sm-2 col-form-label">현재 비밀번호</label>
				<input type = "password" name = "nowpasswd" id = "nowpasswd" maxlength = "12">
			</li>
			<li class = "list-group-item">
				<label for = "newpasswd" class = "col-sm-2 col-form-label">새 비밀번호</label>
				<input type = "password" name = "newpasswd" id = "newpasswd" maxlength = "12">
			</li>
			<li class = "list-group-item">
				<label for = "chepasswd" class = "col-sm-2 col-form-label">새 비밀번호 확인</label>
				<input type = "password" name = "chepasswd" id = "chepasswd" maxlength = "12">
				&emsp;<span id = "message_chepasswd"></span>
			</li>
			<li class = "list-group-item">
				<label for = "email" class = "col-sm-2 col-form-label">이메일</label>
				<input type = "email" name = "email" id = "email" value = "${member.email}" maxlength = "50">
			</li>
			<li class = "list-group-item">
				<label for = "phone" class = "col-sm-2 col-form-label">전화번호</label>
				<input type = "text" name = "phone" id = "phone" value = "${member.phone}" maxlength = "15">
			</li>
			<li class = "list-group-item" class = "col-sm-2 col-form-label">
				<label for = "birthday" class = "col-sm-2 col-form-label">생년월일</label>
				${member.birthday}
			</li>
			<li class = "list-group-item">
				<label class = "col-sm-2 col-form-label">비밀번호 힌트</label>
				 가장 좋아하는 요리는?
			</li>
			<li class = "list-group-item">
				<label for = "passkey" class = "col-sm-2 col-form-label">비밀번호 정답</label>
				<input type = "text" name = "passkey" id = "passkey" value = "${member.passkey}" maxlength = "16">
			</li>
		</ul>
		<br>
		<div class = "align-left">
			<input type = "submit" class = "btn btn-outline-dark" value = "수정">
			<input type = "button" class = "btn btn-outline-dark" value = "뒤로가기" onclick = "location.href='myPage.do'">
		</div>
	</form>
	</div>
</div>
</body>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</html>