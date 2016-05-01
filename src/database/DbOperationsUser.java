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
	public DbOperationsUser(String filename1,String filename2){
			try {
				this.conn = DatabaseConnector.getConnection(filename1);
				FileInputStream in = new FileInputStream(filename1);
				Properties configProps = new Properties();
				configProps.load(in);
				Statement stmt=conn.createStatement(); 
				String query = configProps.getProperty("USE_DB");
				stmt.executeUpdate(query);
				String fileName = "DBSetUp.dat";
			    //this.in = new FileInputStream(fileName);
				this.in = new FileInputStream(filename2);
				props.load(in);
			    
			} catch (ClassNotFoundException | SQLException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public int AddUser(User u) {
		int last_inserted_id  = 0;
		Statement stmt;
		try {
			stmt = conn.createStatement();
			props.load(in);
		String query =  props.getProperty("GETIDRIGHT_USER");
		System.out.println("########"+query);
		stmt.executeUpdate(query);
		query =  props.getProperty("ADD_USER");
		System.out.println("########"+query);
		
		PreparedStatement preparedStmt = conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
		preparedStmt.setString (1, u.getUsername());
		preparedStmt.setString (2, u.getPassword());
		preparedStmt.setString (3, u.getFirstName());
		preparedStmt.setString (4, u.getLastName());
		preparedStmt.setString (5, u.getRoleType());
		if(u.getInterestArea().equals("Indoor")){
			preparedStmt.setInt (6, 1);
		}else if(u.getInterestArea().equals("Outdoor")){
			preparedStmt.setInt (6, 2);
		}else{
			preparedStmt.setInt (6, 3);
		}
		preparedStmt.setString (7, u.getCity());
		preparedStmt.setString (8, u.getState());
		preparedStmt.setString (9, u.getImageURL());
		preparedStmt.executeUpdate();
		ResultSet rs = preparedStmt.getGeneratedKeys();
        if(rs.next())
        {
            last_inserted_id = rs.getInt(1);
            System.out.println("$$$$$$$$$$$$$"+last_inserted_id);
        }
		stmt.close();
		} catch (SQLException | IOException e1) {
			e1.printStackTrace();
		} 
		return last_inserted_id;
	}
	
	public ArrayList<String> ReadUsers() {
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
	
	public String GetUserRole(int id){
		String role = "";
		   try{
			   String query =  props.getProperty("GET_USER_ID");
			   PreparedStatement preparedStmt = conn.prepareStatement(query);
			   preparedStmt.setInt (1, id);
			   ResultSet rs = preparedStmt.executeQuery();
			   if (rs.next()) {
				   role = rs.getString(1);
			   }
				} catch (SQLException e) {
					e.printStackTrace();
				}
		return role;
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
