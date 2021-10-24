package com.recipe.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import com.controller.Action;
import com.recipe.dao.RecipeDAO;
import com.recipe.vo.RecipeCommendsVO;

/**
 * @Package Name   : kr.board.action
 * @FileName  : WriteReplyAction.java
 * @�ۼ���       : 2021. 9. 9. 
 * @�ۼ���       : ������
 * @���α׷� ���� : ������ �󼼰Խ��� ��� �ۼ� �׼�
 */

public class RecipeReplyWriteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Map<String, String> mapAjax = new HashMap<String, String>();
		
		HttpSession session = request.getSession();
		Integer mem_num = (Integer)session.getAttribute("mem_num");
		
		if(mem_num == null) {	// �α����� �ȵ� ���
			mapAjax.put("result", "logout");
		}else {	// �α��� �� ���
			RecipeCommendsVO recipeReply = new RecipeCommendsVO();
			recipeReply.setComm_con(request.getParameter("re_content"));
			recipeReply.setBoard_num(Integer.parseInt(request.getParameter("board_num")));
			recipeReply.setMem_num(mem_num); // ȸ����ȣ(��� �ۼ���)
		
			RecipeDAO dao = RecipeDAO.getInstance();
			dao.insertRecipeCommend(recipeReply);
			
			mapAjax.put("result", "success");
		}
		
		// JSON ������ ����
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
