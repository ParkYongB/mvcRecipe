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
import com.util.StringUtil;

/**
 * @Package Name   : com.news.action
 * @FileName  : NewsDetailAction.java
 * @�ۼ���       : 2021. 9. 7. 
 * @�ۼ���       : ������
 * @���α׷� ���� : ���� ������ ������
 */
public class NewsDetailAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer auth =(Integer)session.getAttribute("auth");
		Integer mem_num =(Integer)session.getAttribute("mem_num");
		int user_num = Integer.parseInt(request.getParameter("news_num"));
		NewsDAO dao=NewsDAO.getInstance();
		dao.updateCount(user_num);
		NewsVO news=dao.getNews(user_num);
		news.setNews_title(StringUtil.useNoHtml(news.getNews_title()));
		news.setNews_content(StringUtil.useBrNoHtml(news.getNews_content()));
		
		request.setAttribute("news", news);
		request.setAttribute("auth", auth);
		request.setAttribute("mem_num", mem_num);
		request.setAttribute("user_num",user_num);
		return "/WEB-INF/views/news/newsDetail.jsp";
	}

}
