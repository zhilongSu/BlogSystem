package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.po.Comment;
import com.util.DBconnection;

public class CommentDao {
	//增加评论
		public int AddComment(String username,int blogid,String commentcontent){
			
			Connection conn=null;
			try {
				conn = DBconnection.getConnection();
			} catch (Exception ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
			
			String sql = "insert into comment(commentcontent,blogid,username) values(?,?,?)";
			try{
				PreparedStatement ps = conn.prepareStatement(sql);
				
	
				ps.setString(1, commentcontent);
				ps.setInt(2, blogid);
				ps.setString(3, username);
				
				if(ps.executeUpdate()!=0){
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
	        	DBconnection.closeConnection(conn);
	        }
		}
	//查看评论
		public Comment[] CheckComment (int blogid){
			
			Comment[] comments =new Comment[100];
			
			Connection conn=null;
			try {
				conn = DBconnection.getConnection();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			String sql = "select * from comment where blogid = ?";
			try {
				
				PreparedStatement ps = conn.prepareStatement(sql);
			
				ps.setInt(1,blogid);
				
				ResultSet rs = ps.executeQuery();
				
				int i = 0;
				while(rs.next()){
					Comment comment = new Comment();
					comment.setCommentid(rs.getInt("commentid"));
					comment.setCommentcontent(rs.getString("commentcontent"));
					comment.setBlogid(rs.getInt("blogid"));
					comment.setUsername(rs.getString("username"));
					comments[i] = comment;
					i++;
				}
				rs.close();
				ps.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}finally{
				DBconnection.closeConnection(conn);
			}
			return comments;					
			}
		
	//删除评论
		public int DeleteComment(int comid){
			
			Connection conn = null;
			try{
				conn = DBconnection.getConnection();
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
			
			String sql = "delete from comment where commentid = ?";
			try{
				PreparedStatement ps = conn.prepareStatement(sql);
				
				ps.setLong(1, comid);
				
				if(ps.executeUpdate()!=0){
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
