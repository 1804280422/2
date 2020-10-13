package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;

import vo.Download;

public class DownloadDao extends HttpServlet {

	public ArrayList<Download> query() {
		ArrayList<Download> list = new ArrayList<Download>();
		try {
			Class.forName("com.mysq.jdbc.Driver");
    	    
    	    Connection con=DriverManager.getConnection(
    	    		"jdbc:mysql://127.0.0.1.3306/excise?useunicode=true&character=utf-8",
    	    		"root","990428");
    	    String sql = "select * from t_downloadList" ;
    	    PreparedStatement pst = con.prepareStatement(sql);
    	    ResultSet rs = pst.executeQuery();
    	    
    	 while (rs.next()) {
    		 Download download = new Download();
    		 download.setId(rs.getInt("id"));
    		 download.setName(rs.getInt("name"));
    		 download.setPath(rs.getInt("path"));
    		 download.setDescription(rs.getInt("description"));
    		 long size=rs.getLong("size");
    		 String sizeStr=fileSizeTransfer(size);
    		 download.setSizeStr(sizeStr);
    		 download.setStar(rs.getInt("star"));
    		 download.setImage(rs.getInt("image"));
    		 download.setTime(rs.getInt("time"));
    		 
    	       list.add(download);	 
    	 }
    	 con.close();
    	 
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	public Download get(int id) {
		Download download = null;
		try {
            Class.forName("com.mysq.jdbc.Driver");
    	    
    	    Connection con=DriverManager.getConnection(
    	    		"jdbc:mysql://127.0.0.1.3306/excise?useunicode=true&character=utf-8",
    	    		"root","990428");
			String sql = "select * from t_downloadList where id=?";
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1,id);
			
			ResultSet rs = pst.executeQuery();
			
			if (rs.next()) {
	    		 download = new Download();
	    		 download.setId(rs.getInt("id"));
	    		 download.setName(rs.getInt("name"));
	    		 download.setPath(rs.getInt("path"));
	    		 download.setDescription(rs.getInt("description"));
	    		 long size=rs.getLong("size");
	    		 String sizeStr=fileSizeTransfer(size);
	    		 download.setSizeStr(sizeStr);
	    		 download.setStar(rs.getInt("star"));
	    		 download.setImage(rs.getInt("image"));
	    		 download.setTime(rs.getInt("time"));
		}
			con.close();
	}catch (Exception e){
		e.printStackTrace();
	}
      return download;
	}
public String fileSizeTransfer(long fileSize) {
	String mFileSize;
	DecimalFormat df = new DecimalFormat("######0.00");
	double size = (double) fileSize;
	if(size > 1024 * 1024 * 1024) {
		size = size /(1024 * 1024 *1024);
		mFileSize = df.format(size) + "G";
	}else if (size >1024 * 1024) {
		size = size / (1024 * 1024);
		mFileSize = df.format(size) + "MB";
		
	}else if (size >1024) {
		size = size / (1024);
		mFileSize = df.format(size) + "KB";
    }else {
		mFileSize = df.format(size) + "B";
    }
	return mFileSize;
}
}