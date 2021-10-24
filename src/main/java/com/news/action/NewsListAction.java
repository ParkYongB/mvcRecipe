package com.news.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.controller.Action;
import com.news.dao.NewsDAO;
import com.news.vo.NewsVO;
import com.util.PagingUtil;

/**
 * @Package Name   : com.news.action
 * @FileName  : NewsListAction.java
 * @�ۼ���       : 2021. 9. 7. 
 * @�ۼ���       : ������
 * @���α׷� ���� : �ո����� �׼� Ŭ����
 */
public class NewsListAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//�������� ȸ������ľ�
		HttpSession session = request.getSession();
		Integer auth =(Integer)session.getAttribute("auth");
		String news_search = request.getParameter("search");
		String division = request.getParameter("division");
		request.setCharacterEncoding("utf-8");
		request.setAttribute("search",news_search);
		request.setAttribute("division", division);
		
		//
		if(division == null) {
		String pageNum = request.getParameter("pageNum");
		if(pageNum==null)pageNum = "1";
		
		NewsDAO dao = NewsDAO.getInstance();
		int count = dao.getNewsCount();
		
		//������ó�� �⺻�϶�
		//currentPage, count, rowCount, pageCount, url
		PagingUtil page = new PagingUtil(Integer.parseInt(pageNum),count,10,10,"newsList.do");
		
		List<NewsVO> list =null;
		if(count > 0) {
			list = dao.getNewsList(page.getStartCount(), page.getEndCount());
		}
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("pagingHtml", page.getPagingHtml());
		request.setAttribute("auth",auth);
			return "/WEB-INF/views/news/newsList.jsp";
		
			
			
			//������ó�� ����˻���
		}else if(division.equals("����")) {
			String pageNum = request.getParameter("pageNum");
			if(pageNum==null) pageNum = "1";		
			
			NewsDAO dao = NewsDAO.getInstance();
			int count = dao.getNewsCount(division,news_search);
			PagingUtil page = new PagingUtil(news_search,division,Integer.parseInt(pageNum),count,10,10,"newsList.do");
			List<NewsVO> list =null;
			if(count > 0) {
				list = dao.getNewsList(page.getStartCount(), page.getEndCount(),division,news_search);
			}
			request.setAttribute("count", count);
			request.setAttribute("list", list);
			request.setAttribute("pagingHtml", page.getPagingHtml());
			request.setAttribute("auth",auth);	
		
			return "/WEB-INF/views/news/newsList.jsp";
		}else if(division.equals("����")) {
			
			String pageNum = request.getParameter("pageNum");
			if(pageNum==null) pageNum = "1";
				
			NewsDAO dao = NewsDAO.getInstance();
			int count = dao.getNewsCount(division,news_search);	// �˻����ǿ� ������ �� ���ڵ� ��
					
			// ������ ó��
			// currentPage, count, rowCount, pageCount, url
			PagingUtil page = new PagingUtil(news_search,division,Integer.parseInt(pageNum), count, 4, 5,"newsList.do");
					
			List<NewsVO> list =null;
					
			if(count > 0) {
				list = dao.getNewsList(page.getStartCount(), page.getEndCount(),division,news_search);
			}

					
			// list�� �� ������, ������ �ϴܺκ� �Ѱ��ֱ�
			request.setAttribute("count", count);
			request.setAttribute("list", list);
			request.setAttribute("pagingHtml", page.getPagingHtml());
			request.setAttribute("auth",auth);	
					
			// ����� �����Ƿ� �̵�		
			
			return "/WEB-INF/views/news/newsList.jsp";
			
			// �˻������� �ۼ����� ���
		}else {
			String pageNum = request.getParameter("pageNum");
			if(pageNum==null) pageNum = "1";
				
			NewsDAO dao = NewsDAO.getInstance();
			int count = dao.getNewsCount(division,news_search);	// �˻����ǿ� ������ �� ���ڵ� ��
					
			// ������ ó��
			// currentPage, count, rowCount, pageCount, url
			PagingUtil page = new PagingUtil(news_search,division,Integer.parseInt(pageNum), count, 4, 5,"newsList.do");
					
			List<NewsVO> list =null;
					
			// �� ���ڵ尡 0�� �ƴ� �� list�� ���� ��´�
			if(count > 0) {
				list = dao.getNewsList(page.getStartCount(), page.getEndCount(),division,news_search);
			}

					
			// list�� �� ������, ������ �ϴܺκ� �Ѱ��ֱ�
			request.setAttribute("count", count);
			request.setAttribute("list", list);
			request.setAttribute("pagingHtml", page.getPagingHtml());
			request.setAttribute("auth",auth);	
			request.setAttribute("division", division);
			request.setAttribute("search", news_search);
			// ����� �����Ƿ� �̵�	
			return "/WEB-INF/views/news/newsList.jsp";
		}
	} 

}
