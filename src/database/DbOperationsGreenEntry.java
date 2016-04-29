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

import model.GreenEntry;

public class DbOperationsGreenEntry {
	Properties props = new Properties();
	FileInputStream in;
	Connection conn;
	public DbOperationsGreenEntry(){
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
	
	public int AddGreenEntry(GreenEntry ge) {
		int res = 1;
		Statement stmt;
		try {
			stmt = conn.createStatement();
		
		String query =  props.getProperty("GETIDRIGHT_GR_ENTRY");
		stmt.executeUpdate(query);
		query =  props.getProperty("ADD_GR_ENTRY");
		
		PreparedStatement preparedStmt = conn.prepareStatement(query);
		preparedStmt.setInt (1, ge.getPostedByUserId());
		preparedStmt.setString (2, ge.getPostType());
		preparedStmt.setString (3, ge.getPostMessage());
		preparedStmt.setString (4, ge.getPostImageURL());
		preparedStmt.execute();
		stmt.close();
		} catch (SQLException e1) {
			res=0;
			e1.printStackTrace();
		} 
		return res;
	}
	
	public ArrayList<String> ReadGreenEntry() {
		ArrayList<String> arr = new ArrayList<String>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String query =  props.getProperty("READ_GR_ENTRY");
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
	
	public int UpdateGreenEntry(int id, String valLabel, String val) {
		int res = 1;
		Statement stmt;
		   try{
			   stmt = conn.createStatement();
			   String query =  props.getProperty("UPDATE_GR_ENTRY");
			   query = query + " "+valLabel+" = '"+val+ "' where id = "+id;
			   stmt.executeUpdate(query);
			   stmt.close();
				} catch (SQLException e) {
					res=0;
					e.printStackTrace();
				}
		return res;
	}
	
	public int DeleteGreenEntry(int id) {
		int res = 1;
		Statement stmt;
		try{
			   stmt = conn.createStatement();
			   String query =  props.getProperty("DELETE_GR_ENTRY");
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
