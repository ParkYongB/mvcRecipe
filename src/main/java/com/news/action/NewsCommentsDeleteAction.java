/**
 * 
 */
package com.news.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import com.controller.Action;
import com.news.dao.NewsDAO;

/**
 * @Package Name   : com.news.action
 * @FileName  : NewsCommentsDeleteAction.java
 * @작성일       : 2021. 9. 13. 
 * @작성자       : 신혜지
 * @프로그램 설명 : 댓글 삭제 서블릿
 */
public class NewsCommentsDeleteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		//전송된 데이터 반환
		int comment_num = Integer.parseInt(request.getParameter("comment_num"));
		int writer_num = Integer.parseInt(request.getParameter("mem_num"));
		
		Map<String,String> mapAjax = new HashMap<String,String>();
		//로그인한 이용자 정보 받아오기
		HttpSession session = request.getSession();
		Integer mem_num = (Integer)session.getAttribute("mem_num");
		
		if(mem_num == null) {
			mapAjax.put("result", "logout");
		}else if(mem_num!=null && writer_num == mem_num){
			NewsDAO dao = NewsDAO.getInstance();
			dao.deleteCommentsNews(comment_num);
			mapAjax.put("result", "success");
		}else {
			mapAjax.put("result", "wrongAccess");
		}
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}
}
