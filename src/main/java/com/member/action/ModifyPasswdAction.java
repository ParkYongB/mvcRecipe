package com.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.controller.Action;
import com.member.dao.MemberDAO;

/**
 * @Package Name   : com.member.action
 * @FileName  : ModifyPasswdAction.java
 * @작성일       : 2021. 9. 9. 
 * @작성자       : 박용복
 * @프로그램 설명 : 비밀번호 찾기 후 비밀번호 수정 Action
 */

public class ModifyPasswdAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("UTF-8");
		
		String id = request.getParameter("id");
		
		String passwd = request.getParameter("passwd");
				
		MemberDAO dao = MemberDAO.getInstance();
		dao.updatePassword(passwd, id);
		
		return "/WEB-INF/views/member/modifyPassword.jsp";
	}

}
