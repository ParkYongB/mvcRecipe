package com.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DBUtil {
	// DAO가 늘어날 수 있기 때문에 UtilClass를 static하게 만들어서 언제나 사용할 수 있도록 같은 코드 재활용을 한다
	// Ex) getConnection, 자원정리 메소드 등
	
	// context.xml에서 설정 정보를 읽어들여 커넥션 풀로부터 커넥션을 할당 받는 메소드 작성
	public static Connection getConnection()throws Exception{
		Context initCtx = new InitialContext();
		DataSource ds = (DataSource)initCtx.lookup("java:comp/env/jdbc/xe");
		return ds.getConnection();
	}
	
	// 자원정리
	public static void executeClose(ResultSet rs, PreparedStatement pstmt, Connection conn) {
		if(rs != null) try {rs.close();}catch(SQLException e) {}
		if(pstmt != null) try {pstmt.close();}catch(SQLException e) {}
		if(conn != null) try {conn.close();}catch(SQLException e) {}
		
	}
}
