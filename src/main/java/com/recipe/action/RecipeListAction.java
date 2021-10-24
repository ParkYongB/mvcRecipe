package com.recipe.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.controller.Action;
import com.recipe.dao.RecipeDAO;
import com.recipe.vo.RecipeNewsVO;
import com.recipe.vo.RecipeVO;
import com.util.PagingUtil;

/**
 * @Package Name   : com.recipe.action
 * @FileName  : RecipeListAction.java
 * @작성일       : 2021. 9. 7. 
 * @작성자       : 오상준
 * @프로그램 설명 : 모두의 레시피 액션 클래스
 */

public class RecipeListAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 로그인 정보 가져오기
		HttpSession session = request.getSession();
		Integer mem_num = (Integer)session.getAttribute("mem_num");
		Integer auth = (Integer)session.getAttribute("auth");
		
		// 검색 조건 체크를 위해 변수 할당
		String search = request.getParameter("search");
		String division = request.getParameter("division");
		
		// 전송된 데이터 타입
		request.setCharacterEncoding("utf-8");
		
		request.setAttribute("search", search);
		request.setAttribute("division", division);
		request.setAttribute("mem_num", mem_num);
		request.setAttribute("auth", auth);
		
		// ------------------------------- 공지사항
		// 공지사항 게시글 수 체크
		RecipeDAO dao = RecipeDAO.getInstance();
		int news_count = dao.getRecipeNewsCount();
		
		
		
		List<RecipeNewsVO> news_list = null;
		if(news_count > 0) {
			news_list = dao.getRecipeNewsList(1, 5);
		}
		
		request.setAttribute("news_count", news_count);
		request.setAttribute("news_list", news_list);
		
		
		// ------------------------ 레시피 게시판
		// 변수 준비
		int count = 0;
		PagingUtil page = null;
		
		// -------- 레시피 게시판 출력
		
		String pageNum = request.getParameter("pageNum");
		if(pageNum==null) pageNum = "1";
		
		List<RecipeVO> list = null;
			
		// 총 레코드가 0이 아닐 때 list에 값을 담는다
		
			if(division != null) {	// 검색이 있는 경우
				count = dao.getRecipeCount(division, search);	// 검색조건에 부합한 총 레코드 수
				
				if(count > 0) {
					// 페이지 처리
					page = new PagingUtil(search,division,Integer.parseInt(pageNum), count, 4, 5,"recipeList.do");
						
					list = dao.getSearchlRecipeList(page.getStartCount(), page.getEndCount(), division, search);
					
					request.setAttribute("pagingHtml", page.getPagingHtml());
				}
			}else {	// 검색이 없는 경우
				count = dao.getRecipeCount();	// 검색조건에 부합한 총 레코드 수
				
				if(count > 0) {
					// 페이지 처리
					// currentPage, count, rowCount, pageCount, url
					page = new PagingUtil(Integer.parseInt(pageNum), count, 4, 5,"recipeList.do");
						
					list = dao.getTotalRecipeList(page.getStartCount(), page.getEndCount());
					
					request.setAttribute("pagingHtml", page.getPagingHtml());
				}
			}
				
		
		
			// list와 총 페이지, 페이지 하단부분 넘겨주기
			request.setAttribute("count", count);
			request.setAttribute("list", list);
			request.setAttribute("division", division);
			request.setAttribute("search", search);
			
			// 모두의 레시피로 이동	
			return "/WEB-INF/views/recipe/recipeList.jsp";
	}

}
