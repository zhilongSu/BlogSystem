package com.view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.BlogDao;
import com.po.Blog;
import com.po.User;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class checktagServlet
 */
@WebServlet("/CheckBlogIdServlet")
public class CheckBlogIdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckBlogIdServlet() {
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
		
		String data = request.getParameter("data");		
		JSONObject jsonobj = JSONObject.fromObject(data);
		int blogid =Integer.parseInt(jsonobj.getString("blogid")) ;
		
		BlogDao blogdao = new BlogDao();
		Blog blog= new Blog();
		blog = blogdao.CheckBlogById(blogid);
		
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		int userid = user.getUserid();
		
		PrintWriter out  = response.getWriter();
		JSONObject res = new JSONObject();
		if(blog.getBlogid()!=0){
			res.put("state", 201);
			res.put("blog", blog);
			res.put("userid", userid);
			System.out.print(blog.getUsername());
			if(userid==blog.getUserid()){
				res.put("ismine", true);
			}else{
				res.put("ismine", false);
			}
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
