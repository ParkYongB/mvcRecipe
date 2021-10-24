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
import com.news.vo.NewsCommentsVO;

/**
 * @Package Name   : com.news.action
 * @FileName  : NewsCommentsUpdateAction.java
 * @작성일       : 2021. 9. 10. 
 * @작성자       : 신혜지
 * @프로그램 설명 : 
 */
public class NewsCommentsUpdateAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");

		int writer_num = Integer.parseInt(request.getParameter("mem_num"));
		Map<String,String> mapAjax = new HashMap<String,String>();
		HttpSession session = request.getSession();
		Integer mem_num = (Integer)session.getAttribute("mem_num");
		Integer auth = (Integer)session.getAttribute("auth");
		
		if(mem_num==null) {
			mapAjax.put("result", "logout");
		}else if((mem_num!=null && mem_num == writer_num) || auth == 3) {//로그인이 되어있고 로그인한 회원번호와 작성자 회원번호 일치
			NewsCommentsVO comments = new NewsCommentsVO();
			comments.setComm_num(Integer.parseInt(request.getParameter("comm_num")));
			comments.setComm_con(request.getParameter("comm_con"));
			NewsDAO dao = NewsDAO.getInstance();
			dao.updateCommentsNews(comments);
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
