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
 * @FileName  : NwesCommentsAction.java
 * @�ۼ���       : 2021. 9. 9. 
 * @�ۼ���       : ������
 * @���α׷� ���� : ���� �ڸ�Ʈ �ٴ� �׼�
 */
public class NewsCommentsAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Map<String,String> mapAjax = new HashMap<String,String>();
		
		HttpSession session = request.getSession();
		Integer mem_num = (Integer)session.getAttribute("mem_num");
		if(mem_num == null){
			mapAjax.put("result","logout");
		}else { //�α��� �� ��쿡
			NewsCommentsVO commentsVO = new NewsCommentsVO();
			commentsVO.setComm_con(request.getParameter("re_content"));
			commentsVO.setNews_num(Integer.parseInt(request.getParameter("news_num")));
			NewsDAO dao = NewsDAO.getInstance();
			commentsVO.setMem_num(mem_num);
			dao.insertReplyBoard(commentsVO);
			
			mapAjax.put("result", "success");
		}
		ObjectMapper mapper = new ObjectMapper();
		
		String ajaxData = mapper.writeValueAsString(mapAjax);
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
