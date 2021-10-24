/**
 * 
 */
package com.news.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.controller.Action;
import com.news.dao.NewsDAO;

/**
 * @Package Name   : com.news.action
 * @FileName  : NewsDeleteAction.java
 * @�ۼ���       : 2021. 9. 8. 
 * @�ۼ���       : ������
 * @���α׷� ���� : ���������� �����ϴ� �����Դϴ�.
 */
public class NewsDeleteAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer mem_num = (Integer)session.getAttribute("mem_num");
		Integer auth =(Integer)session.getAttribute("auth");

		if(mem_num == null || auth != 3) {//�α��� ���� ���� ���, ������ 3�� �ƴ� ���
			return "redirect:/member/loginForm.do";
		}
		
		int num = Integer.parseInt(request.getParameter("news_num"));
		NewsDAO dao=NewsDAO.getInstance();
		dao.DeleteNews(num);
		return "/WEB-INF/views/news/newsDelete.jsp";
	}

}
