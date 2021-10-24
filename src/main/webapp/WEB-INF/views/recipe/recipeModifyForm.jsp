<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- 
 * 작성일 : 2021. 9. 7.
 * 작성자 : 이현지
 * 설명 : 레시피 글 수정폼
 * 수정일 : 
--%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>EEEMT - 모두의 레시피 글수정</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		// 이벤트 연결
		$('#modify_form').submit(function() {
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
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
</head>
<body>
<!-- container 시작 -->
<div class="container" style="width:100%">
	<br><br>
	<h2>레시피 수정</h2>
	<hr size="1" noshade width="100%">
	<form id="modify_form" action="recipeModify.do" method="post" enctype="multipart/form-data">
		<input type="hidden" name="board_num" value="${recipe.board_num}">
		<ul>
			<li>
				<br>
				<p>
				<label for="category"></label>
				<select name="category" id="category">
					<option value="식사" <c:if test="${recipe.category.equals('식사')}"> selected </c:if>>식사</option>
					<option value="간식" <c:if test="${recipe.category.equals('간식')}"> selected </c:if>>간식</option>
					<option value="음료" <c:if test="${recipe.category.equals('음료')}"> selected </c:if>>음료</option>
				</select>
				</p>
			</li>
			<li>
				<div>
				<label for="title"></label>
				<input type="text" name="title" id="title" class="input2" value="${recipe.title}" maxlength="50">
				<span class="underline"></span>
				</div>
			</li>
			<li>
				<label for="content"></label>
				<textarea class="form-control" id="content" rows="11" name="content">${recipe.content}</textarea>
			</li>
			<li>
				<div>
				<label for="filename"></label>
				<c:if test="${!empty recipe.filename}">
				<br>
				<span style="background-color:#ffe8e8">* 이미 등록된 파일이 있습니다. [${recipe.filename}]</span> 
				</c:if>
				</div>
				<br>
				<input type="file" name="filename" id="filename" class="form-control-file border" value="${recipe.filename}" accept="image/gif,image/png,image/jpeg">
			</li>
		</ul>
		<br>
		<div align="center">
			<input type="submit" value="등록" class="btn btn-outline-dark">&nbsp;
			<input type="button" value="취소" class="btn btn-outline-dark" onclick="location.href='recipeList.do'">
		</div>
	</form>
	<br><br>
</div>
<!-- container 끝 -->
</body>
<jsp:include page = "/WEB-INF/views/common/footer.jsp" />
</html>