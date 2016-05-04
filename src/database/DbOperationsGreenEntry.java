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
	
	public DbOperationsGreenEntry(String filename1,String filename2){
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
	
	public int AddGreenEntry(GreenEntry ge) {
		int last_inserted_id  = 0;
		Statement stmt;
		try {
			stmt = conn.createStatement();
			props.load(in);
		
		String query =  props.getProperty("GETIDRIGHT_GR_ENTRY");
		stmt.executeUpdate(query);
		query =  props.getProperty("ADD_GR_ENTRY");
		
		PreparedStatement preparedStmt = conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
		preparedStmt.setInt (1, ge.getPostedByUserId());
		preparedStmt.setString (2, ge.getPostType());
		preparedStmt.setString (3, ge.getPostMessage());
		preparedStmt.setString (4, ge.getPostImageURL());
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
		return last_inserted_id;
	}
	
	public ArrayList<GreenEntry> ReadBlogGreenEntry() {
		ArrayList<GreenEntry> arr = new ArrayList<GreenEntry>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			props.load(in);
			String query =  props.getProperty("READ_BLOG_GR_ENTRY");
			ResultSet rs = stmt.executeQuery(query);
			//ResultSetMetaData rsmd = rs.getMetaData();
			//int columnCount = rsmd.getColumnCount();
			while (rs.next())
			{   
				String temp = "";
				InputStream tempPic = null;
				String qry =  props.getProperty("GET_NAME_N_PIC_BY_UID");
				PreparedStatement preparedStmt = conn.prepareStatement(qry);
				preparedStmt.setInt (1, rs.getInt("user_id"));
				ResultSet tempRset = preparedStmt.executeQuery();
				if(tempRset.next())
		        {
		            temp = tempRset.getString("first_name") + " " + tempRset.getString("last_name");
		            System.out.println("$$$$$$$$$$$$$"+temp);
		            if(tempRset.getString("picture")!=null){
		            	tempPic = tempRset.getBinaryStream("picture");
					   }
		        }
			    InputStream is = null;
					if(rs.getBinaryStream("picture")!=null){
					   is = rs.getBinaryStream("picture");
				   }
				
				arr.add(new GreenEntry(rs.getInt("id"),rs.getInt("user_id"),temp,rs.getString("type"),rs.getString("message"),getStringFromInputStream(is),getStringFromInputStream(tempPic),rs.getDate("creation_date")));
				is.close();
				tempPic.close();
				preparedStmt.close();
			}
			rs.close();
			stmt.close();
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return arr;
	}
	
	public ArrayList<GreenEntry> ReadQuestionGreenEntry() {
		ArrayList<GreenEntry> arr = new ArrayList<GreenEntry>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			props.load(in);
			String query =  props.getProperty("READ_QUESTION_GR_ENTRY");
			ResultSet rs = stmt.executeQuery(query);
			//ResultSetMetaData rsmd = rs.getMetaData();
			//int columnCount = rsmd.getColumnCount();
			while (rs.next())
			{   
				String temp = "";
				InputStream tempPic = null;
				String qry =  props.getProperty("GET_NAME_N_PIC_BY_UID");
				PreparedStatement preparedStmt = conn.prepareStatement(qry);
				preparedStmt.setInt (1, rs.getInt("user_id"));
				ResultSet tempRset = preparedStmt.executeQuery();
				if(tempRset.next())
		        {
		            temp = tempRset.getString("first_name") + " " + tempRset.getString("last_name");
		            System.out.println("$$$$$$$$$$$$$"+temp);
		            if(tempRset.getString("picture")!=null){
		            	tempPic = tempRset.getBinaryStream("picture");
					   }
		        }
			    InputStream is = null;
					if(rs.getBinaryStream("picture")!=null){
					   is = rs.getBinaryStream("picture");
				   }
				
				arr.add(new GreenEntry(rs.getInt("id"),rs.getInt("user_id"),temp,rs.getString("type"),rs.getString("message"),getStringFromInputStream(is),getStringFromInputStream(tempPic),rs.getDate("creation_date")));
			is.close();
			tempPic.close();
			preparedStmt.close();
			}
			rs.close();
			stmt.close();
		} catch (SQLException | IOException e) {
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
			   props.load(in);
			   String query =  props.getProperty("UPDATE_GR_ENTRY");
			   query = query + " "+valLabel+" = '"+val+ "' where id = "+id;
			   stmt.executeUpdate(query);
			   stmt.close();
				} catch (SQLException | IOException e) {
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
			   props.load(in);
			   String query =  props.getProperty("DELETE_GR_ENTRY");
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
	
	private static String getStringFromInputStream(InputStream is) {

		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		String res=null;
		String line;
		try {
			if(is!=null){
			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			}
			res=sb.toString();

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

		//return sb.toString();
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
