package com.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.controller.Action;
import com.member.dao.MemberDAO;
import com.member.vo.MemberVO;

/**
 * @Package Name   : com.member.action
 * @FileName  : ModifyMemberFormAction.java
 * @작성일       : 2021. 9. 7. 
 * @작성자       : 박용복
 * @프로그램 설명 : 회원 수정 폼 Action
 */

public class ModifyMemberFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer mem_num = (Integer)session.getAttribute("mem_num");
		
		if(mem_num == null) {
			return "redirect:/member/loginForm.do";
		}
		
		MemberDAO dao = MemberDAO.getInstance();
		MemberVO member = dao.getMember(mem_num);
		
		request.setAttribute("member", member);
		
		return "/WEB-INF/views/member/modifyMemberForm.jsp";
	}

}
