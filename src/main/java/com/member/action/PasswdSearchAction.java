package com.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.controller.Action;
import com.member.dao.MemberDAO;
import com.member.vo.MemberVO;

/**
 * @Package Name   : com.member.action
 * @FileName  : PasswdSearchAction.java
 * @�ۼ���       : 2021. 9. 9. 
 * @�ۼ���       : �ڿ뺹
 * @���α׷� ���� : ��й�ȣ ã�� Action
 */

public class PasswdSearchAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("utf-8");
		
		String searchId = request.getParameter("id");
		String searchName = request.getParameter("name");
		String searchPasskey = request.getParameter("passkey");
		
		MemberDAO dao = MemberDAO.getInstance();
		MemberVO member = dao.passwdSearch(searchId, searchName, searchPasskey);
		
		request.setAttribute("member", member);
		
		return "/WEB-INF/views/member/passwdSearch.jsp";
	}

}
