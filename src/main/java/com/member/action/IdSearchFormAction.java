package com.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.controller.Action;

/**
 * @Package Name   : com.member.action
 * @FileName  : IdSearchFormAction.java
 * @작성일       : 2021. 9. 8. 
 * @작성자       : 박용복
 * @프로그램 설명 : 아이디 찾기 폼 액션
 */

public class IdSearchFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		return "/WEB-INF/views/member/idSearchForm.jsp";
	}

}
