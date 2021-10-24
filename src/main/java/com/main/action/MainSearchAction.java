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
 * @�ۼ���       : 2021. 9. 13. 
 * @�ۼ���       : ������
 * @���α׷� ���� : ���ο��� ���հ˻��Ҷ� ���� �����Դϴ�. 
 *  mainSearchList.do�� �������ּ���! 
 */
public class MainSearchAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//���۵� ������ ���ڵ�
		request.setCharacterEncoding("utf-8");
		String search = request.getParameter("search");
		//DAO ȣ��
		MainDAO dao = MainDAO.getInstance();
		
		String newsPageNum = request.getParameter("newsPageNum");
		String recipePageNum = request.getParameter("recipePageNum");
		String qnaPageNum = request.getParameter("qnaPageNum");
		if(newsPageNum==null) newsPageNum ="1";
		if(recipePageNum==null) recipePageNum ="1";
		if(qnaPageNum==null) qnaPageNum ="1";
		
		//--------------�������� �Խ���
		
		// ���� �Խñ� �� üũ 
		int news_count = dao.searchNewsCount(search);
		// ���� ����Ʈ ���
		
		NewsPagingUtil page1 = new NewsPagingUtil(search,qnaPageNum,Integer.parseInt(newsPageNum), news_count, 10, 5,"mainSearchList.do", recipePageNum);
		List<NewsVO> NewsList = null;
		if(news_count > 0) {
			NewsList = dao.getNewsList(page1.getStartCount(), page1.getEndCount(),search);
		}
		
		//-----------����� ������ �Խ���
		
		// ����� ������ �Խñ� �� üũ
		int recipe_count = dao.searchRecipeCount(search);
		// ����� ������ ����Ʈ ���
		RecipePagingUtil page2 = new RecipePagingUtil(search,newsPageNum,Integer.parseInt(recipePageNum),recipe_count, 8, 5,"mainSearchList.do", qnaPageNum);
		List<RecipeVO> recipeList =null;
		if(recipe_count > 0) {
			recipeList = dao.getRecipeList(page2.getStartCount(), page2.getEndCount(),search);
		}
		
		
		//--------------QNA�Խ���
		
		//qna �Խñ� �� üũ
		int qna_count = dao.searchQnaCount(search);
		//qna ���� ����Ʈ ���
		QnAPagingUtil page3 = new QnAPagingUtil(search,newsPageNum,Integer.parseInt(qnaPageNum),qna_count,10,5,"mainSearchList.do", recipePageNum);
		List<QnaBoardVO> qnaList = null;
		if(qna_count > 0) {
			qnaList = dao.getQnaList(page3.getStartCount(), page3.getEndCount(), search);
		}
		
		
		// �Խñ� ���� list �Ѱ��ֱ�
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
