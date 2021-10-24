package com.qnaboard.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import com.controller.Action;
import com.qnaboard.dao.QnaBoardDAO;

/**
 * @Package Name   : com.qnaboard.action
 * @FileName  : QnaReplyDeleteAction.java
 * @�ۼ���       : 2021. 9. 12. 
 * @�ۼ���       : ������
 * @���α׷� ���� : ������ �Խ��� ��� ���� �׼�
 */
public class QnaReplyDeleteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		// ���۵� ������ ���ڵ� ó��
		request.setCharacterEncoding("utf-8");
				
		// ���۵� ������ ��ȯ
		int comm_num = Integer.parseInt(request.getParameter("comm_num"));	// ��� ��ȣ
		int writer_auth = Integer.parseInt(request.getParameter("writer_auth"));	// ������ ���
				
		Map<String,String> mapAjax = new HashMap<String, String>();
			
		if(writer_auth==3) {	// ������ ����� 3�̸�
			QnaBoardDAO dao = QnaBoardDAO.getInstance();
			dao.deleteQnaCommend(comm_num);
			mapAjax.put("result", "success");
		}else { //�����ڰ� �ƴ� ���
			mapAjax.put("result", "Wrong");
		}
				
		// JSON �����ͷ� ��ȯ
		   ObjectMapper mapper = new ObjectMapper();
		   String ajaxData = mapper.writeValueAsString(mapAjax);
			      
		   request.setAttribute("ajaxData", ajaxData);

				
		   return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
