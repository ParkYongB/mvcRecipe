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
 * @�ۼ���       : 2021. 9. 12. 
 * @�ۼ���       :
 * @���α׷� ���� : ����ȭ�� �׼� Ŭ����
 */

public class MainAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// �α��� üũ
		HttpSession session = request.getSession();
		Integer mem_num = (Integer) session.getAttribute("mem_num");
			
		// ���۵� ������ ���ڵ�
		request.setCharacterEncoding("utf-8");
		
		request.setAttribute("mem_num", mem_num);
		
		// DAO ȣ��
		RecipeDAO dao = RecipeDAO.getInstance();
		NewsDAO newsdao=NewsDAO.getInstance();
		// ---------- �������� ----------
		// �Խñ� �� üũ
		int news_count = newsdao.getNewsCount();
		// ����Ʈ ���
		List<NewsVO> newsList = null;
		if(news_count > 0) {
			newsList = newsdao.getNewsList(1, 7);
		}
		
		// ---------- ����� ������ ----------
		// �Խñ� �� üũ
		int recipe_count = dao.getRecipeCount();
		// ����Ʈ ���
		List<RecipeVO> recipeList = null;
		if(recipe_count > 0) {
			recipeList = dao.getTotalRecipeList(1, 2);
		}
		
		// ---------- ����Ʈ ������ ----------
		// �Խñ� �� üũ
		int bestRecipe_count = dao.getRecipeCount();
		// ����Ʈ ���
		List<RecipeVO> bestRecipeList = null;
		if(bestRecipe_count > 0) {
			bestRecipeList = dao.getRecommTotalRecipeList(1, 4);
		}
		
		// �Խñ� ���� list �Ѱ��ֱ�
		request.setAttribute("news_count", news_count);
		request.setAttribute("recipe_count", recipe_count);
		request.setAttribute("bestRecipe_count", bestRecipe_count);
		request.setAttribute("newsList", newsList);
		request.setAttribute("recipeList", recipeList);
		request.setAttribute("bestRecipeList", bestRecipeList);
		
		// ���� JSP ��� ��ȯ
		return "/WEB-INF/views/main/main.jsp";
	}
	
}
