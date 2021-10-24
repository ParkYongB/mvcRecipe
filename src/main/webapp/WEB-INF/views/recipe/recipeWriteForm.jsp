<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- 
 * 작성일 : 2021. 9. 6.
 * 작성자 : 이현지
 * 설명 : 레시피 글작성 폼
 * 수정일 : 2021. 9. 10
 * 설명 : 
--%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>EEEMT - 모두의 레시피 글작성</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		// 이벤트 연결
		$('#write_form').submit(function() {
			if($('#category').val() == '') {
				alert('카테고리를 선택하세요!');
				$('#category').val('').focus();
				return false;
			}
			
			if($('#title').val().trim() == '') {
				alert('제목을 입력하세요!');
				$('#title').val('').focus();
				return false;
			}
			if($('#content').val().trim() == '') {
				alert('내용을 입력하세요!');
				$('#content').val('').focus();
				return false;
			}
		});
	});
</script>
<jsp:include page="/WEB-INF/views/common/header.jsp" />
</head>
<body>
<!-- container 시작 -->
<div class="container" style="width:100%">
	<br><br>
	<h2>레시피 작성</h2>
	<hr size="1" noshade width="100%">
	<form id="write_form" action="recipeWrite.do" method="post" enctype="multipart/form-data">
		<ul>
			<li>
				<br>
				<p>
				<label for="category"></label>
				<select name="category" id="category">
					<option value="">카테고리 선택</option>
					<option value="식사">식사</option>
					<option value="간식">간식</option>
					<option value="음료">음료</option>
				</select>
				</p>
			</li>
			<li>
				<div>
				<label for="title"></label>
				<input type="text" name="title" id="title" class="input2" maxlength="50" placeholder="제목을 입력하세요">
				<span class="underline"></span>
				</div>
			</li>
			<li>
				<div>
				<label for="content"></label>
				<textarea class="form-control" id="content" rows="11" name="content"></textarea>
				</div>
			</li>
			<li>
				<div>
				<label for="filename"></label>
				<input type="file" name="filename" id="filename" class="form-control-file border" accept="image/gif,image/png,image/jpeg">
				</div>
			</li>
		</ul>
		<br>
		<div align="center">
			<input type="submit" value="등록" class="btn btn-outline-dark">&nbsp;
			<input type="button" value="목록" class="btn btn-outline-dark" onclick="location.href='recipeList.do'">
		</div>
	</form>
	<br><br>
</div>
<!-- container 끝 -->
</body>
<jsp:include page = "/WEB-INF/views/common/footer.jsp" />
</html>