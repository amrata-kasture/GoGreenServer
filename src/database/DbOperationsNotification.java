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

import model.Notification;

public class DbOperationsNotification {
	Properties props = new Properties();
	FileInputStream in;
	Connection conn;
	public DbOperationsNotification(){
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
	
	public DbOperationsNotification(String filename1,String filename2){
		try {
			this.conn = DatabaseConnector.getConnection(filename1);
			FileInputStream in = new FileInputStream(filename1);
			Properties configProps = new Properties();
			configProps.load(in);
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
	
	public int AddNotification(Notification n) {
		int last_inserted_id = 0;
		Statement stmt;
		try {
			stmt = conn.createStatement();
			props.load(in);
		
		String query =  props.getProperty("GETIDRIGHT_NOTIFICATION");
		stmt.executeUpdate(query);
		query =  props.getProperty("ADD_NOTIFICATION");
		
		PreparedStatement preparedStmt = conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
		preparedStmt.setInt (1, (n).getGreenEntryId());
		preparedStmt.setInt (2, n.getEventId());
		preparedStmt.setString (3, n.getNotificationMessage());
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
	
	public ArrayList<Notification> ReadNotification() {
		ArrayList<Notification> arr = new ArrayList<Notification>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			props.load(in);
			String query =  props.getProperty("READ_NOTIFICATION");
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next())
			{   
				String temp = "";
				InputStream tempPic = null;
				String qry =  props.getProperty("GET_USER_INFO_BY_EVENTID");
				PreparedStatement preparedStmt = conn.prepareStatement(qry);
				preparedStmt.setInt (1, rs.getInt("event_id"));
				ResultSet tempRset = preparedStmt.executeQuery();
				if(tempRset.next())
		        {
		            temp = tempRset.getString("first_name") + " " + tempRset.getString("last_name");
		            System.out.println("$$$$$$$$$$$$$"+temp);
		            if(tempRset.getString("picture")!=null){
		            	tempPic = tempRset.getBinaryStream("picture");
					   }
		        }
				arr.add(new Notification(rs.getInt("id"),rs.getInt("green_entry_id"),rs.getInt("event_id"),rs.getString("notification_msg"),rs.getDate("creation_date"),temp,getStringFromInputStream(tempPic)));
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
	
	
	public Notification GetNotifDetailsById(int notifId){
		Notification nf = new Notification();
		   try{
			   props.load(in);
			   String query =  props.getProperty("GET_A_NOTIFICATION");
			   int interestArea = 0;
			   PreparedStatement preparedStmt = conn.prepareStatement(query);
			   preparedStmt.setInt (1, notifId);
			   ResultSet rs = preparedStmt.executeQuery();
			   if (rs.next()) {
				   String temp = "";
					InputStream tempPic = null;
					String qry =  props.getProperty("GET_USER_INFO_BY_EVENTID");
					PreparedStatement prepStmt2 = conn.prepareStatement(qry);
					prepStmt2.setInt (1, rs.getInt("id"));
					ResultSet tempRset = prepStmt2.executeQuery();
					if(tempRset.next())
			        {
			            temp = tempRset.getString("first_name") + " " + tempRset.getString("last_name");
			            System.out.println("$$$$$$$$$$$$$"+temp);
			            if(tempRset.getString("picture")!=null){
			            	tempPic = tempRset.getBinaryStream("picture");
						   }
			        }
					prepStmt2.close();
					nf = new Notification(rs.getInt("id"),rs.getInt("green_entry_id"),rs.getInt("event_id"),rs.getString("notification_msg"),rs.getDate("creation_date"),temp,getStringFromInputStream(tempPic));
				  // nf = new Notification(rs.getInt("id"),rs.getInt("green_entry_id"),rs.getInt("event_id"),rs.getString("notification_msg"),rs.getDate("creation_date"));
			   }
			   rs.close();
			   
			   preparedStmt.close();
			} catch (SQLException | IOException e) {
				e.printStackTrace();
			}
		return nf;
	}

	
	public int UpdateNotification(int id, String valLabel, String val) {
		int res = 1;
		Statement stmt;
		   try{
			   stmt = conn.createStatement();
			   props.load(in);
			   String query =  props.getProperty("UPDATE_NOTIFICATION");
			   query = query + " "+valLabel+" = '"+val+ "' where id = "+id;
			   stmt.executeUpdate(query);
			   stmt.close();
				} catch (SQLException | IOException e) {
					res=0;
					e.printStackTrace();
				}
		return res;
	}
	
	public int DeleteNotification(int id) {
		int res = 1;
		Statement stmt;
		try{
			   stmt = conn.createStatement();
			   props.load(in);
			   String query =  props.getProperty("DELETE_NOTIFICATION");
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
