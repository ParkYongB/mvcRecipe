package com.bestRecipe.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.controller.Action;
import com.recipe.dao.RecipeDAO;
import com.recipe.vo.RecipeVO;
import com.util.PagingUtil;

/**
 * @Package Name   : com.bestRecipe.action
 * @FileName  : RecipeListAction.java
 * @작성일       : 2021. 9. 10. 
 * @작성자       : 오상준
 * @프로그램 설명 : 베스트 레시피 액션 클래스
 */
public class BestRecipeListAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 로그인 정보 가져오기
		HttpSession session = request.getSession();
		Integer mem_num = (Integer)session.getAttribute("mem_num");
				
		// 검색 조건 체크를 위해 변수 할당
		String division = request.getParameter("division");
				
		// 전송된 데이터 타입
		request.setCharacterEncoding("utf-8");
				
		request.setAttribute("mem_num", mem_num);
		request.setAttribute("division", division);
		
		// 처음 입장시 기본값 추천순으로 셋팅
		if(division == null) {
			division = "추천순";
		}
		
		// 조건에 맞는 리스트 추출 메소드 실행
		searchCheck(request, response, division);
					
		return "/WEB-INF/views/bestRecipe/bestRecipeList.jsp";
		}	
	
	/**
	 * @Method 메소드명  : searchCheck
	 * @작성일     : 2021. 9. 11. 
	 * @작성자     : 오상준
	 * @Method 설명 : 베스트 게시판 조회순, 추천순 출력
	 */
	public void searchCheck(HttpServletRequest request, HttpServletResponse response, String division) throws Exception {
		
		String pageNum = request.getParameter("pageNum");
		if(pageNum==null) pageNum = "1";
				
		RecipeDAO dao = RecipeDAO.getInstance();
		int count = dao.getRecipeCount();	// 총 레코드 수
				
		// 페이지 처리
		// currentPage, count, rowCount, pageCount, url
		PagingUtil page = new PagingUtil(null, division, Integer.parseInt(pageNum), count, 4, 5,"bestRecipeList.do");
				
		List<RecipeVO> list = null;
				
		// 총 레코드가 0이 아닐 때 list에 값을 담는다
		if(count > 0) {
			if(division.equals("조회순")) {
				list = dao.getHitsTotalRecipeList(page.getStartCount(), page.getEndCount());
			}
			if(division.equals("추천순")) {
				// 리스트에 추천순 담기
				list = dao.getRecommTotalRecipeList(page.getStartCount(), page.getEndCount());
			}
			if(division.equals("댓글순")) {
				// 리스트에 댓글순 담기
				list = dao.getCommentsTotalRecipeList(page.getStartCount(), page.getEndCount());
			}
		}
				
		// list와 총 페이지, 페이지 하단부분 넘겨주기
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("pagingHtml", page.getPagingHtml());
				
	}

}
