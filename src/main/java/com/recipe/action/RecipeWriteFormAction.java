package com.recipe.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.controller.Action;

/**
 * @Package Name   : com.recipe.action
 * @FileName  : RecipeWriteFormAction.java
 * @작성일       : 2021. 9. 7. 
 * @작성자       : 이현지
 * @프로그램 설명 : 레시피 글작성 폼 액션 클래스
 */
public class RecipeWriteFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 로그인 체크
		HttpSession session = request.getSession();
		Integer mem_num = (Integer)session.getAttribute("mem_num");
		
		if(mem_num == null) { // 로그인 하지 않은 경우
			return "redirect:/member/loginForm.do";
		}			
		
		// 로그인 한 경우
		// JSP 경로 반환
		return "/WEB-INF/views/recipe/recipeWriteForm.jsp";
	}

}
