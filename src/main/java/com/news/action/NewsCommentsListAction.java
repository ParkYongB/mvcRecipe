/**
 * 
 */
package com.news.action;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import com.controller.Action;
import com.news.dao.NewsDAO;
import com.news.vo.NewsCommentsVO;
import com.util.PagingUtil;

/**
 * @Package Name   : com.news.action
 * @FileName  : NewsCommentsListAction.java
 * @작성일       : 2021. 9. 9. 
 * @작성자       : 신혜지
 * @프로그램 설명 : 
 */
public class NewsCommentsListAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("utf-8");
		
		String pageNum = request.getParameter("pageNum");
		if(pageNum==null) {
			pageNum = "1";
		}
		
		int news_num = Integer.parseInt(request.getParameter("news_num"));
		
		int rowCount = 5;
		
		NewsDAO dao = NewsDAO.getInstance();
		int count = dao.getNewsCommentsCount(news_num);
		
		
		PagingUtil page = new PagingUtil(Integer.parseInt(pageNum),count,rowCount,1,null);
		
		List<NewsCommentsVO> list =null;
		if(count > 0) {
			list = dao.getListCommentsNews(page.getStartCount(),page.getEndCount(),news_num);
		}else {
			list = Collections.emptyList();
		}
		Map<String,Object> mapAjax = new HashMap<String,Object>();
		mapAjax.put("count", count);
		mapAjax.put("rowCount", rowCount);
		mapAjax.put("list", list);
		
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
