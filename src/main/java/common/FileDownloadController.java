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
		
		//- Cache-control ???????????? : HTTP 1.1 ???????????? ???????????? ??????, ?????? 'no-cache'??? ???????????? ????????? ???????????? ?????????
		response.setHeader("Cache-control", "no-cache");
		// ????????? ????????? ?????? ?????? ??? ????????? response??? ?????? ?????? ??????
		response.addHeader("Content-disposition", "attachment;fileName=" + imageFileName);
		FileInputStream in = new FileInputStream(imageFile);
		
		// ????????? ????????? ??? ?????? 8kb??? ??????
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
