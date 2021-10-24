package com.qnaboard.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import com.controller.Action;
import com.qnaboard.dao.QnaBoardDAO;
import com.recipe.vo.RecipeCommendsVO;

/**
 * @Package Name   : com.qnaboard.action
 * @FileName  : QnaCommentsWriteAction.java
 * @작성일       : 2021. 9. 11. 
 * @작성자       : 나윤경
 * @프로그램 설명 : 고객센터 댓글 작성 액션
 */
public class QnaReplyWriteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> mapAjax = new HashMap<String, String>();
		
		HttpSession session = request.getSession();
		Integer auth = (Integer)session.getAttribute("auth");
		
		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		
		if(auth != 3) {
			mapAjax.put("result", "Wrong");
		}else {
			//자바빈 생성
			RecipeCommendsVO comments = new RecipeCommendsVO();
			comments.setComm_con(request.getParameter("re_content"));
			comments.setQna_num(Integer.parseInt(request.getParameter("qna_num")));
			//comments.setMem_num(mem_num);	//회원번호(댓글 작성자)
			
			QnaBoardDAO dao = QnaBoardDAO.getInstance();
			dao.insertQnACommend(comments);
			
			mapAjax.put("result", "success");
		}
		
		
		
		//JSON 데이터 생성
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
