package com.bestRecipe.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.controller.Action;
import com.recipe.dao.RecipeDAO;
import com.recipe.vo.RecipeVO;
import com.util.PagingUtil;

/**
 * @Package Name   : com.bestRecipe.action
 * @FileName  : RecipeListAction.java
 * @�ۼ���       : 2021. 9. 10. 
 * @�ۼ���       : ������
 * @���α׷� ���� : ����Ʈ ������ �׼� Ŭ����
 */
public class BestRecipeListAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// �α��� ���� ��������
		HttpSession session = request.getSession();
		Integer mem_num = (Integer)session.getAttribute("mem_num");
				
		// �˻� ���� üũ�� ���� ���� �Ҵ�
		String division = request.getParameter("division");
				
		// ���۵� ������ Ÿ��
		request.setCharacterEncoding("utf-8");
				
		request.setAttribute("mem_num", mem_num);
		request.setAttribute("division", division);
		
		// ó�� ����� �⺻�� ��õ������ ����
		if(division == null) {
			division = "��õ��";
		}
		
		// ���ǿ� �´� ����Ʈ ���� �޼ҵ� ����
		searchCheck(request, response, division);
					
		return "/WEB-INF/views/bestRecipe/bestRecipeList.jsp";
		}	
	
	/**
	 * @Method �޼ҵ��  : searchCheck
	 * @�ۼ���     : 2021. 9. 11. 
	 * @�ۼ���     : ������
	 * @Method ���� : ����Ʈ �Խ��� ��ȸ��, ��õ�� ���
	 */
	public void searchCheck(HttpServletRequest request, HttpServletResponse response, String division) throws Exception {
		
		String pageNum = request.getParameter("pageNum");
		if(pageNum==null) pageNum = "1";
				
		RecipeDAO dao = RecipeDAO.getInstance();
		int count = dao.getRecipeCount();	// �� ���ڵ� ��
				
		// ������ ó��
		// currentPage, count, rowCount, pageCount, url
		PagingUtil page = new PagingUtil(null, division, Integer.parseInt(pageNum), count, 4, 5,"bestRecipeList.do");
				
		List<RecipeVO> list = null;
				
		// �� ���ڵ尡 0�� �ƴ� �� list�� ���� ��´�
		if(count > 0) {
			if(division.equals("��ȸ��")) {
				list = dao.getHitsTotalRecipeList(page.getStartCount(), page.getEndCount());
			}
			if(division.equals("��õ��")) {
				// ����Ʈ�� ��õ�� ���
				list = dao.getRecommTotalRecipeList(page.getStartCount(), page.getEndCount());
			}
			if(division.equals("��ۼ�")) {
				// ����Ʈ�� ��ۼ� ���
				list = dao.getCommentsTotalRecipeList(page.getStartCount(), page.getEndCount());
			}
		}
				
		// list�� �� ������, ������ �ϴܺκ� �Ѱ��ֱ�
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("pagingHtml", page.getPagingHtml());
				
	}

}
