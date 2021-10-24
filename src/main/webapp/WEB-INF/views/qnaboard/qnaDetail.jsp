<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- 
 * 작성일 : 2021. 9. 8.
 * 작성자 : 나윤경
 * 설명 : 고객센터 상세게시판 폼 및 댓글
 * 수정일 : 
--%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>EEEMT - 고객센터</title>
<script src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
<script type="text/javascript">
   $(function(){
      // 댓글 기능쪽 자바스크립트
      var currentPage;
      var count;
      var rowCount;
      
      // --------------- 댓글 등록
      
      $("#re_form").submit(function(event){   // 폼에 있는 이벤트 제거하기 위해 인자값 넣음
         if($("#re_content").val().trim() == ""){
            alert("내용을 입력하세요!");
            $("#re_content").val("").focus();
            return false;
         }
         
         // form 이하의 태그에 입력한 데이터를 모두 읽어옴
         var form_data = $(this).serialize();
      
      
         // ajax를 통해 댓글 등록 처리
         $.ajax({
               type:"post",
               data:form_data,
               url:"qnaReplyWrite.do",
               dataType:"json",
               cache : false,
               timeout:30000,
               success:function(param){
                  if(param.result == "Wrong"){
                     alert("관리자만 작성할 수 있습니다.");
                  }else if(param.result == "success"){
                     // 폼 초기화 함수 호출
                     initForm();
                     // 댓글 작성이 성공하면 새로 삽입한 글을 포함해서 첫번째 페이지의 게시글을 다시 호출함
                     selectData(1);
                  }else{
                     alert("등록시 오류 발생");
                  }
               },
               error : function(request,status,error){      // 에러메세지 반환
                  alert("code = "+ request.status + " message = " + request.responseText + " error = " + error);
               }
            });
         
         // 기본 이벤트 제거하는 부분    이거는 꼭 넣어주어야 한다. 이게 빠지면 서밋이 되버림
         event.preventDefault();
         
      });   // end of reply
      
      // 댓글 작성 폼 초기화
      function initForm(){
         $("textarea").val("");
         $("#re_first .letter-count").text("300/300");
      }
      
      // textarea에 내용 입력시 글자수 체크
      $(document).on("keyup","textarea",function(){
          // 입력한 글자 구함
            var inputLength = $(this).val().length;
            
            if(inputLength > 300){   // 300자를 넘어선 경우
               $(this).val($(this).val().substring(0,300));
            }else{   // 300자 이하
               var remain = 300 - inputLength;
               remain += '/300';
               if($(this).attr('id') == 're_content'){ 
                  // 등록폼 글자수   후손선택자라 중간에 공백이 있음
                  $('#re_first .letter-count').text(remain);
               }else{
                  // 수정폼 글자수
                  $('#mre_first .letter-count').text(remain);
               }
            }

      });
      
      // ---------------  댓글 목록
      function selectData(pageNum){
         currentPage = pageNum;
         if(pageNum == 1){
            // 처음 호출시는 해당 ID의 div의 내부 내용물을 제거
            $("#output").empty();
         }
         // 로딩 이미지 노출
         $("#loading").show();
         
         $.ajax({
            type:"post",
            data:{
               pageNum : pageNum, 
               qna_num : $("#qna_num").val()
               },
            url:"qnaReplyList.do",
            dataType:"json",
            cache:false,
            timeout:30000,
            success:function(param){
               // 로딩이미지 감추기
               $("#loading").hide();
               count = param.count;
               rowCount = param.rowCount;
               
               $(param.list).each(function(index,item){   // item을 이용해서 배열형식인 list 값을 뽑아온다
                  //each() 메서드는 매개 변수로 받은 것을 사용해 for in 반복문과 같이 배열이나 객체의 요소를 검사할 수 있는 메서드
                  var output = "<div class='item'>";
                        
                        output += '<div style ="width:40px; height:40px; float:left; padding-right: 4em;"><img src="${pageContext.request.contextPath}/images/crown.gif" style="height: 35px; width:40;" /></div>'
                        output += "<span><h4> 관리자<h4></span>";
                        output += "<div class='sub-item'>";
                           output += "<p style='font-size:16px;'>" + item.comm_con + "</p>";
                           output += "<span style='font-size:14px;'>" + item.comm_date + "</span>";
                           // 로그인한 회원번호와 작성자의 회원번호 일치 여부 체크
                           if(${auth==3}){
                              // 댓글 번호와 회원번호를 속성을 통해 값을 준다. (커스텀 데이터 속성을 만들어서) 이걸로인해 수정이나 삭제 처리가 쉽다.
                              output += " <input type='button' data-renum='" + item.comm_num + "' data-memnum='${auth}' value='수정' style='font-size:14px;' class='modify-btn'>";      
                              output += ' <input type="button" data-renum="'+item.comm_num+'" data-memnum="${auth}" value="삭제" style="font-size:14px;" class="delete-btn">';
                           }
                           output += "<hr size='1' noshade width='100%'>";
                        output += "</div>";
                     output += "</div>";
                     
                      // 문서 객체 추가
                          $('#output').append(output);
               });
               
               //page button 처리
               if(currentPage >= Math.ceil(count/rowCount)){
                  // 다음 페이지가 없음
                  $(".paging-button").hide();
               }else{
                  // 다음 페이지가 존재
                  $(".paging-button").show();
               }
            },
            error : function(request,status,error){      // 에러메세지 반환
               alert("code = "+ request.status + " message = " + request.responseText + " error = " + error);
            }
         });
      } // end of RecommentsList
      
      // 페이지 처리 이벤트 연결(다음 댓글 보기 버튼 클릭시 데이터 추가)
      $(".paging-button input").click(function(){
         selectData(currentPage + 1);
      });    
      
// --------------- 댓글 수정
      
      // -------- 댓글 수정 버튼 클릭시 수정 폼 노출
      $(document).on("click", ".modify-btn", function(){
         
         // 댓글 번호
         var comm_num = $(this).attr("data-renum");
         // 작성자 회원등급
         var writer_auth = $(this).attr("data-memnum");
         // 댓글 내용
         var content = $(this).parent().find("p").html().replace(/<br>/gi, "\n");      
         // this는 수정버튼 부모태그로 가서 p태그를 찾고 내용을 가져온다 br/gi 태그는 \n으로 변경한다. i의 의미는 대소문자 구분 x g의 의미는 전체
         
         // 댓글 수정 폼 UI
         var modifyUI = "<form id='mre_form'>";
            modifyUI += "   <input type='hidden' name='comm_num' id='mre_num' value='" + comm_num + "'>";
            modifyUI += "   <input type='hidden' name='writer_auth' id='muser_num' value='" + writer_auth + "'>";
            modifyUI += "   <textarea rows='3' cols='50' name='comm_con' id='mre_content' class='rep-content'>" + content + "</textarea>";
            modifyUI += "   <div id='mre_first'><span class='letter-count'>300/300</span></div>";
            modifyUI += "   <div id='mre_second' align='right'>";
            modifyUI += "      <input type='submit' value='수정'>";
            modifyUI += "      <input type='button' value='취소' class='re-reset'>";
            modifyUI += "   <div>";
            modifyUI += "   <hr size='1' noshade width='96%'>";      
            modifyUI += "</form>";
         
            // 이전에 이미 수정하는 댓글이 있을 경우 수정버튼을 클릭하면 숨김 sub-item을 환원시키고 수정폼을 초기화 한다
            initModifyForm();
            // 수정 버튼을 감싸고 있는 div
            $(this).parent().hide(); // 수정버튼의 부모태그 sub-item 숨기기
            
            // 수정 폼을 수정하고자 하는 데이터가 있는 div에 노출
            $(this).parents(".item").append(modifyUI); // 수정버튼의 부모들 중에 class가 item인 태그에 append
      
            // 입력한 글자수 셋팅
            var inputLength = $("#mre_content").val().length;
            var remain = 300 - inputLength;
            remain += "/300";
            
            // 문서 객체에 반영
            $("#mre_first .letter-count").text(remain);
      }); // end of recommentsUpdateClick
      
      
      // 수정 폼에서 취소 버튼 클릭시 수정폼 초기화
      $(document).on('click','.re-reset',function(){
         initModifyForm();
         })
      
      // 댓글 수정 폼 초기화
      function initModifyForm(){
         $(".sub-item").show();   // sub-item을 보여주고
         $("#mre_form").remove();    // 폼 초기화
      }
      
      // ----------------- 댓글 수정
      $(document).on("submit","#mre_form", function(event){
         if($("#mre_content").val().trim() == ""){
            alert("내용을 입력하세요!");
            $("#re_content").val("").focus();
            return false;
         }
         
         // 폼에 입력한 데이터 변환
         var form_data = $(this).serialize();
               
         // 댓글 수정
         $.ajax({
            type:"post",
            url:"qnaReplyModify.do", 
            data: form_data,
            dataType:"json",
            cache:false,
            timeout:30000,
            success:function(param){
               if(param.result == "Wrong"){
                  alert("관리자만 수정할 수 있습니다.")
               }else if(param.result == "success"){
                  // 전송을 누르면 전송되지 않고 그대로 화면에 읽어와서 표시되도록
                  // 부모로 올라가서 p태그를 찾아서 내용을 넣어준다. 그리고 html 태그 불허용을 했기때문에 바꾸는 작업처리
                  $('#mre_form').parent().find('p').html($('#mre_content').val().replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/\n/gi,'<br>'));
                  // 수정폼 삭제 및 초기화
                  initModifyForm();
               }else if(param.result == "wrongAccess"){
                  alert("글을 수정할 수 없습니다.");
               }else{
                  alert("DAO수정시 오류 발생");
               }
            },
            error : function(request,status,error){      // 에러메세지 반환
               alert("code = "+ request.status + " message = " + request.responseText + " error = " + error);
            }
         });
         // 기본 이벤트 제거하는 부분    이거는 꼭 넣어주어야 한다. 이게 빠지면 서밋이 되버림
         event.preventDefault();
      });
      
      // --------------- 댓글 삭제
      $(document).on("click",".delete-btn",function(){
         // 확인 / 취소 선택창
         var check = confirm('삭제하시겠습니까?'); 
         
         if(check){
             //확인 눌렀을시 실행문
            // 댓글 번호
            var comm_num = $(this).attr("data-renum");
            // 작성자 회원등급
            var writer_auth = $(this).attr("data-memnum");
            
            $.ajax({
               type:"post",
               url:"qnaReplyDelete.do",
               data:{
                  comm_num : comm_num,
                  writer_auth : writer_auth               
               },
               dataType:"json",
               cache:false,
               timeout:30000,
               success:function(param){
                  if(param.result == "success"){
                     alert("댓글이 삭제되었습니다.");
                     selectData(1);
                  }else if(param.result == "Wrong"){
                     alert("글을 삭제할 수 없습니다.");
                  }else{
                     alert("삭제시 오류 발생");
                  }
               },
               error : function(request,status,error){      // 에러메세지 반환
                  alert("code = "+ request.status + " message = " + request.responseText + " error = " + error);
               }
            });
         
         }else {
            
         }
      });
      
      selectData(1);
      
   });
