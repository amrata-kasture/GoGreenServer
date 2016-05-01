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

public class DbOperationsInterestArea {
	Properties props = new Properties();
	FileInputStream in;
	Connection conn;
	public DbOperationsInterestArea(){
			try {
				this.conn = DatabaseConnector.getConnection("DBConfig.properties");
				FileInputStream in = new FileInputStream("DBConfig.properties");
				Properties configProps = new Properties();
				configProps.load(in);
				Statement stmt=conn.createStatement(); 
				String query = configProps.getProperty("USE_DB");
				stmt.executeUpdate(query);
				String fileName = "DBSetUp.dat";
			    this.in = new FileInputStream(fileName);
			    props.load(in);
			} catch (ClassNotFoundException | SQLException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public int AddInterestArea(String catg, String descr) {
		int res = 1;
		Statement stmt;
		try {
			stmt = conn.createStatement();
			props.load(in);
		String query =  props.getProperty("GETIDRIGHT_INTR_AREA");
		stmt.executeUpdate(query);
		query =  props.getProperty("ADD_INTR_AREA");
		
		PreparedStatement preparedStmt = conn.prepareStatement(query);
		preparedStmt.setString (1, catg);
		preparedStmt.setString (2,descr);
		preparedStmt.execute();
		stmt.close();
		} catch (SQLException | IOException e1) {
			res=0;
			e1.printStackTrace();
		} 
		return res;
	}
	
	public ArrayList<String> ReadInterestArea() {
		ArrayList<String> arr = new ArrayList<String>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String query =  props.getProperty("READ_INTR_AREA");
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
	
	public int UpdateInterestArea() {
		int res = 0;
		return res;
	}
	
	public int DeleteInterestArea() {
		int res = 0;
		return res;
	}

	public int UpdateInterestArea(int id, String valLabel, String val) {
		int res = 1;
		Statement stmt;
		   try{
			   stmt = conn.createStatement();
			   String query =  props.getProperty("UPDATE_EVENT");
			   query = query + " "+valLabel+" = '"+val+ "' where id = "+id;
			   stmt.executeUpdate(query);
			   stmt.close();
				} catch (SQLException e) {
					res=0;
					e.printStackTrace();
				}
		return res;
	}
	
	public int DeleteInterestArea(int id) {
		int res = 1;
		Statement stmt;
		try{
			   stmt = conn.createStatement();
			   String query =  props.getProperty("DELETE_INTR_AREA");
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
