package com.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.controller.Action;
import com.member.dao.MemberDAO;
import com.member.vo.MemberVO;

/**
 * @Package Name   : com.member.action
 * @FileName  : IdSearchAction.java
 * @작성일       : 2021. 9. 8. 
 * @작성자       : 박용복
 * @프로그램 설명 : 아이디 찾기 Action
 */

public class IdSearchAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("utf-8");
		
		String searchName = request.getParameter("name");
		String searchPhone = request.getParameter("phone");
		
		MemberDAO dao = MemberDAO.getInstance();
		MemberVO member = dao.idSearch(searchName, searchPhone);
		
		request.setAttribute("member", member);
		
		return "/WEB-INF/views/member/idSearch.jsp";
	}

}
