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
 * @FileName  : RecipeBookmarkAction.java
 * @�ۼ���       : 2021. 9. 8. 
 * @�ۼ���       : ������
 * @���α׷� ���� : �����ǰԽ��� �ϸ�ũ �׼�
 */
public class RecipeBookmarkAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		// ���۵� ������ ����
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		int mem_num = Integer.parseInt(request.getParameter("mem_num"));
		
		
		RecipeDAO dao = RecipeDAO.getInstance();
		// ȸ���� �����Ǹ� �ϸ�ũ�� �̹� �����ߴ��� Ȯ��
		int check = 0;
		check = dao.bookmarkCheck(board_num, mem_num);
		
		Map<String, String> ajaxMap = new HashMap<String, String>();
		
		
		if(check == 0) {	// �����Ǹ� �ϸ�ũ ���� �ʾ��� ���
			// �ϸ�ũ�� ������ ����
			dao.addBookmark(board_num, mem_num);
			ajaxMap.put("result", "addition");
		}else {			// ������ �ϸ�ũ �� ���
			dao.deleteBookmark(board_num, mem_num);
			ajaxMap.put("result", "cancel");
		}
		
		// JSON ������ ���ڿ� �����ͷ� ��ȯ
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(ajaxMap);
		
		request.setAttribute("ajaxData", ajaxData);
		
		// JSP ��� ��ȯ   ajax���� ���������� ó���ϱ� ���� ������
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
