package com.recipe.action;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import com.controller.Action;
import com.recipe.dao.RecipeDAO;

/**
 * @Package Name   : com.recipe.action
 * @FileName  : RecipeRecomAction.java
 * @�ۼ���       : 2021. 9. 8. 
 * @�ۼ���       : ������
 * @���α׷� ���� : ������ ��õ ������Ʈ(�ߺ�üũ �� ���� �� ����) �׼� ajax ��ȯ
 */
public class RecipeRecomAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		// ���۵� ������ ����
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		int mem_num = Integer.parseInt(request.getParameter("mem_num"));
		
		
		// ��õ �ߺ� üũ�� ���� check �Լ� ����
		RecipeDAO dao = RecipeDAO.getInstance();
		int duplication = dao.recomDuplicationCheck(board_num, mem_num);
		
		if(duplication == 0) { // ��õ���� �ʾҴٸ� ��õ �߰�
			dao.recomAdd(board_num, mem_num);
		}else { // ��õ�� �ߴٸ� ��õ ����
			dao.recomDelete(board_num, mem_num);
		}
		
		// ���� ��õ�� ���ϴ� �Լ� ����
		int recom_count = dao.recomCount(board_num);
		
		// JSON �������� �񵿱� ������ ���� ���ڿ� Map ����
		Map<String, String> mapAjax = new HashMap<String, String>();
		mapAjax.put("count", String.valueOf(recom_count));
		mapAjax.put("duplication", String.valueOf(duplication));
		
		// JSON ������ ���ڿ� �����ͷ� ��ȯ
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		// JSP ��� ��ȯ   ajax���� ���������� ó���ϱ� ���� ������
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
