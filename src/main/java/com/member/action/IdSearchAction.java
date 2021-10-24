package com.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.controller.Action;
import com.member.dao.MemberDAO;
import com.member.vo.MemberVO;

/**
 * @Package Name   : com.member.action
 * @FileName  : IdSearchAction.java
 * @�ۼ���       : 2021. 9. 8. 
 * @�ۼ���       : �ڿ뺹
 * @���α׷� ���� : ���̵� ã�� Action
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
