package com.recipe.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import com.controller.Action;
import com.recipe.dao.RecipeDAO;

/**
 * @Package Name   : com.recipe.action
 * @FileName  : RecipeReplyDeleteAction.java
 * @�ۼ���       : 2021. 9. 10. 
 * @�ۼ���       : ������
 * @���α׷� ���� : ������ �Խ��� ��� ���� �׼�
 */
public class RecipeReplyDeleteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// ���۵� ������ ���ڵ� ó��
		request.setCharacterEncoding("utf-8");
		
		// ���۵� ������ ��ȯ
		int comm_num = Integer.parseInt(request.getParameter("comm_num"));	// ��� ��ȣ
		int writer_num = Integer.parseInt(request.getParameter("user_num"));	// ��� �ۼ��� ��ȣ
		
		Map<String,String> mapAjax = new HashMap<String, String>();
		HttpSession session = request.getSession();
		Integer mem_num = (Integer)session.getAttribute("mem_num");		// �α��� ȸ�� ��ȣ
		Integer auth = (Integer)session.getAttribute("auth");
		
		if(mem_num == null) {	// �α��� x
			mapAjax.put("result", "logout");
		}else if((mem_num!=null && mem_num == writer_num) || auth == 3) {	// �α��� O �ۼ��ڿ� ��ġ or �������ΰ��
			RecipeDAO dao = RecipeDAO.getInstance();
			dao.deleteRecipeCommend(comm_num);
			mapAjax.put("result", "success");
		}else { //�α����� �Ǿ��ְ� �α����� ȸ����ȣ�� �ۼ��� ȸ����ȣ�� ����ġ
			mapAjax.put("result", "wrongAccess");
		}
		
		// JSON �����ͷ� ��ȯ
	    ObjectMapper mapper = new ObjectMapper();
	    String ajaxData = mapper.writeValueAsString(mapAjax);
	      
	    request.setAttribute("ajaxData", ajaxData);

		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
