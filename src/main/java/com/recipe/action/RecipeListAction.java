package com.recipe.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.controller.Action;
import com.recipe.dao.RecipeDAO;
import com.recipe.vo.RecipeNewsVO;
import com.recipe.vo.RecipeVO;
import com.util.PagingUtil;

/**
 * @Package Name   : com.recipe.action
 * @FileName  : RecipeListAction.java
 * @�ۼ���       : 2021. 9. 7. 
 * @�ۼ���       : ������
 * @���α׷� ���� : ����� ������ �׼� Ŭ����
 */

public class RecipeListAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// �α��� ���� ��������
		HttpSession session = request.getSession();
		Integer mem_num = (Integer)session.getAttribute("mem_num");
		Integer auth = (Integer)session.getAttribute("auth");
		
		// �˻� ���� üũ�� ���� ���� �Ҵ�
		String search = request.getParameter("search");
		String division = request.getParameter("division");
		
		// ���۵� ������ Ÿ��
		request.setCharacterEncoding("utf-8");
		
		request.setAttribute("search", search);
		request.setAttribute("division", division);
		request.setAttribute("mem_num", mem_num);
		request.setAttribute("auth", auth);
		
		// ------------------------------- ��������
		// �������� �Խñ� �� üũ
		RecipeDAO dao = RecipeDAO.getInstance();
		int news_count = dao.getRecipeNewsCount();
		
		
		
		List<RecipeNewsVO> news_list = null;
		if(news_count > 0) {
			news_list = dao.getRecipeNewsList(1, 5);
		}
		
		request.setAttribute("news_count", news_count);
		request.setAttribute("news_list", news_list);
		
		
		// ------------------------ ������ �Խ���
		// ���� �غ�
		int count = 0;
		PagingUtil page = null;
		
		// -------- ������ �Խ��� ���
		
		String pageNum = request.getParameter("pageNum");
		if(pageNum==null) pageNum = "1";
		
		List<RecipeVO> list = null;
			
		// �� ���ڵ尡 0�� �ƴ� �� list�� ���� ��´�
		
			if(division != null) {	// �˻��� �ִ� ���
				count = dao.getRecipeCount(division, search);	// �˻����ǿ� ������ �� ���ڵ� ��
				
				if(count > 0) {
					// ������ ó��
					page = new PagingUtil(search,division,Integer.parseInt(pageNum), count, 4, 5,"recipeList.do");
						
					list = dao.getSearchlRecipeList(page.getStartCount(), page.getEndCount(), division, search);
					
					request.setAttribute("pagingHtml", page.getPagingHtml());
				}
			}else {	// �˻��� ���� ���
				count = dao.getRecipeCount();	// �˻����ǿ� ������ �� ���ڵ� ��
				
				if(count > 0) {
					// ������ ó��
					// currentPage, count, rowCount, pageCount, url
					page = new PagingUtil(Integer.parseInt(pageNum), count, 4, 5,"recipeList.do");
						
					list = dao.getTotalRecipeList(page.getStartCount(), page.getEndCount());
					
					request.setAttribute("pagingHtml", page.getPagingHtml());
				}
			}
				
		
		
			// list�� �� ������, ������ �ϴܺκ� �Ѱ��ֱ�
			request.setAttribute("count", count);
			request.setAttribute("list", list);
			request.setAttribute("division", division);
			request.setAttribute("search", search);
			
			// ����� �����Ƿ� �̵�	
			return "/WEB-INF/views/recipe/recipeList.jsp";
	}

}
