package db_operations;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;

public class DBUtils {
	static Connection con;
	static Statement stmt;

	static {
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/shop_management","root","123418");
			stmt = con.createStatement();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	public static void executeQuery( String query)
	{
		try {
			stmt.execute(query);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public static ResultSet executeQueryGetResult(String query)
	{
		ResultSet resultSet = null;
		try {
			resultSet = stmt.executeQuery(query);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}

}



