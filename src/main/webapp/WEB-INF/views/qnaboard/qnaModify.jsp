<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- 
 * 작성일 : 2021. 9. 10.
 * 작성자 : 나윤경
 * 설명 : 고객센터 게시판 수정 완료
 * 수정일 : 
--%>
<c:if test="${check == true}">
	<script>
		alert('글수정을 완료했습니다.');
		location.href='qnaList.do';
	</script>
</c:if>
<c:if test="${check == false}">
	<script>
		alert('비밀번호 불일치');
		history.go(-1);
	</script>
</c:if>