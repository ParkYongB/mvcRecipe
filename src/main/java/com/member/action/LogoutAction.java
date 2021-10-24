package com.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.controller.Action;

// 로그아웃 Action

/**
 * @Package Name   : com.member.action
 * @FileName  : LogoutAction.java
 * @작성일       : 2021. 9. 7. 
 * @작성자       : 박용복
 * @프로그램 설명 : 로그아웃 액션
 */

public class LogoutAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		
		// 로그아웃 처리
		session.invalidate();
		
		return "redirect:/main/main.do";
	}

}
