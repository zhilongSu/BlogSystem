package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.po.Blog;
import com.util.DBconnection;


public class BlogDao {
	
	//添加博客
	public int AddBlog(String username,int userid,String blogtitle,String tagname,String blogcontent){
		
		Connection conn=null;
		try {
			conn = DBconnection.getConnection();
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		
		String sql = "insert into blog(tagname,blogtitle,blogcontent,userid,username) values(?,?,?,?,?)";
		try{
			PreparedStatement ps = conn.prepareStatement(sql);
			//将sql中的占位符动态赋值
			ps.setString(1,tagname);
			ps.setString(2, blogtitle);
			ps.setString(3, blogcontent);			
			ps.setInt(4, userid);
			ps.setString(5, username);
			//执行更新
			if(ps.executeUpdate()!=0){
				//关闭PreparedStatement和释放JDBc资源；
				ps.close();
				return 1;
			}else{
				return 0;
			}
			
		}
		catch (Exception e) {
            e.printStackTrace();
            return 0;
        }finally{
           //关闭数据库
        	DBconnection.closeConnection(conn);
        }
	}
	
	//查看博文,通过标博文名
		public Blog[] CheckBlog (String blogtitle){
			
			Connection conn=null;
			try {
				conn = DBconnection.getConnection();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			String sql = "select * from blog where blogtitle like ?";
			Blog[] blogs = new Blog[20];
			try {
				PreparedStatement ps = conn.prepareStatement(sql);
				//将sql中的占位符动态赋值
				ps.setString(1, "%"+blogtitle+"%");
				
				ResultSet rs = ps.executeQuery();
				
				int i = 0;
				while(rs.next()){
					Blog blog = new Blog();
					//为blog赋值
					blog.setBlogid(rs.getInt("blogid"));
					blog.setBlogtitle(rs.getString("blogtitle"));
					blog.setBlogcontent(rs.getString("blogcontent"));
					blog.setTagname(rs.getString("tagname"));
					blog.setUsername(rs.getString("username"));
					blog.setUserid(rs.getInt("userid"));
					blogs[i]=blog;
					i++;
				}
				rs.close();
				ps.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}finally{
				DBconnection.closeConnection(conn);
			}
			return blogs;					
			}
		
		//查看博文，通过标签
		public Blog[] CheckBlogByTag (String tagname){
			
			
			Connection conn=null;
			try {
				conn = DBconnection.getConnection();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			Blog[] blogs1 = new Blog[20];
			try {
				String sql = "select * from blog where tagname = ?";
				PreparedStatement ps = conn.prepareStatement(sql);
				//将sql中的占位符动态赋值
				ps.setString(1, tagname);
				
				ResultSet rs = ps.executeQuery();
	
				int i = 0;
				while(rs.next()){	
					//为blog赋值
					Blog blog = new Blog();
					System.out.println(rs.getInt("blogid"));
					blog.setBlogid(rs.getInt("blogid"));
					blog.setBlogtitle(rs.getString("blogtitle"));
					blog.setBlogcontent(rs.getString("blogcontent"));
					blog.setTagname(rs.getString("tagname"));
					blog.setUsername(rs.getString("username"));
					blog.setUserid(rs.getInt("userid"));
					blogs1[i] = blog;
					i++;
				}
				rs.close();
				ps.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}finally{
				DBconnection.closeConnection(conn);
			}
			return blogs1;					
			}
		
		//查看博文,通过blogid
		public Blog CheckBlogById (int blogid){
			Blog blog =new Blog();
			Connection conn=null;
			try {
				conn = DBconnection.getConnection();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			try {
				String sql = "select * from blog where blogid = ?";
				PreparedStatement ps = conn.prepareStatement(sql);
				//将sql中的占位符动态赋值
				ps.setLong(1, blogid);
				
				ResultSet rs = ps.executeQuery();
		
				if(rs.next()){
					//为blog赋值
					blog.setBlogid(rs.getInt("blogid"));
					blog.setBlogtitle(rs.getString("blogtitle"));
					blog.setBlogcontent(rs.getString("blogcontent"));
					blog.setTagname(rs.getString("tagname"));
					blog.setUsername(rs.getString("username"));
					System.out.print(rs.getString("username"));
					blog.setUserid(rs.getInt("userid"));							
				}
				rs.close();
				ps.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}finally{
				DBconnection.closeConnection(conn);
			}
			return blog;					
			}

     public Blog[] CheckMyBlog (String tagname,int userid){
			
			
			Connection conn=null;
			try {
				conn = DBconnection.getConnection();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			Blog[] blogs1 = new Blog[20];
			try {
				String sql = "select * from blog where tagname = ? and userid = ?";
				PreparedStatement ps = conn.prepareStatement(sql);
				//将sql中的占位符动态赋值
				ps.setString(1, tagname);
				ps.setInt(2, userid);
				ResultSet rs = ps.executeQuery();
	
		 		int i = 0;
				while(rs.next()){	
					//为blog赋值
					Blog blog = new Blog();
					System.out.println(rs.getInt("blogid"));
					blog.setBlogid(rs.getInt("blogid"));
					blog.setBlogtitle(rs.getString("blogtitle"));
					blog.setBlogcontent(rs.getString("blogcontent"));
					blog.setTagname(rs.getString("tagname"));
					blog.setUsername(rs.getString("username"));
					blog.setUserid(rs.getInt("userid"));
					blogs1[i] = blog;
					i++;
				}
				rs.close();
				ps.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}finally{
				DBconnection.closeConnection(conn);
			}
			return blogs1;					
			}
/*			
	//修改博客
	public void ModifyBlog(String blogtitle,String blogcontent,String tagname,int blogid){
		Connection conn = null;
		try{
			conn = DBconnection.getConnection();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		
		String sql = "update blog set blogtitle = ?,blogcontent = ?,tagname=? where blogid = ?";
		try{
			PreparedStatement ps = conn.prepareStatement(sql);
			//将sql中的占位符动态赋值
			ps.setString(1, blogtitle);
			ps.setString(2, blogcontent);
			ps.setString(3, tagname);
			ps.setInt(4, blogid);
			//执行更新语句
			ps.executeUpdate();
			//关闭PreparedStatement和释放JDBc资源；
			ps.close();	
		}catch(SQLException e) {
			e.printStackTrace();
		}finally{
			DBconnection.closeConnection(conn);
		}
	}*/
	
	//删除博客
	public int DeleteBlog(int blogid){
		Connection conn = null;
		try{
			conn = DBconnection.getConnection();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		try{
			String sql = "delete from blog where blogid = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			//将sql中的占位符动态赋值
			ps.setInt(1, blogid);
			//执行更新语句
			if(ps.executeUpdate()!=0){
				//关闭PreparedStatement和释放JDBc资源；
				ps.close();
				return 1;
			}else{
				return 0;
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
			return 0;
		}finally{
			DBconnection.closeConnection(conn);
		}
	}
	
	
}
		
