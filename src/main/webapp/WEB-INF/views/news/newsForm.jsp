<%--
/**
 * 1. 프로젝트명 : 잇마이레시피
 * 2. 작성일 : 2021. 9. 6. 오후 4:04:21
 * 3. 작성자 : ASUS
 * 4. 설명 : 공지사항의 작성 폼 입니다.
 */
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<script src="../js/bootstrap.bundle.min.js"></script>
<script type = "text/javascript" src = "../js/jquery-3.6.0.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<link rel="stylesheet" href="../css/bootstrap.min.css">
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>EEEMT - 공지사항 글 작성</title>
<script type="text/javascript">
	$(document).ready(function() {
		$('#news_form').submit(function() {
			if($('#title').val().trim() == '') {
				alert('제목을 입력하세요!');
				$('#title').focus();
				$('#title').val('');
				return false;
			}
			if($('#content').val().trim() == '') {
				alert('내용을 입력하세요!');
				$('#content').focus();
				$('#content').val('');
				return false;
			}
			if($('#category').val() == '') {
				alert('카테고리를 선택하세요!');
				$('#category').val('').focus();
				return false;
			}
		});
	});
</script>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
</head>
<body>
<div class="container">
	<br><br>
	<h2>공지사항</h2>	
	<hr size="1" noshade width="100%">
	<form id="news_form" action="newsWrite.do" method="post" enctype="multipart/form-data">	
		<div class="form-group">
			<h6>카테고리</h6>
				<p>
				<label for="category"></label>
				<select name="category" id="category">
					<option value="">카테고리 선택</option>
					<option value="공지사항">공지사항</option>
					<option value="레시피">레시피</option>
					<option value="베스트레시피">베스트레시피</option>
					<option value="이벤트">이벤트</option>
					<option value="문의사항">문의사항</option>
				</select>
				</p>
		</div>
		<div class="form-group">
			<label for="title">제목</label>
			<input type="text" class="form-control" name="title" id="title" maxlength="50">
		</div>
			<div class="form-group">
			<label for="content">내용</label>
			<textarea class="form-control" id="content" rows="11" name="content"></textarea>
		<br>
		</div>
		<div class="form-group">
			<label for="filename">파일</label>
			<input type="file" name="filename" id="filename" class="form-control-file border" accept="image/gif,image/png,image/jpeg">
		</div>
		<br>
		<br>
		<div align="center">
		<input type="submit" value="등록" class="btn btn-outline-dark">&nbsp;
		<input type="button" value="취소" class="btn btn-outline-dark" onclick="location.href='newsList.do'">
		</div>
	</form>
	<br>
	<br>	
</div>
</body>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>

</html>