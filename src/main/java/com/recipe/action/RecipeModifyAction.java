package com.recipe.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.controller.Action;
import com.oreilly.servlet.MultipartRequest;
import com.recipe.dao.RecipeDAO;
import com.recipe.vo.RecipeVO;
import com.util.FileUtil;

/**
 * @Package Name   : com.recipe.action
 * @FileName  : RecipeModifyAction.java
 * @�ۼ���       : 2021. 9. 8.
 * @�ۼ���       : ������
 * @���α׷� ���� : ������ �ۼ��� �׼� Ŭ����
 */

public class RecipeModifyAction implements Action{

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
		// ���۵� ������ ���ڵ� ó��
		request.setCharacterEncoding("utf-8");
		
		MultipartRequest multi = FileUtil.createFile(request);
		int board_num = Integer.parseInt(multi.getParameter("board_num"));
		String filename = multi.getFilesystemName("filename");
		
		// RecipeDAO ȣ��
		RecipeDAO dao = RecipeDAO.getInstance();
		
		// ���� �� ������ �о����
		RecipeVO dbRecipe = dao.getRecipeBoard(board_num);
		if(mem_num != dbRecipe.getMem_num() && auth != 3) { // �α����� ȸ����ȣ�� �ۼ��� ȸ����ȣ ����ġ�ϰ� ����� 3�� �ƴ϶��
			FileUtil.removeFile(request, filename); // ���ε��� ������ ������ ���� ����
			
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		// �α����� ȸ����ȣ�� �ۼ� ȸ����ȣ ��ġ
		// �ڹٺ�(VO) ����
		RecipeVO recipe = new RecipeVO();
		recipe.setBoard_num(board_num);
		recipe.setCategory(multi.getParameter("category"));
		recipe.setTitle(multi.getParameter("title"));
		recipe.setContent(multi.getParameter("content"));
		recipe.setFilename(filename);
		recipe.setIp(request.getRemoteAddr());
		
		// �� ����
		if(dbRecipe.getFilename() != null && recipe.getFilename() != null) FileUtil.removeFile(request, dbRecipe.getFilename());
		dao.updateRecipe(recipe);
		
		// JSP ��� ��ȯ
		return "/WEB-INF/views/recipe/recipeModify.jsp";
	}

}
