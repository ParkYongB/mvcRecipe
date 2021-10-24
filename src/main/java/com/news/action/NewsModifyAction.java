/**
 * 
 */
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
 * @FileName  : NewsModify.java
 * @�ۼ���       : 2021. 9. 7. 
 * @�ۼ���       : ������
 * @���α׷� ���� : ���� ���� ������ �޾Ƽ� ���� �����ϰ� �� ���� ���� �������� ������
 */
public class NewsModifyAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer mem_num = (Integer)session.getAttribute("mem_num");
		Integer auth =(Integer)session.getAttribute("auth");
		if(mem_num == null || auth != 3) {//�α��� ���� ���� ���, ������ 3�� �ƴ� ���
			return "redirect:/member/loginForm.do";
		}
		
		MultipartRequest multi = FileUtil.createFile(request);
		int num = Integer.parseInt(multi.getParameter("news_num"));
		String filename= multi.getFilesystemName("filename");
		
		NewsDAO dao = NewsDAO.getInstance();
		//���� �� ������
		NewsVO db= dao.getNews(num);
		if(mem_num != db.getMem_num()) {
			FileUtil.removeFile(request, filename);//���ε�� ������ ������ ���� ����
			return "redirect:/main/main.do";
		}
		NewsVO news = new NewsVO();
		news.setNews_num(num);
		news.setNews_title(multi.getParameter("title"));
		news.setNews_content(multi.getParameter("content"));
		news.setNews_category(multi.getParameter("category"));
		news.setNews_file(filename);
		
		dao.updateNews(news);
		
		if(filename!=null) {
			FileUtil.removeFile(request, db.getNews_file());
		}
		
		return "/WEB-INF/views/news/newsModify.jsp";
	}

}
