package com.recipe.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.controller.Action;
import com.recipe.dao.RecipeDAO;
import com.recipe.vo.RecipeVO;
import com.util.StringUtil;

/**
 * @Package Name   : com.recipe.action
 * @FileName  : RecipeDetailAction.java
 * @작성일       : 2021. 9. 7. 
 * @작성자       : 대장님
 * @프로그램 설명 : 레시피 디테일 액션 한건의 레코드 처리
 */
public class RecipeDetailAction implements Action {

   @Override
   public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
      
      // 세션을 통해 현재 로그인한 멤버 확인
      HttpSession session = request.getSession();
      Integer mem_num = (Integer)session.getAttribute("mem_num");
      Integer auth = (Integer)session.getAttribute("auth");
      int board_num = Integer.parseInt(request.getParameter("board_num"));
      
      int recommBtnCheck = 0;
      int bookmarkBtnCheck = 0;
      // 댓글 수 받기
      int comm = 0;
      
        if(mem_num == null) {//로그인 되지 않은 경우
         RecipeDAO dao = RecipeDAO.getInstance();
         // 조회수 증가
         dao.updateHitsCount(board_num);
         // 한건의 레코드 받기
         RecipeVO recipe = dao.getRecipeBoard(board_num);
         
         // HTML을 허용하지 않음
         recipe.setTitle(StringUtil.useNoHtml(recipe.getTitle()));
         // HTML을 허용하지 않으면서 줄바꿈 처리
         recipe.setContent(StringUtil.useBrHtml(recipe.getContent()));
         
         comm = dao.getRecipeReplyBoardCount(board_num);
         
         // 찜하기와 북마크 중복 체크, 댓글수 값 반환
         request.setAttribute("bookmarkBtnCheck", bookmarkBtnCheck);
         request.setAttribute("recommBtnCheck", recommBtnCheck);
         request.setAttribute("comm", comm);
                  
         // 한건의 레코드 반환
         request.setAttribute("recipe", recipe);
         
         // 디테일 페이지로 이동
          return "/WEB-INF/views/recipe/recipeDetail.jsp";
          
        }else {//로그인 된 경우
         
         RecipeDAO dao = RecipeDAO.getInstance();
         
         // 조회수 증가
         dao.updateHitsCount(board_num);
         // 한건의 레코드 받기
         RecipeVO recipe = dao.getRecipeBoard(board_num);
         
         // HTML을 허용하지 않음
         recipe.setTitle(StringUtil.useNoHtml(recipe.getTitle()));
         // HTML을 허용하면서 줄바꿈 처리
         recipe.setContent(StringUtil.useBrHtml(recipe.getContent()));
         
         // 찜하기 버튼과 북마크 버튼 유효성 체크를 위해 변수 지정
         recommBtnCheck = 0;
         bookmarkBtnCheck = 0;
         
         // 북마크 유효성 체크 1일 경우 중복 0일경우 미중복
         bookmarkBtnCheck = dao.bookmarkCheck(board_num, mem_num);
         
         // 찜하기 버튼 유효성 체크 1일 경우 중복 0일 경우 미중복
         recommBtnCheck = dao.recomDuplicationCheck(board_num, mem_num);
         
         // 댓글수 반환 메소드 실행
         comm = dao.getRecipeReplyBoardCount(board_num);
         
         // 한건의 레코드 반환
         request.setAttribute("recipe", recipe);
         
         // 찜하기와 북마크 중복 체크, 댓글수 값 반환
         request.setAttribute("bookmarkBtnCheck", bookmarkBtnCheck);
         request.setAttribute("recommBtnCheck", recommBtnCheck);
         request.setAttribute("comm", comm);
         request.setAttribute("auth", auth);
         
         // 디테일 페이지로 이동
         return "/WEB-INF/views/recipe/recipeDetail.jsp";
      }
   }

}