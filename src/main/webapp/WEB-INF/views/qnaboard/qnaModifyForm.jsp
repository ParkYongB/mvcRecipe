<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- 
 * 작성일 : 2021. 9. 10.
 * 작성자 : 나윤경
 * 설명 : 고객센터 게시판 글 수정
 * 수정일 : 
--%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="../css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<script type = "text/javascript" src = "../js/jquery-3.6.0.min.js"></script>
<script src="../js/bootstrap.bundle.min.js"></script>
<script type="text/javascript">
//유효성 체크
window.onload=function(){
   var form = document.getElementById('qnaModify_form');
   //이벤트 연결
   form.onsubmit=function(){
      var qna_title = document.getElementById('qna_title');
      if(qna_title.value.trim()==''){
         alert('제목을 입력하세요!');
         qna_title.focus();
         qna_title.value = '';
         return false;
      }
      var qna_passwd = document.getElementById('qna_passwd');
      if(qna_passwd.value.trim()==''){
         alert('비밀번호를 입력하세요!');
         qna_passwd.focus();
         qna_passwd.value = '';
         return false;
      }
      var qna_content = document.getElementById('qna_content');
      if(qna_content.value.trim()==''){
         alert('내용을 입력하세요!');
         qna_content.focus();
         qna_content.value = '';
         return false;
      }
   };
};


</script>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>EEEMT - 고객센터 글수정</title>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
</head>
<body>
<div class="container">
<br><br>
   <h2>글 수정</h2>
   <hr size="1" noshade width="100%">
   <form id="qnaModify_form" action="qnaModify.do" method="post">
      <input type="hidden" name="qna_num" value="${qnaboardVO.qna_num}">
         <div class="form-group">
            <label for="qna_title">제목</label>
            <input type="text" class="form-control" name="qna_title" id="qna_title" value="${qnaboardVO.qna_title}" >
         </div>
               <label for="qna_id">닉네임</label>
               <c:if test="${empty mem_num }">
                  <input type="text"class="form-control" name="qna_id" id="qna_id" maxlength="12" value="${qnaboardVO.qna_id }" readonly>
               </c:if>
               <c:if test="${!empty mem_num }">
                  <input type="text"class="form-control" name="qna_id" id="qna_id" maxlength="12" value="${qnaboardVO.qna_id }" readonly>
               </c:if>
            <label for="qna_passwd">비밀번호</label>
            <input type="password" class="form-control" name="qna_passwd" id="qna_passwd" maxlength="12">
            *등록시 입력한 비밀번호
            
         <div class="form-group">
            <br><label for="qna_content">내용</label><br>
            <textarea class="form-control" rows="11" name="qna_content" id="qna_content">${qnaboardVO.qna_content}</textarea>
         	<br>
         </div>
         <div align="center">
			<input type="submit" value="등록" class="btn btn-outline-dark">&nbsp;
			<input type="button" value="취소" class="btn btn-outline-dark"  onclick="location.href='qnaList.do'">
   		</div>
   </form>
   <br><br>
</div>
</body>
<jsp:include page = "/WEB-INF/views/common/footer.jsp" />
</html>