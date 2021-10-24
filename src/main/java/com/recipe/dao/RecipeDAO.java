package com.recipe.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.news.dao.NewsDAO;
import com.recipe.vo.RecipeCommendsVO;
import com.recipe.vo.RecipeNewsVO;
import com.recipe.vo.RecipeVO;
import com.util.DBUtil;
import com.util.DurationFromNow;
import com.util.StringUtil;


public class RecipeDAO {
	// �̱��� ����
	private static RecipeDAO instance = new RecipeDAO();
	
	public static RecipeDAO getInstance() {
		return instance;
	}
	
	private RecipeDAO() {}
	
	
	/**
	 * @Method �޼ҵ��  : insertRecipe
	 * @�ۼ���     : 2021. 9. 7. 
	 * @�ۼ���     : ������
	 * @Method ���� : �۵��
	 */
	public void insertRecipe(RecipeVO recipe) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			// Ŀ�ؼ�Ǯ�κ��� Ŀ�ؼ� �Ҵ����
			conn = DBUtil.getConnection();
			// SQL�� �ۼ�
			sql = "INSERT INTO recipe_board (board_num,category,title,content,filename,ip,mem_num) VALUES (recipe_board_seq.nextval,?,?,?,?,?,?)";
						
			// PreparedStatement ��ü ����
			pstmt = conn.prepareStatement(sql);
			// ?�� ������ ���ε�
			pstmt.setString(1, recipe.getCategory());
			pstmt.setString(2, recipe.getTitle());
			pstmt.setString(3, recipe.getContent());
			pstmt.setString(4, recipe.getFilename());
			pstmt.setString(5, recipe.getIp());
			pstmt.setInt(6, recipe.getMem_num());
						
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
	 * @Method �޼ҵ��  : updateRecipe
	 * @�ۼ���     : 2021. 9. 7. 
	 * @�ۼ���     : ������
	 * @Method ���� : �� �����ϱ�
	 */
	
