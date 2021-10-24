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
	// 싱글턴 패턴
	private static RecipeDAO instance = new RecipeDAO();
	
	public static RecipeDAO getInstance() {
		return instance;
	}
	
	private RecipeDAO() {}
	
	
	/**
	 * @Method 메소드명  : insertRecipe
	 * @작성일     : 2021. 9. 7. 
	 * @작성자     : 이현지
	 * @Method 설명 : 글등록
	 */
	public void insertRecipe(RecipeVO recipe) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			// 커넥션풀로부터 커넥션 할당받음
			conn = DBUtil.getConnection();
			// SQL문 작성
			sql = "INSERT INTO recipe_board (board_num,category,title,content,filename,ip,mem_num) VALUES (recipe_board_seq.nextval,?,?,?,?,?,?)";
						
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// ?에 데이터 바인딩
			pstmt.setString(1, recipe.getCategory());
			pstmt.setString(2, recipe.getTitle());
			pstmt.setString(3, recipe.getContent());
			pstmt.setString(4, recipe.getFilename());
			pstmt.setString(5, recipe.getIp());
			pstmt.setInt(6, recipe.getMem_num());
						
			// SQL문 실행
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		
		}finally {
			// 자원정리
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	/**
	 * @Method 메소드명  : updateRecipe
	 * @작성일     : 2021. 9. 7. 
	 * @작성자     : 이현지
	 * @Method 설명 : 글 수정하기
	 */
	
	public void updateRecipe(RecipeVO recipe) throws Exception {
		// 수정 가능 : category, title, content, filename, ip, modify_date
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			// 커넥션풀로부터 커넥션 할당받음
			conn = DBUtil.getConnection();
			
			if(recipe.getFilename() != null) { // 업로드한 파일이 있을 경우
				// SQL문 작성
				sql = "UPDATE recipe_board SET category=?,title=?,content=?,filename=?,ip=?,modify_date=SYSDATE WHERE board_num=?";
				
				// PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				// ?에 데이터 바인딩
				pstmt.setString(1, recipe.getCategory());
				pstmt.setString(2, recipe.getTitle());
				pstmt.setString(3, recipe.getContent());
				pstmt.setString(4, recipe.getFilename());
				pstmt.setString(5, recipe.getIp());
				pstmt.setInt(6, recipe.getBoard_num());
			
			}else { // 업로드한 파일이 없을 경우
				// SQL문 작성
				sql = "UPDATE recipe_board SET category=?,title=?,content=?,ip=?,modify_date=SYSDATE WHERE board_num=?";
				
				// PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				// ?에 데이터 바인딩
				pstmt.setString(1, recipe.getCategory());
				pstmt.setString(2, recipe.getTitle());
				pstmt.setString(3, recipe.getContent());
				pstmt.setString(4, recipe.getIp());
				pstmt.setInt(5, recipe.getBoard_num());
			}
			
			// SQL문 실행
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		
		}finally {
			// 자원정리
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	
	/**
	 * @Method 메소드명  : recipeDelete
	 * @작성일     : 2021. 9. 7. 
	 * @작성자     : 이현지
	 * @Method 설명 : 글 삭제하기
	 */
	
	public void deleteRecipe(int board_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt1 = null;		// 글번호 일치한 댓글 전체 삭제
		PreparedStatement pstmt2 = null;		// 글번호 일치한 찜하기 전체 삭제
		PreparedStatement pstmt3 = null;		// 글번호 일치한 추천 전체 삭제
		PreparedStatement pstmt4 = null;		// 글번호 일치한 게시글 삭제
		String sql = null;
		
		try {
			// 커넥션풀로부터 커넥션 할당받음
			conn = DBUtil.getConnection();
			// 오토커밋 해제
			conn.setAutoCommit(false);
			
			// 1. 무결성 제약조건 위반으로 인해 댓글 테이블에서 글번호가 일치한 모든 행(댓글) 삭제
			// SQL문 작성
			sql = "DELETE FROM comments WHERE board_num=?";	
			// PreparedStatment 객체 생성
			pstmt1 = conn.prepareStatement(sql);
			// ?에 데이터 바인딩
			pstmt1.setInt(1, board_num);		
			// SQL문 실행
			pstmt1.executeUpdate();
			
			// 2. 무결성 제약조건 위반으로 인해 추천 테이블에서 글번호가 일치한 모든 행(추천) 삭제
			// SQL문 작성
			sql = "DELETE FROM recommend WHERE board_num=?";					
			// PreparedStatment 객체 생성
			pstmt2 = conn.prepareStatement(sql);
			// ?에 데이터 바인딩
			pstmt2.setInt(1, board_num);				
			// SQL문 실행
			pstmt2.executeUpdate();			
		
			
			// 3. 무결성 제약조건 위반으로 인해 북마크 테이블에서 글번호가 일치한 모든 행(북마크) 삭제
			// SQL문 작성
			sql = "DELETE FROM bookmark WHERE board_num=?";				
			// PreparedStatment 객체 생성
			pstmt3 = conn.prepareStatement(sql);
			// ?에 데이터 바인딩
			pstmt3.setInt(1, board_num);				
			// SQL문 실행
			pstmt3.executeUpdate();
			
			// 4. 레시피 테이블에서 글번호가 일치하는 (글)행 삭제
			// SQL문 작성
			sql = "DELETE FROM recipe_board WHERE board_num=?";
			// PreparedStatment 객체 생성
			pstmt4 = conn.prepareStatement(sql);
			// ?에 데이터 바인딩
			pstmt4.setInt(1, board_num);
			// SQL문 실행
			pstmt4.executeUpdate();
			
			// 예외 발생 없이 정상적으로 SQL문 실행
			conn.commit();
			
		}catch(Exception e) {
			// 예외 발생
			conn.rollback();
			throw new Exception(e);
		
		}finally {
			// 자원정리.
			DBUtil.executeClose(null, pstmt1, null);
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(null, pstmt3, null);
			DBUtil.executeClose(null, pstmt4, conn);
		}
	}
	
	
	//-------------------레시피 디테일

	
	/**
	 * @Method 메소드명  : getRecipeCount
	 * @작성일     : 2021. 9. 7. 
	 * @작성자     : 오상준
	 * @Method 설명 : 총레코드 수 추출
	 */
	
	public int getRecipeCount()throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;
		
		try {
			// 커넥션 풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			
			// sql문 작성
			sql = "select count(*) from recipe_board b join member m on b.mem_num = m.mem_num";
			
			// pstmt 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// rs에 결과행 담기
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt(1);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			// 자원정리
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return count;
		
		
	}
	
	
	/**
	 * @Method 메소드명  : getRecipeCount
	 * @작성일     : 2021. 9. 7. 
	 * @작성자     : 오상준
	 * @Method 설명 : 총 레코드 수 추출 메소드 오버로딩 (매개변수 카테고리, 검색값)
	 */
	
	public int getRecipeCount(String division, String search)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;
		
		try {
			// 커넥션 풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			
			// 검색 조건이 제목인 경우
			if(division.equals("제목")) {
				// sql문 작성
				sql = "select count(*) from recipe_board b join member m on b.mem_num = m.mem_num where title LIKE ?";
				
				// pstmt 객체 생성
				pstmt = conn.prepareStatement(sql);
				
				// ?에 데이터 바인딩
				pstmt.setString(1, "%" + search + "%");
			
				
				// 검색 조건이 내용인 경우
			}else if(division.equals("내용")) {
				// sql문 작성
				sql = "select count(*) from recipe_board b join member m on b.mem_num = m.mem_num where content LIKE ?";
				
				// pstmt 객체 생성
				pstmt = conn.prepareStatement(sql);
				
				// ?에 데이터 바인딩
				pstmt.setString(1, "%" + search + "%");
			
				// 검색 조건이 작성자인 경우
			}else {
				// sql문 작성
				sql = "select count(*) from recipe_board b join member m on b.mem_num = m.mem_num where id LIKE ?";
				
				// pstmt 객체 생성
				pstmt = conn.prepareStatement(sql);
				
				// ?에 데이터 바인딩
				pstmt.setString(1, "%" + search + "%");
			}
			
			// rs에 결과행 담기
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt(1);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			// 자원정리
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return count;
		
		
	}
	
	
	
	/**
	 * @Method 메소드명  : getTotalRecipeList
	 * @작성일     : 2021. 9. 7. 
	 * @작성자     : 오상준
	 * @Method 설명 : 레시피 전체 리스트 추출 메소드 (글 번호 순 청렬)
	 */
	
	public List<RecipeVO> getTotalRecipeList(int start, int end)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		List<RecipeVO> list = null;
		int news_comments_count = 0;	// 댓글 카운트 수
		
		try {
			// 커넥션 풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			
			// SQL문장 작성
			sql = "select * from (select a.*, rownum rnum from "
					+ "(select * from recipe_board b join member m on b.mem_num = m.mem_num order by b.board_num desc) a) "
					+ "where rnum >=? and rnum <=?";
			
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// ? 에 데이터 바인딩
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			
			// sql 문을 실행하고 rs에 결과행 담기
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
				recipe.setNews_comments_count(news_comments_count); // 댓글 수 담기
				
				list.add(recipe);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			// 자원정리
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return list;
	}
	
	/**
	 * @Method 메소드명  : getHitsTotalRecipeList
	 * @작성일     : 2021. 9. 11. 
	 * @작성자     : 오상준
	 * @Method 설명 : 조회순 레시피 전체 리스트 추출 메소드 (1순위 조회 2순위 글 번호 순 청렬)
	 */
	
	public List<RecipeVO> getHitsTotalRecipeList(int start, int end)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		List<RecipeVO> list = null;
		
		int news_comments_count = 0;	// 댓글 카운트 수
		
		try {
			// 커넥션 풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			
			// SQL문장 작성
			sql = "select * from (select a.*, rownum rnum from "
					+ "(select * from recipe_board b join member m on b.mem_num = m.mem_num order by b.hits desc, b.board_num desc) a) "
					+ "where rnum >=? and rnum <=?";
			
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// ? 에 데이터 바인딩
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			
			// sql 문을 실행하고 rs에 결과행 담기
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
				recipe.setNews_comments_count(news_comments_count); // 댓글 수 담기
				
				list.add(recipe);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			// 자원정리
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return list;
	}
	
	
	/**
	 * @Method 메소드명  : getRecommTotalRecipeList
	 * @작성일     : 2021. 9. 11. 
	 * @작성자     : 오상준
	 * @Method 설명 : 추천순 레시피 전체 리스트 추출 메소드 (1순위 추천수 2순위 글 번호 순 청렬)
	 */
	
	public List<RecipeVO> getRecommTotalRecipeList(int start, int end)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		List<RecipeVO> list = null;
		
		int news_comments_count = 0;	// 댓글 카운트 수
		
		try {
			// 커넥션 풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			
			// SQL문장 작성
			sql = "select * from (select a.*, rownum rnum from "
					+ "(select * from recipe_board b join member m on b.mem_num = m.mem_num order by b.recom_count desc, b.board_num desc) a) "
					+ "where rnum >=? and rnum <=?";
			
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// ? 에 데이터 바인딩
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			
			// sql 문을 실행하고 rs에 결과행 담기
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
				recipe.setNews_comments_count(news_comments_count); // 댓글 수 담기
				
				list.add(recipe);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			// 자원정리
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return list;
	}
	
	/**
	 * @Method 메소드명  : getCommentsTotalRecipeList
	 * @작성일     : 2021. 9. 11. 
	 * @작성자     : 오상준
	 * @Method 설명 : 댓글순 레시피 전체 리스트 추출 메소드 (1순위 댓글수 2순위 글 번호 순 청렬)
	 */
	
	public List<RecipeVO> getCommentsTotalRecipeList(int start, int end)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		List<RecipeVO> list = null;
		
		try {
			// 커넥션 풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			
			// SQL문장 작성
			sql = "select * from (select a.*, rownum rnum from "
					+ "(select * from recipe_board b left outer join "
					+ "(select  board_num, count(*) comm_cnt from comments group by board_num) "
					+ "using(board_num) join member "
					+ "using(mem_num) order by comm_cnt desc nulls last)a) "
					+ "where rnum >=? and rnum <=?";
			
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// ? 에 데이터 바인딩
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			
			// sql 문을 실행하고 rs에 결과행 담기
			rs = pstmt.executeQuery();
			
			list = new ArrayList<RecipeVO>();
			
			while(rs.next()) {
				RecipeVO recipe = new RecipeVO();
				
				// VO에 값 삽입
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
			// 자원정리
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		// 리스트 댓글수별 내림차순 정렬
		return list;
	}
	
	/**
	 * @Method 메소드명  : getSearchlRecipeList
	 * @작성일     : 2021. 9. 7. 
	 * @작성자     : 오상준
	 * @Method 설명 : 검색값과 카테고리명이 일치하는 전체 값 추출 메소드
	 */
	
	public List<RecipeVO> getSearchlRecipeList(int start, int end, String division, String search)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		List<RecipeVO> list = null;
		int news_comments_count = 0;	// 댓글 카운트 수
		
		try {
			// 커넥션 풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			
				// 조건시작
			// SQL문 작성
			// 검색 조건이 제목인 경우
			if(division.equals("제목")) {
				// SQL문장 작성
				sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM "
						+ "(SELECT * FROM recipe_board b JOIN member m ON b.mem_num = m.mem_num WHERE title LIKE ? ORDER BY b.board_num desc) a) "
						+ "WHERE rnum >=? AND rnum <=?";
				
				// PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				
				// ? 에 데이터 바인딩
				pstmt.setString(1, "%" + search + "%");
				pstmt.setInt(2, start);
				pstmt.setInt(3, end);
				
				
				
			// 검색조건이 내용인 경우
			}else if(division.equals("내용")){
				// SQL문장 작성
				sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM "
						+ "(SELECT * FROM recipe_board b JOIN member m ON b.mem_num = m.mem_num WHERE content LIKE ? ORDER BY b.board_num desc) a) "
						+ "WHERE rnum >=? AND rnum <=?";
				
				// PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				
				// ? 에 데이터 바인딩
				pstmt.setString(1, "%" + search + "%");
				pstmt.setInt(2, start);
				pstmt.setInt(3, end);
			
			// 검색 조건이 작성자인 경우
			}else {
				// SQL문장 작성
				sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM "
						+ "(SELECT * FROM recipe_board b JOIN member m ON b.mem_num = m.mem_num WHERE id LIKE ? ORDER BY b.board_num desc) a) "
						+ "WHERE rnum >=? AND rnum <=?";
				
				// PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				
				// ? 에 데이터 바인딩
				pstmt.setString(1, "%" + search + "%");
				pstmt.setInt(2, start);
				pstmt.setInt(3, end);
			}
			// 조건문 종료
			
			// sql 문을 실행하고 rs에 결과행 담기
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
				recipe.setNews_comments_count(news_comments_count);	// 댓글 수 담기
				
				list.add(recipe);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			// 자원정리
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return list;
	}
	
	
	/**
	 * @Method 메소드명  : getRecipeBoard
	 * @작성일     : 2021. 9. 8. 
	 * @작성자     : 오상준
	 * @Method 설명 : 레시피 상세 페이지 출력 메소드
	 */
	
	public RecipeVO getRecipeBoard(int board_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		RecipeVO recipe = null;
		String sql = null;
		
		try {
			// 커넥션 풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			
			//SQL문 작성
			sql = "select * from recipe_board b join member m on b.mem_num = m.mem_num where b.board_num=?";
			
			// pstmt 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// ?에 데이터 바인딩
			pstmt.setInt(1, board_num);
			
			// SQL문을 실행해서 결과행을 resultSet에 담음
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				recipe = new RecipeVO();
				recipe.setBoard_num(rs.getInt("board_num"));
				recipe.setTitle(rs.getString("title"));
				recipe.setContent(rs.getString("content"));
				recipe.setHits(rs.getInt("hits"));
				recipe.setRecom_count(rs.getInt("recom_count"));	// 추천수
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
			// 자원 정리
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return recipe;
	}
	
	
	// ---------------------------- 조회수
	
	/**
	 * @Method 메소드명  : updateHitsCount
	 * @작성일     : 2021. 9. 8. 
	 * @작성자     : 오상준
	 * @Method 설명 : 조회수 업데이트 메소드
	 */
	
	public void updateHitsCount(int board_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			// 커넥션 풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			
			//SQL문 작성
			sql = "UPDATE recipe_board set hits=hits+1 where board_num=?";
			
			// pstmt 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// ? 에 데이터 바인딩
			pstmt.setInt(1, board_num);
			
			// SQL문 실행
			pstmt.executeUpdate();
					
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			// 자원 정리
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	// ----------------------추천
	
	/**
	 * @Method 메소드명  : recomDuplicationCheck
	 * @작성일     : 2021. 9. 8. 
	 * @작성자     : 오상준
	 * @Method 설명 : 회원의 추천수 중복 체크 메소드
	 */
	
	public int recomDuplicationCheck(int board_num, int mem_num)throws Exception{
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			// 커넥션 풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			
			//SQL문 작성
			sql = "select count(*) from recommend where board_num = ? and mem_num = ?";
			
			// pstmt 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// ? 에 데이터 바인딩
			pstmt.setInt(1, board_num);
			pstmt.setInt(2, mem_num);
			
			// SQL문 실행
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt(1);
			}
					
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			// 자원 정리
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return result;
	}
	
	/**
	 * @Method 메소드명  : recomDelete
	 * @작성일     : 2021. 9. 8. 
	 * @작성자     : 오상준
	 * @Method 설명 :  레시피 추천 삭제 메소드
	 */
	
	public void recomDelete(int board_num, int mem_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		String sql = null;
		
		try {
			// 커넥션 풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			
			// SQL문 작성
			sql = "delete from recommend where board_num = ? and mem_num = ?";
			
			// pstmt 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// ? 에 데이터 바인딩
			pstmt.setInt(1, board_num);
			pstmt.setInt(2, mem_num);
			
			// SQL문 실행
			pstmt.executeUpdate();
					
			// 추천 테이블의 데이터를 삭제하면 레시피 테이블의 추천수도 -1 헤야 한다.
			
			//SQL문 작성
			sql = "update recipe_board set recom_count = recom_count-1 where board_num=?";
			
			// pstmt 객체 생성
			pstmt2 = conn.prepareStatement(sql);
						
			// ? 에 데이터 바인딩
			pstmt2.setInt(1, board_num);
					
			// SQL문 실행
			pstmt2.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			// 자원 정리
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	/**
	 * @Method 메소드명  : recomAdd
	 * @작성일     : 2021. 9. 8. 
	 * @작성자     : 오상준
	 * @Method 설명 : 레시피의 추천수를 1 증가시켜주면서 추천테이블에 추가하는 메소드
	 */
	
	public void recomAdd(int board_num , int mem_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		String sql = null;
		
		try {
			// 커넥션 풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			
			//SQL문 작성
			sql = "update recipe_board set recom_count = recom_count+1 where board_num=?";
			
			// pstmt 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// ? 에 데이터 바인딩
			pstmt.setInt(1, board_num);
			
			// SQL문 실행
			pstmt.executeUpdate();
					
			// 추천 테이블의 데이터를 삭제하면 레시피 테이블의 추천수도 -1 헤야 한다.
			
			//SQL문 작성
			sql = "insert into recommend (board_num, mem_num) values (?,?)";
					
			// pstmt 객체 생성
			pstmt2 = conn.prepareStatement(sql);
									
			// ? 에 데이터 바인딩
			pstmt2.setInt(1, board_num);
			pstmt2.setInt(2, mem_num);			
			
			// SQL문 실행
			pstmt2.executeUpdate();			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			// 자원 정리
			DBUtil.executeClose(null, pstmt, conn);
			DBUtil.executeClose(null, pstmt2, conn);
		}
	}
	
	/**
	 * @Method 메소드명  : recomCount
	 * @작성일     : 2021. 9. 8. 
	 * @작성자     : 오상준
	 * @Method 설명 : 현재 레시피의 추천수를 반환하는 메소드
	 */
	
	public int recomCount(int board_num)throws Exception {
		int count = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			// 커넥션 풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			
			//SQL문 작성
			sql = "select recom_count from recipe_board where board_num = ?";
			
			// pstmt 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// ? 에 데이터 바인딩
			pstmt.setInt(1, board_num);
			
			// SQL문 실행
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt("recom_count");
			}
					
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			// 자원 정리
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return count;
	}
	
	// --------------------북마크
	
	/**
	 * @Method 메소드명  : addBookmark
	 * @작성일     : 2021. 9. 8. 
	 * @작성자     : 오상준
	 * @Method 설명 : 북마크 테이블에 북마크 추가 메소드
	 */
	
	public void addBookmark(int board_num, int mem_num)throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
	
		try {
			// 커넥션 풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			
			//SQL문 작성
			sql = "insert into bookmark (book_num, board_num, mem_num) values (bookmark_seq.nextval,?,?)";
					
			// pstmt 객체 생성
			pstmt = conn.prepareStatement(sql);
									
			// ? 에 데이터 바인딩
			pstmt.setInt(1, board_num);
			pstmt.setInt(2, mem_num);			
			
			// SQL문 실행
			pstmt.executeUpdate();			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			// 자원 정리
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	

	/**
	 * @Method 메소드명  : deleteBookmark
	 * @작성일     : 2021. 9. 8. 
	 * @작성자     : 오상준
	 * @Method 설명 : 북마크 삭제
	 */
	
	public void deleteBookmark(int board_num, int mem_num)throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
	
		try {
			// 커넥션 풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			
			//SQL문 작성
			sql = "delete from bookmark where board_num = ? and mem_num = ?";
					
			// pstmt 객체 생성
			pstmt = conn.prepareStatement(sql);
									
			// ? 에 데이터 바인딩
			pstmt.setInt(1, board_num);
			pstmt.setInt(2, mem_num);			
			
			// SQL문 실행
			pstmt.executeUpdate();			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			// 자원 정리
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	/**
	 * @Method 메소드명  : bookmarkCheck
	 * @작성일     : 2021. 9. 8. 
	 * @작성자     : 오상준
	 * @Method 설명 : 회원이 이미 북마크를 했는지 체크
	 */
	
	public int bookmarkCheck(int board_num, int mem_num)throws Exception{
		int check = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			// 커넥션 풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			
			//SQL문 작성
			sql = "select count(*) from bookmark where board_num = ? and mem_num = ?";
			
			// pstmt 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// ? 에 데이터 바인딩
			pstmt.setInt(1, board_num);
			pstmt.setInt(2, mem_num);
			
			// SQL문 실행
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				check = rs.getInt(1);
			}
					
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			// 자원 정리
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return check;
	}
	
	
	
	// ------------ 댓글 부분
	
	/**
	 * @Method 메소드명  : insertRecipeCommend
	 * @작성일     : 2021. 9. 10. 
	 * @작성자     : 오상준
	 * @Method 설명 : 댓글테이블에 등록
	 */
	
	public void insertRecipeCommend(RecipeCommendsVO recipeReply)throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			// 커넥션 풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			// SQL문 작성
			sql = "insert into comments (comm_num, comm_con, mem_num, board_num) "
					+ "values (comments_seq.nextval,?,?,?)";
			
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// ? 에 데이터 바인딩
			pstmt.setString(1, recipeReply.getComm_con());
			pstmt.setInt(2, recipeReply.getMem_num());
			pstmt.setInt(3, recipeReply.getBoard_num());
			
			// SQL문 실행
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			// 자원정리
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	
	
	/**
	 * @Method 메소드명  : getRecipeReplyBoardCount
	 * @작성일     : 2021. 9. 10. 
	 * @작성자     : 오상준
	 * @Method 설명 : 댓글 갯수 반환 메소드
	 */
	
	public int getRecipeReplyBoardCount(int board_num)throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;
		String sql = null;
		
		try {
			// 커넥션 풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			// SQL문 작성
			sql = "select count(*) from comments where board_num = ?";
			
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// ? 에 데이터 바인딩
			pstmt.setInt(1, board_num);
			
			// SQL문을 실행해서 결과행을 rs에 담는다
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt(1);
			}
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			// 자원정리
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return count;
	}
	
	
		/**
		 * @Method 메소드명  : getRecipeListReplyBoard
		 * @작성일     : 2021. 9. 10. 
		 * @작성자     : 오상준
		 * @Method 설명 : 댓글 목록 리스트 반환 메소드
		 */
	
		public List<RecipeCommendsVO> getRecipeListReplyBoard(int start, int end, int board_num)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<RecipeCommendsVO> list = null;
			String sql = null;
			
			try {
				// 커넥션 풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				// SQL문 작성
				sql = "SELECT * FROM (SELECT a.*, rownum rnum "
						+ "FROM (SELECT b.comm_num,TO_CHAR(b.comm_date,'YYYY-MM-DD HH24:MI:SS') comm_date, "
						+ "TO_CHAR(b.comm_modifydate,'YYYY-MM-DD HH24:MI:SS') comm_modifydate, "
						+ "b.comm_con, b.board_num, b.mem_num, m.id, md.photo "
						+ "FROM comments b JOIN member m ON b.mem_num=m.mem_num "
						+ "JOIN member_detail md ON md.mem_num = m.mem_num "
						+ "WHERE b.board_num=? ORDER BY b.comm_num DESC)a) "
						+ "WHERE rnum >=? and rnum <=?";
				
				// PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				
				// ? 에 데이터 바인딩
				pstmt.setInt(1, board_num);
				pstmt.setInt(2, start);
				pstmt.setInt(3, end);
				
				// SQL문을 실행해서 결과행들을 rs에 담는다
				rs = pstmt.executeQuery();
				
				list = new ArrayList<RecipeCommendsVO>();
				
				while(rs.next()) {
					RecipeCommendsVO reply = new RecipeCommendsVO();
					reply.setComm_num(rs.getInt("comm_num"));
					// 날짜 -> 1분전, 1시간전, 1일전 형식
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
				// 자원정리
				DBUtil.executeClose(rs, pstmt, conn);
			}
			return list;
			
			
		}
	
	/**
	 * @Method 메소드명  : updateRecipeCommend
	 * @작성일     : 2021. 9. 10. 
	 * @작성자     : 오상준
	 * @Method 설명 : 레시피 게시판 댓글 업데이트 메소드
	 */
		
	public void updateRecipeCommend(RecipeCommendsVO comm)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			// 커넥션 풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			// SQL문 작성
			sql = "UPDATE comments SET comm_con = ?, comm_modifydate = SYSDATE WHERE comm_num=?";
			
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// ? 에 데이터 바인딩
			pstmt.setString(1, comm.getComm_con());
			pstmt.setInt(2, comm.getComm_num());
			
			// SQL문 실행
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			// 자원정리
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
		
	
	/**
	 * @Method 메소드명  : deleteRecipeCommend
	 * @작성일     : 2021. 9. 10. 
	 * @작성자     : 오상준
	 * @Method 설명 : 레시피 게시판 댓글 테이블의 해당 값 삭제하면서 레시피 테이블 댓글수 -1   메소드
	 */
	
	public void deleteRecipeCommend(int comm_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			// 커넥션 풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			
			// SQL문 작성
			sql = "delete from comments where comm_num = ?";
			
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// ? 에 데이터 바인딩
			pstmt.setInt(1, comm_num);
			
			// SQL문 실행
			pstmt.executeUpdate();
			
			// 레시피 게시판 댓글 수 업데이트(-1)
			
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			// 자원정리
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	
	//---------------------------- 모두의 레시피 공지사항 처리
	
	/**
	 * @Method 메소드명  : getRecipeNewsCount
	 * @작성일     : 2021. 9. 12. 
	 * @작성자     : 오상준
	 * @Method 설명 : 레시피 공지사항 총 갯수 수 반환
	 */
	public int getRecipeNewsCount() throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;
		
		try {
			conn = DBUtil.getConnection();
			
			sql="SELECT COUNT(*) FROM news_board WHERE news_category='레시피'";
			
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
	 * @Method 메소드명  : getRecipeNewsList
	 * @작성일     : 2021. 9. 12. 
	 * @작성자     : 오상준
	 * @Method 설명 : 레시피 공지사항 리스트 반환
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
					+ "(SELECT * FROM news_board WHERE news_category='레시피' ORDER BY news_num DESC)a) "
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
				recipe_News.setWriter("관리자");
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
