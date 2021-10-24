<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>EEEMT - EEEat My Tip</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
<script src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
<jsp:include page="/WEB-INF/views/common/header.jsp" />
</head>
<body>
	<!-- 캐러셀 -->
	<div class="container-fluid mt-5 mb-5">
		<jsp:include page="/WEB-INF/views/main/carousel.jsp" />
	</div>
	<!-- 공지 ｜ 레시피 -->
			<!-- 공지 시작 -->
	<div class="container-fluid">
		<div class="row" style="width: 100%">
			<!-- 공지 시작 -->
			<div class="text-center col-md-6 my-5 mt-5 pt-1">
				<a class=" btn btn-light btn-lg" href="../news/newsList.do">공지사항 &amp; 이벤트</a>
				<hr style="width:80%">
				<span>새로운 소식을 알려드립니다.</span>
				<c:if test="${news_count==0}">
				<br>
				<span> 현재 공지사항이 없습니다.</span>
				<br>
				</c:if>
				<c:if test="${news_count>0}">
				<br>
				<table class="table mt-5 pt-5">
					<thead>
						<tr>
							<th width="450px">제목</th>
							<th>작성자</th>
							<th>작성일</th>
						</tr>
						<c:forEach var="news" items="${newsList}">
						<tr>
							<td><a href="${pageContext.request.contextPath}/news/newsDetail.do?news_num=${news.news_num}">${news.news_title}</a>
							<b style="font-size: 13px; color: red;">[${ news.news_comment_count }]</b></td>
							<td><img src="${pageContext.request.contextPath}/images/crown.gif" style="height: 25px; width:30;" />${news.id}</td>
							<td>${news.news_modi}</td>
						</tr>
						</c:forEach>			
				</table>
				</c:if>
			</div>
			<!-- 공지 끝 -->
			<!-- 모두의 레시피 시작 -->
			<div class="text-center col-md-6 my-5">
				<a class=" btn btn-light btn-lg" href="${pageContext.request.contextPath}/recipe/recipeList.do">신규 레시피</a>
				<hr style="width:80%">
				<span>갓 올라온 따끈한 레시피들을 만나보세요.</span>
				<div class="row mt-5">
				<%-- 등록된 게시물이 없는 경우 --%>
				<c:if test="${count == 0}">
					<div align="center">
						등록된 게시물이 없습니다.
					</div>
				</c:if>
				<!-- 카드 시작 -->
				<%-- 게시물이 있는 경우 --%>
				<c:if test="${recipe_count > 0}">
					<!-- 반복문 시작 -->
					<c:forEach var="recipe" items="${recipeList}">
					<div align="left" class="col-5">
						
						<b>No.${recipe.board_num}</b>
						<span style="float: right; font-size: 14px;">
						<b style="font-size: 14px; color: red;">[${ recipe.news_comments_count }]</b> 조회 ${recipe.hits}
						</span>
						<div class="card" style="height: 460px;">
							<div style="font-size: 14px;" class="card-header">
								<b style="font-size: 16px;">${recipe.id}</b> 님
								<span style="float: right;">
								추천 <b style="color: #f76f31; font-size: 16px;">${recipe.recom_count}</b>
								</span>
							</div>
							<%-- 이미지가 없는 경우 --%>
							<c:if test="${empty recipe.filename}">
								<img src="${pageContext.request.contextPath}/images/basic.png" style="height: 270px;" />
							</c:if>
							<%-- 이미지가 있는 경우 --%>
							<c:if test="${!empty recipe.filename}">
								<img src="${pageContext.request.contextPath}/upload/${recipe.filename}" style="height: 270px;" />
							</c:if>
							<div class="card-body">
								<h5 class="card-title"><a href="${pageContext.request.contextPath}/recipe/recipeDetail.do?board_num=${recipe.board_num}" class="btn btn-outline-dark">${recipe.title}</a></h5>
								<br>
							</div>
						</div>
					</div>
					</c:forEach>
					<!-- 반복문 끝 -->
				</c:if>
				<!-- 카드 끝 -->
				</div>
			</div>
		</div>
	</div>
	<!-- 모두의 레시피 끝 -->
	<!-- 베스트 레시피 시작-->
	<div class="container-fluid">
		<div class="row" style="width: 100%">
			<div class="text-center col-md-12 mt-1 mb-5">
				<a class=" btn btn-light btn-lg" href="${pageContext.request.contextPath}/bestRecipe/bestRecipeList.do">베스트 레시피</a>
				<hr>
				<span>잇마이팁 가족들에게 가장 많은 추천을 받은 그 레시피!</span>
			</div>
		</div>
		<div class="row mb-5 ml-5 mr-5">
		<%-- 등록된 게시물이 없는 경우 --%>
		<c:if test="${count == 0}">
			<div align="center">
				등록된 게시물이 없습니다.
			</div>
		</c:if>
		<!-- 카드 시작 -->
		<%-- 게시물이 있는 경우 --%>
		<c:if test="${bestRecipe_count > 0}">
			<!-- 반복문 시작 -->
			<c:forEach var="recipe" items="${bestRecipeList}">
			<div class="col-3">
				<b>No.${recipe.board_num}</b>
				<span style="float: right; font-size: 14px;">
				<b style="font-size: 14px; color: red;">[${ recipe.news_comments_count }]</b> 조회 ${recipe.hits}
				</span>
				<div class="card" style="height: 460px;">
					<div class="card-header">
						${recipe.id} 님
						<span style="float: right;">
						추천 <b style="color: #f76f31;">${recipe.recom_count}</b>
						</span>
					</div>
					<%-- 이미지가 없는 경우 --%>
					<c:if test="${empty recipe.filename}">
						<img src="${pageContext.request.contextPath}/images/basic.png" style="height: 270px;" />
					</c:if>
					<%-- 이미지가 있는 경우 --%>
					<c:if test="${!empty recipe.filename}">
						<img src="${pageContext.request.contextPath}/upload/${recipe.filename}" style="height: 270px;" />
					</c:if>
					<div class="card-body">
						<h5 class="card-title"><a href="${pageContext.request.contextPath}/recipe/recipeDetail.do?board_num=${recipe.board_num}" class="btn btn-outline-dark">${recipe.title}</a></h5>
						<br>
					</div>
				</div>
			</div>
			</c:forEach>
			<!-- 반복문 끝 -->
		</c:if>
		<!-- 카드 끝 -->
		</div>
	</div>
	<!-- 베스트 레시피 끝 -->
	<!-- container 끝 -->
</body>
<jsp:include page="/WEB-INF/views/common/footer.jsp" />
<script src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
</html>