<%--
/**
 * 1. 프로젝트명 : 잇마이레시피
 * 2. 작성일 : 2021. 9. 14. 오전 12:24:59
 * 3. 작성자 : ASUS
 * 4. 설명 : 
 */
--%>
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
<script type="text/javascript">
	$(document).ready(function() {
		// 카드형 게시물 70글자 초과시 ... 처리
		$('.box').each(function() {
			var content = $(this).children('.content');
			var content_txt = content.text();
			var content_txt_short = content_txt.substring(0,70)+"...";
			
			if(content_txt.length >= 70) {
				content.html(content_txt_short);
			}
		});
		$(document).on("click",".next_page",function(){
			<c:if test="${auth != 3}">
				var inputPasswd = prompt("비밀번호를 입력해주세요", "비밀번호를 입력해주세요");
				var qna_passwd = $(this).attr("data-bnum");
				if(inputPasswd != qna_passwd){
					alert("비밀번호가 틀렸습니다.");
					event.preventDefault();		// 이벤트 취소
				}
			</c:if>
		});
	});
</script>
<jsp:include page="/WEB-INF/views/common/header.jsp" />
</head>
<body>
	
	<input type="hidden" id = "newsPageNumId" name="newsPageNum" value=${ newsPageNum }>
	<input type="hidden" id = "qnaPageNumId" name="qnaPageNum" value=${ qnaPageNum }>
	<input type="hidden" id = "recipePageNumId" name="recipePageNum" value=${ recipePageNum }>
	
	<!-- 본문 -->
	<!-- 본문 -->
	<!-- 공지사항 검색 시작 -->
	<div class="container-fluid" style="width: 90%;">
		<div class="row" style="width: 100%">
			<div class="container text-center mt-5">
				<div align="left">
					<h3>공지사항</h3>
				</div>
				<div align="left">
					<br> 게시물 <span style="font-weight: bold; color: red;">${ news_count }</span>개
				</div>
			</div>
			<hr size="2" noshade width="85%">
			<br>
			<!-- 테이블 시작 -->
			<c:if test="${news_count == 0}">
				<div class="container mt-5">
					<h3>등록된 게시물이 없습니다.</h3>
				</div>
			</c:if>
			<div class="container-fluid mt-3 mb-5" style="width: 90%;">
			<c:if test="${news_count > 0 }">
				<table class="table">
					<thead>
						<tr>
							<td>no</td>
							<td>카테고리</td>
							<td>제목</td>
							<td>작성자</td>
							<td>조회수</td>
							<td>작성일</td>
						</tr>
						<c:forEach var="news" items="${NewsList}">
							<tr>
								<td>${news.news_num}</td>
								<td>${news.news_category}</td>
								<td><a href="../news/newsDetail.do?news_num=${news.news_num}">${news.news_title}</a>
								<b style="font-size: 13px; color: red;">[${ news.news_comment_count }]</b></td>
								<td><img
									src="${pageContext.request.contextPath}/images/crown.gif"
									style="height: 25px; width: 30;" />${news.id}</td>
								<td>${news.news_hits}</td>
								<td>${news.news_modi}</td>
							</tr>
						</c:forEach>
					</thead>
				</table>
					<br>
			</c:if>
					<div id="news_paging" align="center">${pagingHtmlNews}</div>
			<br>
			<br>
			</div>
		</div>
	</div>
	<!-- 공지사항 검색 끝 -->
	<!-- 레시피 검색 시작 -->
	<div class="container-fluid" style="width: 90%;">
		<div class="row" style="width: 100%;">
		      <div class="text-center col-md-12 my-5">
		
			<div align="left">
				<h3>모두의 레시피</h3>
			</div>
			<div align="left">
				<br> 
				게시물 <span style="font-weight: bold; color: red;">${ recipe_count }</span>개
			</div>
			<hr size="2" noshade width="100%">
		</div>
		</div>
		<%-- 카드시작 --%>
		<div class="row my-5 ml-5 mr-5">
			<%-- 등록된 게시물이 없는 경우 --%>
			<c:if test="${ recipe_count == 0 }">
				<div align="center">등록된 게시물이 없습니다.</div>
			</c:if>
			<!-- 카드 부분 -->
			<%-- 게시물이 있는 경우 --%>
			<c:if test="${ recipe_count > 0 }">
				<!-- 반복문 시작 -->
				<c:forEach var="recipe" items="${ recipeList }">
					<div class="col-3">
						<b>No. ${ recipe.board_num }</b> 
						<span style="float: right; font-size: 14px;">
							<b style="font-size: 14px; color: red;">[${ recipe.news_comments_count }]</b> 조회 ${ recipe.hits }
						</span>
						<div class="card" style="height: 540px;">
							<div class="card-header">
								${ recipe.id } 님 <span style="float: right;"> 추천 <b
									style="color: red;">${ recipe.recom_count }</b>
								</span>
							</div>
							<%-- 사진파일이 없는 경우 --%>
							<c:if test="${ empty recipe.filename }">
								<img src="${pageContext.request.contextPath}/images/basic.png"
									style="height: 270px;" />
							</c:if>
							<%-- 사진파일이 있는 경우 --%>
							<c:if test="${ !empty recipe.filename }">
								<img
									src="${pageContext.request.contextPath}/upload/${ recipe.filename }"
									style="height: 270px;" />
							</c:if>
							<div class="card-body">
								<h5 class="card-title">
									<a href="../recipe/recipeDetail.do?board_num=${ recipe.board_num }"
										class="btn btn-outline-dark">${ recipe.title }</a>
								</h5>
								<div class="box">
									<div class="content">
										<p class="card-text">${ recipe.content }</p>
									</div>
								</div>
								<br>
							</div>
						</div>
					</div>
				</c:forEach>
				<!-- 반복문 끝 -->
			</c:if>
			<!-- 카드끝 -->
		</div>
		<div align="center">${ pagingHtmlRecipe }</div>
	</div>
	<br>
	<br>
	<!-- 고객센터게시판 시작 -->
		<div class="container-fluid" style="width: 90%;">
		<div class="row" style="width: 100%">
			<div class="container text-center mt-5">
				<div align="left">
					<h3>문의 게시판</h3>
				</div>
				<div align="left">
					<br> 게시물 <span style="font-weight: bold; color: red;">${qna_count}</span>개
				</div>
			</div>
			<hr size="2" noshade width="85%">
			<br>
			<!-- 테이블 시작 -->
			<c:if test="${qna_count == 0}">
				<div class="container mt-5">
					<h3>등록된 게시물이 없습니다.</h3>
				</div>
			</c:if>
			<div class="container-fluid mt-3 mb-5" style="width: 90%;">
			<c:if test="${qna_count > 0 }">
				<table class="table">
					<thead>
						<tr>
							<th>글번호</th>
							<th>제목</th>
							<th>작성자</th>
							<th>작성일</th>
						</tr>
						<c:forEach var="qnaList" items="${qnaList}">
							<tr>
								<td>${qnaList.qna_num}</td>
								<td><a href="${pageContext.request.contextPath}/qnaboard/qnaDetail.do?qna_num=${qnaList.qna_num}" class="next_page" data-bnum="${qnaList.qna_passwd}">
								${qnaList.qna_title}</a></td>
								<td>${qnaList.qna_id}</td>
								<td>${qnaList.qna_date}</td>
							</tr>
						</c:forEach>
					</thead>
				</table>
					<br>
			</c:if>
					<div align="center">${pagingHtmlQna}</div>
			<br>
			<br>
			</div>
		</div>
	</div>
	<hr>
</body>
<jsp:include page="/WEB-INF/views/common/footer.jsp" />
</html>