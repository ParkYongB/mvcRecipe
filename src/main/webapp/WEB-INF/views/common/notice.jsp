<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- 
 * 작성일 : 2021. 9. 13.
 * 작성자 : 이현지
 * 설명 : 로그인한 회원번호와 작성자 회원번호가 불일치할 때 안내문 뿌려주기
 * 수정일 : 
--%>
<script type="text/javascript">
	alert('잘못된 접속입니다.');
	location.href='${pageContext.request.contextPath}/main/main.do';
</script>