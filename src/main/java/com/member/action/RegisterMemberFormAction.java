package com.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.controller.Action;

/**
 * @Package Name   : com.member.action
 * @FileName  : RegisterMemberFormAction.java
 * @�ۼ���       : 2021. 9. 7. 
 * @�ۼ���       : �ڿ뺹
 * @���α׷� ���� : ȸ������ �� �׼�
 */

public class RegisterMemberFormAction implements Action{

	// ȸ����� �� Action
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		return "/WEB-INF/views/member/registerMemberForm.jsp";
	}
	
}
