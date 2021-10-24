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
 * @�ۼ���       : 2021. 9. 7. 
 * @�ۼ���       : ������
 * @���α׷� ���� : �ո������׼�Ŭ���� �����ۼ� �׼� ���� file�������� ������
 */
public class NewsWriteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer mem_num = (Integer)session.getAttribute("mem_num");
		Integer auth =(Integer)session.getAttribute("auth");
		if(mem_num == null || auth != 3) {//�α��� ���� ���� ���, ������ 3�� �ƴ� ���
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
