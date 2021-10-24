package com.qnaboard.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import com.controller.Action;
import com.recipe.dao.RecipeDAO;
import com.recipe.vo.RecipeCommendsVO;

/**
 * @Package Name   : com.qnaboard.action
 * @FileName  : QnaReplyModifyAction.java
 * @작성일       : 2021. 9. 12. 
 * @작성자       : 나윤경
 * @프로그램 설명 : 고객센터 게시판 댓글 수정 액션
 */
public class QnaReplyModifyAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
			// 전송된 데이터 인코딩 처리
			request.setCharacterEncoding("utf-8");
				
			// 작성자 회원 번호
			int writer_auth = Integer.parseInt(request.getParameter("writer_auth"));
			
			Map<String, String> mapAjax = new HashMap<String, String>();
			
			// 로그인 체크
			if(writer_auth == 3) {	// 로그인이 되어 있고 로그인한 회원 번호와 작성자 회원번호 일치
				// 자바빈 생성
				RecipeCommendsVO comm = new RecipeCommendsVO();
				comm.setComm_num(Integer.parseInt(request.getParameter("comm_num")));
				comm.setComm_con(request.getParameter("comm_con"));
				
				//모두의 레시피DAO의 updateRecipeCommend(댓글 수정) 코드 재사용
				RecipeDAO dao = RecipeDAO.getInstance();
				dao.updateRecipeCommend(comm);
					
				mapAjax.put("result", "success");
			}else {
				mapAjax.put("result", "Wrong");
			}
				
			// JSON 데이터로 변환
			  ObjectMapper mapper = new ObjectMapper();
			  String ajaxData = mapper.writeValueAsString(mapAjax);
			     
			  request.setAttribute("ajaxData", ajaxData);

				
			  return "/WEB-INF/views/common/ajax_view.jsp";
		}

	}
