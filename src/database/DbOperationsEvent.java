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

import model.Event;

public class DbOperationsEvent {
	Properties props = new Properties();
	FileInputStream in;
	Connection conn;
	public DbOperationsEvent(){
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
	
	public DbOperationsEvent(String filename1,String filename2){
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

	public int AddEvent(Event e) {
		int last_inserted_id = 0;
		Statement stmt;
		try {
			stmt = conn.createStatement();
			props.load(in);
		
		String query =  props.getProperty("GETIDRIGHT_EVENT");
		stmt.executeUpdate(query);
		query =  props.getProperty("ADD_EVENT");
		
		PreparedStatement preparedStmt = conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
		preparedStmt.setString (1, e.getEventTitle());
		preparedStmt.setString (2, e.getEventDescription());
		preparedStmt.setString (3, e.getEventLocation());
		preparedStmt.setString (4, e.getEventDate());
		preparedStmt.setString (5, e.getEventStartTime());
		preparedStmt.setString (6, e.getEventEndTime());
		preparedStmt.setInt (7, e.getEventHostedById());
		preparedStmt.setInt (8, e.getInterestAreaId());
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
		System.out.println("%%%%%%%%%%%%%%%"+last_inserted_id);
		return last_inserted_id;
	}
	
	public ArrayList<String> ReadEvent() {
		ArrayList<String> arr = new ArrayList<String>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String query =  props.getProperty("READ_EVENT");
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
	
	public int UpdateEvent(int id, String valLabel, String val) {
		int res = 1;
		Statement stmt;
	   try{
		   stmt = conn.createStatement();
		   String query =  props.getProperty("UPDATE_EVENT");
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
	
	//Please REcheck usage of executeUpdate() and execute() as appropriate
	public int DeleteEvent(int id) {
		int res = 1;
		Statement stmt;
		try{
			   stmt = conn.createStatement();
			   String query =  props.getProperty("DELETE_EVENT");
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
