package application.Model;

import java.sql.*;

public class RegistrationModel extends BaseModel {
	/**
	 * Creating table for users.
	 */
	public static void tableCreate() {
		
			
			String sql = "CREATE TABLE IF NOT EXISTS admin " + "(ID INTEGER PRIMARY KEY autoincrement,"
					+ " name          CHAR(70)    NOT NULL, " + " email    VARCHAR(100)    NOT NULL, "
					+ " password   VARCHAR(100)    NOT NULL)";
			createTable(sql);
	}

	/**
	 * inserting the data to user table
	 * 
	 * @param Name
	 *            string containing the name of user.
	 * @param Email
	 *            string containing the email of user.
	 * @param Password
	 *            string containing the password of user.
	 */
	public static void insertDB(String Name, String Email, String Password) {
		Connection c = null;
		PreparedStatement pstmt = null;
		try {
			c = Connector();
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");

			String sql = "INSERT INTO admin (name,email,password) " + "VALUES (?,?,?);";

			pstmt = c.prepareStatement(sql);
			pstmt.setString(1, Name);
			pstmt.setString(2, Email);
			pstmt.setString(3, Password);
			pstmt.executeUpdate();
			pstmt.close();
			c.commit();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Records created successfully");
	}

	/**
	 * function for checking the user is valid or not
	 * 
	 * @param Email
	 *            string containing the email of user.
	 * @param Password
	 *            string containing the password of user.
	 * @return
	 * @throws SQLException
	 */
	public static boolean isLogin(String Email, String Password) throws SQLException {
		Connection c = null;

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			c = Connector();
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");
			String sql = "SELECT * FROM admin where email=? and password=? ;";
			pstmt = c.prepareStatement(sql);

			pstmt.setString(1, Email);
			pstmt.setString(2, Password);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
			return false;
		} finally {
			rs.close();
			pstmt.close();
			c.close();
		}
	}

}
