package com.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.controller.Action;
import com.member.dao.MemberDAO;
import com.member.vo.MemberVO;

/**
 * @Package Name   : com.member.action
 * @FileName  : ModifyMemberAction.java
 * @작성일       : 2021. 9. 8. 
 * @작성자       : 박용복
 * @프로그램 설명 : 회원정보 수정 Action
 */

public class ModifyMemberAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer mem_num = (Integer)session.getAttribute("mem_num");
		
		if(mem_num == null) {
			return "redirect:/member/loginForm.do";
		}
		
		request.setCharacterEncoding("utf-8");
		
		// 현재 비밀번호
		String nowPasswd = request.getParameter("nowpasswd");
		
		// 새 비밀번호
		String newPasswd = request.getParameter("newpasswd");
		
		// 현재 로그인 한 아이디
		String mem_id = (String)session.getAttribute("id");
		
		MemberDAO dao = MemberDAO.getInstance();
		MemberVO member = dao.checkMember(mem_id);
		boolean check = false;
		
		// 입력한 아이디가 존재하는지 체크
		if(member != null && mem_id.equals(mem_id)) {
			// 비밀번호 일치 여부 체크
			check = member.isCheckedPassword(nowPasswd);
		}
		
		if(check && newPasswd.equals("")) {
			// 비밀번호를 수정하지 않을 경우 실행
			
			member.setMem_num(mem_num);
			member.setEmail(request.getParameter("email"));
			member.setPhone(request.getParameter("phone"));
			member.setPasskey(request.getParameter("passkey"));
			
			dao.updateMember(member, null);
		}else if(check && newPasswd != null) {
			// 비밀번호를 수정할 경우
			
			member.setMem_num(mem_num);
			member.setEmail(request.getParameter("email"));
			member.setPhone(request.getParameter("phone"));
			member.setPasskey(request.getParameter("passkey"));
			
			dao.updateMember(member, newPasswd);
		}
		request.setAttribute("check", check);
		
		return "/WEB-INF/views/member/modifyMember.jsp";
	}

}
