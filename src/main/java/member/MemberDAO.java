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
	
	// 아이디 찾기 ( 이름과 이메일로 )
	public String searchId(String name, String email) {
		
		String id = null;
		
		try {
			conn = dataFactory.getConnection();
			String query = " SELECT id FROM h_member WHERE name = ? and email = ? ";
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, name);
			pstmt.setString(2, email);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				id = rs.getString("id");
				System.out.println(id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close.closeConnStmtRs(conn, pstmt, rs);
		}
		return id;
	}
	// 비밀번호 찾기(아이디 존재유무 먼저 리턴)
	public boolean searchPwdForId(String id) {
		
		try {
			conn = dataFactory.getConnection();
			String query = " SELECT id from h_member ";
			pstmt = conn.prepareStatement(query);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				if (rs.getString("id").equals(id)) {
					return true;
				}
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			close.closeConnStmtRs(conn, pstmt, rs);
		}

		return false;
	}
	
	
	
	// 비밀번호 찾기 ( 이름, 이메일로 )
		public String searchPwd(String name, String email) {
			
			String pwd = null;
			
			try {
				conn = dataFactory.getConnection();
				String query = " SELECT password FROM h_member WHERE name = ? and email = ? ";
				pstmt = conn.prepareStatement(query);
		
				pstmt.setString(1, name);
				pstmt.setString(2, email);
				
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					pwd = rs.getString("password");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return pwd;
		}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
