package application.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class BaseModel {

	/**
	 * Function creating the sqlite connection.
	 * 
	 * @return Connection object.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static Connection Connector() throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
		String connectionURL = "jdbc:sqlite:hotel.db";
		Connection connection = DriverManager.getConnection(connectionURL);
		return connection;
	}

	/**
	 * Function creating the table.
	 */
	public static void createTable(String sql) {
		Connection c = null;
		Statement stmt = null;
		try {
			c = Connector();
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Table created successfully");
	}

	/**
	 * Deleting data of table.
	 * 
	 * @param id
	 *            integer variable contain value of primary key of table.
	 */
	public static void deleteDB(int id, String sql) {
		Connection c = null;
		PreparedStatement pstmt = null;
		try {
			c = Connector();
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");

			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, id);

			pstmt.executeUpdate();
			pstmt.close();
			c.commit();
			c.close();
			System.out.println("deleted successfully");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

	}

	/**
	 * Function selecting the data of table.
	 * 
	 * @param sql
	 *            string containing sql update query.
	 * @return ResultSet having selected data.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static ResultSet select(String sql) throws SQLException, ClassNotFoundException {
		Connection c = null;

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		c = Connector();
		c.setAutoCommit(false);
		System.out.println("Opened database successfully");

		pstmt = c.prepareStatement(sql);
		rs = pstmt.executeQuery();
		c.close();
		pstmt.close();
		return rs;
	}

}
