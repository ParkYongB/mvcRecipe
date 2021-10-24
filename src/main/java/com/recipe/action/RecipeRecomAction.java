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
 * @FileName  : RecipeRecomAction.java
 * @작성일       : 2021. 9. 8. 
 * @작성자       : 오상준
 * @프로그램 설명 : 레시피 추천 업데이트(중복체크 후 증가 및 감소) 액션 ajax 반환
 */
public class RecipeRecomAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		// 전송된 데이터 저장
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		int mem_num = Integer.parseInt(request.getParameter("mem_num"));
		
		
		// 추천 중복 체크를 위해 check 함수 실행
		RecipeDAO dao = RecipeDAO.getInstance();
		int duplication = dao.recomDuplicationCheck(board_num, mem_num);
		
		if(duplication == 0) { // 추천하지 않았다면 추천 추가
			dao.recomAdd(board_num, mem_num);
		}else { // 추천을 했다면 추천 삭제
			dao.recomDelete(board_num, mem_num);
		}
		
		// 현재 추천수 구하는 함수 실행
		int recom_count = dao.recomCount(board_num);
		
		// JSON 형식으로 비동기 전송을 위해 문자열 Map 생성
		Map<String, String> mapAjax = new HashMap<String, String>();
		mapAjax.put("count", String.valueOf(recom_count));
		mapAjax.put("duplication", String.valueOf(duplication));
		
		// JSON 형식의 문자열 데이터로 변환
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		// JSP 경로 반환   ajax사용시 공통적으로 처리하기 위한 페이지
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
