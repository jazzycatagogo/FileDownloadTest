

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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

		//		URL url = new URL("http://localhost:8080/FileDownloadTest/tetsu.jpg");
		//		HttpURLConnection con = (HttpURLConnection)url.openConnection();
		//		con.setInstanceFollowRedirects(false);
		//		con.setAllowUserInteraction(true);
		//		con.setRequestMethod("GET");
		//		con.connect();

		//String fileName = request.getParameter("FileName");

		//File file = new File("C:\\test\\tetsu.jpg");

		//		response.setContentType("application/octet-stream");
		//		response.setHeader("Content-disposition", "attachment; filename=\"tetsu.jpg\"");
		//		response.setContentLength(con.getContentLength());
		//		response.setHeader("Expires", "0");
		//		response.setHeader("Cache-Control", "must-revalidate, post-check=0,pre-check=0");
		//		response.setHeader("Pragma", "private");

		//		int status = con.getResponseCode();
		//
		//		if(status != HttpURLConnection.HTTP_OK){
		//			throw new ServletException();
		//		}

		//		ServletOutputStream os = response.getOutputStream();
		//		BufferedInputStream bis = new BufferedInputStream(con.getInputStream());
		//
		//	     byte[] buff = new byte[500];
		//	     int len;
		//	     while ((len = bis.read(buff)) > 0) {
		//	       os.write(buff, 0, len);
		//	     }
		//	     bis.close();
		//	     os.flush();

		//		DataInputStream ins = new DataInputStream(con.getInputStream());
		//		DataOutputStream ous = new DataOutputStream(
		//				new BufferedOutputStream(
		//						new FileOutputStream("C:\\test\\tetsu.jpg")));

		//PrintWriter writer = response.getWriter();

		//		byte[] b = new byte[4096];
		//		int readByte = 0;
		//
		//
		//		while(-1 != (readByte = ins.read(b))){
		//			ous.write(b, 0, readByte);
		//		}
		//		ins.close();
		//		ous.close();

		//String fileName = request.getParameter("FileName");

		//File file = new File("C:\\test\\tetsu.jpg");
		// httpではディレクトリ以下指定ができない仕様
		// すべてのファイルを取得するにはすべてのファイル名を知る必要がある
		// FTPやNFSなら全ファイルのリストが取れそう？
		URL url = new URL("http://localhost:8080/FileDownloadTest/tetsu.jpg");
		HttpURLConnection con = (HttpURLConnection)url.openConnection();
		con.setRequestMethod("GET");
		con.connect();

		int status = con.getResponseCode();

		if(status != HttpURLConnection.HTTP_OK){
			throw new ServletException();
		}

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ZipOutputStream zos = new ZipOutputStream(baos);

		ZipEntry ze = new ZipEntry("./tetsu/tetsu.jpg");
		zos.putNextEntry(ze);
		int len = con.getContentLength();
		byte[] buf = new byte[len];
		DataInputStream dis = new DataInputStream(con.getInputStream());
		for (int i = 0; i < len; i++) {
			buf[i] = dis.readByte();
		}
		zos.write(buf, 0, len);
		dis.close();

		zos.close();

		response.setContentType("application/zip");
		// filenameはDBから取ってきた値をセットする
		response.setHeader("Content-disposition", "attachment; filename=\"tetsu.zip\"");
		response.setContentLength(baos.size());

		//PrintWriter out = response.getOutputStream();
		OutputStream os = response.getOutputStream();
		os.write(baos.toByteArray());
		System.err.println("size: " + baos.size());


		if(con != null){
			con.disconnect();
		}
	}

}
