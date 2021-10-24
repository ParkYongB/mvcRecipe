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
 * @FileName  : UpAdminMemberAction.java
 * @�ۼ���       : 2021. 9. 13. 
 * @�ۼ���       : �ڿ뺹
 * @���α׷� ���� : ����ȸ���� �ٽ� �Ϲ�ȸ������ �����ϴ� Action
 */

public class UpAdminMemberAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("utf-8");
		
		String output = request.getParameter("output");
		String[] stopChecked = output.split(",");
	
		MemberDAO dao = MemberDAO.getInstance();
		Map<String, String> mapAjax = new HashMap<String, String>();
		
		for(String mem_num : stopChecked) {
			dao.upAdminMember(mem_num);
			
			mapAjax.put("result", "success");
		}

		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);

		return "/WEB-INF/views/common/ajax_view.jsp";
	}
	
}
