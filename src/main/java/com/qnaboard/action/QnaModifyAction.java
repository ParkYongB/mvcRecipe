package com.qnaboard.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.controller.Action;
import com.qnaboard.dao.QnaBoardDAO;
import com.qnaboard.vo.QnaBoardVO;

public class QnaModifyAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
	
		QnaBoardVO qnaboardVO = new QnaBoardVO();
		qnaboardVO.setQna_num(Integer.parseInt(request.getParameter("qna_num")));
		qnaboardVO.setQna_title(request.getParameter("qna_title"));
		qnaboardVO.setQna_passwd(request.getParameter("qna_passwd"));
		qnaboardVO.setQna_content(request.getParameter("qna_content"));
		qnaboardVO.setQna_ip(request.getRemoteAddr());
		
		QnaBoardDAO dao = QnaBoardDAO.getInstance();
		
		//비밀번호 인증을 위해 한 건의 레코드를 자바빈에 담아서 반환
		QnaBoardVO qnaboard = dao.getQnaBoardDetail(qnaboardVO.getQna_num());
		boolean check =false;
		if(qnaboard!=null) {
			//비밀번호 일치 여부 체크
			check = qnaboard.isCheckedPassword(qnaboardVO.getQna_passwd());
		}
		if(check) {	//인증 성공
			//글 수정
			dao.qnaBoardModify(qnaboardVO);
		}
		//request에 데이터 저장
		request.setAttribute("check", check);
		
		//JSP 경로 반환
		return "/WEB-INF/views/qnaboard/qnaModify.jsp";
	}

}
