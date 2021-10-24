package com.news.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.controller.Action;
import com.news.dao.NewsDAO;
import com.news.vo.NewsVO;
import com.oreilly.servlet.MultipartRequest;
import com.util.FileUtil;

/**
 * @Package Name   : com.news.action
 * @FileName  : NewsWriteAction.java
 * @작성일       : 2021. 9. 7. 
 * @작성자       : 신혜지
 * @프로그램 설명 : 잇마이팁액션클래스 뉴스작성 액션 현재 file오류나서 지워둠
 */
public class NewsWriteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer mem_num = (Integer)session.getAttribute("mem_num");
		Integer auth =(Integer)session.getAttribute("auth");
		if(mem_num == null || auth != 3) {//로그인 되지 않은 경우, 권한이 3이 아닌 경우
			return "redirect:/member/loginForm.do";
		}
		MultipartRequest multi = FileUtil.createFile(request);
		NewsVO news= new NewsVO();
		news.setNews_title(multi.getParameter("title"));
		news.setNews_content(multi.getParameter("content"));
		news.setMem_num(mem_num);
		news.setNews_category(multi.getParameter("category"));
		news.setNews_file(multi.getFilesystemName("filename"));
		
		NewsDAO dao= NewsDAO.getInstance();
		dao.insertNews(news);
		
		return "/WEB-INF/views/news/newsWrite.jsp";
	}

}