</script>
</head>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<body>
<div class="recipe-detail-main">
   <div class="detail_title">
	   <br><h3>No. ${qnaboard.qna_num} ${qnaboard.qna_title }</h3>
   </div>
   <hr size="2" noshade width="100%">
   <b>작성자 </b>&nbsp; ${qnaboard.qna_id} | <b>작성일</b> ${qnaboard.qna_date }
     <hr size="2" noshade width="100%">
   
   <!-- 상세글 내용 -->
   <p align="center"><br>
      ${qnaboard.qna_content }
   <br><br><br></p>
   
  <hr size="2" noshade width="100%">
   <%-- 댓글 목록 출력 시작 --%>
      <div id="output"></div>
      <div class="paging-button" style="display: none;">
         <input type="button" value="다음 댓글">
      </div>
      <div id="loading" style="display: none;">
         <img src="${pageContext.request.contextPath }/images/ajax-loader.gif" >
      </div>
   <%-- 댓글 목록 출력 끝 --%>
   
   <div class="align-right">
   
    <%-- 댓글 시작 --%>
   <div id="reply_div">
      <span class="re-title">댓글 달기</span>
      <div align="center">
      <form id="re_form">
         <input type="hidden" name="qna_num" value="${ qnaboard.qna_num }" id="qna_num">
         <input type="hidden" name="auth" value="${ auth }" id="auth">
         <textarea name="re_content" id="re_content" class="rep-content"
            <c:if test="${ auth != 3 }">disabled="disabled"</c:if>
         ><c:if test="${ auth != 3 }">관리자만 작성할 수 있습니다.</c:if></textarea>
         <c:if test="${ auth == 3 }">
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
   <hr size="2" noshade width="100%">
   <%-- 댓글달기 끝 --%>
   
	<%-- 하단 목록, 수정, 삭제 --%>   
         <input type="button" value="목록"  style="color: black; background-color: white; border-color: #d5dfe8" 
         		onclick="location.href='qnaList.do'">
    <div style="float: right;">
         <%-- 로그인 한 회원번호와 작성자 회원번호가 일치해야 수정, 삭제 가능 --%>
         <input type="button" value="수정" onclick="location.href='qnaModifyForm.do?qna_num=${qnaboard.qna_num}'" style="color: black; background-color: white; border-color: #d5dfe8">
         <input type="button" value="삭제" id="qnaDelete_btn" style="color: red; background-color: white; border-color: #d5dfe8">
         <script type="text/javascript">
            var qnaDelete_btn = document.getElementById('qnaDelete_btn');
            //이벤트 연결
            qnaDelete_btn.onclick=function(){
               var choice = confirm('삭제하시겠습니까?');
               if(choice){
                  location.replace('qnaDelete.do?qna_num=${qnaboard.qna_num}');
               }
            };
            
         </script>
   </div>
   	<div style="padding-bottom: 50px;"></div>
</div>
</div>
</body>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</html>