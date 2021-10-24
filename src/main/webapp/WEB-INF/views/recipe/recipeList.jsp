<%-- 
 작성자 : 오상준
 작성일 : 2021-09-07
 설명 : 모두의 레시피 게시판
 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>EEEMT - 모두의 레시피</title>
<script src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
<script type="text/javascript">
	$(document).ready(function(){
		
		$('#search_form').submit(function(){
			if($('#recipe_search').val().trim() == '') {
				alert('검색어를 입력하세요!');
				$('#recipe_search').focus();
				$('#recipe_search').val('');
				return false;
			}
		});
		
		// 카드형 게시물 110글자 초과시 ... 처리
		$('.box').each(function(){
			var content = $(this).children('.content');
			var content_txt = content.text();
			var content_txt_short = content_txt.substring(0,110)+"...";

			if(content_txt.length >= 110){
				content.html(content_txt_short);
            }
        });
	});
</script>
</head>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<body>
<div class="container-fluid" style="width :90%;">
      <div class="row" style="width :100%;">
      <div class="text-center col-md-12 my-5">
      <div align="left">
		<h3>모두의 레시피</h3>
	  </div>
		
		 <div align="left">
		 
		<br>
      	게시물 <span style="font-weight: bold; color: red;">${ count } </span>개
      	<span style="float: right;">
      	<%-- 로그인이 안되어 있다면 글쓰기 클릭시 로그인 페이지로 이동 --%>
		<c:if test="${ empty mem_num }">
				<input type="button" value="글쓰기" onclick="location.href='${pageContext.request.contextPath}/member/loginForm.do'">
				<input type="button" value="홈으로" onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
		</c:if>
		<%-- 로그인 되어 있다면 글쓰기 클릭스 글쓰기 폼으로 이동 --%>
		<c:if test="${ !empty mem_num && (auth == 2 || auth == 3) }">
				<form action="recipeWriteForm.do">
					<input type="hidden" value="${ mem_num }" name="mem_num">
					<input type="submit" value="글쓰기">
					<input type="button" value="홈으로" onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
				</form>
		</c:if>
		<c:if test="${ auth == 1 }">
			<input type="button" value ="목록" onclick="location.href='recipeList.do'">
			<input type="button" value="홈으로" onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
		</c:if>
      </span>
		</div>
		<%-- 구분선 --%>
		 <hr size="2" noshade width="100%">
		 <!-- 공지사항에 게시글이 없을 경우 없다는 표시 -->
	<c:if test="${count == 0}">
		<div class="container mt-5">
			<h3>등록된 게시물이 없습니다.</h3>
		</div>
	</c:if>
<!-- 공지사항에 게시글이 있을 경우 리스트를 출력 -->
	<c:if test="${news_count > 0}">
		<div class="container-fluid mt-3 mb-5">
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
					<c:forEach var="news" items="${news_list}">
						<tr>
							<td>${news.news_num}</td>
							<td>${news.news_category}</td>
							<td><a href="${pageContext.request.contextPath}/news/newsDetail.do?news_num=${news.news_num}">${news.news_title}</a>
							<b style="font-size: 13px; color: red;">[${ news.news_comment_count }]</b></td>
							<td><img src="${pageContext.request.contextPath}/images/crown.gif" style="height: 25px; width:30;" />${news.writer}</td>
							<td>${news.news_hits}</td>
							<td>${news.news_date}</td>
						</tr>
					</c:forEach>
				</thead>
			</table>
		</div>
	</c:if>
		 
		 <%-- 구분선 --%>
		 <hr size="2" noshade width="100%">
	</div>
      </div> 
     
     <%-- 카드시작 --%>
      <div class="row my-5 ml-5 mr-5">
      <%-- 등록된 게시물이 없는 경우 --%>
      <c:if test="${ count == 0 }">
      		<div align="center">
      			등록된 게시물이 없습니다.
      		</div>
      </c:if>
      <!-- 카드 부분 -->
      <%-- 게시물이 있는 경우 --%>
        <c:if test="${ count > 0 }">
        <!-- 반복문 시작 -->
        	<c:forEach var="recipe" items="${ list }">
	       	<div class="col-3">
	            <b>No. ${ recipe.board_num }</b> 
	            <span style="float: right; font-size: 14px;">
	            <b style="font-size: 14px; color: red;">[${ recipe.news_comments_count }]</b> 조회 ${ recipe.hits }
	            </span>
	          <div class="card" style="height: 540px;">
	            <div class="card-header">
	             <b style="font-size: 17px">${ recipe.id }</b> 님
	              <span style="float: right;">
	              추천 <b style="color: red;">${ recipe.recom_count }</b>
	              </span>
	            </div>
	            <%-- 사진파일이 없는 경우 --%>
	            <c:if test="${ empty recipe.filename }">
	            	<img src="${pageContext.request.contextPath}/images/basic.png" style="height: 270px;" />
	            </c:if>
	            <%-- 사진파일이 있는 경우 --%>
	            <c:if test="${ !empty recipe.filename }">
	            	<img src="${pageContext.request.contextPath}/upload/${ recipe.filename }" style="height: 270px;" />
	            </c:if>
	            <div class="card-body">
	              <h5 class="card-title"><a href="recipeDetail.do?board_num=${ recipe.board_num }" class="btn btn-outline-dark">${ recipe.title }</a></h5>
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
      <div align="center">
	        	${ pagingHtml }
	  </div>
      <div align="center" style="width :100%; height: 100%;">
	        <br>
	        <%-- 검색부분 --%>  
		<form action="recipeList.do" method="get" name="search_form" id="search_form">
			<select name="division">
				<option value="제목">제목</option>
				<option value="내용">내용</option>
				<option value="작성자">작성자</option>
			</select>
			<input type="search" id="recipe_search" name="search" style="height: 28px;" placeholder="검색어를 입력하세요.">
			<input type="submit" value="검색">
		</form>
			<%-- 검색 끝 --%>
			<br>
	  </div>
	        
    </div>
</body>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</html>