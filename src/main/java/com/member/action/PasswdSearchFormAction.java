package com.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.controller.Action;

/**
 * @Package Name   : com.member.action
 * @FileName  : PasswdSearchFormAction.java
 * @작성일       : 2021. 9. 9. 
 * @작성자       : 박용복
 * @프로그램 설명 : 비밀번호 찾기 form 액션
 */

public class PasswdSearchFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		return "/WEB-INF/views/member/passwdSearchForm.jsp";
	}

}
