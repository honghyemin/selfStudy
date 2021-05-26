package board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardService {
	BoardDAO boardDAO;

	public BoardService() {

		// 생성자 호출 시 BoardDAO객체 생성
		boardDAO = new BoardDAO();
	}

	public List<BoardVO> boardList() {
		List<BoardVO> list = boardDAO.viewAllBoard();
		return list;
	}
	
	public Map boardList(Map<String, Integer> pagingMap) {
		Map postingMap = new HashMap();
		// 전달된 pagingMap을 사용해 글 목록 조회
		List<BoardVO> boardList = boardDAO.viewAllBoard(pagingMap);
		// 테이블에 존재하는 전체 글 수 조회
		int totPosting = boardDAO.selectToPosting();
		// 조회된 글 목록을 ArrayList에 저장한 후 다시 postingMap에 저장
		postingMap.put("boardList", boardList);
		// 전체 글 수를 postingMap에 저장
		postingMap.put("totPosting", totPosting);
		
		return postingMap;
		
	}
	
	
	
	
	public int addPosting(BoardVO board) {
		
		return boardDAO.addPosting(board);
	}
	
	public BoardVO viewPosting(int articleNum) {
		BoardVO board = null;
		board = boardDAO.selectPosting(articleNum);
		return board;
	}
	
	public void updatePosting(BoardVO board) {
		boardDAO.updatePosting(board);
	}
	
	public List<Integer> removePosting(int articleNum) {
		List<Integer> articleNumList = boardDAO.selectRemovedPostings(articleNum);
		boardDAO.deletePosting(articleNum);
		return articleNumList;
	}
	
	public int addReply(BoardVO board) {
		return boardDAO.addPosting(board);
	}

}
