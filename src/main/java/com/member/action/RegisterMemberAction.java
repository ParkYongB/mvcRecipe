package com.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.controller.Action;
import com.member.dao.MemberDAO;
import com.member.vo.MemberVO;

/**
 * @Package Name   : com.member.action
 * @FileName  : RegisterMemberAction.java
 * @�ۼ���       : 2021. 9. 7. 
 * @�ۼ���       : �ڿ뺹
 * @���α׷� ���� : ȸ������ Action
 */

public class RegisterMemberAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("UTF-8");
		
		MemberVO member = new MemberVO();
		member.setId(request.getParameter("id"));
		member.setName(request.getParameter("name"));
		member.setPasswd(request.getParameter("passwd"));
		member.setEmail(request.getParameter("email"));
		member.setPhone(request.getParameter("phone"));
		member.setBirthday(request.getParameter("birthday"));
		member.setPasskey(request.getParameter("passkey"));
		
		String checkId = request.getParameter("id");
		String checkName = request.getParameter("name");
		
		if(checkId == null && checkName == null) {
			return "/WEB-INF/views/main/main.jsp";
		}
		
		MemberDAO dao = MemberDAO.getInstance();
		dao.insertMember(member);
		
		return "/WEB-INF/views/member/registerMember.jsp";
	}
	
}
