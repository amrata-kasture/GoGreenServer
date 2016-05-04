package database;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import model.User;

public class DbOperationsFollowing {
	Properties props = new Properties();
	FileInputStream in;
	Connection conn;
	
	public DbOperationsFollowing(){
			try {
				this.conn = DatabaseConnector.getConnection();
				String fileName = "DBSetUp.dat";
			    this.in = new FileInputStream(fileName);
			    props.load(in);
			} catch (ClassNotFoundException | SQLException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public DbOperationsFollowing(String filename1, String filename2){
		try {
			this.conn = DatabaseConnector.getConnection(filename1);
			FileInputStream inf1 = new FileInputStream(filename1);
			Properties configProps = new Properties();
			configProps.load(inf1);
			
			Statement stmt=conn.createStatement(); 
			String query = configProps.getProperty("USE_DB");
			stmt.executeUpdate(query);
			
			this.in = new FileInputStream(filename2);
			props.load(in);
		    
		} catch (ClassNotFoundException | SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int AddFollowing(int follwedId,int followerId) {
		int res = 1;
		Statement stmt;
		try {
		
		String queryCheck = props.getProperty("CHK_FOLLOWING");
		PreparedStatement checkFollowing = conn.prepareStatement(queryCheck);
		checkFollowing.setInt (1, follwedId);
		checkFollowing.setInt (2, followerId);
		ResultSet reltet = checkFollowing.executeQuery();
		if(reltet.next()){
			int cnt = reltet.getInt("Count");
			if(cnt>0){
				reltet.close();
				checkFollowing.close();
				return 0;
			}
		}
		stmt = conn.createStatement();
		String query =  props.getProperty("GETIDRIGHT_FOLLOWING");
		stmt.execute(query);
		query =  props.getProperty("ADD_FOLLOWING");
		PreparedStatement preparedStmt = conn.prepareStatement(query);
		preparedStmt.setInt (1, follwedId);
		preparedStmt.setInt (2, followerId);
		preparedStmt.execute();
		stmt.close();
		} catch (SQLException e1) {
			res=0;
			e1.printStackTrace();
		} 
		return res;
	}
	
	public ArrayList<String> ReadFollowing() {
		ArrayList<String> arr = new ArrayList<String>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String query =  props.getProperty("READ_FOLLOWING");
			ResultSet rs = stmt.executeQuery(query);
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			while (rs.next())
			{  
				StringBuilder sb = new StringBuilder();
				for (int i = 1; i <= columnCount; i++){ 
					if(i<columnCount){
					    sb.append(rs.getString(i)).append(",");
					}else{
						sb.append(rs.getString(i));	
					}
					if (i > 1) System.out.print(", ");
					System.out.print(rs.getString(i));
				}
				arr.add(sb.toString());
				System.out.println();
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arr;
	}
	
	public ArrayList<User> getFollowing(int uid){
		ArrayList<User> following = new ArrayList<User>();
        
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String query =  props.getProperty("GET_FOLLOWING");
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setInt (1, uid);
			ResultSet rs = preparedStmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			while (rs.next())
			{  
			  User tempUser = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), (rs.getInt(5)==1?"Indoor":"Outdoor"), rs.getString(6));
			  following.add(tempUser);
			}
			System.out.println();
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return following;
	}
	
	public ArrayList<User> getFollowers(int uid){
		ArrayList<User> followers = new ArrayList<User>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String query =  props.getProperty("GET_FOLLOWERS");
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setInt (1, uid);
			ResultSet rs = preparedStmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			while (rs.next())
			{  
			  User tempUser = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), (rs.getInt(5)==1?"Indoor":"Outdoor"), rs.getString(6));
			  followers.add(tempUser);
			}
			System.out.println();
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return followers;
	}
	
	public int UpdateFollowing(int id, int valLabel, int val) {
		int res = 1;
		Statement stmt;
		   try{
			   stmt = conn.createStatement();
			   String query =  props.getProperty("UPDATE_FOLLOWING");
			   query = query + " "+valLabel+" = '"+val+ "' where id = "+id;
			   stmt.executeUpdate(query);
			   stmt.close();
				} catch (SQLException e) {
					res=0;
					e.printStackTrace();
				}
		return res;
	}
	
	public int DeleteFollowing(int id) {
		int res = 1;
		Statement stmt;
		try{
			   stmt = conn.createStatement();
			   String query =  props.getProperty("DELETE_FOLLOWING");
			   PreparedStatement preparedStmt = conn.prepareStatement(query);
			   preparedStmt.setInt (1, id);
			   preparedStmt.executeUpdate();
			   stmt.close();
		  } catch (SQLException e) {

				res=0;
				e.printStackTrace();
			}
		return res;
	}
	
	public int DeleteFollowing(int followedId,int followerId) {
		int res = 1;
		Statement stmt;
		try{
			   stmt = conn.createStatement();
			   String query =  props.getProperty("DELETE_A_FOLLOWING");
			   PreparedStatement preparedStmt = conn.prepareStatement(query);
			   preparedStmt.setInt (1, followerId);
			   preparedStmt.setInt (2, followedId);
			   preparedStmt.executeUpdate();
			   stmt.close();
		  } catch (SQLException e) {

				res=0;
				e.printStackTrace();
			}
		return res;
	}
	
	public void destroy(){
		try {
			conn.close();
			in.close();
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}

}
