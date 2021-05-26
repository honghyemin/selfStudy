package board;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import common.Close;

public class BoardDAO {
	private DataSource dataFactory;
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	Close close = new Close();
	
	public BoardDAO() {
		try {
			Context ctx = new InitialContext();
			Context envContext = (Context) ctx.lookup("java:/comp/env");
			dataFactory = (DataSource) envContext.lookup("jdbc/oracle");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 페이징 게시판 목록
	public List viewAllBoard(Map<String, Integer> pagingMap){
		List<BoardVO> list = new ArrayList<BoardVO>();
		
		//전송된 section과 pageNum 값을 가ㅈ온다
		int section = pagingMap.get("section");
		int pageNum = pagingMap.get("pageNum");
		
		try {
			conn = dataFactory.getConnection();
			String query = " SELECT * FROM ( " + " SELECT ROWNUM AS recNUM,"
							+ "LVL," + "articleNO,"
							+ "parentNO, " + "title," + " id," +"writeDate "
							+ " FROM (SELECT LEVEL AS LVL, "
							+ " articleNO," + "parentNO, " + "title," + "id,"
							+ " writeDate" + " FROM h_board"
							+ " START WITH parentNO=0" + " CONNECT BY PRIOR articleNO = parentNO"
							+ " ORDER SIBLINGS BY articleNO DESC)" + ") "
							+ " WHERE recNum between(?-1)*100+(?-1)*10+1 and (?-1)*100+?*10"; // section값과 pageNum 값으로 레코드 번호의 범위를 조건으로 정한다(이들 값이 각각 1로 전송되었으면 between1 and 10이 된다.
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, section);
			pstmt.setInt(2, pageNum);
			pstmt.setInt(3, section);
			pstmt.setInt(4, pageNum);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int level = rs.getInt("lvl");
				int articleNum = rs.getInt("articleNO");
				int parentNum = rs.getInt("parentNO");
				String title = rs.getString("title");
				String id = rs.getString("id");
				Date writeDate = rs.getDate("writeDate");
				BoardVO boardVO = new BoardVO();
				boardVO.setLevel(level);
				boardVO.setArticleNum(articleNum);
				boardVO.setParentNum(parentNum);
				boardVO.setTitle(title);
				boardVO.setId(id);
				boardVO.setWriteDate(writeDate);
				list.add(boardVO);
			}
			
			System.out.println("페이징:"+list.toString());
					
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close.closeConnStmtRs(conn, pstmt, rs);
		}
		return list;
		
		
	}
	

