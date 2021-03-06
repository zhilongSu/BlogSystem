package com.view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.BlogDao;
import com.po.Blog;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class checktagServlet
 */
@WebServlet("/CheckBlogTitleServlet")
public class CheckBlogTitleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckBlogTitleServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		doGet(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		request.setCharacterEncoding("UTF-8");
		

		String data = request.getParameter("data");		
		JSONObject jsonobj = JSONObject.fromObject(data);
		String blogtitle =jsonobj.getString("blogtitle") ;
		
		BlogDao blogdao = new BlogDao();
		Blog[] blogs= new Blog[20];
		blogs = blogdao.CheckBlog(blogtitle);
		
		PrintWriter out  = response.getWriter();
		JSONObject res = new JSONObject();
		
		if(blogs[0]!=null){
			res.put("state", 201);
			res.put("blogs", blogs);
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
