package com.recipe.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.controller.Action;

/**
 * @Package Name   : com.recipe.action
 * @FileName  : RecipeWriteFormAction.java
 * @�ۼ���       : 2021. 9. 7. 
 * @�ۼ���       : ������
 * @���α׷� ���� : ������ ���ۼ� �� �׼� Ŭ����
 */
public class RecipeWriteFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// �α��� üũ
		HttpSession session = request.getSession();
		Integer mem_num = (Integer)session.getAttribute("mem_num");
		
		if(mem_num == null) { // �α��� ���� ���� ���
			return "redirect:/member/loginForm.do";
		}			
		
		// �α��� �� ���
		// JSP ��� ��ȯ
		return "/WEB-INF/views/recipe/recipeWriteForm.jsp";
	}

}
