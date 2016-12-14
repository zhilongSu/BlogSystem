package com.view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.UserDao;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class registerServlet
 */
@WebServlet("/RegisterUserServlet")
public class RegisterUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterUserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		request.setCharacterEncoding("UTF-8"); 
		response.setCharacterEncoding("UTF-8");
		
		String data = request.getParameter("data");		
		
		JSONObject jsonobj = JSONObject.fromObject(data);
		String username = jsonobj.getString("username");
		String password = jsonobj.getString("password");
		
		UserDao userdao = new UserDao();
		int userid = userdao.SaveUser(username,password);
		
		PrintWriter out  = response.getWriter();
		JSONObject res = new JSONObject();
		
		if(userid!=0){
			res.put("state", 201);
			res.put("userid",userid);
			out.print(res);
			out.flush();
			out.close();
			
		}else{
			res.put("state", 202);
			out.print(res);
			out.flush();
			out.close();
		}
						
		doGet(request, response);

		}
		
}
