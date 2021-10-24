package com.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.controller.Action;

// �α׾ƿ� Action

/**
 * @Package Name   : com.member.action
 * @FileName  : LogoutAction.java
 * @�ۼ���       : 2021. 9. 7. 
 * @�ۼ���       : �ڿ뺹
 * @���α׷� ���� : �α׾ƿ� �׼�
 */

public class LogoutAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		
		// �α׾ƿ� ó��
		session.invalidate();
		
		return "redirect:/main/main.do";
	}

}
