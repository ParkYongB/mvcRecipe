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
 * @작성일       : 2021. 9. 7. 
 * @작성자       : 이현지
 * @프로그램 설명 : 레시피 글수정 폼 액션 클래스
 */

public class RecipeModifyFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 로그인 체크
		HttpSession session = request.getSession();
		Integer mem_num = (Integer)session.getAttribute("mem_num");
		Integer auth = (Integer)session.getAttribute("auth");
		
		if(mem_num == null) { // 로그인 하지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		
		// 글번호 전달
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		
		RecipeDAO dao = RecipeDAO.getInstance();
		RecipeVO recipe = dao.getRecipeBoard(board_num);
		if(mem_num != recipe.getMem_num() && auth !=3) { // 로그인한 회원번호와 작성자 회원번호 불일치하고 관리자가 아니라면
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		// 로그인한 상태이고 회원번호와 작성자 회원번호 일치
		request.setAttribute("recipe", recipe);
		
		// JSP 경로 반환
		return "/WEB-INF/views/recipe/recipeModifyForm.jsp";
	}

}
