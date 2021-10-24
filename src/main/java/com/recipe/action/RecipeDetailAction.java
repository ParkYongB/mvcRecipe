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
 * @�ۼ���       : 2021. 9. 7. 
 * @�ۼ���       : �����
 * @���α׷� ���� : ������ ������ �׼� �Ѱ��� ���ڵ� ó��
 */
public class RecipeDetailAction implements Action {

   @Override
   public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
      
      // ������ ���� ���� �α����� ��� Ȯ��
      HttpSession session = request.getSession();
      Integer mem_num = (Integer)session.getAttribute("mem_num");
      Integer auth = (Integer)session.getAttribute("auth");
      int board_num = Integer.parseInt(request.getParameter("board_num"));
      
      int recommBtnCheck = 0;
      int bookmarkBtnCheck = 0;
      // ��� �� �ޱ�
      int comm = 0;
      
        if(mem_num == null) {//�α��� ���� ���� ���
         RecipeDAO dao = RecipeDAO.getInstance();
         // ��ȸ�� ����
         dao.updateHitsCount(board_num);
         // �Ѱ��� ���ڵ� �ޱ�
         RecipeVO recipe = dao.getRecipeBoard(board_num);
         
         // HTML�� ������� ����
         recipe.setTitle(StringUtil.useNoHtml(recipe.getTitle()));
         // HTML�� ������� �����鼭 �ٹٲ� ó��
         recipe.setContent(StringUtil.useBrHtml(recipe.getContent()));
         
         comm = dao.getRecipeReplyBoardCount(board_num);
         
         // ���ϱ�� �ϸ�ũ �ߺ� üũ, ��ۼ� �� ��ȯ
         request.setAttribute("bookmarkBtnCheck", bookmarkBtnCheck);
         request.setAttribute("recommBtnCheck", recommBtnCheck);
         request.setAttribute("comm", comm);
                  
         // �Ѱ��� ���ڵ� ��ȯ
         request.setAttribute("recipe", recipe);
         
         // ������ �������� �̵�
          return "/WEB-INF/views/recipe/recipeDetail.jsp";
          
        }else {//�α��� �� ���
         
         RecipeDAO dao = RecipeDAO.getInstance();
         
         // ��ȸ�� ����
         dao.updateHitsCount(board_num);
         // �Ѱ��� ���ڵ� �ޱ�
         RecipeVO recipe = dao.getRecipeBoard(board_num);
         
         // HTML�� ������� ����
         recipe.setTitle(StringUtil.useNoHtml(recipe.getTitle()));
         // HTML�� ����ϸ鼭 �ٹٲ� ó��
         recipe.setContent(StringUtil.useBrHtml(recipe.getContent()));
         
         // ���ϱ� ��ư�� �ϸ�ũ ��ư ��ȿ�� üũ�� ���� ���� ����
         recommBtnCheck = 0;
         bookmarkBtnCheck = 0;
         
         // �ϸ�ũ ��ȿ�� üũ 1�� ��� �ߺ� 0�ϰ�� ���ߺ�
         bookmarkBtnCheck = dao.bookmarkCheck(board_num, mem_num);
         
         // ���ϱ� ��ư ��ȿ�� üũ 1�� ��� �ߺ� 0�� ��� ���ߺ�
         recommBtnCheck = dao.recomDuplicationCheck(board_num, mem_num);
         
         // ��ۼ� ��ȯ �޼ҵ� ����
         comm = dao.getRecipeReplyBoardCount(board_num);
         
         // �Ѱ��� ���ڵ� ��ȯ
         request.setAttribute("recipe", recipe);
         
         // ���ϱ�� �ϸ�ũ �ߺ� üũ, ��ۼ� �� ��ȯ
         request.setAttribute("bookmarkBtnCheck", bookmarkBtnCheck);
         request.setAttribute("recommBtnCheck", recommBtnCheck);
         request.setAttribute("comm", comm);
         request.setAttribute("auth", auth);
         
         // ������ �������� �̵�
         return "/WEB-INF/views/recipe/recipeDetail.jsp";
      }
   }

}