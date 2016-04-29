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

public class DbOperationsUser {
	Properties props = new Properties();
	FileInputStream in;
	Connection conn;
	public DbOperationsUser(){
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
	
	public int AddUser(User u) {
		int res = 1;
		Statement stmt;
		try {
			stmt = conn.createStatement();
		
		String query =  props.getProperty("GETIDRIGHT_USER");
		stmt.executeUpdate(query);
		query =  props.getProperty("ADD_USER");
		
		PreparedStatement preparedStmt = conn.prepareStatement(query);
		preparedStmt.setString (1, u.getUsername());
		preparedStmt.setString (2, u.getPassword());
		preparedStmt.setString (3, u.getFirstName());
		preparedStmt.setString (4, u.getLastName());
		preparedStmt.setString (5, u.getRoleType());
		preparedStmt.setInt (6, u.getUserInterest());
		preparedStmt.setString (7, u.getCity());
		preparedStmt.setString (8, u.getState());
		preparedStmt.setString (9, u.getImageURL());
		
		preparedStmt.execute();
		stmt.close();
		} catch (SQLException e1) {
			res=0;
			e1.printStackTrace();
		} 
		return res;
	}
	
	public ArrayList<String> ReadUser() {
		ArrayList<String> arr = new ArrayList<String>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String query =  props.getProperty("READ_USER");
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
	
	public int UpdateUser(int id, String valLabel, String val) {
		int res = 1;
		Statement stmt;
		   try{
			   stmt = conn.createStatement();
			   String query =  props.getProperty("UPDATE_USER");
			   query = query + " "+valLabel+" = '"+val+ "' where id = "+id;
			   //PreparedStatement preparedStmt = conn.prepareStatement(query);
			   //preparedStmt.executeUpdate();
			   stmt.executeUpdate(query);
			   stmt.close();
				} catch (SQLException e) {
					res=0;
					e.printStackTrace();
				}
		return res;
	}
	
	public int DeleteUser(int id) {
		int res = 1;
		Statement stmt;
		try{
			   stmt = conn.createStatement();
			   String query =  props.getProperty("DELETE_USER");
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
	
	public void destroy(){
		try {
			conn.close();
			in.close();
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}

}
