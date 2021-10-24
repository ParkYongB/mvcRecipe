package com.qnaboard.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.controller.Action;
import com.qnaboard.dao.QnaBoardDAO;
import com.qnaboard.vo.QnaBoardVO;

public class QnaModifyAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//���۵� ������ ���ڵ� ó��
		request.setCharacterEncoding("utf-8");
	
		QnaBoardVO qnaboardVO = new QnaBoardVO();
		qnaboardVO.setQna_num(Integer.parseInt(request.getParameter("qna_num")));
		qnaboardVO.setQna_title(request.getParameter("qna_title"));
		qnaboardVO.setQna_passwd(request.getParameter("qna_passwd"));
		qnaboardVO.setQna_content(request.getParameter("qna_content"));
		qnaboardVO.setQna_ip(request.getRemoteAddr());
		
		QnaBoardDAO dao = QnaBoardDAO.getInstance();
		
		//��й�ȣ ������ ���� �� ���� ���ڵ带 �ڹٺ� ��Ƽ� ��ȯ
		QnaBoardVO qnaboard = dao.getQnaBoardDetail(qnaboardVO.getQna_num());
		boolean check =false;
		if(qnaboard!=null) {
			//��й�ȣ ��ġ ���� üũ
			check = qnaboard.isCheckedPassword(qnaboardVO.getQna_passwd());
		}
		if(check) {	//���� ����
			//�� ����
			dao.qnaBoardModify(qnaboardVO);
		}
		//request�� ������ ����
		request.setAttribute("check", check);
		
		//JSP ��� ��ȯ
		return "/WEB-INF/views/qnaboard/qnaModify.jsp";
	}

}
