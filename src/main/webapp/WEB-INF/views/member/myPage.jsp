<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- 
 * 작성일 : 2021. 9. 8.
 * 작성자 : 박용복
 * 설명 : 내 정보 보기 페이지
 * 수정일 : 2021. 9. 8.
--%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<script src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<title>마이 페이지</title>
<script type ="text/javascript">
	$(document).ready(function() {
		$('#photo_btn').click(function() {
			$('#photo_choice').show();
			$(this).hide();
		});
		
		var photo_path;
		var my_photo;
		$('#photo').change(function() {
			var photo = document.getElementById('photo');
			my_photo = photo.files[0];
			
			if(my_photo) {
				var reader = new FileReader();
				reader.readAsDataURL(my_photo);
				
				reader.onload = function() {
					photo_path = $('.my-photo').attr('src');
					$('.my-photo').attr('src', reader.result);
				};
			}
		});
		
		$('#photo_submit').click(function() {
			if($('#photo').val() == '') {
				alert('파일을 선택하세요!');
				$('#photo').focus();
				return;
			}
			
			var form_data = new FormData();
			form_data.append('photo', my_photo);
			$.ajax({
				data : form_data,
				type : 'post',
				url : 'updateMyPhoto.do',
				dataType : 'json',
				contentType : false,
				enctype : 'multipart/form-data',
				processData : false,
				success : function(param) {
					if(param.result == 'logout') {
						alert('로그인 후 사용하세요!');
					}else if(param.result == 'success') {
						alert('프로필 사진이 수정되었습니다!');
						$('#photo').val('');
						$('#photo_choice').hide();
						$('#photo_btn').show();
					}else {
						alert('파일 전송 오류 발생');
					}
				},
				error : function() {
					alert('네트워크 오류 발생');
				}
			});
		});
		
		$('#photo_reset').click(function() {
			$('.my-photo').attr('src', photo_path);
			$('#photo').val('');
			$('#photo_choice').hide();
			$('#photo_btn').show();
		});
	});
</script>
</head>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<body>
<!-- 본문 -->
<div class = "container-fluid" style = "width:90%">
	<div align = "center" class="col-sm-12 my-5">
	<div align = "left">
		<h3>회원정보</h3>
	</div>
	<div class = "container row">
		<div class = "mypage-div">
			<h3>프로필 사진</h3>
			<ul class = "list-group">
				<li class="list-group-item">
					<c:if test = "${empty member.photo}">
						<img src = "${pageContext.request.contextPath}/images/default_user.png" width = "100" height = "100" class = "my-photo">
					</c:if>
					<c:if test = "${!empty member.photo}">
						<img src = "${pageContext.request.contextPath}/upload/${member.photo}" width = "100" height = "100" class = "my-photo">
					</c:if>
				</li>
				<li class="list-group-item">
				<br>
					<div>
						<input type = "button" class="btn btn-outline-dark" value = "수정" id = "photo_btn">
					</div>
					<div id = "photo_choice" style = "display:none;">
						<input type = "file" id = "photo" accept = "image/png,image/jpeg"><br>
						<input type = "button" class="btn btn-outline-dark" value = "전송" id = "photo_submit">
						<input type = "button" class="btn btn-outline-dark" value = "취소" id = "photo_reset">
					</div>
				</li>
			</ul>
		</div>
		<div class = "mypage-div">
			<h3>개인 정보 수정</h3>
			<ul class = "list-group">
				<li class="list-group-item">아이디 : ${member.id}</li>
				<li class="list-group-item">이름 : ${member.name}</li>
				<li class="list-group-item">전화번호 : ${member.phone}</li>
				<li class="list-group-item">이메일 : ${member.email}</li>
				<li class="list-group-item">생일 : ${member.birthday}</li>
				<li class="list-group-item">가입일 : ${member.join_date}</li>
			</ul>
			<br>
			<input type = "button" class="btn btn-outline-dark" value = "회원 정보 수정" onclick = "location.href='modifyMemberForm.do'">
		</div>
	<c:if test = "${member.auth == 3}">
	<div class = "mypage-div">
		<h3>관리자 페이지</h3>
		<div>
			<input type = "button" class="btn btn-outline-dark" value = "가입 회원 정보 조회" onclick = "location.href='adminMemberView.do'">
		</div>
	</div>
	</c:if>
	</div>
	</div>
</div>
</body>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
<script src="../js/bootstrap.bundle.min.js"></script>
</html>