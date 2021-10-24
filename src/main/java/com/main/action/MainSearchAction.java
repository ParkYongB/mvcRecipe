/**
 * 
 */
package com.main.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.controller.Action;
import com.main.dao.MainDAO;
import com.news.vo.NewsVO;
import com.qnaboard.vo.QnaBoardVO;
import com.recipe.vo.RecipeVO;
import com.util.NewsPagingUtil;
import com.util.QnAPagingUtil;
import com.util.RecipePagingUtil;

/**
 * @Package Name   : com.main.action
 * @FileName  : MainSerachAction.java
 * @작성일       : 2021. 9. 13. 
 * @작성자       : 신혜지
 * @프로그램 설명 : 메인에서 통합검색할때 쓰는 서블릿입니다. 
 *  mainSearchList.do로 연결해주세요! 
 */
public class MainSearchAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//전송된 데이터 인코딩
		request.setCharacterEncoding("utf-8");
		String search = request.getParameter("search");
		//DAO 호출
		MainDAO dao = MainDAO.getInstance();
		
		String newsPageNum = request.getParameter("newsPageNum");
		String recipePageNum = request.getParameter("recipePageNum");
		String qnaPageNum = request.getParameter("qnaPageNum");
		if(newsPageNum==null) newsPageNum ="1";
		if(recipePageNum==null) recipePageNum ="1";
		if(qnaPageNum==null) qnaPageNum ="1";
		
		//--------------공지사항 게시판
		
		// 뉴스 게시글 수 체크 
		int news_count = dao.searchNewsCount(search);
		// 뉴스 리스트 출력
		
		NewsPagingUtil page1 = new NewsPagingUtil(search,qnaPageNum,Integer.parseInt(newsPageNum), news_count, 10, 5,"mainSearchList.do", recipePageNum);
		List<NewsVO> NewsList = null;
		if(news_count > 0) {
			NewsList = dao.getNewsList(page1.getStartCount(), page1.getEndCount(),search);
		}
		
		//-----------모두의 레시피 게시판
		
		// 모두의 레시피 게시글 수 체크
		int recipe_count = dao.searchRecipeCount(search);
		// 모두의 레시피 리스트 출력
		RecipePagingUtil page2 = new RecipePagingUtil(search,newsPageNum,Integer.parseInt(recipePageNum),recipe_count, 8, 5,"mainSearchList.do", qnaPageNum);
		List<RecipeVO> recipeList =null;
		if(recipe_count > 0) {
			recipeList = dao.getRecipeList(page2.getStartCount(), page2.getEndCount(),search);
		}
		
		
		//--------------QNA게시판
		
		//qna 게시글 수 체크
		int qna_count = dao.searchQnaCount(search);
		//qna 뉴스 리스트 출력
		QnAPagingUtil page3 = new QnAPagingUtil(search,newsPageNum,Integer.parseInt(qnaPageNum),qna_count,10,5,"mainSearchList.do", recipePageNum);
		List<QnaBoardVO> qnaList = null;
		if(qna_count > 0) {
			qnaList = dao.getQnaList(page3.getStartCount(), page3.getEndCount(), search);
		}
		
		
		// 게시글 수와 list 넘겨주기
		request.setAttribute("news_count",news_count );
		request.setAttribute("recipe_count",recipe_count );
		request.setAttribute("qna_count",qna_count );
		request.setAttribute("pagingHtmlNews", page1.getPagingHtml());
		request.setAttribute("pagingHtmlRecipe", page2.getPagingHtml());
		request.setAttribute("pagingHtmlQna", page3.getPagingHtml());
		request.setAttribute("NewsList",NewsList );
		request.setAttribute("recipeList",recipeList );
		request.setAttribute("qnaList",qnaList );
		request.setAttribute("search", search);
		request.setAttribute("newsPageNum", newsPageNum);
		request.setAttribute("qnaPageNum", qnaPageNum);
		request.setAttribute("recipePageNum", recipePageNum);
		
		return "/WEB-INF/views/main/mainSearchList.jsp";
	}

}
