package member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import common.Close;

public class MemberDAO {
	private DataSource dataFactory;
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	Close close = new Close();
	
	public MemberDAO() {
		try {
			Context ctx = new InitialContext();
			Context envContext = (Context) ctx.lookup("java:/comp/env");
			dataFactory = (DataSource) envContext.lookup("jdbc/oracle");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 로그인
	public boolean memberLogin(String id, String pwd) {
		boolean result = false;
		
		
		try {
			conn = dataFactory.getConnection();
			String query = " SELECT ID, PASSWORD FROM h_member where id = ? ";
			pstmt = conn.prepareStatement(query);
			
			
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				if(rs.getString("id").equals(id) && rs.getString("password").equals(pwd)) {
					result = true;
				}
				
				
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close.closeConnStmtRs(conn, pstmt, rs);
		}
		
		
		
		return result;
		
	}
	
	
	
	
	// 회원가입
	public void joinMember(MemberVO memberVO) {
		
		try {
			conn = dataFactory.getConnection();
			String query = " INSERT INTO h_member (id, password, name, email, joindate) ";
			query += " VALUES ( ?, ?, ?, ?, SYSDATE ) ";
			
			pstmt = conn.prepareStatement(query);
			
			
			pstmt.setString(1, memberVO.getId());
			pstmt.setString(2, memberVO.getPwd());
			pstmt.setString(3, memberVO.getName());
			pstmt.setString(4, memberVO.getEmail());
			
			pstmt.executeUpdate();
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close.closeConnStmt(conn, pstmt);
			
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
