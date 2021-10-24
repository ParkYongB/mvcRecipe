package com.member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.member.vo.MemberVO;
import com.recipe.vo.RecipeVO;
import com.util.DBUtil;

/**
 * @Package Name   : com.member.dao
 * @FileName  : MemberDAO.java
 * @작성일       : 2021. 9. 8. 
 * @작성자       : 박용복
 * @프로그램 설명 : MemberDAO
 */

public class MemberDAO {
	
	private static MemberDAO instance = new MemberDAO();
	
	public static MemberDAO getInstance() {
		return instance;
	}
	
	private MemberDAO() {}

	/**
	 * @Method 메소드명  : insertMember
	 * @작성일     : 2021. 9. 7. 
	 * @작성자     : 박용복
	 * @Method 설명 : 멤버 회원 가입
	 */
	
	// 회원 가입
	public void insertMember(MemberVO member)throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		ResultSet rs = null;
		String sql = null;
		
		int num = 0;
		
		try {
			conn = DBUtil.getConnection();
			
			conn.setAutoCommit(false);
			
			sql = "SELECT member_seq.nextval FROM dual";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				num = rs.getInt(1);
			}
			
			sql = "INSERT INTO member(mem_num, id) VALUES(?, ?)";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setInt(1, num);
			pstmt2.setString(2, member.getId());
			pstmt2.executeUpdate();
			
			sql = "INSERT INTO member_detail(mem_num, name, passwd, email, phone, birthday, passkey) VALUES(?, ?, ?, ?, ?, ?, ?)";
			pstmt3 = conn.prepareStatement(sql);
			pstmt3.setInt(1, num);
			pstmt3.setString(2, member.getName());
			pstmt3.setString(3, member.getPasswd());
			pstmt3.setString(4, member.getEmail());
			pstmt3.setString(5, member.getPhone());
			pstmt3.setString(6, member.getBirthday());
			pstmt3.setString(7, member.getPasskey());
			pstmt3.executeUpdate();
		
