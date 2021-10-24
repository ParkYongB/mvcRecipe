package com.qnaboard.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.controller.Action;
import com.qnaboard.dao.QnaBoardDAO;

/**
 * @Package Name   : com.qnaboard.action
 * @FileName  : QnaDeleteAction.java
 * @작성일       : 2021. 9. 12. 
 * @작성자       : 나윤경
 * @프로그램 설명 : 고객센터 게시판 삭제
 */
public class QnaDeleteAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		
		//글 번호 반환
		int qna_num = Integer.parseInt(request.getParameter("qna_num"));
		
		//DAO 호출
		QnaBoardDAO dao = QnaBoardDAO.getInstance();
		
		//글 삭제
		dao.qnaBoardDelete(qna_num);
		
		//JSP 경로 반환
		return "/WEB-INF/views/qnaboard/qnaDelete.jsp";
	}

}
