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
 * @작성일       : 2021. 9. 7. 
 * @작성자       : 이현지
 * @프로그램 설명 : 레시피 글삭제 액션 클래스
 */

public class RecipeDeleteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 로그인 체크
		HttpSession session = request.getSession();
		Integer mem_num = (Integer)session.getAttribute("mem_num");
		Integer auth = (Integer)session.getAttribute("auth");
		
		if(mem_num == null) { // 로그인 하지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		
		// 로그인 한 경우
		// 글번호 반환
		int board_num = Integer.parseInt(request.getParameter("board_num"));
				
		// RecipeDAO 호출
		RecipeDAO dao = RecipeDAO.getInstance();
		RecipeVO recipe = dao.getRecipeBoard(board_num);
		if(mem_num != null && mem_num != recipe.getMem_num() && auth != 3) {
			// 로그인한 회원번호와 작성자 회원번호가 불일치
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		// 로그인한 회원번호와 작성자 회원번호가 일치
		// DB 먼저 삭제
		dao.deleteRecipe(board_num);
		// 파일 삭제
		FileUtil.removeFile(request, recipe.getFilename());
				
		// JSP 경로 반환
		return "/WEB-INF/views/recipe/recipeDelete.jsp";
		}
	}
