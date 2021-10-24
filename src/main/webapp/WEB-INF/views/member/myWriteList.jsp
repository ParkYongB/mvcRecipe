<%-- 
 작성자 : 박용복
 작성일 : 2021-09-09
 설명 : 내가 작성한 글 게시판
 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>EEEMT - 내가 작성한 글</title>
<script src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
<script type="text/javascript">
	$(document).ready(function(){
		
		$('.box').each(function(){
            var content = $(this).children('.content');
            var content_txt = content.text();
            var content_txt_short = content_txt.substring(0,110)+"...";

            if(content_txt.length >= 110){
                content.html(content_txt_short)
                
            };
        });
	});
</script>
</head>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<body>
<div class="container-fluid contents-wrap" style="width :90%;">
    <div class="row" style="width :100%;">
    <div class="col-md-12 my-5">
	<div align="left">
		<h3>내가 작성한 글</h3>
	</div>
    <div align="left">
      	<br>
        게시물 <span style="font-weight: bold; color: red;">${ count } </span>개
      	<span style="float: right;">
      	<%-- 로그인 되어 있다면 글쓰기 클릭스 글쓰기 폼으로 이동 --%>
		<c:if test="${ !empty mem_num && (auth == 2 || auth == 3) }">
				<form action="${pageContext.request.contextPath}/recipe/recipeWriteForm.do">
					<input type="hidden" value="${ mem_num }" name="mem_num">
					<input type="submit" class = "btn btn-outline-dark" value="글쓰기">
					<input type="button" class = "btn btn-outline-dark" value="홈으로" onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
				</form>
		</c:if>
		</span>
		</div>
		 <hr size="2" noshade width="100%">
		 <%-- 카드 시작 --%>
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
	         	   <b style="font-size: 14px; color: red;">[${ recipe.comm_count }]</b> 조회 ${ recipe.hits }
	            </span>
	          <div class="card" style="height: 540px;">
	            <div class="card-header">
	              ${ recipe.id } 님
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
      </div>
	</div>
	<div align="center">
		${ pagingHtml }
	</div>
</div>
<footer>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</footer>
</body>
</html>