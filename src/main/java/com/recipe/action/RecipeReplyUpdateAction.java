package com.recipe.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import com.controller.Action;
import com.recipe.dao.RecipeDAO;
import com.recipe.vo.RecipeCommendsVO;

/**
 * @Package Name   : com.recipe.action
 * @FileName  : RecipeReplyUpdateAction.java
 * @작성일       : 2021. 9. 10. 
 * @작성자       : 오상준
 * @프로그램 설명 : 모두의 레시피 댓글 업데이트 액션
 */
public class RecipeReplyUpdateAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		
		// 작성자 회원 번호
		int writer_num = Integer.parseInt(request.getParameter("writer_num"));
	
		
		Map<String, String> mapAjax = new HashMap<String, String>();
		
		HttpSession session = request.getSession();
		Integer mem_num = (Integer)session.getAttribute("mem_num");
		Integer auth = (Integer)session.getAttribute("auth");
		
		// 로그인 체크
		if(mem_num == null) { // 로그인이 되지 않은 경우
			mapAjax.put("result", "logout");
		}else if((mem_num!=null && mem_num == writer_num) || auth == 3) {	// 로그인이 되어 있고 로그인한 회원 번호와 작성자 회원번호 일치 or 관리자인 경우
			// 자바빈 생성
			RecipeCommendsVO comm = new RecipeCommendsVO();
			comm.setComm_num(Integer.parseInt(request.getParameter("comm_num")));
			comm.setComm_con(request.getParameter("comm_con"));
			
			RecipeDAO dao = RecipeDAO.getInstance();
			dao.updateRecipeCommend(comm);
			
			mapAjax.put("result", "success");
		}else {			// 로그인이 되어 있고 로그인한 회원 번호와 작성자 회원 번호 불일치
			mapAjax.put("result", "wrongAccess");
		}
		
		// JSON 데이터로 변환
	   ObjectMapper mapper = new ObjectMapper();
	   String ajaxData = mapper.writeValueAsString(mapAjax);
	      
	   request.setAttribute("ajaxData", ajaxData);

		
	   return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
