package com.news.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.controller.Action;

/**
 * @Package Name   : com.news.action
 * @FileName  : NewsFormAction.java
 * @�ۼ���       : 2021. 9. 7. 
 * @�ۼ���       : ������
 * @���α׷� ���� : �ո����� �׼� Ŭ����
 */
public class NewsFormAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer mem_num = (Integer)session.getAttribute("mem_num");
		Integer auth =(Integer)session.getAttribute("auth");

		if(mem_num == null || auth != 3) {//�α��� ���� ���� ���, ������ 3�� �ƴ� ���
			return "redirect:/member/loginForm.do";
		}
		return "/WEB-INF/views/news/newsForm.jsp";
	}
}
