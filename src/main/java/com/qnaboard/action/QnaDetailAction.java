package com.qnaboard.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.controller.Action;
import com.qnaboard.dao.QnaBoardDAO;
import com.qnaboard.vo.QnaBoardVO;
import com.util.StringUtil;

/**
 * @Package Name   : com.qnaboard.action
 * @FileName  : QnaDetailAction.java
 * @작성일       : 2021. 9. 9. 
 * @작성자       : 나윤경
 * @프로그램 설명 : 고객센터 상세 페이지 액션
 */
public class QnaDetailAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		//글 번호 반환
		Integer qna_num = Integer.parseInt(request.getParameter("qna_num")); 
		
		QnaBoardDAO dao = QnaBoardDAO.getInstance();
		QnaBoardVO qnaboard = dao.getQnaBoardDetail(qna_num);
		
		//HTML을 허용하지 않음
		qnaboard.setQna_title(StringUtil.useNoHtml(qnaboard.getQna_title()));
		
		//HTML을 허용하지 않으면서 줄바꿈 처리
		qnaboard.setQna_content(StringUtil.useBrNoHtml(qnaboard.getQna_content()));
		
		request.setAttribute("qnaboard", qnaboard);
		
		return "/WEB-INF/views/qnaboard/qnaDetail.jsp";
	}

}
