package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.resource.cci.ResultSet;
import javax.servlet.http.HttpServlet;

import vo.User;

public class UserDao extends HttpServlet {
         public User get(String userName)  {
        	 User user = null;
        	 try{
        		 
        	    Class.forName("com.mysq.jdbc.Driver");
        	    
        	    Connection con=DriverManager.getConnection(
        	    		"jdbc:mysql://127.0.0.1.3306/excise?useunicode=true&character=utf-8",
        	    		"root","990428");
        	    String sql="select * from t_user where username=?";
        	    PreparedStatement pst=con.prepareStatement(sql);
        	    pst.setString(1, userName);
        	    ResultSet rs=pst.executeQuery();
        	    if(rs.next()){
        	    	user=new User(rs.getString("userName"),
        	    			rs.getString("password")
        	    			,rs.getString("chrName"));
        	    }
        	    
        	    con.close();
        	 }
        	 catch (Exception e) {
        		 e.printStackTrace();
        	 }
        	 return user;
	
	
           }
}
