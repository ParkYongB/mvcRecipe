/**
 * 
 */
package com.news.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.controller.Action;
import com.news.dao.NewsDAO;
import com.news.vo.NewsVO;

/**
 * @Package Name   : com.news.action
 * @FileName  : NewsModifyFormAction.java
 * @�ۼ���       : 2021. 9. 7. 
 * @�ۼ���       : ������
 * @���α׷� ���� : ���� ���� ������ ���� �����͸� �������ִ� ����
 */
public class NewsModifyFormAction implements Action{

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
		NewsVO news=dao.getNews(num);
		request.setAttribute("news", news);
		return "/WEB-INF/views/news/newsModifyForm.jsp";
	}
}
