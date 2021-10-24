package com.qnaboard.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import com.controller.Action;
import com.recipe.dao.RecipeDAO;
import com.recipe.vo.RecipeCommendsVO;

/**
 * @Package Name   : com.qnaboard.action
 * @FileName  : QnaReplyModifyAction.java
 * @�ۼ���       : 2021. 9. 12. 
 * @�ۼ���       : ������
 * @���α׷� ���� : ������ �Խ��� ��� ���� �׼�
 */
public class QnaReplyModifyAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
			// ���۵� ������ ���ڵ� ó��
			request.setCharacterEncoding("utf-8");
				
			// �ۼ��� ȸ�� ��ȣ
			int writer_auth = Integer.parseInt(request.getParameter("writer_auth"));
			
			Map<String, String> mapAjax = new HashMap<String, String>();
			
			// �α��� üũ
			if(writer_auth == 3) {	// �α����� �Ǿ� �ְ� �α����� ȸ�� ��ȣ�� �ۼ��� ȸ����ȣ ��ġ
				// �ڹٺ� ����
				RecipeCommendsVO comm = new RecipeCommendsVO();
				comm.setComm_num(Integer.parseInt(request.getParameter("comm_num")));
				comm.setComm_con(request.getParameter("comm_con"));
				
				//����� ������DAO�� updateRecipeCommend(��� ����) �ڵ� ����
				RecipeDAO dao = RecipeDAO.getInstance();
				dao.updateRecipeCommend(comm);
					
				mapAjax.put("result", "success");
			}else {
				mapAjax.put("result", "Wrong");
			}
				
			// JSON �����ͷ� ��ȯ
			  ObjectMapper mapper = new ObjectMapper();
			  String ajaxData = mapper.writeValueAsString(mapAjax);
			     
			  request.setAttribute("ajaxData", ajaxData);

				
			  return "/WEB-INF/views/common/ajax_view.jsp";
		}

	}
