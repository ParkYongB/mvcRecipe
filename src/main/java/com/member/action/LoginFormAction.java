package com.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.controller.Action;

/**
 * @Package Name   : com.member.action
 * @FileName  : LoginFormAction.java
 * @�ۼ���       : 2021. 9. 7. 
 * @�ۼ���       : �ڿ뺹
 * @���α׷� ���� : �α��� �� �׼�
 */

public class LoginFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		return "/WEB-INF/views/member/loginForm.jsp";
	}
}
