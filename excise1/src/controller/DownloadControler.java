package controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vo.Download;
import dao.DownloadDao;

public class DownloadControler extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
      String id = request.getParameter("id");
      DownloadDao dao = new DownloadDao();
      Download download=dao.get(Integer.parseInt(id));
	  
      String path = request.getServletContext().getRealPath("/WEB-INF/"+download.getPath());
	  String fileName = path.substring(path.lastIndexOf("\\") + 1);
	  response.setHeader("content-disposition","attachment;filename=",
			            + URLEncoder.encode(fileName,"UTF_8"));
	  InputStream in = new FileInputStream(path);
	  int len = 0;
	  byte[] buffer =new byte[1024];
	  ServletOutputStream out = response.getOutputStream();
	  while ((len = in.read(buffer)) != -1) {
		  out.write(buffer,0,len);
		  
	  }
	  in.close();
	  out.close();
	}

}
