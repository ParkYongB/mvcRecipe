<%--
/**
 * 1. 프로젝트명 : 잇마이레시피
 * 2. 작성일 : 2021. 9. 6. 오후 4:05:01
 * 3. 작성자 : ASUS
 * 4. 설명 : 공지사항의 상세 화면 입니다.
 */
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" src="../js/jquery-3.6.0.min.js"></script>
<script src="../js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<link rel="stylesheet" href="../css/bootstrap.min.css">
<script type="text/javascript">
	//댓글 시작.
	$(document)
			.ready(
					function() {
						var currentPage;
						var count;
						var rowCount;
						//댓글 목록 시작
						function selectData(pageNum) {
							currentPage = pageNum;

							if (pageNum == 1) {
								//처음 호출시는 해당 ID의 div의 내부 내용물을 제거
								$('#output').empty();
							}

							//로딩이미지 노출
							$('#loading').show();
							//댓글 리스트 출력
							$
									.ajax({
										type : 'post',
										data : {
											pageNum : pageNum,
											news_num : $('#news_num').val()
										},
										url : 'newsCommentsList.do',
										dataType : 'json',
										cache : false,
										timeout : 30000,
										success : function(param) {
											//댓글 불러오기 성공하면 로딩 이미지 감춰줌
											$('#loading').hide();
											count = param.count;
											rowCount = param.rowCount;

											$(param.list)
													.each(
															function(index,
																	item) {
																var output = '<div class="item">'
																	if(item.photo != null){
																		output += '<div style ="width:57px; height:57px; float:left; padding-right: 5em;"><img src="${pageContext.request.contextPath}/upload/' + item.photo + '"style="height: 53px; width:53px;" class = "my-photo" /></div>'
																	}else{
																		output += '<div style ="width:57px; height:57px; float:left; padding-right: 5em;"><img src="${pageContext.request.contextPath}/images/default_user.png" style="height: 53px; width:53px;" class = "my-photo"/></div>';
																	}
																	output += "<span><h4>" + item.id + "<h4></span>";
																	output += '<div class="sub-item">';
																	output += "<p style='font-size:16px;'>" + item.comm_con + "</p>";
																	output += "<span style='font-size:14px;'>" + item.comm_date + "</span>";
																	//로그인한 회원번호와 작성자의 일치여부 체크
																	if ($('#mem_num').val() == item.mem_num || $('#mem_auth').val() == 3) {//로그인한 회원번호와 작성자 회원번호와 일치
																		output += ' <input type="button" data-renum="'+item.comm_num+'" data-memnum="'+item.mem_num+'" value="수정" style="font-size:14px;" class="modify-btn">';
																		output += ' <input type="button" data-renum="'+item.comm_num+'" data-memnum="'+item.mem_num+'" value="삭제" style="font-size:14px;" class="delete-btn">';
																		}
																output += '<hr size="1" noshade width="100%">';
																output += '</div>';
																output += '</div>';

																$('#output')
																		.append(
																				output);
															});
											//페이지 버튼 처리함
											if (currentPage >= Math.ceil(count
													/ rowCount)) {
												//다음 페이지가 없음
												$('.paging-button').hide();
											} else {
												//다음 페이지가 존재
												$('.paging-button').show();
											}
										},
										error : function(request,status,error){		// 에러메세지 반환
											alert("code = "+ request.status + " message = " + request.responseText + " error = " + error);
										}
									});
						}

						//페이지 처리 이벤트 연결(다음 댓글 보기 버튼 클릭시 데이터 추가)
						$('.paging-button input').click(function() {
							selectData(currentPage + 1);
						});
						//댓글 목록 출력 끝
						//댓글 등록 시작
						$('#news_comment_form').submit(function() {
							if ($('#mcomm_con').val() == '') {
								alert('내용을 입력하세요!');
								$('#mcomm_con').val('').focus();
								return false;
							}
							//form 이하의 태그에 입력한 데이터를 모두 다 읽어 옴
							var form_data = $(this).serialize();
							//댓글 등록
							$.ajax({
								type : 'post',
								data : form_data,
								url : 'newsComments.do',
								dataType : 'json',
								cache : false,
								timeout : 30000,
								success : function(param) {
									if (param.result == 'logout') {
										alert('로그인해야 작성할 수 있습니다.');
									} else if (param.result == 'success') {
										// 폼 초기화
										initForm();
										// 댓글 작성이 성공하면 새로 삽입한 글을 포함해서 첫번째 페이지의 게시글을 다시 호출함
										selectData(1);
										//등록시 오류	
									} else {
										alert('등록시 오류 발생');
									}
								},
								//sql,servlet오류
								error : function() {
									alert(' 등록시 네트워크 오류!')
								}
							}); // end of ajax

							// 기본 이벤트 제거*****
							event.preventDefault();
						});

						function initForm() {
							$('textarea').val(''); // # 있으면 안 됨
							$('#re_first .letter-count').text('300/300'); // 글자수 초기화
						}

						// textarea에 내용 입력시 글자수 체크
						$(document).on('keyup', 'textarea', function() {
							// 입력한 글자 구함
							var inputLength = $(this).val().length;

							if (inputLength > 300) { // 300자를 넘어선 경우
								$(this).val($(this).val().subString(0, 300));

							} else { // 300자 이하인 경우
								var remain = 300 - inputLength;
								remain += '/300';
								if ($(this).attr('id') == 're_content') {
									// 등록폼 글자수 체크
									$('#re_first .letter-count').text(remain); // 후손 선택자

								} else {
									// 수정폼 글자수
									$('#mre_first .letter-count').text(remain);
								}
							}
						});
						//댓글수정 시작
						//댓글 수정 버튼 클릭시 수정 폼 노출
						$(document)
								.on(
										'click',
										'.modify-btn',
										function() {
											//댓글번호 
											var comm_num = $(this).attr(
													'data-renum');
											var num = $(this).attr(
													'data-memnum');
											var content = $(this).parent()
													.find('p').html().replace(
															/<br>/gi, '\n');
											var modifyUI = '<form id="mnews_comment_form">';
											modifyUI += '  <input type="hidden" name="comm_num" id="mcomm_num" value="' + comm_num + '">';
											modifyUI += '  <input type="hidden" name="mem_num" id="mmem_num" value="' + num + '">';
											modifyUI += '  <textarea rows="3" cols="50" name="comm_con" id="mcomm_con" class="rep-content">'
													+ content + '</textarea>';
											modifyUI += '  <div id="mre_first"><span class="letter-count">300/300</span></div>';
											modifyUI += '  <div id="mre_second" class="align-right">';
											modifyUI += '     <input type="submit" value="수정">';
											modifyUI += '     <input type="button" value="취소" class="re-reset">';
											modifyUI += '  </div>';
											modifyUI += '  <hr size="1" noshade width="96%">';
											modifyUI += '</form>';
											//이전에 이미 수정하는 댓글이 있을 경우 수정버튼을 클릭하면 숨김 sub-item을 환원시키고 수정폼을 초기화함
											initModifyForm();
											//지금 클릭해서 수정하고자 하는 데이터는 감추기
											//수정버튼을 감싸고 있는 div
											$(this).parent().hide();
											//수정폼을 수정하고자 하는 데이터가 있는 div에 노출
											$(this).parents('.item').append(
													modifyUI);
											//입력한 글자수 셋팅
											var inputLength = $('#mcomm_con')
													.val().length;
											var remain = 300 - inputLength;
											remain += '/300';

											//문서 객체에 반영
											$('#mre_first .letter-count').text(
													remain);
										});
						//수정폼에서 취소 버튼 클릭시 수정폼 초기화
						$(document).on('click', '.re-reset', function() {
							initModifyForm();
						});
						//댓글 수정 폼 초기화
						function initModifyForm() {
							$('.sub-item').show();
							$('#mnews_comment_form').remove();
						}
						//댓글 수정
						$(document)
								.on(
										'submit',
										'#mnews_comment_form',
										function(event) {
											if ($('#mcomm_con').val().trim() == '') {
												alert('내용을 입력하세요!');
												$('#mcomm_con').val('').focus();
												return false;
											}
											var form_data = $(this).serialize();
											//수정
											$
													.ajax({
														url : 'NewsCommentsUpdate.do',
														type : 'post',
														data : form_data,
														dataType : 'json',
														cache : false,
														timeout : 30000,
														success : function(
																param) {
															if (param.result == 'logout') {
																alert('로그인해야 수정할 수 있습니다.');
															} else if (param.result == 'success') {
																$(
																		'#mnews_comment_form')
																		.parent()
																		.find(
																				'p')
																		.html(
																				$(
																						'#mcomm_con')
																						.val()
																						.replace(
																								/</g,
																								'&lt;')
																						.replace(
																								/>/g,
																								'&gt;')
																						.replace(
																								/\n/g,
																								'<br>'));
																//수정폼 삭제 및 초기화
																initModifyForm();
															} else if (param.result == 'wrongAccess') {
																alert('타인의 글을 수정할 수 없습니다.');
															} else {
																alert('수정 오류 발생');
															}
														},
														error : function(
																request,
																status, error) { // 에러메세지 반환
															alert("code = "
																	+ request.status
																	+ " message = "
																	+ request.responseText
																	+ " error = "
																	+ error);
														}
													});
											//기본 이벤트 제거
											event.preventDefault();
										});
						//댓글 수정 끝
						//댓글 삭제 시작
						$(document)
								.on(
										'click',
										'.delete-btn',
										function() {
											var check = confirm('삭제하시겠습니까?'); 
											if(check){
											var comment_num = $(this).attr(
													'data-renum');
											var mem_num = $(this).attr(
													'data-memnum');
											$
													.ajax({
														url : 'newsCommentsDelete.do',
														type : 'post',
														data : {
															comment_num : comment_num,
															mem_num : mem_num
														},
														dataType : 'json',
														cache : false,
														timeout : 30000,
														success : function(
																param) {
															if (param.result == 'logout') {
																alert('로그인해야 삭제할 수 있습니다.');
															} else if (param.result == 'success') {
																alert('삭제 완료!');
																selectData(1);
															} else if (param.result == 'wrongAccess') {
																alert('타인의 글을 삭제할 수 없습니다.');
															} else {
																alert('삭제시 오류 발생!');
															}
														},
														error : function(
																request,
																status, error) { // 에러메세지 반환
															alert("code = "
																	+ request.status
																	+ " message = "
																	+ request.responseText
																	+ " error = "
																	+ error);
														}
													});
											}else {
												
											}
										});
						selectData(1);

					});
