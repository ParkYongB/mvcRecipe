package com.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.controller.Action;

/**
 * @Package Name   : com.member.action
 * @FileName  : RegisterMemberFormAction.java
 * @작성일       : 2021. 9. 7. 
 * @작성자       : 박용복
 * @프로그램 설명 : 회원가입 폼 액션
 */

public class RegisterMemberFormAction implements Action{

	// 회원등록 폼 Action
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		return "/WEB-INF/views/member/registerMemberForm.jsp";
	}
	
}
