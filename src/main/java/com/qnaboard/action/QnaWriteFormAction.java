package com.qnaboard.action;

import javax.servlet.http.HttpServletRequest;


import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.controller.Action;

/**
 * @Package Name   : com.qnaboard.action
 * @FileName  : WriteFormAction.java
 * @�ۼ���       : 2021. 9. 6. 
 * @�ۼ���       : ������
 * @���α׷� ���� : ������ �Խ��� �� �ۼ�(��)
 */
public class QnaWriteFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//�α��� üũ
		HttpSession session = request.getSession();
		Integer mem_num = (Integer)session.getAttribute("mem_num");
		String mem_id = (String) session.getAttribute("id");
		
		
		if(mem_num == null) {//�α��� ���� ���� ���
			return "/WEB-INF/views/qnaboard/qnaWriteForm.jsp";
		}else {//�α��� �� ���
			request.setAttribute("mem_id", mem_id);
			request.setAttribute("mem_num", mem_num);
			
			
			//JSP ��� ��ȯ
			return "/WEB-INF/views/qnaboard/qnaWriteForm.jsp";
		}
		
	}

}
