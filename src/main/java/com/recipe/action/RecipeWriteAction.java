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
 * @FileName  : RecipeWriteAction.java
 * @작성일       : 2021. 9. 7. 
 * @작성자       : 이현지
 * @프로그램 설명 : 레시피 글작성 액션 클래스
 */
public class RecipeWriteAction implements Action{

   @Override
   public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
      
      // 로그인 체크
      HttpSession session = request.getSession();
      Integer mem_num = (Integer)session.getAttribute("mem_num");
                  
      if(mem_num == null) { // 로그인 하지 않은 경우
         return "redirect:/member/loginForm.do";
      }
      
      // 로그인 한 경우
      MultipartRequest multi = FileUtil.createFile(request);
      
      // 자바빈(VO) 생성
      RecipeVO recipe = new RecipeVO();
      recipe.setCategory(multi.getParameter("category"));
      recipe.setTitle(multi.getParameter("title"));
      recipe.setContent(multi.getParameter("content"));
      recipe.setFilename(multi.getFilesystemName("filename"));
      recipe.setIp(request.getRemoteAddr()); // 우리가 보낸 데이터 아님
      recipe.setMem_num(mem_num);
      
      // RecipeDAO 호출
      RecipeDAO dao = RecipeDAO.getInstance();
      // 글 작성
      dao.insertRecipe(recipe);
      
      // JSP 경로 반환
      return "/WEB-INF/views/recipe/recipeWrite.jsp";
   }

}