package com.recipe.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import com.controller.Action;
import com.recipe.dao.RecipeDAO;

/**
 * @Package Name   : com.recipe.action
 * @FileName  : RecipeBookmarkAction.java
 * @작성일       : 2021. 9. 8. 
 * @작성자       : 오상준
 * @프로그램 설명 : 레시피게시판 북마크 액션
 */
public class RecipeBookmarkAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		// 전송된 데이터 저장
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		int mem_num = Integer.parseInt(request.getParameter("mem_num"));
		
		
		RecipeDAO dao = RecipeDAO.getInstance();
		// 회원이 레시피를 북마크에 이미 저장했는지 확인
		int check = 0;
		check = dao.bookmarkCheck(board_num, mem_num);
		
		Map<String, String> ajaxMap = new HashMap<String, String>();
		
		
		if(check == 0) {	// 레시피를 북마크 하지 않았을 경우
			// 북마크에 데이터 저장
			dao.addBookmark(board_num, mem_num);
			ajaxMap.put("result", "addition");
		}else {			// 레시피 북마크 한 경우
			dao.deleteBookmark(board_num, mem_num);
			ajaxMap.put("result", "cancel");
		}
		
		// JSON 형식의 문자열 데이터로 변환
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(ajaxMap);
		
		request.setAttribute("ajaxData", ajaxData);
		
		// JSP 경로 반환   ajax사용시 공통적으로 처리하기 위한 페이지
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
