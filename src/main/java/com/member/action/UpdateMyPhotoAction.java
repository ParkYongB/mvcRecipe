package com.member.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import com.controller.Action;
import com.member.dao.MemberDAO;
import com.oreilly.servlet.MultipartRequest;
import com.util.FileUtil;

/**
 * @Package Name   : com.member.action
 * @FileName  : UpdateMyPhotoAction.java
 * @�ۼ���       : 2021. 9. 7. 
 * @�ۼ���       : �ڿ뺹
 * @���α׷� ���� : ���� ���ε� Action
 */

//Update Photo Action
public class UpdateMyPhotoAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Map<String, String> mapAjax = new HashMap<String, String>();
		
		HttpSession session = request.getSession();
		Integer mem_num = (Integer)session.getAttribute("mem_num");
		
		if(mem_num == null) {
			mapAjax.put("result", "logout");
		}else {
			MultipartRequest multi = FileUtil.createFile(request);
			
			MemberDAO dao = MemberDAO.getInstance();
			
			dao.updateMyPhoto(multi.getFilesystemName("photo"), mem_num);
			
			mapAjax.put("result", "success");
		}
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
