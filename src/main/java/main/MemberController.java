package main;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;

import board.BoardService;
import board.BoardVO;
import member.MemberDAO;
import member.MemberVO;
import common.SaveID;

/**
 * Servlet implementation class MainController
 */
@WebServlet("/mct/*")
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String POSTING_IMAGE_REPO = "C:\\stp\\image";
	MemberDAO memberDAO;
	MemberVO memberVO;
	BoardService boardService;
	BoardVO boardVO;
	

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MemberController() {
		super();

	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		memberDAO = new MemberDAO();
		boardService = new BoardService();
		boardVO = new BoardVO();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doHandle(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doHandle(request, response);
	}

	private void doHandle(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String nextPage = null;
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset= utf-8");

		String action = request.getPathInfo();
		System.out.println("action:" + action);
		HttpSession session; // 답글에 대한 부모 글 번호 저장위해 사용

		try {
			List<BoardVO> list = new ArrayList<BoardVO>();

			// 로그인페이지에서 요청
			if (action.equals("/login.do")) {
				String id = request.getParameter("id");
				String pwd = request.getParameter("pwd");
				SaveID.myId = id;

				boolean result = memberDAO.memberLogin(id, pwd);

				if (result == true) {
					request.setAttribute("id", id);
					nextPage = "/member/result.jsp";

				} else {
					nextPage = "/member/falseResult.jsp";
				}
			// 로그아웃
			} else if(action.equals("/logout.do")) {
				SaveID.myId = null;
				nextPage = "/member/login.jsp";
				

				// 회원가입창에서 요청
			} else if (action.equals("/joinForm.do")) {

				MemberVO vo = new MemberVO();
				String id = request.getParameter("id");
				String pwd = request.getParameter("pwd");
				String name = request.getParameter("name");
				String email = request.getParameter("email");

				vo = new MemberVO(id, pwd, name, email);
				memberDAO.joinMember(vo);

				// 회원가입후 로그인페이지 이동
				nextPage = "/member/login.jsp";

				// 아이디 찾기
			} else if (action.equals("/searchId.do")) {
				MemberVO vo = new MemberVO();
				String name = request.getParameter("name");
				String email = request.getParameter("email");
				String id = memberDAO.searchId(name, email);
				

				if (id == null) {
					request.setAttribute("msg", "회원 정보가 존재하지 않습니다.");
					nextPage = "/member/searchInfo.jsp";
					// return;
				} else {

					request.setAttribute("msg", "아이디는 " + id + "입니다.");
					nextPage = "/member/searchInfo.jsp";

				}

				//이메일과 아이디로 비밀번호 찾기
			} else if (action.equals("/searchPwd2.do")) {

				String name = request.getParameter("name");
				String email = request.getParameter("email");
				String pwd = memberDAO.searchPwd(name, email);

				

				if (pwd == null) {
					request.setAttribute("msg", "잘못된 정보를 입력했습니다.");
					nextPage = "/member/searchInfo.jsp";

				} else {
					
					request.setAttribute("msg", "비밀번호는 "+ pwd+"입니다.");
					nextPage = "/member/searchInfo.jsp";

				}
				//  아이디 유무 리턴(비밀번호 찾기)
			} else if (action.equals("/searchPwd1.do")) {

				String id = request.getParameter("pwdId");
				Boolean result = memberDAO.searchPwdForId(id);

				System.out.println("result " + result);

				if (result == false) {
					request.setAttribute("msg", "아이디가 존재하지 않습니다.");
					nextPage = "/member/searchInfo.jsp";

				} else {
					// 비밀번호 찾기 화면 띄우고싶다...
					request.setAttribute("msg", "비밀번호 찾기화면");
					nextPage = "/member/SearchPwd.jsp";
					

				}
			// 내정보 리턴
			} else if (action.equals("/showMyInfo.do")) {
				String id = SaveID.myId;
				memberVO = new MemberVO();
				memberVO = memberDAO.myInfo(id);
				String name = memberVO.getName();
				String email = memberVO.getEmail();
				
				request.setAttribute("id", id);
				request.setAttribute("name", name);
				request.setAttribute("email", email);
				
				nextPage = "/member/myPage.jsp";
			
			// 내가 쓴 글 리턴
			} else if (action.equals("/myPostingList.do")) {
				
				String id = SaveID.myId;
				List<BoardVO> myList = new ArrayList<BoardVO>();
				myList = boardService.myLists(id);
				request.setAttribute("myList", myList);
				nextPage = "/member/myList.jsp";
				
			} else if (action.equals("/boardMain.do")) {

				// /boardMain.do로 요청 시 section값과 pageNum값 구하기
				String _section = request.getParameter("section");
				String _pageNum = request.getParameter("pageNum");

				System.out.println(_section + "," + _pageNum);

				// section값과 pageNum값이 없으면 각각1로 초기화
				int section = Integer.parseInt(((_section == null) ? "1" : _section));
				int pageNum = Integer.parseInt(((_pageNum == null) ? "1" : _pageNum));

				// section값과 pageNum값을 HashMap에 저장한후 메서드로 넘긴다.
				Map<String, Integer> pagingMap = new HashMap<String, Integer>();
				pagingMap.put("section", section);
				pagingMap.put("pageNum", pageNum);

				// section값과 pageNum 값으로 해당 섹션과 페이지에 해당 되는 글 목록 조회
				Map postingMap = boardService.boardList(pagingMap);

				// 브라우저에서 전송된 section과 pageNum값을 articlesMap에 저장한 후 listArticles.jsp로 넘긴다.
				postingMap.put("section", section);
				postingMap.put("pageNum", pageNum);

				// 조회된 글 목록을 postingMap으로 바인딩하여 boardMain.jsp로 넘긴다
				request.setAttribute("postingMap", postingMap);
				nextPage = "/board/boardMain.jsp";

				// 글쓰기 창
			} else if (action.equals("/writingForm.do")) {
				nextPage = "/board/writingForm.jsp";

			} else if (action.equals("/writing.do")) {
				int articleNum = 0;
				Map<String, String> postingMap = upload(request, response);

				String title = postingMap.get("title");
				String content = postingMap.get("content");
				String imageFileName = postingMap.get("imageFileName");

				boardVO.setParentNum(0);
				boardVO.setId(SaveID.myId);
				boardVO.setTitle(title);
				boardVO.setContent(content);
				boardVO.setImageFileName(imageFileName);

				// 새 글에 대한 글번호 가져오기
				articleNum = boardService.addPosting(boardVO);

				// 파일을 첨부한 경우에만 수행
				if (imageFileName != null && imageFileName.length() != 0) {
					File srcFile = new File(POSTING_IMAGE_REPO + "\\" + "temp" + "\\" + imageFileName);
					File destDir = new File(POSTING_IMAGE_REPO + "\\" + articleNum);
					destDir.mkdirs();
					FileUtils.moveFileToDirectory(srcFile, destDir, true);
				}
				PrintWriter pw = response.getWriter();
				pw.print("<script>" + " alert('새글을 추가했습니다.');" + " location.href='" + request.getContextPath()
						+ "/mct/boardMain.do';" + "</script>");

				return;

				// 글 목록에서 글을 클릭하여 들어감
			} else if (action.equals("/viewPosting.do")) {
				// 글 상세창을 요청할 경우 articleNum을 가져온다.

				String articleNum = request.getParameter("articleNum");

				boardVO = boardService.viewPosting(Integer.parseInt(articleNum));
				request.setAttribute("board", boardVO);

				nextPage = "/board/viewPosting.jsp";

				// 수정하기
			} else if (action.equals("/modPosting.do")) {
				Map<String, String> postingMap = upload(request, response);
				int articleNum = Integer.parseInt(postingMap.get("articleNum"));

				boardVO.setArticleNum(articleNum);
				String title = postingMap.get("title");
				String content = postingMap.get("content");
				String imageFileName = postingMap.get("imageFileName");

				boardVO.setParentNum(0);
				boardVO.setId(SaveID.myId);
				boardVO.setTitle(title);
				boardVO.setContent(content);
				boardVO.setImageFileName(imageFileName);

				boardService.updatePosting(boardVO);

				if (imageFileName != null && imageFileName.length() != 0) {
					String originalFileName = postingMap.get("originalFileName");
					File srcFile = new File(POSTING_IMAGE_REPO + "\\temp\\" + imageFileName);
					File destDir = new File(POSTING_IMAGE_REPO + "\\" + articleNum);
					destDir.mkdirs();
					FileUtils.moveFileToDirectory(srcFile, destDir, true);

					File oldFile = new File(POSTING_IMAGE_REPO + "\\" + articleNum + "\\" + originalFileName);
					oldFile.delete();

				}
				PrintWriter pw = response.getWriter();
				pw.print("<script>" + " alert('글 수정 완료!');" + " location.href='" + request.getContextPath()
						+ "/mct/viewPosting.do?articleNum=" + articleNum + "';" + "</script>");
				return;

			} else if (action.equals("/removePosting.do")) {
				int articleNum = Integer.parseInt(request.getParameter("articleNum"));
				List<Integer> articleNumList = boardService.removePosting(articleNum);
				for (int _articleNum : articleNumList) {
					File imgDir = new File(POSTING_IMAGE_REPO + "\\" + _articleNum);
					if (imgDir.exists()) {
						FileUtils.deleteDirectory(imgDir);
					}
				}

				PrintWriter pw = response.getWriter();
				pw.print("<script>" + " alert('글 삭제 완료!');" + " location.href='" + request.getContextPath()
						+ "/mct/boardMain.do';" + "</script>");

				return;

				// 답글작성하기로 요청
			} else if (action.equals("/replyForm.do")) {
				// 답글 창 요청 시 미리 부모 글 번호를 parentNum속성으로 세션에 저장
				int parentNum = Integer.parseInt(request.getParameter("parentNum"));
				session = request.getSession();
				session.setAttribute("parentNum", parentNum);
				nextPage = "/board/replyForm.jsp";

				// 답글 작성 후
			} else if (action.equals("/addReply.do")) {
				// 답글 전송 시 세션에 저장된 parentNum을 가져온다.
				session = request.getSession();
				int parentNum = (Integer) session.getAttribute("parentNum");
				session.removeAttribute("parentNum");

				Map<String, String> postingMap = upload(request, response);
				String title = postingMap.get("title");
				String content = postingMap.get("content");
				String imageFileName = postingMap.get("imageFileName");
				boardVO.setParentNum(parentNum);
				boardVO.setId(SaveID.myId);
				boardVO.setTitle(title);
				boardVO.setContent(content);
				boardVO.setImageFileName(imageFileName);
				int articleNum = boardService.addReply(boardVO);
				if (imageFileName != null && imageFileName.length() != 0) {

					File srcFile = new File(POSTING_IMAGE_REPO + "\\temp\\" + imageFileName);
					File destDir = new File(POSTING_IMAGE_REPO + "\\" + articleNum);
					destDir.mkdirs();
					FileUtils.moveFileToDirectory(srcFile, destDir, true);

				}
				System.out.println("여기까진 실행완료!");
				PrintWriter pw = response.getWriter();
				pw.print("<script>" + " alert('답글 작성 완료!');" + " location.href='" + request.getContextPath()
						+ "/mct/viewPosting.do?articleNum=" + articleNum + "';" + "</script>");

				return;
			}
			request.setAttribute("myId", SaveID.myId);
			RequestDispatcher rd = request.getRequestDispatcher(nextPage);
			rd.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Map<String, String> upload(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map<String, String> postingMap = new HashMap<String, String>();
		String encoding = "utf-8";
		File currentDirPath = new File(POSTING_IMAGE_REPO);
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setRepository(currentDirPath);
		factory.setSizeThreshold(1024 * 1024);
		ServletFileUpload upload = new ServletFileUpload(factory);

		try {
			List items = upload.parseRequest(request);
			for (int i = 0; i < items.size(); i++) {
				FileItem fileItem = (FileItem) items.get(i);
				if (fileItem.isFormField()) {
					System.out.println(fileItem.getFieldName() + "=" + fileItem.getString(encoding));
					postingMap.put(fileItem.getFieldName(), fileItem.getString(encoding));
				} else {
					System.out.println("파일이름 : " + fileItem.getFieldName());
					System.out.println("파일크기 : " + fileItem.getSize() + "bt");

					if (fileItem.getSize() > 0) {
						int idx = fileItem.getName().lastIndexOf("\\");
						if (idx == -1) {
							idx = fileItem.getName().lastIndexOf("/");
						}

						String fileName = fileItem.getName().substring(idx + 1);
						postingMap.put(fileItem.getFieldName(), fileName);// 익스플로러에서 업로드 파일의 경로 제거 후 map에 파일명 저장
						File uploadFile = new File(currentDirPath + "\\temp\\" + fileName);
						fileItem.write(uploadFile);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return postingMap;
	}

}
