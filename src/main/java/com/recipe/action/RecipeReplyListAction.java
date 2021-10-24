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
 * @작성일       : 2021. 9. 9. 
 * @작성자       : 오상준
 * @프로그램 설명 : 레시피 목록 ajax방식으로 리턴하는 액션
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
	       * ajax 방식으로 목록을 표시하기 때문에 PagingUtil은 페이지수 표시가 목적이 아니라
	       * 목록 데이터 페이지 처리를 위해 rownum 번호를 구하는 것이 목적임
	       */
	      
	      // currentPage,count,rowCount,pageCount,url
	      PagingUtil page = new PagingUtil(Integer.parseInt(pageNum), count, rowCount, 1, null);
	      
	      List<RecipeCommendsVO> list = null;
	      if(count > 0) {
	         list = dao.getRecipeListReplyBoard(page.getStartCount(), page.getEndCount(), board_num);
	      }else {
	         list = Collections.emptyList();	// 글이 존재하지 않으면 비우기
	      }
	      
	      Map<String,Object> mapAjax = new HashMap<String, Object>();
	      mapAjax.put("count", count);
	      mapAjax.put("rowCount", rowCount);
	      mapAjax.put("list", list);
	      
	      // JSON 데이터로 변환
	      ObjectMapper mapper = new ObjectMapper();
	      String ajaxData = mapper.writeValueAsString(mapAjax);
	      
	      request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