	public void updateRecipe(RecipeVO recipe) throws Exception {
		// ���� ���� : category, title, content, filename, ip, modify_date
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			// Ŀ�ؼ�Ǯ�κ��� Ŀ�ؼ� �Ҵ����
			conn = DBUtil.getConnection();
			
			if(recipe.getFilename() != null) { // ���ε��� ������ ���� ���
				// SQL�� �ۼ�
				sql = "UPDATE recipe_board SET category=?,title=?,content=?,filename=?,ip=?,modify_date=SYSDATE WHERE board_num=?";
				
				// PreparedStatement ��ü ����
				pstmt = conn.prepareStatement(sql);
				// ?�� ������ ���ε�
				pstmt.setString(1, recipe.getCategory());
				pstmt.setString(2, recipe.getTitle());
				pstmt.setString(3, recipe.getContent());
				pstmt.setString(4, recipe.getFilename());
				pstmt.setString(5, recipe.getIp());
				pstmt.setInt(6, recipe.getBoard_num());
			
			}else { // ���ε��� ������ ���� ���
				// SQL�� �ۼ�
				sql = "UPDATE recipe_board SET category=?,title=?,content=?,ip=?,modify_date=SYSDATE WHERE board_num=?";
				
				// PreparedStatement ��ü ����
				pstmt = conn.prepareStatement(sql);
				// ?�� ������ ���ε�
				pstmt.setString(1, recipe.getCategory());
				pstmt.setString(2, recipe.getTitle());
				pstmt.setString(3, recipe.getContent());
				pstmt.setString(4, recipe.getIp());
				pstmt.setInt(5, recipe.getBoard_num());
			}
			
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
	 * @Method �޼ҵ��  : recipeDelete
	 * @�ۼ���     : 2021. 9. 7. 
	 * @�ۼ���     : ������
	 * @Method ���� : �� �����ϱ�
	 */
	
	public void deleteRecipe(int board_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt1 = null;		// �۹�ȣ ��ġ�� ��� ��ü ����
		PreparedStatement pstmt2 = null;		// �۹�ȣ ��ġ�� ���ϱ� ��ü ����
		PreparedStatement pstmt3 = null;		// �۹�ȣ ��ġ�� ��õ ��ü ����
		PreparedStatement pstmt4 = null;		// �۹�ȣ ��ġ�� �Խñ� ����
		String sql = null;
		
		try {
			// Ŀ�ؼ�Ǯ�κ��� Ŀ�ؼ� �Ҵ����
			conn = DBUtil.getConnection();
			// ����Ŀ�� ����
			conn.setAutoCommit(false);
			
			// 1. ���Ἲ �������� �������� ���� ��� ���̺��� �۹�ȣ�� ��ġ�� ��� ��(���) ����
			// SQL�� �ۼ�
			sql = "DELETE FROM comments WHERE board_num=?";	
			// PreparedStatment ��ü ����
			pstmt1 = conn.prepareStatement(sql);
			// ?�� ������ ���ε�
			pstmt1.setInt(1, board_num);		
			// SQL�� ����
			pstmt1.executeUpdate();
			
			// 2. ���Ἲ �������� �������� ���� ��õ ���̺��� �۹�ȣ�� ��ġ�� ��� ��(��õ) ����
			// SQL�� �ۼ�
			sql = "DELETE FROM recommend WHERE board_num=?";					
			// PreparedStatment ��ü ����
			pstmt2 = conn.prepareStatement(sql);
			// ?�� ������ ���ε�
			pstmt2.setInt(1, board_num);				
			// SQL�� ����
			pstmt2.executeUpdate();			
		
			
			// 3. ���Ἲ �������� �������� ���� �ϸ�ũ ���̺��� �۹�ȣ�� ��ġ�� ��� ��(�ϸ�ũ) ����
			// SQL�� �ۼ�
			sql = "DELETE FROM bookmark WHERE board_num=?";				
			// PreparedStatment ��ü ����
			pstmt3 = conn.prepareStatement(sql);
			// ?�� ������ ���ε�
			pstmt3.setInt(1, board_num);				
			// SQL�� ����
			pstmt3.executeUpdate();
			
			// 4. ������ ���̺��� �۹�ȣ�� ��ġ�ϴ� (��)�� ����
			// SQL�� �ۼ�
			sql = "DELETE FROM recipe_board WHERE board_num=?";
			// PreparedStatment ��ü ����
			pstmt4 = conn.prepareStatement(sql);
			// ?�� ������ ���ε�
			pstmt4.setInt(1, board_num);
			// SQL�� ����
			pstmt4.executeUpdate();
			
			// ���� �߻� ���� ���������� SQL�� ����
			conn.commit();
			
		}catch(Exception e) {
			// ���� �߻�
			conn.rollback();
			throw new Exception(e);
		
		}finally {
			// �ڿ�����.
			DBUtil.executeClose(null, pstmt1, null);
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(null, pstmt3, null);
			DBUtil.executeClose(null, pstmt4, conn);
		}
	}
	
	
	//-------------------������ ������

	
	/**
	 * @Method �޼ҵ��  : getRecipeCount
	 * @�ۼ���     : 2021. 9. 7. 
	 * @�ۼ���     : ������
	 * @Method ���� : �ѷ��ڵ� �� ����
	 */
	
	public int getRecipeCount()throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;
		
		try {
			// Ŀ�ؼ� Ǯ�κ��� Ŀ�ؼ� �Ҵ�
			conn = DBUtil.getConnection();
			
			// sql�� �ۼ�
			sql = "select count(*) from recipe_board b join member m on b.mem_num = m.mem_num";
			
			// pstmt ��ü ����
			pstmt = conn.prepareStatement(sql);
			
			// rs�� ����� ���
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt(1);
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
	 * @Method �޼ҵ��  : getRecipeCount
	 * @�ۼ���     : 2021. 9. 7. 
	 * @�ۼ���     : ������
	 * @Method ���� : �� ���ڵ� �� ���� �޼ҵ� �����ε� (�Ű����� ī�װ�, �˻���)
	 */
	
	public int getRecipeCount(String division, String search)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;
		
		try {
			// Ŀ�ؼ� Ǯ�κ��� Ŀ�ؼ� �Ҵ�
			conn = DBUtil.getConnection();
			
			// �˻� ������ ������ ���
			if(division.equals("����")) {
				// sql�� �ۼ�
				sql = "select count(*) from recipe_board b join member m on b.mem_num = m.mem_num where title LIKE ?";
				
				// pstmt ��ü ����
				pstmt = conn.prepareStatement(sql);
				
				// ?�� ������ ���ε�
				pstmt.setString(1, "%" + search + "%");
			
				
				// �˻� ������ ������ ���
			}else if(division.equals("����")) {
				// sql�� �ۼ�
				sql = "select count(*) from recipe_board b join member m on b.mem_num = m.mem_num where content LIKE ?";
				
				// pstmt ��ü ����
				pstmt = conn.prepareStatement(sql);
				
				// ?�� ������ ���ε�
				pstmt.setString(1, "%" + search + "%");
			
				// �˻� ������ �ۼ����� ���
			}else {
				// sql�� �ۼ�
				sql = "select count(*) from recipe_board b join member m on b.mem_num = m.mem_num where id LIKE ?";
				
				// pstmt ��ü ����
				pstmt = conn.prepareStatement(sql);
				
				// ?�� ������ ���ε�
				pstmt.setString(1, "%" + search + "%");
			}
			
			// rs�� ����� ���
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt(1);
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
	 * @Method �޼ҵ��  : getTotalRecipeList
	 * @�ۼ���     : 2021. 9. 7. 
	 * @�ۼ���     : ������
	 * @Method ���� : ������ ��ü ����Ʈ ���� �޼ҵ� (�� ��ȣ �� û��)
	 */
	
	public List<RecipeVO> getTotalRecipeList(int start, int end)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		List<RecipeVO> list = null;
		int news_comments_count = 0;	// ��� ī��Ʈ ��
		
		try {
			// Ŀ�ؼ� Ǯ�κ��� Ŀ�ؼ� �Ҵ�
			conn = DBUtil.getConnection();
			
			// SQL���� �ۼ�
			sql = "select * from (select a.*, rownum rnum from "
					+ "(select * from recipe_board b join member m on b.mem_num = m.mem_num order by b.board_num desc) a) "
					+ "where rnum >=? and rnum <=?";
			
			// PreparedStatement ��ü ����
			pstmt = conn.prepareStatement(sql);
			
			// ? �� ������ ���ε�
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			
			// sql ���� �����ϰ� rs�� ����� ���
			rs = pstmt.executeQuery();
			
			list = new ArrayList<RecipeVO>();
			
			while(rs.next()) {
				RecipeVO recipe = new RecipeVO();
				
				recipe.setBoard_num(rs.getInt("board_num"));
				recipe.setTitle(StringUtil.useNoHtml(rs.getString("title")));
				recipe.setContent(StringUtil.useNoHtml(rs.getString("content")));
				recipe.setHits(rs.getInt("hits"));
				recipe.setRecom_count(rs.getInt("recom_count"));
				recipe.setReport_date(rs.getDate("report_date"));
				recipe.setModify_date(rs.getDate("modify_date"));
				recipe.setIp(rs.getString("ip"));
				recipe.setFilename(rs.getString("filename"));
				recipe.setCategory(rs.getString("category"));
				recipe.setMem_num(rs.getInt("mem_num"));
				recipe.setId(rs.getString("id"));
				
				news_comments_count = getRecipeReplyBoardCount(rs.getInt("board_num"));
				recipe.setNews_comments_count(news_comments_count); // ��� �� ���
				
				list.add(recipe);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			// �ڿ�����
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return list;
	}
	
	/**
	 * @Method �޼ҵ��  : getHitsTotalRecipeList
	 * @�ۼ���     : 2021. 9. 11. 
	 * @�ۼ���     : ������
	 * @Method ���� : ��ȸ�� ������ ��ü ����Ʈ ���� �޼ҵ� (1���� ��ȸ 2���� �� ��ȣ �� û��)
	 */
	
	public List<RecipeVO> getHitsTotalRecipeList(int start, int end)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		List<RecipeVO> list = null;
		
		int news_comments_count = 0;	// ��� ī��Ʈ ��
		
		try {
			// Ŀ�ؼ� Ǯ�κ��� Ŀ�ؼ� �Ҵ�
			conn = DBUtil.getConnection();
			
			// SQL���� �ۼ�
			sql = "select * from (select a.*, rownum rnum from "
					+ "(select * from recipe_board b join member m on b.mem_num = m.mem_num order by b.hits desc, b.board_num desc) a) "
					+ "where rnum >=? and rnum <=?";
			
			// PreparedStatement ��ü ����
			pstmt = conn.prepareStatement(sql);
			
			// ? �� ������ ���ε�
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			
			// sql ���� �����ϰ� rs�� ����� ���
			rs = pstmt.executeQuery();
			
			list = new ArrayList<RecipeVO>();
			
			while(rs.next()) {
				RecipeVO recipe = new RecipeVO();
				
				recipe.setBoard_num(rs.getInt("board_num"));
				recipe.setTitle(StringUtil.useNoHtml(rs.getString("title")));
				recipe.setContent(StringUtil.useNoHtml(rs.getString("content")));
				recipe.setHits(rs.getInt("hits"));
				recipe.setRecom_count(rs.getInt("recom_count"));
				recipe.setReport_date(rs.getDate("report_date"));
				recipe.setModify_date(rs.getDate("modify_date"));
				recipe.setIp(rs.getString("ip"));
				recipe.setFilename(rs.getString("filename"));
				recipe.setCategory(rs.getString("category"));
				recipe.setMem_num(rs.getInt("mem_num"));
				recipe.setId(rs.getString("id"));
				
				news_comments_count = getRecipeReplyBoardCount(rs.getInt("board_num"));
				recipe.setNews_comments_count(news_comments_count); // ��� �� ���
				
				list.add(recipe);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			// �ڿ�����
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return list;
	}
	
	
	/**
	 * @Method �޼ҵ��  : getRecommTotalRecipeList
	 * @�ۼ���     : 2021. 9. 11. 
	 * @�ۼ���     : ������
	 * @Method ���� : ��õ�� ������ ��ü ����Ʈ ���� �޼ҵ� (1���� ��õ�� 2���� �� ��ȣ �� û��)
	 */
	
	public List<RecipeVO> getRecommTotalRecipeList(int start, int end)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		List<RecipeVO> list = null;
		
		int news_comments_count = 0;	// ��� ī��Ʈ ��
		
		try {
			// Ŀ�ؼ� Ǯ�κ��� Ŀ�ؼ� �Ҵ�
			conn = DBUtil.getConnection();
			
			// SQL���� �ۼ�
			sql = "select * from (select a.*, rownum rnum from "
					+ "(select * from recipe_board b join member m on b.mem_num = m.mem_num order by b.recom_count desc, b.board_num desc) a) "
					+ "where rnum >=? and rnum <=?";
			
			// PreparedStatement ��ü ����
			pstmt = conn.prepareStatement(sql);
			
			// ? �� ������ ���ε�
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			
			// sql ���� �����ϰ� rs�� ����� ���
			rs = pstmt.executeQuery();
			
			list = new ArrayList<RecipeVO>();
			
			while(rs.next()) {
				RecipeVO recipe = new RecipeVO();
				
				recipe.setBoard_num(rs.getInt("board_num"));
				recipe.setTitle(StringUtil.useNoHtml(rs.getString("title")));
				recipe.setContent(StringUtil.useNoHtml(rs.getString("content")));
				recipe.setHits(rs.getInt("hits"));
				recipe.setRecom_count(rs.getInt("recom_count"));
				recipe.setReport_date(rs.getDate("report_date"));
				recipe.setModify_date(rs.getDate("modify_date"));
				recipe.setIp(rs.getString("ip"));
				recipe.setFilename(rs.getString("filename"));
				recipe.setCategory(rs.getString("category"));
				recipe.setMem_num(rs.getInt("mem_num"));
				recipe.setId(rs.getString("id"));
				
				news_comments_count = getRecipeReplyBoardCount(rs.getInt("board_num"));
				recipe.setNews_comments_count(news_comments_count); // ��� �� ���
				
				list.add(recipe);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			// �ڿ�����
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return list;
	}
	
	/**
	 * @Method �޼ҵ��  : getCommentsTotalRecipeList
	 * @�ۼ���     : 2021. 9. 11. 
	 * @�ۼ���     : ������
	 * @Method ���� : ��ۼ� ������ ��ü ����Ʈ ���� �޼ҵ� (1���� ��ۼ� 2���� �� ��ȣ �� û��)
	 */
	
	public List<RecipeVO> getCommentsTotalRecipeList(int start, int end)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		List<RecipeVO> list = null;
		
		try {
			// Ŀ�ؼ� Ǯ�κ��� Ŀ�ؼ� �Ҵ�
			conn = DBUtil.getConnection();
			
			// SQL���� �ۼ�
			sql = "select * from (select a.*, rownum rnum from "
					+ "(select * from recipe_board b left outer join "
					+ "(select  board_num, count(*) comm_cnt from comments group by board_num) "
					+ "using(board_num) join member "
					+ "using(mem_num) order by comm_cnt desc nulls last)a) "
					+ "where rnum >=? and rnum <=?";
			
			// PreparedStatement ��ü ����
			pstmt = conn.prepareStatement(sql);
			
			// ? �� ������ ���ε�
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			
			// sql ���� �����ϰ� rs�� ����� ���
			rs = pstmt.executeQuery();
			
			list = new ArrayList<RecipeVO>();
			
			while(rs.next()) {
				RecipeVO recipe = new RecipeVO();
				
				// VO�� �� ����
				recipe.setBoard_num(rs.getInt("board_num"));
				recipe.setTitle(StringUtil.useNoHtml(rs.getString("title")));
				recipe.setContent(StringUtil.useNoHtml(rs.getString("content")));
				recipe.setHits(rs.getInt("hits"));
				recipe.setRecom_count(rs.getInt("recom_count"));
				recipe.setReport_date(rs.getDate("report_date"));
				recipe.setModify_date(rs.getDate("modify_date"));
				recipe.setIp(rs.getString("ip"));
				recipe.setFilename(rs.getString("filename"));
				recipe.setCategory(rs.getString("category"));
				recipe.setMem_num(rs.getInt("mem_num"));
				recipe.setId(rs.getString("id"));
				recipe.setNews_comments_count(rs.getInt("comm_cnt"));
				
				list.add(recipe);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			// �ڿ�����
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		// ����Ʈ ��ۼ��� �������� ����
		return list;
	}
	
	/**
	 * @Method �޼ҵ��  : getSearchlRecipeList
	 * @�ۼ���     : 2021. 9. 7. 
	 * @�ۼ���     : ������
	 * @Method ���� : �˻����� ī�װ����� ��ġ�ϴ� ��ü �� ���� �޼ҵ�
	 */
	
	public List<RecipeVO> getSearchlRecipeList(int start, int end, String division, String search)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		List<RecipeVO> list = null;
		int news_comments_count = 0;	// ��� ī��Ʈ ��
		
		try {
			// Ŀ�ؼ� Ǯ�κ��� Ŀ�ؼ� �Ҵ�
			conn = DBUtil.getConnection();
			
				// ���ǽ���
			// SQL�� �ۼ�
			// �˻� ������ ������ ���
			if(division.equals("����")) {
				// SQL���� �ۼ�
				sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM "
						+ "(SELECT * FROM recipe_board b JOIN member m ON b.mem_num = m.mem_num WHERE title LIKE ? ORDER BY b.board_num desc) a) "
						+ "WHERE rnum >=? AND rnum <=?";
				
				// PreparedStatement ��ü ����
				pstmt = conn.prepareStatement(sql);
				
				// ? �� ������ ���ε�
				pstmt.setString(1, "%" + search + "%");
				pstmt.setInt(2, start);
				pstmt.setInt(3, end);
				
				
				
			// �˻������� ������ ���
			}else if(division.equals("����")){
				// SQL���� �ۼ�
				sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM "
						+ "(SELECT * FROM recipe_board b JOIN member m ON b.mem_num = m.mem_num WHERE content LIKE ? ORDER BY b.board_num desc) a) "
						+ "WHERE rnum >=? AND rnum <=?";
				
				// PreparedStatement ��ü ����
				pstmt = conn.prepareStatement(sql);
				
				// ? �� ������ ���ε�
				pstmt.setString(1, "%" + search + "%");
				pstmt.setInt(2, start);
				pstmt.setInt(3, end);
			
			// �˻� ������ �ۼ����� ���
			}else {
				// SQL���� �ۼ�
				sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM "
						+ "(SELECT * FROM recipe_board b JOIN member m ON b.mem_num = m.mem_num WHERE id LIKE ? ORDER BY b.board_num desc) a) "
						+ "WHERE rnum >=? AND rnum <=?";
				
				// PreparedStatement ��ü ����
				pstmt = conn.prepareStatement(sql);
				
				// ? �� ������ ���ε�
				pstmt.setString(1, "%" + search + "%");
				pstmt.setInt(2, start);
				pstmt.setInt(3, end);
			}
			// ���ǹ� ����
			
			// sql ���� �����ϰ� rs�� ����� ���
			rs = pstmt.executeQuery();
			
			list = new ArrayList<RecipeVO>();
			
			while(rs.next()) {
				RecipeVO recipe = new RecipeVO();
				recipe.setBoard_num(rs.getInt("board_num"));
				recipe.setTitle(StringUtil.useNoHtml(rs.getString("title")));
				recipe.setContent(StringUtil.useNoHtml(rs.getString("content")));
				recipe.setHits(rs.getInt("hits"));
				recipe.setRecom_count(rs.getInt("recom_count"));
				recipe.setReport_date(rs.getDate("report_date"));
				recipe.setModify_date(rs.getDate("modify_date"));
				recipe.setIp(rs.getString("ip"));
				recipe.setFilename(rs.getString("filename"));
				recipe.setCategory(rs.getString("category"));
				recipe.setMem_num(rs.getInt("mem_num"));
				recipe.setId(rs.getString("id"));
				
				news_comments_count = getRecipeReplyBoardCount(rs.getInt("board_num"));
				recipe.setNews_comments_count(news_comments_count);	// ��� �� ���
				
				list.add(recipe);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			// �ڿ�����
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return list;
	}
	
	
	/**
	 * @Method �޼ҵ��  : getRecipeBoard
	 * @�ۼ���     : 2021. 9. 8. 
	 * @�ۼ���     : ������
	 * @Method ���� : ������ �� ������ ��� �޼ҵ�
	 */
	
	public RecipeVO getRecipeBoard(int board_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		RecipeVO recipe = null;
		String sql = null;
		
		try {
			// Ŀ�ؼ� Ǯ�κ��� Ŀ�ؼ� �Ҵ�
			conn = DBUtil.getConnection();
			
			//SQL�� �ۼ�
			sql = "select * from recipe_board b join member m on b.mem_num = m.mem_num where b.board_num=?";
			
			// pstmt ��ü ����
			pstmt = conn.prepareStatement(sql);
			
			// ?�� ������ ���ε�
			pstmt.setInt(1, board_num);
			
			// SQL���� �����ؼ� ������� resultSet�� ����
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				recipe = new RecipeVO();
				recipe.setBoard_num(rs.getInt("board_num"));
				recipe.setTitle(rs.getString("title"));
				recipe.setContent(rs.getString("content"));
				recipe.setHits(rs.getInt("hits"));
				recipe.setRecom_count(rs.getInt("recom_count"));	// ��õ��
				recipe.setReport_date(rs.getDate("report_date"));
				recipe.setModify_date(rs.getDate("modify_date"));
				recipe.setFilename(rs.getString("filename"));
				recipe.setMem_num(rs.getInt("mem_num"));
				recipe.setIp(rs.getString("ip"));
				recipe.setCategory(rs.getString("category"));
				recipe.setId(rs.getString("id"));
				recipe.setAuth(rs.getInt("auth"));
				
				
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			// �ڿ� ����
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return recipe;
	}
	
	
	// ---------------------------- ��ȸ��
	
	/**
	 * @Method �޼ҵ��  : updateHitsCount
	 * @�ۼ���     : 2021. 9. 8. 
	 * @�ۼ���     : ������
	 * @Method ���� : ��ȸ�� ������Ʈ �޼ҵ�
	 */
	
	public void updateHitsCount(int board_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			// Ŀ�ؼ� Ǯ�κ��� Ŀ�ؼ� �Ҵ�
			conn = DBUtil.getConnection();
			
			//SQL�� �ۼ�
			sql = "UPDATE recipe_board set hits=hits+1 where board_num=?";
			
			// pstmt ��ü ����
			pstmt = conn.prepareStatement(sql);
			
			// ? �� ������ ���ε�
			pstmt.setInt(1, board_num);
			
			// SQL�� ����
			pstmt.executeUpdate();
					
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			// �ڿ� ����
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	// ----------------------��õ
	
	/**
	 * @Method �޼ҵ��  : recomDuplicationCheck
	 * @�ۼ���     : 2021. 9. 8. 
	 * @�ۼ���     : ������
	 * @Method ���� : ȸ���� ��õ�� �ߺ� üũ �޼ҵ�
	 */
	
	public int recomDuplicationCheck(int board_num, int mem_num)throws Exception{
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			// Ŀ�ؼ� Ǯ�κ��� Ŀ�ؼ� �Ҵ�
			conn = DBUtil.getConnection();
			
			//SQL�� �ۼ�
			sql = "select count(*) from recommend where board_num = ? and mem_num = ?";
			
			// pstmt ��ü ����
			pstmt = conn.prepareStatement(sql);
			
			// ? �� ������ ���ε�
			pstmt.setInt(1, board_num);
			pstmt.setInt(2, mem_num);
			
			// SQL�� ����
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt(1);
			}
					
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			// �ڿ� ����
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return result;
	}
	
	/**
	 * @Method �޼ҵ��  : recomDelete
	 * @�ۼ���     : 2021. 9. 8. 
	 * @�ۼ���     : ������
	 * @Method ���� :  ������ ��õ ���� �޼ҵ�
	 */
	
	public void recomDelete(int board_num, int mem_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		String sql = null;
		
		try {
			// Ŀ�ؼ� Ǯ�κ��� Ŀ�ؼ� �Ҵ�
			conn = DBUtil.getConnection();
			
			// SQL�� �ۼ�
			sql = "delete from recommend where board_num = ? and mem_num = ?";
			
			// pstmt ��ü ����
			pstmt = conn.prepareStatement(sql);
			
			// ? �� ������ ���ε�
			pstmt.setInt(1, board_num);
			pstmt.setInt(2, mem_num);
			
			// SQL�� ����
			pstmt.executeUpdate();
					
			// ��õ ���̺��� �����͸� �����ϸ� ������ ���̺��� ��õ���� -1 ��� �Ѵ�.
			
			//SQL�� �ۼ�
			sql = "update recipe_board set recom_count = recom_count-1 where board_num=?";
			
			// pstmt ��ü ����
			pstmt2 = conn.prepareStatement(sql);
						
			// ? �� ������ ���ε�
			pstmt2.setInt(1, board_num);
					
			// SQL�� ����
			pstmt2.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			// �ڿ� ����
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	/**
	 * @Method �޼ҵ��  : recomAdd
	 * @�ۼ���     : 2021. 9. 8. 
	 * @�ۼ���     : ������
	 * @Method ���� : �������� ��õ���� 1 ���������ָ鼭 ��õ���̺� �߰��ϴ� �޼ҵ�
	 */
	
	public void recomAdd(int board_num , int mem_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		String sql = null;
		
		try {
			// Ŀ�ؼ� Ǯ�κ��� Ŀ�ؼ� �Ҵ�
			conn = DBUtil.getConnection();
			
			//SQL�� �ۼ�
			sql = "update recipe_board set recom_count = recom_count+1 where board_num=?";
			
			// pstmt ��ü ����
			pstmt = conn.prepareStatement(sql);
			
			// ? �� ������ ���ε�
			pstmt.setInt(1, board_num);
			
			// SQL�� ����
			pstmt.executeUpdate();
					
			// ��õ ���̺��� �����͸� �����ϸ� ������ ���̺��� ��õ���� -1 ��� �Ѵ�.
			
			//SQL�� �ۼ�
			sql = "insert into recommend (board_num, mem_num) values (?,?)";
					
			// pstmt ��ü ����
			pstmt2 = conn.prepareStatement(sql);
									
			// ? �� ������ ���ε�
			pstmt2.setInt(1, board_num);
			pstmt2.setInt(2, mem_num);			
			
			// SQL�� ����
			pstmt2.executeUpdate();			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			// �ڿ� ����
			DBUtil.executeClose(null, pstmt, conn);
			DBUtil.executeClose(null, pstmt2, conn);
		}
	}
	
	/**
	 * @Method �޼ҵ��  : recomCount
	 * @�ۼ���     : 2021. 9. 8. 
	 * @�ۼ���     : ������
	 * @Method ���� : ���� �������� ��õ���� ��ȯ�ϴ� �޼ҵ�
	 */
	
	public int recomCount(int board_num)throws Exception {
		int count = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			// Ŀ�ؼ� Ǯ�κ��� Ŀ�ؼ� �Ҵ�
			conn = DBUtil.getConnection();
			
			//SQL�� �ۼ�
			sql = "select recom_count from recipe_board where board_num = ?";
			
			// pstmt ��ü ����
			pstmt = conn.prepareStatement(sql);
			
			// ? �� ������ ���ε�
			pstmt.setInt(1, board_num);
			
			// SQL�� ����
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt("recom_count");
			}
					
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			// �ڿ� ����
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return count;
	}
	
	// --------------------�ϸ�ũ
	
	/**
	 * @Method �޼ҵ��  : addBookmark
	 * @�ۼ���     : 2021. 9. 8. 
	 * @�ۼ���     : ������
	 * @Method ���� : �ϸ�ũ ���̺� �ϸ�ũ �߰� �޼ҵ�
	 */
	
	public void addBookmark(int board_num, int mem_num)throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
	
		try {
			// Ŀ�ؼ� Ǯ�κ��� Ŀ�ؼ� �Ҵ�
			conn = DBUtil.getConnection();
			
			//SQL�� �ۼ�
			sql = "insert into bookmark (book_num, board_num, mem_num) values (bookmark_seq.nextval,?,?)";
					
			// pstmt ��ü ����
			pstmt = conn.prepareStatement(sql);
									
			// ? �� ������ ���ε�
			pstmt.setInt(1, board_num);
			pstmt.setInt(2, mem_num);			
			
			// SQL�� ����
			pstmt.executeUpdate();			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			// �ڿ� ����
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	

	/**
	 * @Method �޼ҵ��  : deleteBookmark
	 * @�ۼ���     : 2021. 9. 8. 
	 * @�ۼ���     : ������
	 * @Method ���� : �ϸ�ũ ����
	 */
	
	public void deleteBookmark(int board_num, int mem_num)throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
	
		try {
			// Ŀ�ؼ� Ǯ�κ��� Ŀ�ؼ� �Ҵ�
			conn = DBUtil.getConnection();
			
			//SQL�� �ۼ�
			sql = "delete from bookmark where board_num = ? and mem_num = ?";
					
			// pstmt ��ü ����
			pstmt = conn.prepareStatement(sql);
									
			// ? �� ������ ���ε�
			pstmt.setInt(1, board_num);
			pstmt.setInt(2, mem_num);			
			
			// SQL�� ����
			pstmt.executeUpdate();			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			// �ڿ� ����
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	/**
	 * @Method �޼ҵ��  : bookmarkCheck
	 * @�ۼ���     : 2021. 9. 8. 
	 * @�ۼ���     : ������
	 * @Method ���� : ȸ���� �̹� �ϸ�ũ�� �ߴ��� üũ
	 */
	
	public int bookmarkCheck(int board_num, int mem_num)throws Exception{
		int check = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			// Ŀ�ؼ� Ǯ�κ��� Ŀ�ؼ� �Ҵ�
			conn = DBUtil.getConnection();
			
			//SQL�� �ۼ�
			sql = "select count(*) from bookmark where board_num = ? and mem_num = ?";
			
			// pstmt ��ü ����
			pstmt = conn.prepareStatement(sql);
			
			// ? �� ������ ���ε�
			pstmt.setInt(1, board_num);
			pstmt.setInt(2, mem_num);
			
			// SQL�� ����
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				check = rs.getInt(1);
			}
					
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			// �ڿ� ����
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return check;
	}
	
	
	
	// ------------ ��� �κ�
	
	/**
	 * @Method �޼ҵ��  : insertRecipeCommend
	 * @�ۼ���     : 2021. 9. 10. 
	 * @�ۼ���     : ������
	 * @Method ���� : ������̺� ���
	 */
	
	public void insertRecipeCommend(RecipeCommendsVO recipeReply)throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			// Ŀ�ؼ� Ǯ�κ��� Ŀ�ؼ� �Ҵ�
			conn = DBUtil.getConnection();
			// SQL�� �ۼ�
			sql = "insert into comments (comm_num, comm_con, mem_num, board_num) "
					+ "values (comments_seq.nextval,?,?,?)";
			
			// PreparedStatement ��ü ����
			pstmt = conn.prepareStatement(sql);
			
			// ? �� ������ ���ε�
			pstmt.setString(1, recipeReply.getComm_con());
			pstmt.setInt(2, recipeReply.getMem_num());
			pstmt.setInt(3, recipeReply.getBoard_num());
			
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
	 * @Method �޼ҵ��  : getRecipeReplyBoardCount
	 * @�ۼ���     : 2021. 9. 10. 
	 * @�ۼ���     : ������
	 * @Method ���� : ��� ���� ��ȯ �޼ҵ�
	 */
	
	public int getRecipeReplyBoardCount(int board_num)throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;
		String sql = null;
		
		try {
			// Ŀ�ؼ� Ǯ�κ��� Ŀ�ؼ� �Ҵ�
			conn = DBUtil.getConnection();
			// SQL�� �ۼ�
			sql = "select count(*) from comments where board_num = ?";
			
			// PreparedStatement ��ü ����
			pstmt = conn.prepareStatement(sql);
			
			// ? �� ������ ���ε�
			pstmt.setInt(1, board_num);
			
			// SQL���� �����ؼ� ������� rs�� ��´�
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt(1);
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
		 * @Method �޼ҵ��  : getRecipeListReplyBoard
		 * @�ۼ���     : 2021. 9. 10. 
		 * @�ۼ���     : ������
		 * @Method ���� : ��� ��� ����Ʈ ��ȯ �޼ҵ�
		 */
	
		public List<RecipeCommendsVO> getRecipeListReplyBoard(int start, int end, int board_num)throws Exception{
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
						+ "FROM (SELECT b.comm_num,TO_CHAR(b.comm_date,'YYYY-MM-DD HH24:MI:SS') comm_date, "
						+ "TO_CHAR(b.comm_modifydate,'YYYY-MM-DD HH24:MI:SS') comm_modifydate, "
						+ "b.comm_con, b.board_num, b.mem_num, m.id, md.photo "
						+ "FROM comments b JOIN member m ON b.mem_num=m.mem_num "
						+ "JOIN member_detail md ON md.mem_num = m.mem_num "
						+ "WHERE b.board_num=? ORDER BY b.comm_num DESC)a) "
						+ "WHERE rnum >=? and rnum <=?";
				
				// PreparedStatement ��ü ����
				pstmt = conn.prepareStatement(sql);
				
				// ? �� ������ ���ε�
				pstmt.setInt(1, board_num);
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
					reply.setComm_modifydate(DurationFromNow.getTimeDiffLabel(rs.getString("comm_modifydate")));
					reply.setComm_con(StringUtil.useBrNoHtml(rs.getString("comm_con")));
					reply.setBoard_num(rs.getInt("board_num"));
					reply.setMem_num(rs.getInt("mem_num"));
					reply.setId(rs.getString("id"));
					reply.setPhoto(rs.getString("photo"));
					
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
	
	/**
	 * @Method �޼ҵ��  : updateRecipeCommend
	 * @�ۼ���     : 2021. 9. 10. 
	 * @�ۼ���     : ������
	 * @Method ���� : ������ �Խ��� ��� ������Ʈ �޼ҵ�
	 */
		
	public void updateRecipeCommend(RecipeCommendsVO comm)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			// Ŀ�ؼ� Ǯ�κ��� Ŀ�ؼ� �Ҵ�
			conn = DBUtil.getConnection();
			// SQL�� �ۼ�
			sql = "UPDATE comments SET comm_con = ?, comm_modifydate = SYSDATE WHERE comm_num=?";
			
			// PreparedStatement ��ü ����
			pstmt = conn.prepareStatement(sql);
			
			// ? �� ������ ���ε�
			pstmt.setString(1, comm.getComm_con());
			pstmt.setInt(2, comm.getComm_num());
			
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
	 * @Method �޼ҵ��  : deleteRecipeCommend
	 * @�ۼ���     : 2021. 9. 10. 
	 * @�ۼ���     : ������
	 * @Method ���� : ������ �Խ��� ��� ���̺��� �ش� �� �����ϸ鼭 ������ ���̺� ��ۼ� -1   �޼ҵ�
	 */
	
	public void deleteRecipeCommend(int comm_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
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
			
			// ������ �Խ��� ��� �� ������Ʈ(-1)
			
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			// �ڿ�����
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	
	//---------------------------- ����� ������ �������� ó��
	
	/**
	 * @Method �޼ҵ��  : getRecipeNewsCount
	 * @�ۼ���     : 2021. 9. 12. 
	 * @�ۼ���     : ������
	 * @Method ���� : ������ �������� �� ���� �� ��ȯ
	 */
	public int getRecipeNewsCount() throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;
		
		try {
			conn = DBUtil.getConnection();
			
			sql="SELECT COUNT(*) FROM news_board WHERE news_category='������'";
			
			pstmt = conn.prepareStatement(sql);
			
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				count =rs.getInt(1);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return count;
	}
	
	/**
	 * @Method �޼ҵ��  : getRecipeNewsList
	 * @�ۼ���     : 2021. 9. 12. 
	 * @�ۼ���     : ������
	 * @Method ���� : ������ �������� ����Ʈ ��ȯ
	 */
	
	public List<RecipeNewsVO> getRecipeNewsList(int start, int end)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		List<RecipeNewsVO> list = null;
		NewsDAO dao = NewsDAO.getInstance();
		int news_comments_count = 0;
		
		try {
			conn = DBUtil.getConnection();
			sql= "SELECT * FROM (SELECT a.*,rownum rnum FROM "
					+ "(SELECT * FROM news_board WHERE news_category='������' ORDER BY news_num DESC)a) "
					+ "WHERE rnum>=? AND rnum<=?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
		
			rs=pstmt.executeQuery();
			list = new ArrayList<RecipeNewsVO>();
			while(rs.next()) {
				news_comments_count = dao.getCommentsCount(rs.getInt("news_num"));
				
				RecipeNewsVO recipe_News = new RecipeNewsVO();
				recipe_News.setNews_num(rs.getInt("news_num"));
				recipe_News.setNews_title(rs.getString("news_title"));
				recipe_News.setNews_category(rs.getString("news_category"));
				recipe_News.setNews_content(rs.getString("news_content"));
				recipe_News.setNews_date(rs.getDate("news_date"));
				recipe_News.setNews_modi(rs.getDate("news_modi"));
				recipe_News.setNews_hits(rs.getInt("news_hits"));
				recipe_News.setWriter("������");
				recipe_News.setNews_comment_count(news_comments_count);
				
				list.add(recipe_News);
				
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {		
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list; 
	}
}
