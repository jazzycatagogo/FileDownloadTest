

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FileDownloadServlet
 */
public class FileDownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileDownloadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		URL url = new URL("http://localhost:8080/FileDownloadTest/tetsu.jpg");
		HttpURLConnection con = (HttpURLConnection)url.openConnection();
		con.setInstanceFollowRedirects(false);
		con.setAllowUserInteraction(true);
		con.setRequestMethod("GET");
		con.connect();

		String fileName = request.getParameter("FileName");

		//File file = new File("C:\\test\\tetsu.jpg");

		response.setContentType("application/octet-stream");
		response.setHeader("Content-disposition", "attachment; filename=\"" + fileName + "\"");
		response.setContentLength(con.getContentLength());
		response.setHeader("Expires", "0");
		response.setHeader("Cache-Control", "must-revalidate, post-check=0,pre-check=0");
		response.setHeader("Pragma", "private");

		int status = con.getResponseCode();

		if(status != HttpURLConnection.HTTP_OK){
			throw new ServletException();
		}

		ServletOutputStream os = response.getOutputStream();
		BufferedInputStream bis = new BufferedInputStream(con.getInputStream());

	     byte[] buff = new byte[500];
	     int len;
	     while ((len = bis.read(buff)) > 0) {
	       os.write(buff, 0, len);
	     }
	     bis.close();
	     os.flush();

//		DataInputStream ins = new DataInputStream(con.getInputStream());
//		DataOutputStream ous = new DataOutputStream(
//				new BufferedOutputStream(
//						new FileOutputStream(fileName)));
//
//		PrintWriter writer = response.getWriter();
//
//		byte[] b = new byte[4096];
//		int readByte = 0;
//

//		while(-1 != (readByte = ins.read(b))){
//			ous.write(b, 0, readByte);
//		}
//		ins.close();
//		ous.close();

	}

}
