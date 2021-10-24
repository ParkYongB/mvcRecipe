<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%-- 
 * 작성일 : 2021. 9. 7.
 * 작성자 : 박용복
 * 설명 : 회원 가입 페이지
 * 수정일 : 2021. 9. 9.
--%>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>회원가입</title>
<script type = "text/javascript" src = "${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
<script type="text/javascript">
	$(document).ready(function() {
		var idChecked = 0;
		
		// 아이디 중복 체크
		$('#id_check').click(function() {
			if($('#id').val().trim() == '') {
				alert('아이디를 입력하세요!');
				$('#id').focus();
				$('#id').val('');
				return;
			}
			
			$('#message_id').text('');
			
			// 아이디 중복 체크
			$.ajax({
				url : 'checkDuplicatedId.do',
				type : 'post',
				data : {id : $('#id').val()},
				dataType : 'json',
				cache : false,
				timeout : 30000,
				success : function(param) {
					if(param.result == 'idNotFound') {
						idChecked = 1;
						$('#message_id').css('color', '#0000FF').text('사용 가능한 아이디 입니다.');
					}else if(param.result == 'idDuplicated') {
						idChecked = 0;
						$('#message_id').css('color', 'red').text('이미 사용 중인 아이디 입니다!');
						$('#id').val('').focus();
					}else {
						idChecked = 0;
						alert('아이디 중복 체크 오류 발생!');
					}
				},
				error : function() {
					idChecked = 0;
					alert('네트워크 오류 발생!');
				}
			});
		});
		
		// 아이디 중복 안내 메시지 초기화 및 아이디 중복 값 초기화
		$('#register_form #id').keydown(function() {
			idChecked = 0;
			$('#message_id').text('');
		});
		
		// 회원 정보 등록 유효성 체크
		$('#register_form').submit(function() {
			if($('#id').val().trim() == '') {
				alert('아이디를 입력하세요!');
				$('#id').focus();
				$('#id').val('');
				return false;
			}
			if(idChecked == 0) {
				alert('아이디 중복 체크를 하셔야 합니다!');
				$('#id_check').focus();
				return false;
			}
			if($('#name').val().trim() == '') {
				alert('이름을 입력하세요!');
				$('#name').focus();
				$('#name').val('');
				return false;
			}
			if($('#passwd').val().trim() == '') {
				alert('비밀번호를 입력하세요!');
				$('#passwd').focus();
				$('#passwd').val('');
				return false;
			}
			if($('#passwd2').val().trim() == '') {
				alert('비밀번호를 확인해주세요!');
				$('#passwd2').focus();
				$('#passwd2').val('');
				return false;
			}
			if($('#email').val() == '') {
				alert('이메일을 입력하세요!');
				$('#email').focus();
				$('#email').val('');
				return false;
			}
			if($('#phone').val().trim() == '') {
				alert('전화번호 입력하세요!');
				$('#phone').focus();
				$('#phone').val('');
				return false;
			}
			if($('#birthday').val().trim() == '') {
				alert('생년월일을 입력하세요!');
				$('#birthday').focus();
				$('#birthday').val('');
				return false;
			}
			if($('#passkey').val().trim() == '') {
				alert('비밀번호 힌트를 입력하세요!');
				$('#passkey').focus();
				$('#passkey').val('');
				return false;
			}
			if($('#passwd').val() != $('#passwd2').val()) {
				alert('비밀번호와 비밀번호 확인이 불일치!');
				$('#passwd').val('').focus();
				return false;
			}
			
			var strStart_date = $('#birthday').val();
			
			var startDate = new Date(strStart_date);
			var startYyyy = startDate.getFullYear();
			var startmm = startDate.getMonth() + 1;
			var startDd = startDate.getDate();
			var startDay = new Date(startYyyy, startmm, startDd);
			
			var now = new Date();         		  // 현재 날짜
			var nowYyyy = now.getFullYear();
			var nowmm = now.getMonth() + 1;       // 1월 == 0
			var nowDd = now.getDate();
			var nowDate = new Date(nowYyyy, nowmm, nowDd);   
			
			// 생년월일이 시스템 상 날짜 보다 나중 날짜를 입력 못하게 함
			
			if(nowDate.getTime() < startDay.getTime()) {
				alert('생년월일을 제대로 입력해주세요!');
				return false;
			}
		});
		
		// 비밀번호 확인 일치 여부 체크
		// 비밀번호 확인까지 한 후 다시 비밀번호를 수정하려고 하면 비밀번호 확인을 초기화
		$('#passwd').keyup(function(){
			$('#passwd2').val('');
			$('#message_passwd2').text('');
		});
		
		// 비밀번호와 비밀번호 확인 일치 여부 체크
		$('#passwd2').keyup(function() {
			if($('#passwd').val() == $('#passwd2').val()) {
				$('#message_passwd2').text('비밀번호 일치');
			}else {
				$('#message_passwd2').text('');
			}
		});
	
	});
