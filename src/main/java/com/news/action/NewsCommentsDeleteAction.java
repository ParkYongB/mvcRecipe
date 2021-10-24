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
 * @�ۼ���       : 2021. 9. 13. 
 * @�ۼ���       : ������
 * @���α׷� ���� : ��� ���� ����
 */
public class NewsCommentsDeleteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//���۵� ������ ���ڵ� ó��
		request.setCharacterEncoding("utf-8");
		//���۵� ������ ��ȯ
		int comment_num = Integer.parseInt(request.getParameter("comment_num"));
		int writer_num = Integer.parseInt(request.getParameter("mem_num"));
		
		Map<String,String> mapAjax = new HashMap<String,String>();
		//�α����� �̿��� ���� �޾ƿ���
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