</script>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>EEEMT - 공지사항</title>
<jsp:include page="/WEB-INF/views/common/header.jsp" />
</head>
<body>
	<div class="recipe-detail-main">
		<div class="detail_title">
			<br>
			<br>
			<h3>No. ${news.news_num} [${news.news_category}]
				${news.news_title}</h3>
		</div>
		<hr size="2" noshade width="100%">
		<b>작성자 </b> &nbsp;${news.id} | <b>작성일 </b> ${news.news_date} <span
			style="float: right;"> <b>조회수 : </b> ${news.news_hits}
		</span>
		<%-- 글부분 --%>
		<hr size="2" noshade width="100%">
		<!-- 상세 글 부분 -->
		<!-- 사진 있을 경우 -->
		<c:if test="${!empty news.news_file}">
			<div class="detail-content">
				<img
					src="${pageContext.request.contextPath}/upload/${news.news_file}"
					class="detail-img">
			</div>
		</c:if>
		<!-- 사진 없을 경우 디폴트 이미지 보여줌 -->
		<c:if test="${empty news.news_file}">
			<div class="detail-content">
				<img src="${ pageContext.request.contextPath }/images/basic.png"
					class="detail-img">
			</div>
		</c:if>
		<!-- 공지 내용 -->
		<p align="center">${news.news_content }</p>
		<%-- 댓글 내용 시작 --%>
		<hr size="2" noshade width="100%">
		<div id="output"></div>
		<div class="paging-button" style="display: none;">
			<input type="button" value="다음 댓글">
		</div>
		<div id="loading" style="display: none;">
			<img src="${pageContext.request.contextPath }/images/ajax-loader.gif">
		</div>
		<%-- 댓글 내용 끝 --%>
		<%-- 댓글 기능 시작 --%>
		<div id="reply_div">
			<span class="re-title">댓글 달기</span>
			<div align="center">
				<form style="width : 750px; border : none;" id="news_comment_form">
					<input type="hidden" name="news_num" value="${news.news_num}" id="news_num"> 
					<input type="hidden" name="mem_num" value="${mem_num}" id="mem_num"> 
					<input type="hidden" name="mem_auth" value="${auth}" id="mem_auth">
					<textarea name="re_content" id="re_content" class="rep-content"
						<c:if test="${empty mem_num || auth == 1}">disabled="disabled"</c:if>
					><c:if test="${empty mem_num || auth == 1}">정지회원 또는 비회원은 작성할 수 없습니다.</c:if></textarea>
					<c:if test="${!empty mem_num && auth != 1}">
						<div id="re_first">
							<span class="letter-count">300/300</span>
						</div>
						<div id="re_second">
							<input type="submit" value="등록">
						</div>
					</c:if>
				</form>
			</div>
		</div>

		<%-- 댓글 기능 끝 --%>
		<%--하단에 목록, 수정,삭제  --%>
				<hr size="2" noshade width="100%">
		<br>
		<input type="button" value="목록"
			style="color: black; background-color: white; border-color: #d5dfe8"
			onclick="history.go(-1)">
		<div style="float: right;">
			수정일 : ${news.news_modi}
			<c:if test="${auth==3}">
				<input type="button" value="수정"
					onclick="location.href='newsModifyForm.do?news_num=${news.news_num}'"
					style="color: black; background-color: white; border-color: #d5dfe8">
				<input type="button" value="삭제" id="delete_btn" style="color: red; background-color: white; border-color: #d5dfe8">
				<script type="text/javascript">
					var delete_btn = document.getElementById("delete_btn");
					delete_btn.onclick = function() {
						var choice = confirm("삭제하시겠습니까?");
						if (choice) {
							location.replace("newsDelete.do?news_num=${news.news_num}");
						}
					};
				</script>
			</c:if>
		</div>
		<div style="padding-bottom: 50px;"></div>
	</div>
</body>
<jsp:include page="/WEB-INF/views/common/footer.jsp" />
</html>