			conn.commit();
		}catch(Exception e) {
			conn.rollback();
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt3, null);
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(null, pstmt, null);
		}
	}
	
	
	// ID 중복 체크 및 로그인 처리
	/**
	 * @Method 메소드명  : checkMember
	 * @작성일     : 2021. 9. 7. 
	 * @작성자     : 박용복
	 * @Method 설명 : 아이디 중복 체크 및 로그인 처리
	 */
	
	public MemberVO checkMember(String id)throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberVO member = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "SELECT * FROM member m LEFT OUTER JOIN member_detail d ON m.mem_num = d.mem_num WHERE m.id = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				member = new MemberVO();
				member.setMem_num(rs.getInt("mem_num"));
				member.setId(rs.getString("id"));
				member.setAuth(rs.getInt("auth"));
				member.setPasswd(rs.getString("passwd"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return member;
	}
	
	// 회원 상세 정보
	/**
	 * @Method 메소드명  : getMember
	 * @작성일     : 2021. 9. 8. 
	 * @작성자     : 박용복
	 * @Method 설명 : 회원 상세 정보 조회
	 */
	public MemberVO getMember(int mem_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberVO member = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT d.mem_num, id, auth, name, passwd, email, phone, to_char(birthday, 'yyyy-MM-dd') as birthday, passkey, photo, join_date FROM member m JOIN member_detail d ON m.mem_num = d.mem_num WHERE m.mem_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mem_num);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				member = new MemberVO();
				member.setMem_num(rs.getInt("mem_num"));
				member.setId(rs.getString("id"));
				member.setAuth(rs.getInt("auth"));
				member.setName(rs.getString("name"));
				member.setPasswd(rs.getString("passwd"));
				member.setEmail(rs.getString("email"));
				member.setPhone(rs.getString("phone"));
				member.setBirthday(rs.getString("birthday"));
				member.setPasskey(rs.getString("passkey"));
				member.setPhoto(rs.getString("photo"));
				member.setJoin_date(rs.getDate("join_date"));
				
			}
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return member;
	}
	
	// 프로필 사진 수정
	/**
	 * @Method 메소드명  : updateMyPhoto
	 * @작성일     : 2021. 9. 7. 
	 * @작성자     : 박용복
	 * @Method 설명 : 프로필 사진 수정
	 */
	public void updateMyPhoto(String photo, int mem_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "UPDATE member_detail SET photo = ? WHERE mem_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, photo);
			pstmt.setInt(2, mem_num);
			
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}
	}
	
	
	// 회원 정보 및 비밀번호 수정
	
	/**
	 * @Method 메소드명  : updateMember
	 * @작성일     : 2021. 9. 8. 
	 * @작성자     : 박용복
	 * @Method 설명 : 회원 정보 및 비밀번호 수정
	 */
	
	public void updateMember(MemberVO member, String newPasswd)throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "UPDATE member_detail SET email = ?, phone = ?, passkey = ? WHERE mem_num = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getEmail());
			pstmt.setString(2, member.getPhone());
			pstmt.setString(3, member.getPasskey());
			pstmt.setInt(4, member.getMem_num());
			
			pstmt.executeUpdate();
			
			if(newPasswd != null) {
				sql = "UPDATE member_detail SET passwd = ? WHERE mem_num = ?";
				
				pstmt2 = conn.prepareStatement(sql);
				pstmt2.setString(1, newPasswd);
				pstmt2.setInt(2, member.getMem_num());
				pstmt2.executeUpdate();
			}
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	// 회원 탈퇴
	
	/**
	 * @Method 메소드명  : deleteMember
	 * @작성일     : 2021. 9. 8. 
	 * @작성자     : 박용복
	 * @Method 설명 : 회원 탈퇴
	 */
	public void deleteMember(int mem_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		PreparedStatement pstmt4 = null;
		PreparedStatement pstmt5 = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			
			sql = "UPDATE member SET auth = 0 WHERE mem_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mem_num);
			pstmt.executeUpdate();
			
			sql = "DELETE FROM member_detail WHERE mem_num = ?";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setInt(1, mem_num);
			pstmt2.executeUpdate();
		
			sql = "DELETE FROM comments WHERE mem_num = ?";
			pstmt3 = conn.prepareStatement(sql);
			pstmt3.setInt(1, mem_num);
			pstmt3.executeUpdate();
			
			sql = "DELETE FROM bookmark WHERE mem_num = ?";
			pstmt4 = conn.prepareStatement(sql);
			pstmt4.setInt(1, mem_num);
			pstmt4.executeUpdate();
			
			sql = "DELETE FROM recommend WHERE mem_num = ?";
			pstmt5 = conn.prepareStatement(sql);
			pstmt5.setInt(1, mem_num);
			pstmt5.executeUpdate();
			
			conn.commit();
		}catch (Exception e) {
			conn.rollback();
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt5, null);
			DBUtil.executeClose(null, pstmt4, null);
			DBUtil.executeClose(null, pstmt3, null);
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	// ID 찾기
	
	/**
	 * @Method 메소드명  : idSearch
	 * @작성일     : 2021. 9. 9. 
	 * @작성자     : 박용복
	 * @Method 설명 : ID 찾기
	 */
	
	public MemberVO idSearch(String name, String phone)throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		MemberVO member = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT id,name FROM member m, member_detail d WHERE m.mem_num = d.mem_num AND name = ? AND phone = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, phone);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				member = new MemberVO();
				member.setId(rs.getString("id"));
				member.setName(rs.getString("name"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return member;
	}
	
	// 비밀번호를 몰라서 변경하기 전 작업
	/**
	 * @Method 메소드명  : passwdSearch
	 * @작성일     : 2021. 9. 9. 
	 * @작성자     : 박용복
	 * @Method 설명 : 비밀번호 찾기
	 */
	public MemberVO passwdSearch(String id, String name, String passkey)throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		MemberVO member = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT id, name, passkey FROM member m, member_detail d WHERE m.mem_num = d.mem_num AND id = ? AND name = ? AND passkey = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, name);
			pstmt.setString(3, passkey);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				member = new MemberVO();
				member.setId(rs.getString("id"));
				member.setName(rs.getString("name"));
				member.setPasskey(rs.getString("passkey"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return member;
	}

	// 비밀번호 변경
	
	/**
	 * @Method 메소드명  : updatePassword
	 * @작성일     : 2021. 9. 9. 
	 * @작성자     : 박용복
	 * @Method 설명 : 비밀번호 변경
	 */
	
	public void updatePassword(String passwd, String id) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "UPDATE (SELECT passwd FROM member_detail d, member m WHERE d.mem_num = m.mem_num AND id = ?) SET passwd = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, passwd);
			
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	/**
	 * @Method 메소드명  : getRecipeCount
	 * @작성일     : 2021. 9. 9. 
	 * @작성자     : 박용복
	 * @Method 설명 : 아이디 별 게시글 count 조회
	 */
	public int getRecipeCount(String id)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;
		MemberVO member = null;
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT COUNT(id) id FROM recipe_board b JOIN member m ON b.mem_num = m.mem_num WHERE id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();	
			
			if(rs.next()) {
				member = new MemberVO();
				count = rs.getInt(1);
				
				member.setId(rs.getString("id"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			// 자원정리
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return count;
	}
	
	// 작성한 글 조회
	
	/**
	 * @Method 메소드명  : getTotalRecipeList
	 * @작성일     : 2021. 9. 9. 
	 * @작성자     : 박용복
	 * @Method 설명 : 작성한 글 조회
	 */
	public List<RecipeVO> getTotalRecipeList(int start, int end, String id)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		List<RecipeVO> list = null;
		int comments_count = 0;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM (SELECT * FROM recipe_board b JOIN member m ON b.mem_num = m.mem_num where id = ? ORDER BY b.board_num DESC) a)WHERE rnum >= ? AND rnum <= ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
			rs = pstmt.executeQuery();
			
			list = new ArrayList<RecipeVO>();
			
			while(rs.next()) {
				RecipeVO recipe = new RecipeVO();
				recipe.setBoard_num(rs.getInt("board_num"));
				recipe.setTitle(rs.getString("title"));
				recipe.setContent(rs.getString("content"));
				recipe.setHits(rs.getInt("hits"));
				recipe.setRecom_count(rs.getInt("recom_count"));
				recipe.setReport_date(rs.getDate("report_date"));
				recipe.setModify_date(rs.getDate("modify_date"));
				recipe.setIp(rs.getString("ip"));
				recipe.setFilename(rs.getString("filename"));
				recipe.setCategory(rs.getString("category"));
				recipe.setMem_num(rs.getInt("mem_num"));
				recipe.setId(rs.getString("id"));
				
				comments_count = getRecipeReplyBoardCount(rs.getInt("board_num"));
				recipe.setComm_count(comments_count);
				
				list.add(recipe);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return list;
	}
	
	/**
	 * @Method 메소드명  : getRecipeCount
	 * @작성일     : 2021. 9. 9. 
	 * @작성자     : 박용복
	 * @Method 설명 : 아이디 별 북마크 count 조회
	 */
	public int getBookmarkCount(String id)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;
		MemberVO member = null;
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT COUNT(id) id FROM bookmark b JOIN member m ON b.mem_num = m.mem_num WHERE id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();	
			
			if(rs.next()) {
				member = new MemberVO();
				count = rs.getInt(1);
				
				member.setId(rs.getString("id"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			// 자원정리
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return count;
	}
	
	// 북마크 리스트 조회
	
	/**
	 * @Method 메소드명  : getBookMarkRecipeList
	 * @작성일     : 2021. 9. 15. 
	 * @작성자     : 박용복
	 * @Method 설명 : 북마크 리스트 조회
	 */
	public List<RecipeVO> getBookMarkRecipeList(int start, int end, int mem_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		List<RecipeVO> list = null;
		int comments_count = 0;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "SELECT * FROM(SELECT a.*, rownum rnum FROM (SELECT * FROM recipe_board b JOIN member m ON b.mem_num = m.mem_num JOIN bookmark o on b.board_num = o.board_num WHERE o.mem_num = ? ORDER BY b.board_num DESC) a)WHERE rnum >= ? AND rnum <= ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mem_num);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
			rs = pstmt.executeQuery();
			
			list = new ArrayList<RecipeVO>();
			
			while(rs.next()) {
				RecipeVO recipe = new RecipeVO();
				recipe.setBoard_num(rs.getInt("board_num"));
				recipe.setTitle(rs.getString("title"));
				recipe.setContent(rs.getString("content"));
				recipe.setHits(rs.getInt("hits"));
				recipe.setRecom_count(rs.getInt("recom_count"));
				recipe.setReport_date(rs.getDate("report_date"));
				recipe.setModify_date(rs.getDate("modify_date"));
				recipe.setIp(rs.getString("ip"));
				recipe.setFilename(rs.getString("filename"));
				recipe.setCategory(rs.getString("category"));
				recipe.setMem_num(rs.getInt("mem_num"));
				recipe.setId(rs.getString("id"));
				recipe.setBook_num(rs.getInt("book_num"));
				
				comments_count = getRecipeReplyBoardCount(rs.getInt("board_num"));
				recipe.setComm_count(comments_count);
				
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
	
	// 총 아이디 수
	
	/**
	 * @Method 메소드명  : getMemberCount
	 * @작성일     : 2021. 9. 10. 
	 * @작성자     : 박용복
	 * @Method 설명 : 총 아이디 수 구하기
	 */
	
	public int getMemberCount(String keyfield, String keyword) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String sub_sql = null;
		int count = 0;
		
		try {
			conn = DBUtil.getConnection();
			
			if(keyword == null || "".equals(keyword)) {
				sql = "SELECT COUNT(*) FROM member m LEFT OUTER JOIN member_detail d USING (mem_num)";
				pstmt = conn.prepareStatement(sql);
			}else {
				if(keyfield.equals("1")) sub_sql = "id LIKE ?";
				else if(keyfield.equals("2")) sub_sql = "name LIKE ?";
				else if(keyfield.equals("3")) sub_sql = "email LIKE ?";
			
				sql = "SELECT COUNT(*) FROM member m LEFT OUTER JOIN member_detail d USING (mem_num) WHERE " + sub_sql;
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, "%"+keyword+"%");
			}
			rs = pstmt.executeQuery();
			
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
	
	// 가입한 회원 리스트 출력

	/**
	 * @Method 메소드명  : getListMember
	 * @작성일     : 2021. 9. 10. 
	 * @작성자     : 박용복
	 * @Method 설명 : 가입한 회원 리스트 출력
	 */

	public List<MemberVO> getListMember(int start, int end, String keyfield, String keyword)throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<MemberVO> list = null;
		String sql = null;
		String sub_sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			if(keyword == null || "".equals(keyword)) {
				sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM (SELECT mem_num, id, name, email, phone, to_char(birthday, 'yyyy-MM-dd') as birthday, auth, join_date FROM member m LEFT OUTER JOIN member_detail d USING (mem_num) ORDER BY mem_num DESC NULLS LAST)a) WHERE rnum >= ? AND rnum <= ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, start);
				pstmt.setInt(2, end);
			}else {
				if(keyfield.equals("1")) sub_sql = "id LIKE ?";
				else if(keyfield.equals("2")) sub_sql = "name LIKE ?";
				else if(keyfield.equals("3")) sub_sql = "email LIKE ?";
				
				sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM (SELECT mem_num, id, name, email, phone, to_char(birthday, 'yyyy-MM-dd') as birthday, auth, join_date FROM member m LEFT OUTER JOIN member_detail d USING (mem_num) WHERE " + sub_sql + "  ORDER BY mem_num DESC NULLS LAST)a) WHERE rnum >= ? AND rnum <= ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, "%"+keyword+"%");
				pstmt.setInt(2, start);
				pstmt.setInt(3, end);
			}
			
			rs = pstmt.executeQuery();
			list = new ArrayList<MemberVO>();
			
			while(rs.next()) {
				MemberVO member = new MemberVO();
				member.setMem_num(rs.getInt("mem_num"));
				member.setId(rs.getString("id"));
				member.setAuth(rs.getInt("auth"));
				member.setName(rs.getString("name"));
				member.setEmail(rs.getString("email"));
				member.setPhone(rs.getString("phone"));
				member.setBirthday(rs.getString("birthday"));
				member.setJoin_date(rs.getDate("join_date"));
				
				list.add(member);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return list;
	}
	
	// 관리자 메뉴 정지 회원 설정
	
	/**
	 * @Method 메소드명  : stopMember
	 * @작성일     : 2021. 9. 11. 
	 * @작성자     : 박용복
	 * @Method 설명 : 회원을 정지 회원으로 설정
	 */
	
	public void stopAdminMember(String mem_num)throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "UPDATE member SET auth = 1 WHERE mem_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mem_num);
			
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	// 관리자 메뉴 회원 탈퇴 설정
	
	/**
	 * @Method 메소드명  : stopMember
	 * @작성일     : 2021. 9. 11. 
	 * @작성자     : 박용복
	 * @Method 설명 : 회원을 탈퇴 시킬 수 있는 설정
	 */
	
	public void deleteAdminMember(String mem_num)throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		PreparedStatement pstmt4 = null;
		PreparedStatement pstmt5 = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			conn.setAutoCommit(false);
			
			sql = "UPDATE member SET auth = 0 WHERE mem_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mem_num);
			pstmt.executeUpdate();
			
			sql = "DELETE member_detail WHERE mem_num = ?";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setString(1, mem_num);
			pstmt2.executeUpdate();
			
			sql = "DELETE comments WHERE mem_num = ?";
			pstmt3 = conn.prepareStatement(sql);
			pstmt3.setString(1, mem_num);
			pstmt3.executeUpdate();
			
			sql = "DELETE bookmark WHERE mem_num = ?";
			pstmt4 = conn.prepareStatement(sql);
			pstmt4.setString(1, mem_num);
			pstmt4.executeUpdate();
			
			sql = "DELETE recommend WHERE mem_num = ?";
			pstmt5 = conn.prepareStatement(sql);
			pstmt5.setString(1, mem_num);
			pstmt5.executeUpdate();
					
			conn.commit();
		}catch(Exception e) {
			conn.rollback();
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt5, null);
			DBUtil.executeClose(null, pstmt4, null);
			DBUtil.executeClose(null, pstmt3, null);
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	// 관리자 정지 회원을 다시 일반 회원으로 설정
	
	/**
	 * @Method 메소드명  : upAdminMember
	 * @작성일     : 2021. 9. 13. 
	 * @작성자     : 박용복
	 * @Method 설명 : 정지 회원을 다시 일반 회원으로 설정
	 */
	public void upAdminMember(String mem_num)throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "UPDATE member SET auth = 2 WHERE mem_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mem_num);
			
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
		
	}
	
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
			sql = "SELECT COUNT(*) FROM comments WHERE board_num = ?";
			
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
}
