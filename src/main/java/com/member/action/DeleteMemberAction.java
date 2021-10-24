package com.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.controller.Action;
import com.member.dao.MemberDAO;
import com.member.vo.MemberVO;
import com.util.FileUtil;

/**
 * @Package Name   : com.member.action
 * @FileName  : DeleteMemberAction.java
 * @�ۼ���       : 2021. 9. 8. 
 * @�ۼ���       : �ڿ뺹
 * @���α׷� ���� : ȸ�� ���� Action
 */

public class DeleteMemberAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer mem_num = (Integer)session.getAttribute("mem_num");
		String id = (String)session.getAttribute("id");
		
		if(mem_num == null) {
			return "redirect:/member/loginForm.do";
		}
		request.setCharacterEncoding("UTF-8");
		
		String passwd = request.getParameter("passwd");
		
		String mem_id = (String)session.getAttribute("id");
		
		MemberDAO dao = MemberDAO.getInstance();
		MemberVO member = dao.checkMember(id);
		
		boolean check = false;
		
		// ����ڰ� �Է��� ���̵� �����ϰ� �α����� ���̿� ��ġ�ϴ��� üũ
		if(member != null && id.equals(mem_id)) {
			check = member.isCheckedPassword(passwd);
		}
		if(check) {
			dao.deleteMember(mem_num);
			
			FileUtil.removeFile(request, member.getPhoto());
		
			session.invalidate();
		}
		request.setAttribute("check", check);
		
		return "/WEB-INF/views/member/deleteMember.jsp";
	}

}
