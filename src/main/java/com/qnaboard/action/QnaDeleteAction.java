package com.qnaboard.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.controller.Action;
import com.qnaboard.dao.QnaBoardDAO;

/**
 * @Package Name   : com.qnaboard.action
 * @FileName  : QnaDeleteAction.java
 * @�ۼ���       : 2021. 9. 12. 
 * @�ۼ���       : ������
 * @���α׷� ���� : ������ �Խ��� ����
 */
public class QnaDeleteAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//���۵� ������ ���ڵ� ó��
		request.setCharacterEncoding("utf-8");
		
		//�� ��ȣ ��ȯ
		int qna_num = Integer.parseInt(request.getParameter("qna_num"));
		
		//DAO ȣ��
		QnaBoardDAO dao = QnaBoardDAO.getInstance();
		
		//�� ����
		dao.qnaBoardDelete(qna_num);
		
		//JSP ��� ��ȯ
		return "/WEB-INF/views/qnaboard/qnaDelete.jsp";
	}

}
