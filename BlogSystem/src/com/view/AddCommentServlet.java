 package com.view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.CommentDao;
import com.po.User;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class addcomServlet
 */
@WebServlet("/AddCommentServlet")
public class AddCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddCommentServlet() {
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
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		
		String data = request.getParameter("data");		
		JSONObject jsonobj = JSONObject.fromObject(data);
		int blogid = Integer.parseInt(jsonobj.getString("blogid"));
		String commentcontent = jsonobj.getString("commentcontent");
		String username = user.getUsername();
		
		CommentDao commentdao = new CommentDao();
		
		PrintWriter out  = response.getWriter();
		JSONObject res = new JSONObject();
		
		if(commentdao.AddComment(username,blogid,commentcontent)!=0){
			res.put("state", 201);
			res.put("username", username);
			out.print(res);
			out.flush();
			out.close();
		}else{
			res.put("state", 202);
			out.print(res);
			out.flush();
			out.close();
		}
	}

}
