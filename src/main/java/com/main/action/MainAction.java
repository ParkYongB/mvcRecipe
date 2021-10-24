package com.main.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.controller.Action;
import com.news.dao.NewsDAO;
import com.news.vo.NewsVO;
import com.recipe.dao.RecipeDAO;
import com.recipe.vo.RecipeVO;

/**
 * @Package Name   : com.main.action
 * @FileName  : MainAction.java
 * @작성일       : 2021. 9. 12. 
 * @작성자       :
 * @프로그램 설명 : 메인화면 액션 클래스
 */

public class MainAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 로그인 체크
		HttpSession session = request.getSession();
		Integer mem_num = (Integer) session.getAttribute("mem_num");
			
		// 전송된 데이터 인코딩
		request.setCharacterEncoding("utf-8");
		
		request.setAttribute("mem_num", mem_num);
		
		// DAO 호출
		RecipeDAO dao = RecipeDAO.getInstance();
		NewsDAO newsdao=NewsDAO.getInstance();
		// ---------- 공지사항 ----------
		// 게시글 수 체크
		int news_count = newsdao.getNewsCount();
		// 리스트 출력
		List<NewsVO> newsList = null;
		if(news_count > 0) {
			newsList = newsdao.getNewsList(1, 7);
		}
		
		// ---------- 모두의 레시피 ----------
		// 게시글 수 체크
		int recipe_count = dao.getRecipeCount();
		// 리스트 출력
		List<RecipeVO> recipeList = null;
		if(recipe_count > 0) {
			recipeList = dao.getTotalRecipeList(1, 2);
		}
		
		// ---------- 베스트 레시피 ----------
		// 게시글 수 체크
		int bestRecipe_count = dao.getRecipeCount();
		// 리스트 출력
		List<RecipeVO> bestRecipeList = null;
		if(bestRecipe_count > 0) {
			bestRecipeList = dao.getRecommTotalRecipeList(1, 4);
		}
		
		// 게시글 수와 list 넘겨주기
		request.setAttribute("news_count", news_count);
		request.setAttribute("recipe_count", recipe_count);
		request.setAttribute("bestRecipe_count", bestRecipe_count);
		request.setAttribute("newsList", newsList);
		request.setAttribute("recipeList", recipeList);
		request.setAttribute("bestRecipeList", bestRecipeList);
		
		// 메인 JSP 경로 반환
		return "/WEB-INF/views/main/main.jsp";
	}
	
}
