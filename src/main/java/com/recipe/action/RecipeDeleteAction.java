package com.recipe.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.controller.Action;
import com.recipe.dao.RecipeDAO;
import com.recipe.vo.RecipeVO;
import com.util.FileUtil;

/**
 * @Package Name   : com.recipe.action
 * @FileName  : RecipeDeleteAction.java
 * @�ۼ���       : 2021. 9. 7. 
 * @�ۼ���       : ������
 * @���α׷� ���� : ������ �ۻ��� �׼� Ŭ����
 */

public class RecipeDeleteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// �α��� üũ
		HttpSession session = request.getSession();
		Integer mem_num = (Integer)session.getAttribute("mem_num");
		Integer auth = (Integer)session.getAttribute("auth");
		
		if(mem_num == null) { // �α��� ���� ���� ���
			return "redirect:/member/loginForm.do";
		}
		
		// �α��� �� ���
		// �۹�ȣ ��ȯ
		int board_num = Integer.parseInt(request.getParameter("board_num"));
				
		// RecipeDAO ȣ��
		RecipeDAO dao = RecipeDAO.getInstance();
		RecipeVO recipe = dao.getRecipeBoard(board_num);
		if(mem_num != null && mem_num != recipe.getMem_num() && auth != 3) {
			// �α����� ȸ����ȣ�� �ۼ��� ȸ����ȣ�� ����ġ
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		// �α����� ȸ����ȣ�� �ۼ��� ȸ����ȣ�� ��ġ
		// DB ���� ����
		dao.deleteRecipe(board_num);
		// ���� ����
		FileUtil.removeFile(request, recipe.getFilename());
				
		// JSP ��� ��ȯ
		return "/WEB-INF/views/recipe/recipeDelete.jsp";
		}
	}
