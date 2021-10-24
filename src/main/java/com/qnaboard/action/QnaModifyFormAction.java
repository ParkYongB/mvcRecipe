package com.qnaboard.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.controller.Action;
import com.qnaboard.dao.QnaBoardDAO;
import com.qnaboard.vo.QnaBoardVO;

public class QnaModifyFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		//�۹�ȣ ��ȯ
		int qna_num = Integer.parseInt(request.getParameter("qna_num"));
		
		QnaBoardDAO dao = QnaBoardDAO.getInstance();
		QnaBoardVO qnaboardVO = dao.getQnaBoardDetail(qna_num);
		
		//request�� ������ ����
		request.setAttribute("qnaboardVO", qnaboardVO);
		
		return "/WEB-INF/views/qnaboard/qnaModifyForm.jsp";
	}

}
