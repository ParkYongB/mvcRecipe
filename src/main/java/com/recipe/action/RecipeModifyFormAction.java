package com.recipe.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.controller.Action;
import com.recipe.dao.RecipeDAO;
import com.recipe.vo.RecipeVO;

/**
 * @Package Name   : com.recipe.action
 * @FileName  : RecipeModifyFormAction.java
 * @�ۼ���       : 2021. 9. 7. 
 * @�ۼ���       : ������
 * @���α׷� ���� : ������ �ۼ��� �� �׼� Ŭ����
 */

public class RecipeModifyFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// �α��� üũ
		HttpSession session = request.getSession();
		Integer mem_num = (Integer)session.getAttribute("mem_num");
		Integer auth = (Integer)session.getAttribute("auth");
		
		if(mem_num == null) { // �α��� ���� ���� ���
			return "redirect:/member/loginForm.do";
		}
		
		// �۹�ȣ ����
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		
		RecipeDAO dao = RecipeDAO.getInstance();
		RecipeVO recipe = dao.getRecipeBoard(board_num);
		if(mem_num != recipe.getMem_num() && auth !=3) { // �α����� ȸ����ȣ�� �ۼ��� ȸ����ȣ ����ġ�ϰ� �����ڰ� �ƴ϶��
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		// �α����� �����̰� ȸ����ȣ�� �ۼ��� ȸ����ȣ ��ġ
		request.setAttribute("recipe", recipe);
		
		// JSP ��� ��ȯ
		return "/WEB-INF/views/recipe/recipeModifyForm.jsp";
	}

}
