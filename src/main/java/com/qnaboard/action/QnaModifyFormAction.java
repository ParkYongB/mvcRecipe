package com.qnaboard.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.controller.Action;
import com.qnaboard.dao.QnaBoardDAO;
import com.qnaboard.vo.QnaBoardVO;

public class QnaModifyFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		//글번호 반환
		int qna_num = Integer.parseInt(request.getParameter("qna_num"));
		
		QnaBoardDAO dao = QnaBoardDAO.getInstance();
		QnaBoardVO qnaboardVO = dao.getQnaBoardDetail(qna_num);
		
		//request에 데이터 저장
		request.setAttribute("qnaboardVO", qnaboardVO);
		
		return "/WEB-INF/views/qnaboard/qnaModifyForm.jsp";
	}

}
