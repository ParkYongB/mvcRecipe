package com.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.controller.Action;

/**
 * @Package Name   : com.member.action
 * @FileName  : IdSearchFormAction.java
 * @�ۼ���       : 2021. 9. 8. 
 * @�ۼ���       : �ڿ뺹
 * @���α׷� ���� : ���̵� ã�� �� �׼�
 */

public class IdSearchFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		return "/WEB-INF/views/member/idSearchForm.jsp";
	}

}
