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
	//싱글턴 패턴
	private static QnaBoardDAO instance = new QnaBoardDAO();
	
	public static QnaBoardDAO getInstance() {
		return instance;
	}
	
	private QnaBoardDAO() {}

	/**
	 * @Method 메소드명  : QnaBoardWrite
	 * @작성일     : 2021. 9. 6. 
	 * @작성자     : 나윤경
	 * @Method 설명 : 고객센터 게시판 글 작성
	 */
	public void QnaBoardWrite(QnaBoardVO qnaboard)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			
			//SQL문 작성
			sql = "INSERT INTO qna_board (qna_num, qna_title, qna_content, qna_id, qna_passwd, qna_ip, qna_date) "
					+ "VALUES (qna_board_seq.nextval,?,?,?,?,?,SYSDATE)";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setString(1, qnaboard.getQna_title());
			pstmt.setString(2, qnaboard.getQna_content());
			pstmt.setString(3, qnaboard.getQna_id());
			pstmt.setString(4, qnaboard.getQna_passwd());
			pstmt.setString(5, qnaboard.getQna_ip());
			
			//SQL문 실행
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			//자원정리
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	/**
	 * @Method 메소드명  : getQnaBoardCount
	 * @작성일     : 2021. 9. 6. 
	 * @작성자     : 나윤경
	 * @Method 설명 : 고객센터 게시판 총 레코드 수(검색 레코드 수)
	 */
	public int getQnaBoardCount(String keyfield, String keyword)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String sub_sql =null;
		int count = 0;
		
		try {
			//커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			
			if(keyword == null || "".equals(keyword)) {
				//전체 글 개수
				sql="SELECT COUNT(*) FROM qna_board";
				pstmt = conn.prepareStatement(sql);
			}else {
				//검색 글 개수
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
			//SQL문 실행하고 결과행을 ResultSet에 담음
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt(1);
			}			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			//자원 정리
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return count;
	}

	
	/**
	 * @Method 메소드명  : getQnaBoardList
	 * @작성일     : 2021. 9. 6. 
	 * @작성자     : 나윤경
	 * @Method 설명 : 고객센터 게시판 글 목록(검색글 목록)
	 */
	public List<QnaBoardVO> getQnaBoardList(int startRow, int endRow, String keyfield, String keyword)throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<QnaBoardVO> list = null;
		String sql = null;
		String sub_sql = null;
		
		try {
			//커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			
			if(keyword==null || "".equals(keyword)) {
				//전체 글 보기
				sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM (SELECT * FROM qna_board "
						+ "ORDER BY qna_num DESC)a) WHERE rnum >= ? AND rnum <= ?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setInt(1, startRow);
				pstmt.setInt(2, endRow);
				
			}else {
				//검색 글 보기
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
			
			
			//SQL문을 실행하고 결과행을 ResultSet에 담음
			rs=pstmt.executeQuery();
			list = new ArrayList<QnaBoardVO>();
			while(rs.next()) {
				QnaBoardVO qnaboardVO = new QnaBoardVO();
				qnaboardVO.setQna_num(rs.getInt("qna_num"));
				qnaboardVO.setQna_title(rs.getString("qna_title"));
				qnaboardVO.setQna_passwd(rs.getString("qna_passwd"));
				qnaboardVO.setQna_id(rs.getString("qna_id"));
				qnaboardVO.setQna_date(rs.getDate("qna_date"));
				
				//자바빈(VO)을 ArrayList에 저장
				list.add(qnaboardVO);
			}
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			//자원정리
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
		
	}
	
	
	/**
	 * @Method 메소드명  : getQnaBoardDetail
	 * @작성일     : 2021. 9. 9. 
	 * @작성자     : 나윤경
	 * @Method 설명 : 고객센터 게시판 상세페이지
	 */
	public QnaBoardVO getQnaBoardDetail(int qna_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		QnaBoardVO qnaboard = null;
		String sql =null;
		
		try {
			//커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			
			//SQL문 작성
			sql = "SELECT * FROM qna_board WHERE qna_num=?";
			
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			//?에 데이터 바인딩
			pstmt.setInt(1, qna_num);
			
			//SQL문 실행해서 결과행을 ResultSet에 담음
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
			//자원 정리
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return qnaboard;
	}
	
	
	
	
	//글 수정
	/**
	 * @Method 메소드명  : qnaBoardModify
	 * @작성일     : 2021. 9. 12. 
	 * @작성자     : 나윤경
	 * @Method 설명 : 고객센터 게시판 글 수정
	 */
	public void qnaBoardModify(QnaBoardVO qnaboardVO)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			
			//SQL문 작성
			sql = "UPDATE qna_board SET qna_title=?, qna_content=?, qna_ip=? WHERE qna_num=?";
			
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			//?에 데이터 바인딩
			pstmt.setString(1, qnaboardVO.getQna_title());
			pstmt.setString(2, qnaboardVO.getQna_content());
			pstmt.setString(3, qnaboardVO.getQna_ip());
			pstmt.setInt(4, qnaboardVO.getQna_num());
			
			//SQL문 실행
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			//자원정리
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	
	/**
	 * @Method 메소드명  : qnaBoardDelete
	 * @작성일     : 2021. 9. 12. 
	 * @작성자     : 나윤경
	 * @Method 설명 :고객센터 게시판 글 삭제
	 */
	public void qnaBoardDelete(int qna_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션 할당받음
			conn = DBUtil.getConnection();
			
			conn.setAutoCommit(false);
			
			// 무결성 제약조건으로 인해 문의번호가 일치한 댓글 테이블에서 값 전체 삭제
			
			sql = "DELETE FROM comments WHERE qna_num=?";
			
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			//?에 데이터 바인딩
			pstmt.setInt(1, qna_num);
			
			//SQL문 실행
			pstmt.executeUpdate();
	
			// 고객센터 문의 게시글 삭제
			
			//SQL문 작성
			sql = "DELETE FROM qna_board WHERE qna_num=?";
			
			//PreparedStatement 객체 생성
			pstmt2 = conn.prepareStatement(sql);
			
			//?에 데이터 바인딩
			pstmt2.setInt(1, qna_num);
			
			//SQL문 실행
			pstmt2.executeUpdate();
			
			
			conn.commit();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			//자원정리
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	/**
	 * @Method 메소드명  : insertQnACommend
	 * @작성일     : 2021. 9. 12. 
	 * @작성자     : 나윤경
	 * @Method 설명 : 고객센터 댓글 달기
	 */
	public void insertQnACommend(RecipeCommendsVO qnaReply)throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = null;
		
		try {
			// 커넥션 풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			// SQL문 작성
			sql = "insert into comments (comm_num, comm_con, qna_num) "
					+ "values (comments_seq.nextval,?,?)";
			
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// ? 에 데이터 바인딩
			pstmt.setString(1, qnaReply.getComm_con());
			pstmt.setInt(2, qnaReply.getQna_num());
			
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
	 * @Method 메소드명  : getQnaReplyBoardCount
	 * @작성일     : 2021. 9. 12. 
	 * @작성자     : 나윤경
	 * @Method 설명 : 고객센터 게시판 댓글 개수
	 */
	public int getQnaReplyBoardCount(int qna_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;
		String sql = null;
		
		try {
			// 커넥션 풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			// SQL문 작성
			sql = "select count(*) as count from comments where qna_num = ?";
			
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// ? 에 데이터 바인딩
			pstmt.setInt(1, qna_num);
			
			// SQL문을 실행해서 결과행을 rs에 담는다
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt("count");
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
	 * @Method 메소드명  : getQnaListReplyBoard
	 * @작성일     : 2021. 9. 12. 
	 * @작성자     : 나윤경
	 * @Method 설명 : 고객센터 게시판 댓글 목록
	 */
	public List<RecipeCommendsVO> getQnaListReplyBoard(int start, int end, int qna_num)throws Exception{
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
					+ "FROM (SELECT comm_num,TO_CHAR(comm_date,'YYYY-MM-DD HH24:MI:SS') comm_date,"
					+ " comm_con, qna_num FROM comments WHERE qna_num=? ORDER BY comm_num DESC)a) "
					+ "WHERE rnum >=? and rnum <=?";
			
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// ? 에 데이터 바인딩
			pstmt.setInt(1, qna_num);
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
				reply.setComm_con(StringUtil.useBrNoHtml(rs.getString("comm_con")));
				reply.setQna_num(rs.getInt("qna_num"));
				
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
	
	public void deleteQnaCommend(int comm_num)throws Exception{
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			
			// 댓글 번호가 일치하는 댓글 삭제
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
			
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			// 자원정리
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
}
	
