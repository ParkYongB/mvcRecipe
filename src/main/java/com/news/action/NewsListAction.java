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
 * @작성일       : 2021. 9. 7. 
 * @작성자       : 신혜지
 * @프로그램 설명 : 잇마이팁 액션 클래스
 */
public class NewsListAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//세션으로 회원등급파악
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
		
		//페이지처리 기본일때
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
		
			
			
			//페이지처리 제목검색시
		}else if(division.equals("제목")) {
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
		}else if(division.equals("내용")) {
			
			String pageNum = request.getParameter("pageNum");
			if(pageNum==null) pageNum = "1";
				
			NewsDAO dao = NewsDAO.getInstance();
			int count = dao.getNewsCount(division,news_search);	// 검색조건에 부합한 총 레코드 수
					
			// 페이지 처리
			// currentPage, count, rowCount, pageCount, url
			PagingUtil page = new PagingUtil(news_search,division,Integer.parseInt(pageNum), count, 4, 5,"newsList.do");
					
			List<NewsVO> list =null;
					
			if(count > 0) {
				list = dao.getNewsList(page.getStartCount(), page.getEndCount(),division,news_search);
			}

					
			// list와 총 페이지, 페이지 하단부분 넘겨주기
			request.setAttribute("count", count);
			request.setAttribute("list", list);
			request.setAttribute("pagingHtml", page.getPagingHtml());
			request.setAttribute("auth",auth);	
					
			// 모두의 레시피로 이동		
			
			return "/WEB-INF/views/news/newsList.jsp";
			
			// 검색조건이 작성자인 경우
		}else {
			String pageNum = request.getParameter("pageNum");
			if(pageNum==null) pageNum = "1";
				
			NewsDAO dao = NewsDAO.getInstance();
			int count = dao.getNewsCount(division,news_search);	// 검색조건에 부합한 총 레코드 수
					
			// 페이지 처리
			// currentPage, count, rowCount, pageCount, url
			PagingUtil page = new PagingUtil(news_search,division,Integer.parseInt(pageNum), count, 4, 5,"newsList.do");
					
			List<NewsVO> list =null;
					
			// 총 레코드가 0이 아닐 때 list에 값을 담는다
			if(count > 0) {
				list = dao.getNewsList(page.getStartCount(), page.getEndCount(),division,news_search);
			}

					
			// list와 총 페이지, 페이지 하단부분 넘겨주기
			request.setAttribute("count", count);
			request.setAttribute("list", list);
			request.setAttribute("pagingHtml", page.getPagingHtml());
			request.setAttribute("auth",auth);	
			request.setAttribute("division", division);
			request.setAttribute("search", news_search);
			// 모두의 레시피로 이동	
			return "/WEB-INF/views/news/newsList.jsp";
		}
	} 

}
