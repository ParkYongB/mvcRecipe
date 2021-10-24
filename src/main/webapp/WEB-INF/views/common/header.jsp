<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="shortcut icon" href="${pageContext.request.contextPath}/images/favicon.ico" type="image/x-icon" />
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@300&display=swap" rel="stylesheet">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
<style>
body {
	font-family: 'Noto Sans KR', sans-serif;
}
</style>
<body>
<!-- 잇마이팁 네비바 -->
<!-- 회원 메뉴 시작-->
<!-- 로그인/로그아웃, 회원가입, 마이페이지/관리자페이지, 내가 쓴 글, 북마크 -->
<nav class="navbar navbar-expand-sm fixed-top justify-content-end" style="background-color:#ffffff;">
<c:if test="${empty mem_num}">
	<ul class="navbar-nav">
		<li class="nav-item mr-4">
			<a class="nav-link" style="color:#f76f31" href="${pageContext.request.contextPath}/member/loginForm.do">로그인</a>
		</li>
		<li class="nav-item mr-4">
			<a class="nav-link" style="color:#f76f31" href="${pageContext.request.contextPath}/member/registerMemberForm.do">회원가입</a>
		</li>
	</ul>
</c:if>
<c:if test="${!empty mem_num && auth == 1}">
	<ul class="navbar-nav">
		<li class="nav-item mr-4">
			<a class = "nav-link" style="color:#000">정지 회원입니다. 고객센터를 이용해주세요.</a>
		</li>
		<li class="nav-item mr-4">
			<a class="nav-link" style="color:#f76f31" href="${pageContext.request.contextPath}/member/logout.do">로그아웃</a>
		</li>
	</ul>
</c:if>
<c:if test="${!empty mem_num && auth == 2}">
	<ul class="navbar-nav">
		<li class="nav-item mr-4">
			<a class="nav-link" style="color:#f76f31" href="${pageContext.request.contextPath}/member/myPage.do">마이페이지</a>
		</li>
		<li class="nav-item mr-4">
			<a class="nav-link" style="color:#f76f31" href="${pageContext.request.contextPath}/member/myWriteList.do">내가 쓴 글</a>
		</li>
		<li class="nav-item mr-4">
			<a class="nav-link" style="color:#f76f31" href="${pageContext.request.contextPath}/member/myBookmarkList.do">북마크</a>
		</li>
		<li class="nav-item mr-4">
			<a class="nav-link" style="color:#f76f31" href="${pageContext.request.contextPath}/member/logout.do">로그아웃</a>
		</li>
	</ul>
</c:if>
<c:if test="${!empty mem_num && auth == 3}">
	<ul class="navbar-nav">
		<li class="nav-item mr-4">
			<a class="nav-link" style="color:#f76f31" href="${pageContext.request.contextPath}/member/myPage.do">관리자 페이지</a>
		</li>
		<li class="nav-item mr-4">
			<a class="nav-link" style="color:#f76f31" href="${pageContext.request.contextPath}/member/myWriteList.do">내가 쓴 글</a>
		</li>
		<li class="nav-item mr-4">
			<a class="nav-link" style="color:#f76f31" href="${pageContext.request.contextPath}/member/myBookmarkList.do">북마크</a>
		</li>
		<li class="nav-item mr-4">
			<a class="nav-link" style="color:#f76f31" href="${pageContext.request.contextPath}/member/logout.do">로그아웃</a>
		</li>
	</ul>
</c:if>
<!-- 검색창 -->
<form id="search" action="${pageContext.request.contextPath}/main/mainSearchList.do" method="get">
	<div class="search-bar input-group" style="width:170px;">
		<input type="search" name="search" id="search" class="form-control rounded-pill" placeholder="통합검색" autocomplete="off" aria-label="Recipient's username" aria-describedby="button-addon2">
		<div class="input-group-append"></div>
	</div>
</form>
</nav>
<!-- 회원메뉴 끝 -->
<!-- 로고 시작 -->
<nav class="navbar navbar-expand-md justify-content-center" style="background-color:#fff; padding:60px; margin:0px; position:0; font-family:'Noto Sans KR', sans-serif;">
	<a href="../main/main.do"><img src="../images/logo.png" height="80px" width="200px"></a>
</nav>
<!-- 로고 끝 -->
<!-- 사이트 메뉴 시작 -->
<!-- 공지사항, 모두의레시피, 베스트레시피, 고객센터 -->
<nav class="navbar navbar-expand-md justify-content-center" style="background-color:#f76f31; padding:0px; font-family:'Noto Sans KR', sans-serif;">
  <div class="navbar navbar-expand-sm justify-content-center" id="navbarsExample08" style="padding:0px; position:0px; margin:0px;">
    <ul class="navbar-nav">
      <li class="nav-item mr-5" style="padding:10px; font-size:15pt; font-weight:bold;">
        <a class="nav-link" href="../news/newsList.do" style="color:#000;">공지사항</a>
      </li>
      <li class="nav-item mx-5" style="padding:10px; font-size:15pt; font-weight:bold;">
        <a class="nav-link" href="${pageContext.request.contextPath}/recipe/recipeList.do" style="color:#000;">모두의 레시피</a>
      </li>
      <li class="nav-item mx-5" style="padding:10px; font-size:15pt; font-weight:bold;">
        <a class="nav-link" href="${pageContext.request.contextPath}/bestRecipe/bestRecipeList.do" style="color:#000;">베스트 레시피</a>
      </li>
      <li class="nav-item mx-5" style="padding:10px; font-size:15pt; font-weight:bold;">
        <a class="nav-link" href="${pageContext.request.contextPath}/qnaboard/qnaList.do" style="color:#000;">고객센터</a>
      </li>
    </ul>
  </div>
</nav>
<!-- 사이트 메뉴 끝 -->
<!-- header 끝 -->
</body>