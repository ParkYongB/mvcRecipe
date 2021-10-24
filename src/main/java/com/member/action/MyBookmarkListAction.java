package com.member.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.controller.Action;
import com.member.dao.MemberDAO;
import com.recipe.vo.RecipeVO;
import com.util.PagingUtil;

/**
 * @Package Name   : com.member.action
 * @FileName  : MyBookmarkListAction.java
 * @작성일       : 2021. 9. 9. 
 * @작성자       : 박용복
 * @프로그램 설명 : 북마크한 게시글을 출력하기 위한 Action
 */
public class MyBookmarkListAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 로그인 정보 가져오기
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		Integer mem_num = (Integer)session.getAttribute("mem_num");
		
		if(mem_num == null) {
			return "redirect:/member/loginForm.do";
		}
						
		// 전송된 데이터 타입
		request.setCharacterEncoding("utf-8");

		request.setAttribute("id", id);
		request.setAttribute("mem_num", mem_num);
						
		// 검색 조건 체크 null이라면 일반 페이지 처리
				
		String pageNum = request.getParameter("pageNum");
		if(pageNum==null) pageNum = "1";
							
		MemberDAO dao = MemberDAO.getInstance();
		int count = dao.getBookmarkCount(id);	// 총 레코드 수
							
		// 페이지 처리
		// currentPage, count, rowCount, pageCount, url
		PagingUtil page = new PagingUtil(Integer.parseInt(pageNum), count, 4, 5,"myBookmarkList.do");
							
		List<RecipeVO> list = null;
							
		// 총 레코드가 0이 아닐 때 list에 값을 담는다
		if(count > 0) {
			list = dao.getBookMarkRecipeList(page.getStartCount(), page.getEndCount(), mem_num);
		}
							
		// list와 총 페이지, 페이지 하단부분 넘겨주기
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("pagingHtml", page.getPagingHtml());
		
		return "/WEB-INF/views/member/myBookmarkList.jsp";

	}
}
