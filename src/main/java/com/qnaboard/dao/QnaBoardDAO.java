package com.qnaboard.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


import com.qnaboard.vo.QnaBoardVO;
import com.recipe.vo.RecipeCommendsVO;
import com.util.DBUtil;
import com.util.DurationFromNow;
import com.util.StringUtil;


public class QnaBoardDAO {
	//�̱��� ����
	private static QnaBoardDAO instance = new QnaBoardDAO();
	
	public static QnaBoardDAO getInstance() {
		return instance;
	}
	
	private QnaBoardDAO() {}

	/**
	 * @Method �޼ҵ��  : QnaBoardWrite
	 * @�ۼ���     : 2021. 9. 6. 
	 * @�ۼ���     : ������
	 * @Method ���� : ������ �Խ��� �� �ۼ�
	 */
	public void QnaBoardWrite(QnaBoardVO qnaboard)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//Ŀ�ؼ�Ǯ�κ��� Ŀ�ؼ��� �Ҵ�
			conn = DBUtil.getConnection();
			
			//SQL�� �ۼ�
			sql = "INSERT INTO qna_board (qna_num, qna_title, qna_content, qna_id, qna_passwd, qna_ip, qna_date) "
					+ "VALUES (qna_board_seq.nextval,?,?,?,?,?,SYSDATE)";
			//PreparedStatement ��ü ����
			pstmt = conn.prepareStatement(sql);
			//?�� ������ ���ε�
			pstmt.setString(1, qnaboard.getQna_title());
			pstmt.setString(2, qnaboard.getQna_content());
			pstmt.setString(3, qnaboard.getQna_id());
			pstmt.setString(4, qnaboard.getQna_passwd());
			pstmt.setString(5, qnaboard.getQna_ip());
			
			//SQL�� ����
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			//�ڿ�����
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	/**
	 * @Method �޼ҵ��  : getQnaBoardCount
	 * @�ۼ���     : 2021. 9. 6. 
	 * @�ۼ���     : ������
	 * @Method ���� : ������ �Խ��� �� ���ڵ� ��(�˻� ���ڵ� ��)
	 */
	public int getQnaBoardCount(String keyfield, String keyword)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String sub_sql =null;
		int count = 0;
		
