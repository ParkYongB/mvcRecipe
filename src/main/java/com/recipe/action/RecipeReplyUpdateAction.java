package com.recipe.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import com.controller.Action;
import com.recipe.dao.RecipeDAO;
import com.recipe.vo.RecipeCommendsVO;

/**
 * @Package Name   : com.recipe.action
 * @FileName  : RecipeReplyUpdateAction.java
 * @�ۼ���       : 2021. 9. 10. 
 * @�ۼ���       : ������
 * @���α׷� ���� : ����� ������ ��� ������Ʈ �׼�
 */
public class RecipeReplyUpdateAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// ���۵� ������ ���ڵ� ó��
		request.setCharacterEncoding("utf-8");
		
		// �ۼ��� ȸ�� ��ȣ
		int writer_num = Integer.parseInt(request.getParameter("writer_num"));
	
		
		Map<String, String> mapAjax = new HashMap<String, String>();
		
		HttpSession session = request.getSession();
		Integer mem_num = (Integer)session.getAttribute("mem_num");
		Integer auth = (Integer)session.getAttribute("auth");
		
		// �α��� üũ
		if(mem_num == null) { // �α����� ���� ���� ���
			mapAjax.put("result", "logout");
		}else if((mem_num!=null && mem_num == writer_num) || auth == 3) {	// �α����� �Ǿ� �ְ� �α����� ȸ�� ��ȣ�� �ۼ��� ȸ����ȣ ��ġ or �������� ���
			// �ڹٺ� ����
			RecipeCommendsVO comm = new RecipeCommendsVO();
			comm.setComm_num(Integer.parseInt(request.getParameter("comm_num")));
			comm.setComm_con(request.getParameter("comm_con"));
			
			RecipeDAO dao = RecipeDAO.getInstance();
			dao.updateRecipeCommend(comm);
			
			mapAjax.put("result", "success");
		}else {			// �α����� �Ǿ� �ְ� �α����� ȸ�� ��ȣ�� �ۼ��� ȸ�� ��ȣ ����ġ
			mapAjax.put("result", "wrongAccess");
		}
		
		// JSON �����ͷ� ��ȯ
	   ObjectMapper mapper = new ObjectMapper();
	   String ajaxData = mapper.writeValueAsString(mapAjax);
	      
	   request.setAttribute("ajaxData", ajaxData);

		
	   return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
