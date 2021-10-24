package com.member.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import com.controller.Action;
import com.member.dao.MemberDAO;

/**
 * @Package Name   : com.member.action
 * @FileName  : StopAdminMemberAction.java
 * @작성일       : 2021. 9. 11. 
 * @작성자       : 박용복
 * @프로그램 설명 : 관리자가 회원 정지를 하게 해주는 Action
 */

public class StopAdminMemberAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("utf-8");
		
		String output = request.getParameter("output");
		String[] stopChecked = output.split(",");
	
		MemberDAO dao = MemberDAO.getInstance();
		Map<String, String> mapAjax = new HashMap<String, String>();
		
		for(String mem_num : stopChecked) {
			dao.stopAdminMember(mem_num);
			
			mapAjax.put("result", "success");
		}

		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);

		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
