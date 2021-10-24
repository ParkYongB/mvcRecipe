<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- 
 * 작성일 : 2021. 9. 10.
 * 작성자 : 오상준
 * 설명 : 
 * 수정일 : 
--%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>베스트 레시피</title>
<script src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
<script type="text/javascript">
	$(document).ready(function(){
		
		$('#search_form').submit(function(){
			if($('#best_search').val().trim() == '') {
				alert('검색어를 입력하세요!');
				$('#search').focus();
				$('#search').val('');
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
<div class="container-fluid contents-wrap" style="width :90%;">
      <div class="row" style="width :100%;">
      <div class="text-center col-md-12 my-5">
      <div align="left">
		<h3>베스트 레시피</h3>
	  </div>
		
		 <div align="left">
		 
		<br>
      	게시물 <span style="font-weight: bold; color: red;">${ count } </span>개
      	<span style="float: right;">
      	
      	<%-- 목록 및 메인으로 버튼 --%>
		<input type="button" value="홈으로" onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
		
      </span>
		</div>
		<%-- 구분선 --%>
		 <hr size="2" noshade width="100%">
		 <span style="float: left;">
		 <input type ="button" value="조회순" name="condition" onclick="location.href='bestRecipeList.do?division=조회순'"> 
		 <input type ="button" value="추천순" name="condition" onclick="location.href='bestRecipeList.do?division=추천순'"> 
		 <input type ="button" value="댓글순" name="condition" onclick="location.href='bestRecipeList.do?division=댓글순'"> 
		 </span>
	</div>
      </div> 
     
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
	              <h5 class="card-title"><a href="${pageContext.request.contextPath}/recipe/recipeDetail.do?board_num=${ recipe.board_num }" class="btn btn-outline-dark">${ recipe.title }</a></h5>
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
		<form action="${pageContext.request.contextPath}/recipe/recipeList.do" method="get" name="search_form" id="search_form">
			<select name="division">
				<option value="제목">제목</option>
				<option value="내용">내용</option>
				<option value="작성자">작성자</option>
			</select>
			<input type="hidden" value="${ mem_num }" name="mem_num">
			<input type="search" id="best_search" name="search" style="height: 28px;" placeholder="검색어를 입력하세요.">
			<input type="submit" value="검색">
		</form>
			<%-- 검색 끝 --%>
			<br>
	  </div>
	        
    </div>
</body>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</html>