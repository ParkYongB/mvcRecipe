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
 * @작성일       : 2021. 9. 8.
 * @작성자       : 이현지
 * @프로그램 설명 : 레시피 글수정 액션 클래스
 */

public class RecipeModifyAction implements Action{

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
		// 전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		
		MultipartRequest multi = FileUtil.createFile(request);
		int board_num = Integer.parseInt(multi.getParameter("board_num"));
		String filename = multi.getFilesystemName("filename");
		
		// RecipeDAO 호출
		RecipeDAO dao = RecipeDAO.getInstance();
		
		// 수정 전 데이터 읽어오기
		RecipeVO dbRecipe = dao.getRecipeBoard(board_num);
		if(mem_num != dbRecipe.getMem_num() && auth != 3) { // 로그인한 회원번호와 작성자 회원번호 불일치하고 등급이 3이 아니라면
			FileUtil.removeFile(request, filename); // 업로드한 파일이 있으면 파일 삭제
			
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		// 로그인한 회원번호와 작성 회원번호 일치
		// 자바빈(VO) 생성
		RecipeVO recipe = new RecipeVO();
		recipe.setBoard_num(board_num);
		recipe.setCategory(multi.getParameter("category"));
		recipe.setTitle(multi.getParameter("title"));
		recipe.setContent(multi.getParameter("content"));
		recipe.setFilename(filename);
		recipe.setIp(request.getRemoteAddr());
		
		// 글 수정
		if(dbRecipe.getFilename() != null && recipe.getFilename() != null) FileUtil.removeFile(request, dbRecipe.getFilename());
		dao.updateRecipe(recipe);
		
		// JSP 경로 반환
		return "/WEB-INF/views/recipe/recipeModify.jsp";
	}

}
