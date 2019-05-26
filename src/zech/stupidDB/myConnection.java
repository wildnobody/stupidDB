package zech.stupidDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class myConnection {
	public static Connection connection;
	
	public static String dataBaseURL;
	
	public static Class<?> settings;
	
	public static void startConnection(StackTraceElement opener) throws SQLException{
			myConnection.connection = DriverManager.getConnection(dataBaseURL);
			System.out.println("connection successful " + opener.getClassName() + " " + opener.getMethodName());
	}
	
	public static void closeConnection() {
		try {
			myConnection.connection.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
