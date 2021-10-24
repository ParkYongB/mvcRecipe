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
 * @작성일       : 2021. 9. 15. 
 * @작성자       : 박용복
 * @프로그램 설명 : 관리자의 회원 관리를 위한 Action
 */

public class AdminMemberViewAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 로그인한 정보를 받아 옴
		HttpSession session = request.getSession();
		// 로그인한 회원의 회원 번호를 가져 옴
		Integer mem_num = (Integer)session.getAttribute("mem_num");
		// 로그인한 회원의 회원 등급을 가져 옴
		Integer auth = (Integer)session.getAttribute("auth");
		
		// 회원 번호를 가져 오지 못할 경우
		if(mem_num == null) {
			// 로그인 페이지로 보냄
			return "redirect:/member/loginForm.do";
		}
		
		if(auth != 3) {
			// 로그인한 사람이 관리자가 아닐 경우 잘못된 접근 페이지로 보냄
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		// 페이지 넘버를 불러옴
		String pageNum = request.getParameter("pageNum");
		// 페이지 넘버가 없을 경우 default 값으로 1을 넣어 줌
		if(pageNum == null)pageNum = "1";
		
		// 검색 항목을 불러옴
		String keyfield = request.getParameter("keyfield");
		// 검색 내용을 불러옴
		String keyword = request.getParameter("keyword");
		
		// 검색 항목이 설정되지 않을 경우 다른 작업이 정상적으로 실행 되기 위해 검색 항목을 공백으로 넣음
		if(keyfield == null) keyfield = "";
		// 검색 내용이 설정되지 않을 경우 다른 작업이 정상적으로 실행 되기 위해 검색 내용을 공백으로 넣음
		if(keyword == null) keyword = "";
		
		MemberDAO dao = MemberDAO.getInstance();
		// 총 회원의 수를 구하는 메소드에 검색 항목 검색 내용을 삽입
		int count = dao.getMemberCount(keyfield, keyword);
		
		// 목록 데이터의 페이지 처리
		// 														currentPage, count, rowCount, pageCount, url
		PagingUtil page = new PagingUtil(keyfield, keyword, Integer.parseInt(pageNum), count, 20, 10, "adminMemberView.do");
		
		List<MemberVO> list = null;
		// 배열에 PagingUtil 메소드에 저장된 시작 번호, 끝 번호, 검색 항목, 검색 내용을 삽입한다.
		if(count > 0) {
			list = dao.getListMember(page.getStartCount(), page.getEndCount(), keyfield, keyword);
		}
		
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("pagingHtml", page.getPagingHtml());
		
		return "/WEB-INF/views/member/adminMemberView.jsp";
	}

}
