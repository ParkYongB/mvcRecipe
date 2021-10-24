package com.qnaboard.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.controller.Action;
import com.qnaboard.dao.QnaBoardDAO;
import com.qnaboard.vo.QnaBoardVO;

/**
 * @Package Name   : com.qnaboard.action
 * @FileName  : WriteAction.java
 * @�ۼ���       : 2021. 9. 6. 
 * @�ۼ���       : ������
 * @���α׷� ���� : ������ �Խ��� �� �ۼ�
 */
public class QnaWriteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//���۵� ������ ���ڵ� ó��
		request.setCharacterEncoding("utf-8");
		
		//�ڹٺ�(VO)����
		QnaBoardVO qnaboardVO = new QnaBoardVO();
		
		qnaboardVO.setQna_title(request.getParameter("qna_title"));
		qnaboardVO.setQna_content(request.getParameter("qna_content"));
		qnaboardVO.setQna_id(request.getParameter("qna_id"));
		qnaboardVO.setQna_passwd(request.getParameter("qna_passwd"));
		qnaboardVO.setQna_ip(request.getRemoteAddr());
		
		//QnaBoardDAO ȣ��
		QnaBoardDAO dao = QnaBoardDAO.getInstance();
		dao.QnaBoardWrite(qnaboardVO);
		
		//JSP ��� ��ȯ
		return "/WEB-INF/views/qnaboard/qnaWrite.jsp";
	}

}
