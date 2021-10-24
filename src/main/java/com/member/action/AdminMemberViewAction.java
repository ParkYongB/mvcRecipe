package com.member.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.controller.Action;
import com.member.dao.MemberDAO;
import com.member.vo.MemberVO;
import com.util.PagingUtil;

/**
 * @Package Name   : com.member.action
 * @FileName  : AdminMemberViewAction.java
 * @�ۼ���       : 2021. 9. 15. 
 * @�ۼ���       : �ڿ뺹
 * @���α׷� ���� : �������� ȸ�� ������ ���� Action
 */

public class AdminMemberViewAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// �α����� ������ �޾� ��
		HttpSession session = request.getSession();
		// �α����� ȸ���� ȸ�� ��ȣ�� ���� ��
		Integer mem_num = (Integer)session.getAttribute("mem_num");
		// �α����� ȸ���� ȸ�� ����� ���� ��
		Integer auth = (Integer)session.getAttribute("auth");
		
		// ȸ�� ��ȣ�� ���� ���� ���� ���
		if(mem_num == null) {
			// �α��� �������� ����
			return "redirect:/member/loginForm.do";
		}
		
		if(auth != 3) {
			// �α����� ����� �����ڰ� �ƴ� ��� �߸��� ���� �������� ����
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		// ������ �ѹ��� �ҷ���
		String pageNum = request.getParameter("pageNum");
		// ������ �ѹ��� ���� ��� default ������ 1�� �־� ��
		if(pageNum == null)pageNum = "1";
		
		// �˻� �׸��� �ҷ���
		String keyfield = request.getParameter("keyfield");
		// �˻� ������ �ҷ���
		String keyword = request.getParameter("keyword");
		
		// �˻� �׸��� �������� ���� ��� �ٸ� �۾��� ���������� ���� �Ǳ� ���� �˻� �׸��� �������� ����
		if(keyfield == null) keyfield = "";
		// �˻� ������ �������� ���� ��� �ٸ� �۾��� ���������� ���� �Ǳ� ���� �˻� ������ �������� ����
		if(keyword == null) keyword = "";
		
		MemberDAO dao = MemberDAO.getInstance();
		// �� ȸ���� ���� ���ϴ� �޼ҵ忡 �˻� �׸� �˻� ������ ����
		int count = dao.getMemberCount(keyfield, keyword);
		
		// ��� �������� ������ ó��
		// 														currentPage, count, rowCount, pageCount, url
		PagingUtil page = new PagingUtil(keyfield, keyword, Integer.parseInt(pageNum), count, 20, 10, "adminMemberView.do");
		
		List<MemberVO> list = null;
		// �迭�� PagingUtil �޼ҵ忡 ����� ���� ��ȣ, �� ��ȣ, �˻� �׸�, �˻� ������ �����Ѵ�.
		if(count > 0) {
			list = dao.getListMember(page.getStartCount(), page.getEndCount(), keyfield, keyword);
		}
		
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("pagingHtml", page.getPagingHtml());
		
		return "/WEB-INF/views/member/adminMemberView.jsp";
	}

}
