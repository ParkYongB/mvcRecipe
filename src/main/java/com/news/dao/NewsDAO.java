package com.news.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.news.vo.NewsCommentsVO;
import com.news.vo.NewsVO;
import com.util.DBUtil;
import com.util.StringUtil;

import com.util.DurationFromNow;




/**
 * @Package Name   : com.news.dao
 * @FileName  : NewsDAO.java
 * @작성일       : 2021. 9. 7. 
 * @작성자       : 신혜지
 * @프로그램 설명 : 공지사항 DAO
 */


public class NewsDAO {
	
	private static NewsDAO instance = new NewsDAO();
	
	public static NewsDAO getInstance() {
		return instance;
	}
	
	private NewsDAO() {}

	
	
	/**
	 * @Method 메소드명  : insertNews
	 * @작성일     : 2021. 9. 7. 
	 * @작성자     : 신혜지
	 * @Method 설명 : 공지사항에 올라갈 정보를 담고 있습니다.
	 */
	public void insertNews(NewsVO news) throws Exception{
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
		conn = DBUtil.getConnection();
		sql="insert into NEWS_BOARD(news_num, news_title, news_content, mem_num, news_hits, news_date, news_modi, news_category, news_file) VALUES (NEWS_BOARD_SEQ.nextval, ?, ?, ? , 1, SYSDATE, SYSDATE, ?,?)";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1,news.getNews_title());
		pstmt.setString(2,news.getNews_content());
		pstmt.setInt(3,news.getMem_num());
		pstmt.setString(4, news.getNews_category());
		pstmt.setString(5,news.getNews_file());
		pstmt.executeQuery();
		}catch(Exception e){
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	/**
	 * @Method 메소드명  : getNewsCount
	 * @작성일     : 2021. 9. 7. 
	 * @작성자     : 신혜지
	 * @Method 설명 : 공지사항 리스트 개수 세는 메서드
	 */
	public int getNewsCount() throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;
		
		try {
			conn = DBUtil.getConnection();
			sql="SELECT COUNT(*) FROM NEWS_BOARD";
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				count =rs.getInt(1);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
		return count;
	}
	
	/**
	 * @Method 메소드명  : getNewsCount
	 * @작성일     : 2021. 9. 12. 
	 * @작성자     : 신혜지
	 * @Method 설명 : 검색 후 리스트 뽑을 떄 세는 메소드
	 */
	public int getNewsCount(String division, String search) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;
		
		try {
			conn = DBUtil.getConnection();
			if(division.equals("제목")) {
			sql="SELECT COUNT(*) FROM NEWS_BOARD Where news_title Like ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,"%"+search+"%");
			}else if(division.equals("내용")) {
				sql="SELECT COUNT(*) FROM NEWS_BOARD Where news_content Like ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1,"%"+search+"%");
			}else {
				sql="SELECT COUNT(*) FROM NEWS_BOARD n join member_detail m on n.mem_num = m.mem_num Where name Like ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1,"%"+search+"%");
			}
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
	 * @Method 메소드명  : getNewsList
	 * @작성일     : 2021. 9. 7. 
	 * @작성자     : 신혜지
	 * @Method 설명 :공지사항 리스트 뽑는 메서드
	 */
	public List<NewsVO> getNewsList(int start, int end) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		List<NewsVO> list = null;
		int news_comments_count = 0;
		try {
			conn = DBUtil.getConnection();
			sql= "select * from (select a.*, rownum rnum from "
					+ "(select * from News_board order by news_num desc) a) where rnum >=? and rnum <=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
		
			rs=pstmt.executeQuery();
			list = new ArrayList<NewsVO>();
			while(rs.next()) {
				news_comments_count = getCommentsCount(rs.getInt("news_num"));
				NewsVO news = new NewsVO();
				news.setNews_num(rs.getInt("news_num"));
				news.setNews_title(rs.getString("news_title"));
				news.setNews_category(rs.getString("news_category"));
				news.setNews_content(rs.getString("news_content"));
				news.setId("관리자");
				news.setNews_date(rs.getDate("news_date"));
				news.setNews_modi(rs.getDate("news_modi"));
				news.setNews_hits(rs.getInt("news_hits"));
				news.setNews_comment_count(news_comments_count);
				
				list.add(news);
				
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {		
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list; 	
	}
	
	
	/**
	 * @Method 메소드명  : getNewsList
	 * @작성일     : 2021. 9. 12. 
	 * @작성자     : 신혜지
	 * @Method 설명 : 뉴스리스트 뽑는 메서드(검색시)
	 */
	public List<NewsVO> getNewsList(int start, int end, String division, String search) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		List<NewsVO> list = null;
		int news_comments_count = 0;
		try {
			// 커넥션 풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			
				// 조건시작
			// SQL문 작성
			// 검색 조건이 제목인 경우
			if(division.equals("제목")) {
				// SQL문장 작성
				sql = "select * from (select a.*, rownum rnum from "
						+ "(select * from News_board  where news_title LIKE ? order by news_num desc) a) "
						+ "where rnum >=? and rnum <=?";
				
				// PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				
				// ? 에 데이터 바인딩
				pstmt.setString(1, "%" + search + "%");
				pstmt.setInt(2, start);
				pstmt.setInt(3, end);
				
				
			// 검색조건이 내용인 경우
			}else{
				// SQL문장 작성
				sql = "select * from (select a.*, rownum rnum from "
						+ "(select * from News_board  where news_content LIKE ? order by news_num desc) a) "
						+ "where rnum >=? and rnum <=?";
				
				// PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				
				// ? 에 데이터 바인딩
				pstmt.setString(1, "%" + search + "%");
				pstmt.setInt(2, start);
				pstmt.setInt(3, end);
			
			} // 조건문 종료
			
			// sql 문을 실행하고 rs에 결과행 담기
			rs = pstmt.executeQuery();
			
			list = new ArrayList<NewsVO>();
			while(rs.next()) {
				news_comments_count = getCommentsCount(rs.getInt("news_num"));
				NewsVO news = new NewsVO();
				news.setNews_num(rs.getInt("news_num"));
				news.setNews_title(rs.getString("news_title"));
				news.setNews_category(rs.getString("news_category"));
				news.setNews_content(rs.getString("news_content"));
				news.setId("관리자");
				news.setNews_date(rs.getDate("news_date"));
				news.setNews_modi(rs.getDate("news_modi"));
				news.setNews_hits(rs.getInt("news_hits"));
				news.setNews_comment_count(news_comments_count);
				
				list.add(news);
				
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {		
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list; 	
	}
	
	/**
	 * @Method 메소드명  : updateCount
	 * @작성일     : 2021. 9. 7. 
	 * @작성자     : 신혜지
	 * @Method 설명 : 조회수 증가 
	 */
	public void updateCount(int num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql="update news_board set news_hits = news_hits + 1 where news_num = ?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs=pstmt.executeQuery();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);			
		}	
	}
	/**
	 * @Method 메소드명  : getNews
	 * @작성일     : 2021. 9. 7. 
	 * @작성자     : 신혜지
	 * @Method 설명 : 공지사항 본문을 확인하는 메소드.
	 */
	public NewsVO getNews(int num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		NewsVO news = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql="SELECT * FROM news_board b JOIN member m ON b.mem_num = m.mem_num WHERE news_num=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				news = new NewsVO();
				news.setNews_num(rs.getInt("news_num"));
				news.setId("관리자");
				news.setNews_category(rs.getString("news_category"));
				news.setNews_title(rs.getString("news_title"));
				news.setNews_date(rs.getDate("news_date"));
				news.setNews_modi(rs.getDate("news_modi"));
				news.setNews_content(rs.getString("news_content"));
				news.setNews_hits(rs.getInt("news_hits"));
				news.setNews_file(rs.getString("news_file"));
				news.setMem_num(rs.getInt("mem_num"));
				news.setAuth(rs.getInt("auth"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);			
		}
		return news;
	}
	/**
	 * @Method 메소드명  : updateNews
	 * @작성일     : 2021. 9. 7. 
	 * @작성자     : 신혜지
	 * @Method 설명 :공지사항을 수정하는 코드
	 */
	public void updateNews(NewsVO news) throws Exception{
		Connection conn= null;
		PreparedStatement pstmt= null;
		String sql = null;

		
		try {
			conn=DBUtil.getConnection();
			if(news.getNews_file()!=null) {
			sql="UPDATE news_board SET news_title=?, news_content=?, news_file=?, news_modi=SYSDATE, news_category=? WHERE news_num=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1,news.getNews_title());
			pstmt.setString(2,news.getNews_content());
			pstmt.setString(3,news.getNews_file());
			pstmt.setString(4, news.getNews_category());
			pstmt.setInt(5,news.getNews_num());
			}else {
				sql="UPDATE news_board SET news_title=?, news_content=?, news_modi=SYSDATE, news_category=? WHERE news_num=?";
				pstmt=conn.prepareStatement(sql);
				pstmt.setString(1,news.getNews_title());
				pstmt.setString(2,news.getNews_content());
				pstmt.setString(3,news.getNews_category());
				pstmt.setInt(4,news.getNews_num());	
			}
			pstmt.executeUpdate();	
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);			
		}
	}
	/**
	 * @Method 메소드명  : DeleteNews
	 * @작성일     : 2021. 9. 15. 
	 * @작성자     : 신혜지
	 * @Method 설명 : 공지사항 삭제할때 쓰는 메서드
	 */
	public void DeleteNews(int num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		String sql= null;
		
		try {
			conn=DBUtil.getConnection();
			conn.setAutoCommit(false);
			sql="DELETE FROM comments where news_num=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.executeUpdate();
			sql = "delete from news_board where news_num=?";
			pstmt2=conn.prepareStatement(sql);
			pstmt2.setInt(1, num);
			pstmt2.executeUpdate();
			conn.commit();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);			
			DBUtil.executeClose(null, pstmt2, conn);			
		}
	}
	//****
	//댓글 CRUD시작
	//****
	/**
	 * @Method 메소드명  : insertReplyBoard
	 * @작성일     : 2021. 9. 9. 
	 * @작성자     : 신혜지
	 * @Method 설명 : 댓글 입력하는 메서드
	 */
	public void insertReplyBoard(NewsCommentsVO commentsVO) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql = "INSERT INTO Comments (comm_num, mem_num, comm_con, news_num) VALUES (comments_seq.nextval, ?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, commentsVO.getMem_num());
			pstmt.setString(2, commentsVO.getComm_con());
			pstmt.setInt(3, commentsVO.getNews_num());
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	/**
	 * @Method 메소드명  : getNewsCommentsCount
	 * @작성일     : 2021. 9. 9. 
	 * @작성자     : 신혜지
	 * @Method 설명 : 뉴스의 댓글의 개수를 세옴
	 */
	public int getNewsCommentsCount(int news_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT COUNT(*) FROM comments WHERE news_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, news_num);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
			return count;
	}
	/**
	 * @Method 메소드명  : getListReplyNews
	 * @작성일     : 2021. 9. 9. 
	 * @작성자     : 신혜지
	 * @Method 설명 : 댓글 리스트 뽑음
	 */
	public List<NewsCommentsVO> getListCommentsNews(int start, int end, int news_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<NewsCommentsVO> list =null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql="SELECT * FROM (SELECT a.*, rownum rnum FROM (SELECT b.comm_num, TO_CHAR(b.comm_date,'YYYY-MM-DD HH24:MI:SS')comm_date, "
					+ "TO_CHAR(b.comm_modifydate,'YYYY-MM-DD HH24:MI:SS')comm_modifydate, "
					+ "b.comm_con, b.news_num, b.mem_num, m.id, md.photo "
					+ "FROM comments b JOIN member_detail md ON b.mem_num=md.mem_num JOIN "
					+ "member m ON b.mem_num=m.mem_num "
					+ "WHERE b.news_num=? ORDER BY b.comm_num DESC)a) "
					+ "WHERE rnum >= ? AND rnum <= ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, news_num);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
			rs=pstmt.executeQuery();
			list = new ArrayList<NewsCommentsVO>();
			while(rs.next()) {
				NewsCommentsVO comments = new NewsCommentsVO();
				comments.setComm_num(rs.getInt("comm_num"));
				comments.setComm_date(DurationFromNow.getTimeDiffLabel(rs.getString("comm_date")));
				comments.setComm_modifydate(DurationFromNow.getTimeDiffLabel(rs.getString("comm_modifydate")));
				comments.setComm_con(StringUtil.useBrNoHtml(rs.getString("comm_con")));
				comments.setNews_num(rs.getInt("news_num"));
				comments.setMem_num(rs.getInt("mem_num"));
				comments.setId(rs.getString("id"));
				comments.setPhoto(rs.getString("photo"));
				
				list.add(comments);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}
	
	
	/**
	 * @Method 메소드명  : deleteCommentsNews
	 * @작성일     : 2021. 9. 10. 
	 * @작성자     : 신혜지
	 * @Method 설명 : 댓글 삭제하는 메서드 
	 */
	public void deleteCommentsNews(int comm_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "DELETE FROM comments WHERE comm_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, comm_num);
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			//자원정리
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	/**
	 * @Method 메소드명  : updateCommentsNews
	 * @작성일     : 2021. 9. 10. 
	 * @작성자     : 신혜지
	 * @Method 설명 : 댓글 수정하는 메서드
	 */
	public void updateCommentsNews(NewsCommentsVO newsComments)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "UPDATE comments set comm_con=?, comm_modifydate=sysdate WHERE comm_num=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, newsComments.getComm_con());
			pstmt.setInt(2, newsComments.getComm_num());
			pstmt.executeUpdate();
			
			}catch(Exception e) {
			throw new Exception(e);
			}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	/**
	 * @Method 메소드명  : getCommentsCount
	 * @작성일     : 2021. 9. 16. 
	 * @작성자     : 오상준
	 * @Method 설명 : 공지사항 댓글 갯수 세는 메소드
	 */
	public int getCommentsCount(int news_num)throws Exception {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;
		String sql = null;
		
		try {
			// 커넥션 풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			// SQL문 작성
			sql = "select count(*) from comments where news_num = ?";
			
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// ? 에 데이터 바인딩
			pstmt.setInt(1, news_num);
			
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
}
