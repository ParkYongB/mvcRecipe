package com.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.controller.Action;

/**
 * @Package Name   : com.member.action
 * @FileName  : PasswdSearchFormAction.java
 * @�ۼ���       : 2021. 9. 9. 
 * @�ۼ���       : �ڿ뺹
 * @���α׷� ���� : ��й�ȣ ã�� form �׼�
 */

public class PasswdSearchFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		return "/WEB-INF/views/member/passwdSearchForm.jsp";
	}

}
