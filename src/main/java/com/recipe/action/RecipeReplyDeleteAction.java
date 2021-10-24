package com.recipe.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import com.controller.Action;
import com.recipe.dao.RecipeDAO;

/**
 * @Package Name   : com.recipe.action
 * @FileName  : RecipeReplyDeleteAction.java
 * @작성일       : 2021. 9. 10. 
 * @작성자       : 오상준
 * @프로그램 설명 : 레시피 게시판 댓글 삭제 액션
 */
public class RecipeReplyDeleteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		
		// 전송된 데이터 반환
		int comm_num = Integer.parseInt(request.getParameter("comm_num"));	// 댓글 번호
		int writer_num = Integer.parseInt(request.getParameter("user_num"));	// 댓글 작성자 번호
		
		Map<String,String> mapAjax = new HashMap<String, String>();
		HttpSession session = request.getSession();
		Integer mem_num = (Integer)session.getAttribute("mem_num");		// 로그인 회원 번호
		Integer auth = (Integer)session.getAttribute("auth");
		
		if(mem_num == null) {	// 로그인 x
			mapAjax.put("result", "logout");
		}else if((mem_num!=null && mem_num == writer_num) || auth == 3) {	// 로그인 O 작성자와 일치 or 관리자인경우
			RecipeDAO dao = RecipeDAO.getInstance();
			dao.deleteRecipeCommend(comm_num);
			mapAjax.put("result", "success");
		}else { //로그인이 되어있고 로그인한 회원번호와 작성자 회원번호가 불일치
			mapAjax.put("result", "wrongAccess");
		}
		
		// JSON 데이터로 변환
	    ObjectMapper mapper = new ObjectMapper();
	    String ajaxData = mapper.writeValueAsString(mapAjax);
	      
	    request.setAttribute("ajaxData", ajaxData);

		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
