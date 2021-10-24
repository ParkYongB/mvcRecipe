package com.recipe.action;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import com.controller.Action;
import com.recipe.dao.RecipeDAO;
import com.recipe.vo.RecipeCommendsVO;
import com.util.PagingUtil;

/**
 * @Package Name   : com.recipe.action
 * @FileName  : RecipeReplyListAction.java
 * @�ۼ���       : 2021. 9. 9. 
 * @�ۼ���       : ������
 * @���α׷� ���� : ������ ��� ajax������� �����ϴ� �׼�
 */
public class RecipeReplyListAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("utf-8");
		
		String pageNum = request.getParameter("pageNum");
	      if(pageNum == null) pageNum = "1";
	      
	      int board_num = Integer.parseInt(request.getParameter("board_num"));
	      
	      int rowCount = 5;
	      
	      RecipeDAO dao = RecipeDAO.getInstance();
	      int count = dao.getRecipeReplyBoardCount(board_num);
	      
	      /*
	       * ajax ������� ����� ǥ���ϱ� ������ PagingUtil�� �������� ǥ�ð� ������ �ƴ϶�
	       * ��� ������ ������ ó���� ���� rownum ��ȣ�� ���ϴ� ���� ������
	       */
	      
	      // currentPage,count,rowCount,pageCount,url
	      PagingUtil page = new PagingUtil(Integer.parseInt(pageNum), count, rowCount, 1, null);
	      
	      List<RecipeCommendsVO> list = null;
	      if(count > 0) {
	         list = dao.getRecipeListReplyBoard(page.getStartCount(), page.getEndCount(), board_num);
	      }else {
	         list = Collections.emptyList();	// ���� �������� ������ ����
	      }
	      
	      Map<String,Object> mapAjax = new HashMap<String, Object>();
	      mapAjax.put("count", count);
	      mapAjax.put("rowCount", rowCount);
	      mapAjax.put("list", list);
	      
	      // JSON �����ͷ� ��ȯ
	      ObjectMapper mapper = new ObjectMapper();
	      String ajaxData = mapper.writeValueAsString(mapAjax);
	      
	      request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
