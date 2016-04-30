package database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.print.DocFlavor.URL;
import javax.servlet.ServletContext;

public class DatabaseConnector {
	static Properties configProps = new Properties();
	static String Configfile = "DBConfig.properties";
	static String ROOT_USERNAME="root";
	static String ROOT_PASSWORD="mysql123";
	
	public static Connection getConnection(String filename)
			throws SQLException, IOException, ClassNotFoundException
	{  
		FileInputStream in = new FileInputStream(filename);
		configProps.load(in);
		String drivers = configProps.getProperty("JDBC_DRIVER");
		Class.forName("com.mysql.jdbc.Driver");
		//return DriverManager.getConnection("jdbc:mysql://localhost/?user=root");
		return DriverManager.getConnection("jdbc:mysql://localhost/",ROOT_USERNAME,ROOT_PASSWORD);
	}
	public static Connection getConnection()
			throws SQLException, IOException, ClassNotFoundException
	{  
		FileInputStream in = new FileInputStream(Configfile);
		configProps.load(in);
		String drivers = configProps.getProperty("JDBC_DRIVER");
		Class.forName("com.mysql.jdbc.Driver");
		//return DriverManager.getConnection("jdbc:mysql://localhost/?user=root");
		return DriverManager.getConnection("jdbc:mysql://localhost/",ROOT_USERNAME,ROOT_PASSWORD);
	}
	
	public static void createDB(Connection conn)
					throws SQLException, IOException
	{  
		FileInputStream in = new FileInputStream(Configfile);
		configProps.load(in);
		Statement stmt=conn.createStatement(); 
		String DBName = configProps.getProperty("DB_NAME");
		int Result=stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS "+DBName);
		if (Result>=0)
			System.out.println(Result+" Database GoGreen is active on the server");
	}
	
	public static void createTables(Connection conn, String propertyName)
			throws SQLException, IOException
	{  
		FileInputStream in = new FileInputStream(Configfile);
		configProps.load(in);
		Statement stmt=conn.createStatement(); 
		String query = configProps.getProperty("USE_DB");
		stmt.executeUpdate(query);
		String propName = propertyName;
		query =  configProps.getProperty(propName);
		int Result=stmt.executeUpdate(query);
		if (Result==0)
			System.out.println("DB query "+propName+" executed successfully");
		stmt.close();
	}
}
