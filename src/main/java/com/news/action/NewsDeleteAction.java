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
 * @작성일       : 2021. 9. 8. 
 * @작성자       : 신혜지
 * @프로그램 설명 : 공지사항을 삭제하는 서블릿입니다.
 */
public class NewsDeleteAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer mem_num = (Integer)session.getAttribute("mem_num");
		Integer auth =(Integer)session.getAttribute("auth");

		if(mem_num == null || auth != 3) {//로그인 되지 않은 경우, 권한이 3이 아닌 경우
			return "redirect:/member/loginForm.do";
		}
		
		int num = Integer.parseInt(request.getParameter("news_num"));
		NewsDAO dao=NewsDAO.getInstance();
		dao.DeleteNews(num);
		return "/WEB-INF/views/news/newsDelete.jsp";
	}

}
