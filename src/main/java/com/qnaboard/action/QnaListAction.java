package com.qnaboard.action;

import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.qnaboard.dao.QnaBoardDAO;
import com.util.PagingUtil;
import com.qnaboard.vo.QnaBoardVO;
import com.controller.Action;


/**
 * @Package Name   : com.qnaboard.action
 * @FileName  : ListAction.java
 * @작성일       : 2021. 9. 6. 
 * @작성자       : 나윤경
 * @프로그램 설명 : 고객센터 게시판 목록 액션
 */
public class QnaListAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String pageNum = request.getParameter("pageNum");
		if(pageNum==null) pageNum="1";
		
		String keyfield = request.getParameter("keyfield");
		String keyword = request.getParameter("keyword");
		
		if(keyfield ==null) keyfield = "";
		if(keyword == null) keyword = "";
		
		
		QnaBoardDAO dao = QnaBoardDAO.getInstance();
		int count = dao.getQnaBoardCount(keyfield, keyword);
		
		//페이지 처리
		//currentPage,count,rowCount,pageCount,url
		PagingUtil page = new PagingUtil(keyfield, keyword, Integer.parseInt(pageNum),count,20,10,"qnaList.do");
		
		List<QnaBoardVO> list = null;
		if(count>0) {
			list = dao.getQnaBoardList(page.getStartCount(), page.getEndCount(), keyfield, keyword);
		}
		
		//request에 데이터 저장
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("pagingHtml", page.getPagingHtml());		

		//JSP 경로 반환
		return "/WEB-INF/views/qnaboard/qnaList.jsp";
	}

}
