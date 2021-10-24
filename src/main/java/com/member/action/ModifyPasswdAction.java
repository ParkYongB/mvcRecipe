package com.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.controller.Action;
import com.member.dao.MemberDAO;

/**
 * @Package Name   : com.member.action
 * @FileName  : ModifyPasswdAction.java
 * @�ۼ���       : 2021. 9. 9. 
 * @�ۼ���       : �ڿ뺹
 * @���α׷� ���� : ��й�ȣ ã�� �� ��й�ȣ ���� Action
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
