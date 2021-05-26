package common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FileDownloadController
 */
@WebServlet("/download.do")
public class FileDownloadController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String POSTING_IMAGE_REPO = "C:\\stp\\image";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileDownloadController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doHandle(request, response);
	}

	private void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		String imageFileName = (String)request.getParameter("imageFileName");
		String articleNum = request.getParameter("articleNum");
		System.out.println("imageFileName=" + imageFileName);
		OutputStream out = response.getOutputStream();
		String path = POSTING_IMAGE_REPO + "\\" + articleNum + "\\" + imageFileName;
		File imageFile = new File(path);
		
		//- Cache-control 응답헤더 : HTTP 1.1 버전에서 지원하는 헤더, 값을 'no-cache'로 지정하면 캐시에 저장하지 않는다
		response.setHeader("Cache-control", "no-cache");
		// 이미지 파일을 내려 받는 데 필요한 response에 헤더 정보 설정
		response.addHeader("Content-disposition", "attachment;fileName=" + imageFileName);
		FileInputStream in = new FileInputStream(imageFile);
		
		// 버퍼를 이용해 한 번에 8kb씩 전송
		byte[] buffer = new byte[1024*8];
		while(true) {
			int count = in.read(buffer);
			if(count==-1) 
				break;
			out.write(buffer, 0, count);
		}
		in.close();
		out.close();
		
		
		
	}

}
