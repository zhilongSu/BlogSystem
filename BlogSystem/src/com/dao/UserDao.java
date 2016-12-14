package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import com.po.User;
import com.util.DBconnection;

public class UserDao {
	
	
	//生成随机八位数函数
	public int eight(){
		//随机生成八位账号
		StringBuilder str=new StringBuilder();//定义变长字符串
		Random random=new Random();
		//随机生成数字，并添加到字符串
		for(int i=0;i<8;i++){
		    str.append(random.nextInt(10));
		}
		//将字符串转换为数字并输出
		int id=Integer.parseInt(str.toString());
		return id;
		
	}
	
	
//用户注册
	public int SaveUser(String username,String password){
		Connection conn=null;
		try {
			conn = DBconnection.getConnection();
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		
		try{
			
			int id = eight();
			String sql1 = "select * from user where userid = ?";
			PreparedStatement ps1 = conn.prepareStatement(sql1);
			ps1.setInt(1, id);
			ResultSet rs1 = ps1.executeQuery();
			do{
				id = eight();
				rs1 = ps1.executeQuery();
			}while(rs1.next());
			
			String sql = "insert into user(userid,username,userpassword) values(?,?,?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setInt(1,id);
			ps.setString(2, username);
			ps.setString(3, password);
						
			if(ps.executeUpdate()!=0){
				ps.close();
				return id;
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
	

    //用户登录 
	public User Login (int userid, String userpassword){
		
		User user =new User();
		
		Connection conn=null;
		try {
			conn = DBconnection.getConnection();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		try {
			String sql = "select * from user where userid = ? and userpassword = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setInt(1, userid);
			ps.setString(2, userpassword);
			
			ResultSet rs = ps.executeQuery();
	
			if(rs.next()){
				
				user.setUsername(rs.getString("username"));
				user.setUserpassword(rs.getString("userpassword"));
				user.setUserid(rs.getInt("userid"));
				
			}
			
			rs.close();
			ps.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally{
			DBconnection.closeConnection(conn);
		}
		return user;					
		}
	
	/*//修改用户信息
	public void ModifyUser(String username,String userpassword,int userid){
		Connection conn = null;
		try{
			conn = DBconnection.getConnection();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		
		String sql = "update user set username = ?,userpassword = ? where userid = ?";
		try{
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setString(1, username);
			ps.setString(2, userpassword);
			ps.setLong(3, userid);
			
			ps.executeUpdate();
			
			ps.close();	
		}catch(SQLException e) {
			e.printStackTrace();
		}finally{
			DBconnection.closeConnection(conn);
		}
	}
*/	
}
		