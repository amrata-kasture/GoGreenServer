package driver;

import java.io.IOException;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

import database.DatabaseConnector;

public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//DatabaseConnector dc = new DatabaseConnector();
		try {
			java.sql.Connection conn = DatabaseConnector.getConnection();
			DatabaseConnector.createDB(conn);
			DatabaseConnector.createTables(conn, "INTEREST_AREA_TABLE_CREATION");
			DatabaseConnector.createTables(conn, "USER_TABLE_CREATION"); 
			DatabaseConnector.createTables(conn, "GR_ENTRY_TABLE_CREATION");
			DatabaseConnector.createTables(conn, "QA_REL_TABLE_CREATION");
			DatabaseConnector.createTables(conn, "EVENT_TABLE_CREATION");
			DatabaseConnector.createTables(conn, "NOTIFICATION_TABLE_CREATION");
			DatabaseConnector.createTables(conn, "FOLLOWING_TABLE_CREATION");
			DatabaseConnector.createTables(conn, "SHARE_TABLE_CREATION");
			DatabaseConnector.createTables(conn, "PLANT_TABLE_CREATION");

		} catch (ClassNotFoundException | SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