		try {
			//Ŀ�ؼ�Ǯ�κ��� Ŀ�ؼ��� �Ҵ�
			conn = DBUtil.getConnection();
			
			if(keyword == null || "".equals(keyword)) {
				//��ü �� ����
				sql="SELECT COUNT(*) FROM qna_board";
				pstmt = conn.prepareStatement(sql);
			}else {
				//�˻� �� ����
				if(keyfield.equals("1")) sub_sql = "qna_title LIKE ?";
				else if(keyfield.equals("2")) sub_sql = "qna_id =?";
				else if(keyfield.equals("3")) sub_sql = "qna_content LIKE ?";
				
				sql="SELECT COUNT(*) FROM qna_board WHERE " + sub_sql;
				pstmt = conn.prepareStatement(sql);
				if(keyfield.equals("2")) {
					pstmt.setString(1, keyword);
				}else {
					pstmt.setString(1, "%"+keyword+"%");
				}
				
			}
			//SQL�� �����ϰ� ������� ResultSet�� ����
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt(1);
			}			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			//�ڿ� ����
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return count;
	}

	
	/**
	 * @Method �޼ҵ��  : getQnaBoardList
	 * @�ۼ���     : 2021. 9. 6. 
	 * @�ۼ���     : ������
	 * @Method ���� : ������ �Խ��� �� ���(�˻��� ���)
	 */
	public List<QnaBoardVO> getQnaBoardList(int startRow, int endRow, String keyfield, String keyword)throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<QnaBoardVO> list = null;
		String sql = null;
		String sub_sql = null;
		
		try {
			//Ŀ�ؼ�Ǯ�κ��� Ŀ�ؼ��� �Ҵ�
			conn = DBUtil.getConnection();
			
			if(keyword==null || "".equals(keyword)) {
				//��ü �� ����
				sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM (SELECT * FROM qna_board "
						+ "ORDER BY qna_num DESC)a) WHERE rnum >= ? AND rnum <= ?";
				//PreparedStatement ��ü ����
				pstmt = conn.prepareStatement(sql);
				//?�� ������ ���ε�
				pstmt.setInt(1, startRow);
				pstmt.setInt(2, endRow);
				
			}else {
				//�˻� �� ����
				if(keyfield.equals("1")) sub_sql = "qna_title LIKE ?";
				else if(keyfield.equals("2")) sub_sql = "qna_id = ?";
				else if(keyfield.equals("3")) sub_sql = "qna_content LIKE ?";

				sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM (SELECT * FROM qna_board WHERE " + sub_sql
						+ " ORDER BY qna_num DESC)a) WHERE rnum >= ? AND rnum <= ?";
				pstmt = conn.prepareStatement(sql);
				if(keyfield.equals("2")) {
					pstmt.setString(1, keyword);
				}else {
					pstmt.setString(1, "%"+keyword+"%");
				}
				pstmt.setInt(2, startRow);
				pstmt.setInt(3, endRow);
				
			}
			
			
			//SQL���� �����ϰ� ������� ResultSet�� ����
			rs=pstmt.executeQuery();
			list = new ArrayList<QnaBoardVO>();
			while(rs.next()) {
				QnaBoardVO qnaboardVO = new QnaBoardVO();
				qnaboardVO.setQna_num(rs.getInt("qna_num"));
				qnaboardVO.setQna_title(rs.getString("qna_title"));
				qnaboardVO.setQna_passwd(rs.getString("qna_passwd"));
				qnaboardVO.setQna_id(rs.getString("qna_id"));
				qnaboardVO.setQna_date(rs.getDate("qna_date"));
				
				//�ڹٺ�(VO)�� ArrayList�� ����
				list.add(qnaboardVO);
			}
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			//�ڿ�����
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
		
	}
	
	
	/**
	 * @Method �޼ҵ��  : getQnaBoardDetail
	 * @�ۼ���     : 2021. 9. 9. 
	 * @�ۼ���     : ������
	 * @Method ���� : ������ �Խ��� ��������
	 */
	public QnaBoardVO getQnaBoardDetail(int qna_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		QnaBoardVO qnaboard = null;
		String sql =null;
		
		try {
			//Ŀ�ؼ�Ǯ�κ��� Ŀ�ؼ��� �Ҵ�
			conn = DBUtil.getConnection();
			
			//SQL�� �ۼ�
			sql = "SELECT * FROM qna_board WHERE qna_num=?";
			
			//PreparedStatement ��ü ����
			pstmt = conn.prepareStatement(sql);
			
			//?�� ������ ���ε�
			pstmt.setInt(1, qna_num);
			
			//SQL�� �����ؼ� ������� ResultSet�� ����
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				qnaboard = new QnaBoardVO();
				qnaboard.setQna_num(rs.getInt("qna_num"));
				qnaboard.setQna_title(rs.getString("qna_title"));
				qnaboard.setQna_content(rs.getString("qna_content"));
				qnaboard.setQna_date(rs.getDate("qna_date"));
				qnaboard.setQna_id(rs.getString("qna_id"));
				qnaboard.setQna_passwd(rs.getString("qna_passwd"));
			}
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			//�ڿ� ����
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return qnaboard;
	}
	
	
	
	
	//�� ����
	/**
	 * @Method �޼ҵ��  : qnaBoardModify
	 * @�ۼ���     : 2021. 9. 12. 
	 * @�ۼ���     : ������
	 * @Method ���� : ������ �Խ��� �� ����
	 */
	public void qnaBoardModify(QnaBoardVO qnaboardVO)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//Ŀ�ؼ�Ǯ�κ��� Ŀ�ؼ��� �Ҵ�
			conn = DBUtil.getConnection();
			
			//SQL�� �ۼ�
			sql = "UPDATE qna_board SET qna_title=?, qna_content=?, qna_ip=? WHERE qna_num=?";
			
			//PreparedStatement ��ü ����
			pstmt = conn.prepareStatement(sql);
			
			//?�� ������ ���ε�
			pstmt.setString(1, qnaboardVO.getQna_title());
			pstmt.setString(2, qnaboardVO.getQna_content());
			pstmt.setString(3, qnaboardVO.getQna_ip());
			pstmt.setInt(4, qnaboardVO.getQna_num());
			
			//SQL�� ����
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			//�ڿ�����
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	
	/**
	 * @Method �޼ҵ��  : qnaBoardDelete
	 * @�ۼ���     : 2021. 9. 12. 
	 * @�ۼ���     : ������
	 * @Method ���� :������ �Խ��� �� ����
	 */
	public void qnaBoardDelete(int qna_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		String sql = null;
		
		try {
			//Ŀ�ؼ�Ǯ�κ��� Ŀ�ؼ� �Ҵ����
			conn = DBUtil.getConnection();
			
			conn.setAutoCommit(false);
			
			// ���Ἲ ������������ ���� ���ǹ�ȣ�� ��ġ�� ��� ���̺��� �� ��ü ����
			
			sql = "DELETE FROM comments WHERE qna_num=?";
			
			//PreparedStatement ��ü ����
			pstmt = conn.prepareStatement(sql);
			
			//?�� ������ ���ε�
			pstmt.setInt(1, qna_num);
			
			//SQL�� ����
			pstmt.executeUpdate();
	
			// ������ ���� �Խñ� ����
			
			//SQL�� �ۼ�
			sql = "DELETE FROM qna_board WHERE qna_num=?";
			
			//PreparedStatement ��ü ����
			pstmt2 = conn.prepareStatement(sql);
			
			//?�� ������ ���ε�
			pstmt2.setInt(1, qna_num);
			
			//SQL�� ����
			pstmt2.executeUpdate();
			
			
			conn.commit();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			//�ڿ�����
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	/**
	 * @Method �޼ҵ��  : insertQnACommend
	 * @�ۼ���     : 2021. 9. 12. 
	 * @�ۼ���     : ������
	 * @Method ���� : ������ ��� �ޱ�
	 */
	public void insertQnACommend(RecipeCommendsVO qnaReply)throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = null;
		
		try {
			// Ŀ�ؼ� Ǯ�κ��� Ŀ�ؼ� �Ҵ�
			conn = DBUtil.getConnection();
			// SQL�� �ۼ�
			sql = "insert into comments (comm_num, comm_con, qna_num) "
					+ "values (comments_seq.nextval,?,?)";
			
			// PreparedStatement ��ü ����
			pstmt = conn.prepareStatement(sql);
			
			// ? �� ������ ���ε�
			pstmt.setString(1, qnaReply.getComm_con());
			pstmt.setInt(2, qnaReply.getQna_num());
			
			// SQL�� ����
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			// �ڿ�����
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	/**
	 * @Method �޼ҵ��  : getQnaReplyBoardCount
	 * @�ۼ���     : 2021. 9. 12. 
	 * @�ۼ���     : ������
	 * @Method ���� : ������ �Խ��� ��� ����
	 */
	public int getQnaReplyBoardCount(int qna_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;
		String sql = null;
		
		try {
			// Ŀ�ؼ� Ǯ�κ��� Ŀ�ؼ� �Ҵ�
			conn = DBUtil.getConnection();
			// SQL�� �ۼ�
			sql = "select count(*) as count from comments where qna_num = ?";
			
			// PreparedStatement ��ü ����
			pstmt = conn.prepareStatement(sql);
			
			// ? �� ������ ���ε�
			pstmt.setInt(1, qna_num);
			
			// SQL���� �����ؼ� ������� rs�� ��´�
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt("count");
			}
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			// �ڿ�����
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return count;
	}
	
	/**
	 * @Method �޼ҵ��  : getQnaListReplyBoard
	 * @�ۼ���     : 2021. 9. 12. 
	 * @�ۼ���     : ������
	 * @Method ���� : ������ �Խ��� ��� ���
	 */
	public List<RecipeCommendsVO> getQnaListReplyBoard(int start, int end, int qna_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<RecipeCommendsVO> list = null;
		String sql = null;
		
		try {
			// Ŀ�ؼ� Ǯ�κ��� Ŀ�ؼ� �Ҵ�
			conn = DBUtil.getConnection();
			// SQL�� �ۼ�
			sql = "SELECT * FROM (SELECT a.*, rownum rnum "
					+ "FROM (SELECT comm_num,TO_CHAR(comm_date,'YYYY-MM-DD HH24:MI:SS') comm_date,"
					+ " comm_con, qna_num FROM comments WHERE qna_num=? ORDER BY comm_num DESC)a) "
					+ "WHERE rnum >=? and rnum <=?";
			
			// PreparedStatement ��ü ����
			pstmt = conn.prepareStatement(sql);
			
			// ? �� ������ ���ε�
			pstmt.setInt(1, qna_num);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
			
			// SQL���� �����ؼ� �������� rs�� ��´�
			rs = pstmt.executeQuery();
			
			list = new ArrayList<RecipeCommendsVO>();
			
			while(rs.next()) {
				RecipeCommendsVO reply = new RecipeCommendsVO();
				reply.setComm_num(rs.getInt("comm_num"));
				// ��¥ -> 1����, 1�ð���, 1���� ����
				reply.setComm_date(DurationFromNow.getTimeDiffLabel(rs.getString("comm_date")));
				reply.setComm_con(StringUtil.useBrNoHtml(rs.getString("comm_con")));
				reply.setQna_num(rs.getInt("qna_num"));
				
				list.add(reply);
			}
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			// �ڿ�����
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;	
	}
	
	public void deleteQnaCommend(int comm_num)throws Exception{
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			
			// ��� ��ȣ�� ��ġ�ϴ� ��� ����
			// Ŀ�ؼ� Ǯ�κ��� Ŀ�ؼ� �Ҵ�
			conn = DBUtil.getConnection();
			
			// SQL�� �ۼ�
			sql = "delete from comments where comm_num = ?";
			
			// PreparedStatement ��ü ����
			pstmt = conn.prepareStatement(sql);
			
			// ? �� ������ ���ε�
			pstmt.setInt(1, comm_num);
			
			// SQL�� ����
			pstmt.executeUpdate();
			
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			// �ڿ�����
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
}
	
