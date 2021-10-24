package com.qnaboard.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.controller.Action;
import com.qnaboard.dao.QnaBoardDAO;
import com.qnaboard.vo.QnaBoardVO;

/**
 * @Package Name   : com.qnaboard.action
 * @FileName  : WriteAction.java
 * @작성일       : 2021. 9. 6. 
 * @작성자       : 나윤경
 * @프로그램 설명 : 고객센터 게시판 글 작성
 */
public class QnaWriteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		
		//자바빈(VO)생성
		QnaBoardVO qnaboardVO = new QnaBoardVO();
		
		qnaboardVO.setQna_title(request.getParameter("qna_title"));
		qnaboardVO.setQna_content(request.getParameter("qna_content"));
		qnaboardVO.setQna_id(request.getParameter("qna_id"));
		qnaboardVO.setQna_passwd(request.getParameter("qna_passwd"));
		qnaboardVO.setQna_ip(request.getRemoteAddr());
		
		//QnaBoardDAO 호출
		QnaBoardDAO dao = QnaBoardDAO.getInstance();
		dao.QnaBoardWrite(qnaboardVO);
		
		//JSP 경로 반환
		return "/WEB-INF/views/qnaboard/qnaWrite.jsp";
	}

}