</script>
</head>
<jsp:include page = "/WEB-INF/views/common/header.jsp" />
<body>
<!-- container 시작 -->
<div class = "container-fluid" style = "width:90%">
	<div class="text-center col-sm-12 my-5">
		<div align = "left">
			<h3>회원 가입</h3>
		</div>
		<form id = "register_form" action = "registerMember.do" method = "post">
			<div class = "form-group row">
				<label for = "id" class = "col-sm-2 col-form-label">아이디</label>
				<input type = "text" class = "col-sm-4 form-control mr-4" name = "id" id = "id" maxlength = "10">
				<input type = "button" class = "btn btn-outline-dark" value = "ID 중복체크" id = "id_check">
				&emsp;<span id = "message_id"></span>
			</div>
			<div class = "form-group row">
				<label for = "name" class = "col-sm-2 col-form-label">이름</label>
				<input type = "text" class = "col-sm-4 form-control" name = "name" id = "name" maxlength = "8">
			</div>
			<div class = "form-group row">
				<label for = "passwd" class = "col-sm-2 col-form-label">비밀번호</label>
				<input type = "password" class = "col-sm-4 form-control" name = "passwd" id = "passwd" maxlength = "12">
			</div>
			<div class = "form-group row">
				<label for = "passwd2" class = "col-sm-2 col-form-label">비밀번호 확인</label>
				<input type = "password" class = "col-sm-4 form-control" id = "passwd2" maxlength = "12">
				&emsp;<span id = "message_passwd2"></span>
			</div>
			<div class = "form-group row">
				<label for = "email" class = "col-sm-2 col-form-label">이메일</label>
				<input type = "email" class = "col-sm-4 form-control" name = "email" id = "email" maxlength = "50">
			</div>
			<div class = "form-group row">
				<label for = "phone" class = "col-sm-2 col-form-label">전화번호</label>
				<input type = "text" class = "col-sm-4 form-control" name = "phone" id = "phone" maxlength = "15">
			</div>
			<div class = "form-group row">
				<label for = "birthday" class = "col-sm-2 col-form-label">생년월일</label>
				<input type = "date" class = "col-sm-4 form-control" name = "birthday" id = "birthday" max = "today">
			</div>
			<div class = "form-group row">
				<label class = "col-sm-2 col-form-label">비밀번호 힌트</label> 
				<label class = "col-form-label">가장 좋아하는 요리는?</label>
			</div>
			<div class = "form-group row">
				<label for = "passkey" class = "col-sm-2 col-form-label">비밀번호 정답</label>
				<input type = "text" class = "col-sm-4 form-control" name = "passkey" id = "passkey" maxlength = "16">
			</div>
			<div class = "form-group row">
				<div class = "text-center col-sm-10">
					<button type = "submit" class = "btn btn-outline-dark">회원 가입</button>
				</div>
			</div>
		</form>
	</div>
</div>
<!-- container 끝 -->
<jsp:include page = "/WEB-INF/views/common/footer.jsp" />
</body>
</html>