package com.qnaboard.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import com.controller.Action;
import com.qnaboard.dao.QnaBoardDAO;

/**
 * @Package Name   : com.qnaboard.action
 * @FileName  : QnaReplyDeleteAction.java
 * @작성일       : 2021. 9. 12. 
 * @작성자       : 나윤경
 * @프로그램 설명 : 고객센터 게시판 댓글 삭제 액션
 */
public class QnaReplyDeleteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		// 전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
				
		// 전송된 데이터 반환
		int comm_num = Integer.parseInt(request.getParameter("comm_num"));	// 댓글 번호
		int writer_auth = Integer.parseInt(request.getParameter("writer_auth"));	// 관리자 등급
				
		Map<String,String> mapAjax = new HashMap<String, String>();
			
		if(writer_auth==3) {	// 관리자 등급이 3이면
			QnaBoardDAO dao = QnaBoardDAO.getInstance();
			dao.deleteQnaCommend(comm_num);
			mapAjax.put("result", "success");
		}else { //관리자가 아닌 경우
			mapAjax.put("result", "Wrong");
		}
				
		// JSON 데이터로 변환
		   ObjectMapper mapper = new ObjectMapper();
		   String ajaxData = mapper.writeValueAsString(mapAjax);
			      
		   request.setAttribute("ajaxData", ajaxData);

				
		   return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
