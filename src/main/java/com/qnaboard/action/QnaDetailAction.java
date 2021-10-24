package com.qnaboard.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.controller.Action;
import com.qnaboard.dao.QnaBoardDAO;
import com.qnaboard.vo.QnaBoardVO;
import com.util.StringUtil;

/**
 * @Package Name   : com.qnaboard.action
 * @FileName  : QnaDetailAction.java
 * @�ۼ���       : 2021. 9. 9. 
 * @�ۼ���       : ������
 * @���α׷� ���� : ������ �� ������ �׼�
 */
public class QnaDetailAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		//�� ��ȣ ��ȯ
		Integer qna_num = Integer.parseInt(request.getParameter("qna_num")); 
		
		QnaBoardDAO dao = QnaBoardDAO.getInstance();
		QnaBoardVO qnaboard = dao.getQnaBoardDetail(qna_num);
		
		//HTML�� ������� ����
		qnaboard.setQna_title(StringUtil.useNoHtml(qnaboard.getQna_title()));
		
		//HTML�� ������� �����鼭 �ٹٲ� ó��
		qnaboard.setQna_content(StringUtil.useBrNoHtml(qnaboard.getQna_content()));
		
		request.setAttribute("qnaboard", qnaboard);
		
		return "/WEB-INF/views/qnaboard/qnaDetail.jsp";
	}

}
