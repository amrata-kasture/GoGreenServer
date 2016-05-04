package database;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
        preparedStmt.close();
		stmt.close();
		} catch (SQLException | IOException e1) {
			e1.printStackTrace();
		} 
		int uid = GetUserIdByUsername(u.getUsername());
		return uid;
	}
	
	public ArrayList<String> ReadUsers() {
		ArrayList<String> arr = new ArrayList<String>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			props.load(in);
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
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arr;	
	}
	
	public String GetUserRole(int id){
		String role = "";
		   try{
			   props.load(in);
			   String query =  props.getProperty("GET_USER_ROLE");
			   PreparedStatement preparedStmt = conn.prepareStatement(query);
			   preparedStmt.setInt (1, id);
			   ResultSet rs = preparedStmt.executeQuery();
			   if (rs.next()) {
				   role = rs.getString(1);
			   }
				} catch (SQLException | IOException e) {
					e.printStackTrace();
				}
		return role;
	}
	
	// convert InputStream to String
			private static String getStringFromInputStream(InputStream is) {

				BufferedReader br = null;
				StringBuilder sb = new StringBuilder();

				String line;
				try {

					br = new BufferedReader(new InputStreamReader(is));
					while ((line = br.readLine()) != null) {
						sb.append(line);
					}

				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (br != null) {
						try {
							br.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}

				return sb.toString();

			}

		public User GetUserDetailsFromUserId(int userId){
			User user = new User();
			   try{
				   props.load(in);
				   String query =  props.getProperty("GET_USER_DETAILS");
				   System.out.println("************ userId="+userId);
				   System.out.println("************ query="+query);
				   int interestArea = 0;
				   PreparedStatement preparedStmt = conn.prepareStatement(query);
				   preparedStmt.setInt (1, userId);
				   ResultSet rs = preparedStmt.executeQuery();
				   if (rs.next()) {
					   System.out.println("************ rs="+rs.getInt(1));
					   System.out.println("************ rs="+rs.getString(4));
					   System.out.println("************ rs="+rs.getString(5));
					   System.out.println("************ rs="+rs.getString(6));
					   System.out.println("******INTEREST****** rs="+rs.getInt(7));
					   System.out.println("************ rs="+rs.getString(8));
					   System.out.println("************ rs="+rs.getString(9));
					   System.out.println("************ rs="+rs.getBinaryStream(10));
					   user.setUserId(rs.getInt(1));
					   user.setUsername(rs.getString(2));
					   user.setPassword(rs.getString(3));
					   user.setFirstName(rs.getString(4));
					   user.setLastName(rs.getString(5));
					   user.setRoleId(rs.getString(6));
					   interestArea = rs.getInt(7);
					   user.setCity(rs.getString(8));
					   user.setState(rs.getString(9));
					   if(rs.getBinaryStream(10)!=null){
						   InputStream is = rs.getBinaryStream(10);
						   user.setImageURL(getStringFromInputStream(is));
					   }
				   }
				   String query1 =  props.getProperty("GET_INTEREST_AREA");
				   System.out.println("************ query1="+query1);
				   PreparedStatement preparedStmt1 = conn.prepareStatement(query1);
				   preparedStmt1.setInt (1, interestArea);
				   ResultSet rs1 = preparedStmt1.executeQuery();
				   if (rs1.next()) {
					   System.out.println("************ rs1="+rs1.getString(1));
					   user.setInterestArea(rs1.getString(1));
				   }
				   preparedStmt1.close();
				   preparedStmt.close();
				} catch (SQLException | IOException e) {
					e.printStackTrace();
				}
			return user;
		}

	
	
	public int GetUserIdByUsername(String username){
		int userid = 0;
		   try{
			   props.load(in);
			   String query =  props.getProperty("GET_USERID_BY_UNAME");
			   PreparedStatement preparedStmt = conn.prepareStatement(query);
			   preparedStmt.setString (1, username);
			   ResultSet rs = preparedStmt.executeQuery();
			   if (rs.next()) {
				   userid = rs.getInt(1);
			   }
			   preparedStmt.close();
				} catch (SQLException | IOException e) {
					e.printStackTrace();
				}
		return userid;
	}
	
	public int UpdateUser(User u) {
		Statement stmt;
		try {
			stmt = conn.createStatement();
			props.load(in);
		
			String query =  props.getProperty("UPDATE_USER");
		
		
		PreparedStatement preparedStmt = conn.prepareStatement(query);
		preparedStmt.setString (1, u.getFirstName());
		preparedStmt.setString (2, u.getLastName());
		preparedStmt.setString (3, u.getRoleType());
		if(u.getInterestArea().equals("Indoor")){
			preparedStmt.setInt (4, 1);
		}else if(u.getInterestArea().equals("Outdoor")){
			preparedStmt.setInt (4, 2);
		}else{
			preparedStmt.setInt (4, 3);
		}
		preparedStmt.setString (5, u.getCity());
		preparedStmt.setString (6, u.getState());
		preparedStmt.setString (7, u.getImageURL());
		preparedStmt.setInt (8, u.getUserId());
		preparedStmt.executeUpdate();
		stmt.close();
		} catch (SQLException | IOException e1) {
			e1.printStackTrace();
		} 
		
		return  u.getUserId();
		
	}
	
	public int DeleteUser(int id) {
		int res = 1;
		Statement stmt;
		try{
			   stmt = conn.createStatement();
			   props.load(in);
			   String query =  props.getProperty("DELETE_USER");
			   PreparedStatement preparedStmt = conn.prepareStatement(query);
			   preparedStmt.setInt (1, id);
			   preparedStmt.executeUpdate();
			   stmt.close();
		  } catch (SQLException | IOException e) {
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
