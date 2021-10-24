<%--
/**
 * 1. 프로젝트명 : 잇마이레시피
 * 2. 작성일 : 2021. 9. 6. 오후 4:04:33
 * 3. 작성자 : ASUS
 * 4. 설명 : 공지사항의 목록입니다.
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
<title>EEEMT - 공지사항</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<script type="text/javascript" src="../js/jquery-3.6.0.min.js"></script>
<script src="../js/bootstrap.bundle.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$('#search_form').submit(function() {
			if ($('#news_search').val().trim() == '') {
				alert('검색어를 입력하세요!');
				$('#news_search').val('').focus();
				return false;
			}
		});
	});
</script>
<link rel="stylesheet" href="../css/bootstrap.min.css">
<jsp:include page="/WEB-INF/views/common/header.jsp" />
</head>
<body>
	<!-- 공지사항 상단 -->
	<div class="container-fluid" style="width: 90%;">
		<div class="row" style="width: 100%">
			<div class="container text-center mt-5">
				<div align="left">
					<h3>공지사항</h3>
				</div>
				<div align="left">

					<br> 게시물 <span style="font-weight: bold; color: red;">${ count }
					</span>개 <span style="float: right;"> <c:if test="${auth==3}">
							<form action="newsForm.do">
								<input type="submit" value="글쓰기"> <input type="button"
									value="홈으로"
									onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
							</form>
						</c:if>
						<c:if test="${auth!=3}">
						<input type="button"
									value="홈으로"
									onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
						</c:if>
					</span>
				</div>
			</div>
			<hr size="2" noshade width="85%">
			<!-- 관리자일 경우 공지사항 글 작성 버튼 보이게 -->
			<br>
			<!-- 공지사항에 게시글이 없을 경우 없다는 표시 -->
			<c:if test="${count == 0}">
				<div class="container mt-5">
					<h3>등록된 게시물이 없습니다.</h3>
				</div>
			</c:if>
			<!-- 공지사항에 게시글이 있을 경우 리스트를 출력 -->
			<c:if test="${count > 0}">
				<div class="container-fluid mt-3 mb-5" style="width: 90%;">
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
							<c:forEach var="news" items="${list}">
								<tr>
									<td>${news.news_num}</td>
									<td>${news.news_category}</td>
									<td><a href="newsDetail.do?news_num=${news.news_num}">${news.news_title}</a> 
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
					<div class="row">
						<div class="col text-center mt-4 mb-n1">${pagingHtml}</div>
					</div>
				</div>
			</c:if>
			<!-- 검색 -->
			<div align="center"
				style="width: 100%; height: 100%;">
				<br>
				<form id="search_form" action="newsList.do" method="get">
					<select name="division">
						<option value="제목">제목</option>
						<option value="내용">내용</option>
					</select>  <input
						type="search" id="news_search" name="search" placeholder="검색어를 입력하세요.">
					<input type="submit" value="검색">
				</form>
				<br>
			</div>
		</div>

	</div>
</body>
<jsp:include page="/WEB-INF/views/common/footer.jsp" />
</html>