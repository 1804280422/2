package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import vo.User;
import dao.UserDao;
 @webServlet(urlPatterns = "/login.do")
public class LoginControler extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        String vcode = request.getParameter("vcode");
        
        HttpSession session = request.getSession();
        
        String saveVcode = (String) session.getAttribute("verifyCode");
        String forwardPath = "";
        
        if(!vcode.equalsIgnoreCase(saveVcode)) {
        	
           request.setAttribute("info", "验证码不正确！");
           forwardPath = "/error.jsp";
           
        }else {
        	UserDao userDao = new UserDao();
        	User user=userDao.get(userName);
        	if (user == null) {
        		request.setAttribute("info", "您输入的用户名不存在！");
        		forwardPath = "/error.jsp";
        	}else{
        		if(!user.getPassword().equals(password)){
        			request.setAttribute("info", "您输入的密码不正确！");
        			forwardPath = "/error.jsp";
        		}
        		else {
        			session.setAttribute("currentUser", user);
        			forwardPath = "/main.jsp";
        		}
        	}
        	
        }
        RequestDispatcher rd = request.getRequestDispatcher(forwardPath);
        rd.forward(request, response);
        
		
	}

}