	// ?
	public int selectToPosting() {
		
		try {
			conn = dataFactory.getConnection();
			String query = "SELECT COUNT(articleNO) from h_board ";
			pstmt = conn.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next())
				return (rs.getInt(1));
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close.closeConnStmtRs(conn, pstmt, rs);
		}
		return 0;
	}
	
	
	// 게시판 글 목록 조회
	public List<BoardVO> viewAllBoard(){
		List<BoardVO> list = new ArrayList<>();
		
		
		
		try {
			conn = dataFactory.getConnection();
			String query = " SELECT LEVEL, articleNO, parentNO, title, content, id, writeDate ";
			query += " FROM h_board START WITH parentNO=0 ";
			query += " CONNECT BY PRIOR articleNO=parentNO ";
			query += " ORDER SIBLINGS BY articleNO DESC ";
			
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				int level = rs.getInt("level");
				int articleNO = rs.getInt("articleNO");
				int parentNO = rs.getInt("parentNO");
				String title = rs.getString("title");
				String content = rs.getString("content");
				String id = rs.getString("id");
				Date writeDate = rs.getDate("writeDate");
				BoardVO boardVO = new BoardVO();
				boardVO.setLevel(level);
				boardVO.setArticleNum(articleNO);
				boardVO.setParentNum(parentNO);
				boardVO.setTitle(title);
				boardVO.setContent(content);
				boardVO.setId(id);
				boardVO.setWriteDate(writeDate);
				list.add(boardVO);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close.closeConnStmtRs(conn, pstmt, rs);
		}
		
		return list;
		
	}
	// 게시판 글쓰기 전에 마지막 글번호 반환
	public int newArticleNum() {
		try {
			conn = dataFactory.getConnection();
			String query = " SELECT MAX(ARTICLENO) AS MAX FROM h_board ";
			pstmt = conn.prepareStatement(query);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				return (rs.getInt("MAX")+1);
			}
			
		} catch (SQLException e) { 
			e.printStackTrace();
		
		} finally {
			close.closeConnStmtRs(conn, pstmt, rs);
			
		}
		
		return 0;
	}
	
	
	// 게시판 글쓰기
	public int addPosting(BoardVO board) {
		// 아이디 연결해서 하고ㅅ;ㅣㅍ은ㄷ=ㅇ..
		
		
		int articleNum = newArticleNum();
		int parentNum = board.getParentNum();
		String title = board.getTitle();
		String content = board.getContent();
		String imageFileName = board.getImageFileName();
		String id = board.getId();
		
		try {
			conn = dataFactory.getConnection();
			String query = " INSERT INTO h_board (ARTICLENO, PARENTNO, TITLE, CONTENT, IMAGEFILENAME, WRITEDATE, ID) ";
				   query += " VALUES (?, ?, ?, ?, ?, SYSDATE, ? ) ";
				   
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, articleNum);
			pstmt.setInt(2, parentNum);
			pstmt.setString(3, title);
			pstmt.setString(4, content);
			pstmt.setString(5, imageFileName);
			pstmt.setString(6, id);
			
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close.closeConnStmt(conn, pstmt);
		}
		
		return articleNum;
	}
	
	
	// 게시판 상세 글 보기
	public BoardVO selectPosting(int articleNum) {
		
		BoardVO boardVO = new BoardVO();
		
		try {
			conn = dataFactory.getConnection();
			String query = " SELECT articleNO, parentNO, title, content, imageFileName, id, writeDate";
				   query += " FROM h_board WHERE articleNO=? ";
				   
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, articleNum);
			ResultSet rs = pstmt.executeQuery();
			
			rs.next();
			int _articleNum = rs.getInt("articleNO");
			int parentNum = rs.getInt("parentNO");
			String title = rs.getString("title");
			String content = rs.getString("content");
			String imageFileName = rs.getString("imageFileName");
			String id = rs.getString("id");
			Date writeDate = rs.getDate("writeDate");
			
			boardVO.setArticleNum(_articleNum);
			boardVO.setParentNum(parentNum);
			boardVO.setTitle(title);
			boardVO.setContent(content);
			boardVO.setImageFileName(imageFileName);
			boardVO.setId(id);
			boardVO.setWriteDate(writeDate);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close.closeConnStmtRs(conn, pstmt, rs);
			
		}
		return boardVO;
	}
	
	// 글 수정하기
	public void updatePosting(BoardVO boardVO) {
		
		int articleNum = boardVO.getArticleNum();
		String title = boardVO.getTitle();
		String content = boardVO.getContent();
		String imageFileName = boardVO.getImageFileName();
		
		try {
			conn = dataFactory.getConnection();
			String query = " UPDATE h_board set title =?, content = ?";
			if(imageFileName != null && imageFileName.length()!=0) {
				query += ", imageFileName = ? WHERE articleNO=? ";
			} else {
				query += " where articleNO = ?";
			}
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			
			if(imageFileName != null && imageFileName.length()!=0) {
				pstmt.setString(3, imageFileName);
				pstmt.setInt(4, articleNum);
			} else {
				pstmt.setInt(3, articleNum);
			}
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close.closeConnStmtRs(conn, pstmt, rs);
		}
		
	}
	
	// 글 삭제하기
	public void deletePosting(int articleNum) {
		
		try {
			conn = dataFactory.getConnection();
			// 삭제할 게시글과 하위의 답글 까지 모두 삭제
			String query = " DELETE FROM h_board";
				   query += " WHERE articleNO in ( ";
				   query += " SELECT articleNO FROM h_board ";
				   query += " START WITH articleNO=? ";
				   query += " CONNECT BY PRIOR articleNO = parentNO )";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, articleNum);
			pstmt.executeUpdate();
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close.closeConnStmt(conn, pstmt);
		}
		
		
	}
	
	
	// ?
	public List<Integer> selectRemovedPostings(int articleNum){
		
		List<Integer> articleNumList = new ArrayList<Integer>();
		
		try {
			conn = dataFactory.getConnection();
			String query = "SELECT articleNO FROM h_board ";
					query += " START WITH articleNO = ? ";
					query += " CONNECT BY PRIOR articleNO = parentNO ";
					
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, articleNum);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				articleNum = rs.getInt("articleNO");
				articleNumList.add(articleNum);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close.closeConnStmtRs(conn, pstmt, rs);
		}
		
		return articleNumList;
		
	}
	
	
	
	
	

}
















