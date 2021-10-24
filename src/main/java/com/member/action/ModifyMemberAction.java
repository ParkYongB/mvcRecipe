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
 * @�ۼ���       : 2021. 9. 8. 
 * @�ۼ���       : �ڿ뺹
 * @���α׷� ���� : ȸ������ ���� Action
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
		
		// ���� ��й�ȣ
		String nowPasswd = request.getParameter("nowpasswd");
		
		// �� ��й�ȣ
		String newPasswd = request.getParameter("newpasswd");
		
		// ���� �α��� �� ���̵�
		String mem_id = (String)session.getAttribute("id");
		
		MemberDAO dao = MemberDAO.getInstance();
		MemberVO member = dao.checkMember(mem_id);
		boolean check = false;
		
		// �Է��� ���̵� �����ϴ��� üũ
		if(member != null && mem_id.equals(mem_id)) {
			// ��й�ȣ ��ġ ���� üũ
			check = member.isCheckedPassword(nowPasswd);
		}
		
		if(check && newPasswd.equals("")) {
			// ��й�ȣ�� �������� ���� ��� ����
			
			member.setMem_num(mem_num);
			member.setEmail(request.getParameter("email"));
			member.setPhone(request.getParameter("phone"));
			member.setPasskey(request.getParameter("passkey"));
			
			dao.updateMember(member, null);
		}else if(check && newPasswd != null) {
			// ��й�ȣ�� ������ ���
			
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
