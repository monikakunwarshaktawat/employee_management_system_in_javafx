package application.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeModel extends BaseModel {
public static void employeeTable()
{
	String sql = "CREATE TABLE IF NOT EXISTS employee " + "(eid INTEGER PRIMARY KEY autoincrement,"
			+ " name          VARCHAR(100)    NOT NULL, " + " email    VARCHAR(150)    NOT NULL, "
			+ " position  VARCHAR(100)    NOT NULL)";
	createTable(sql);
}
/**
 * Inserting employee with particular id.
 * 
 * @param Name
 *            string containing name of employee.
 * @param Email
 *            string containing email of employee.
 * @param Position
 *            string containing position of employee.
 */
public static void insertEmployee(String Name, String Email, String Position) {
	Connection c = null;
	PreparedStatement pstmt = null;
	try {
		c = Connector();
		c.setAutoCommit(false);
		System.out.println("Opened database successfully");

		String sql = "INSERT INTO employee (name,email,position)" + "VALUES (?,?,?);";

		pstmt = c.prepareStatement(sql);
		pstmt.setString(1, Name);
		pstmt.setString(2, Email);
		pstmt.setString(3, Position);
		pstmt.executeUpdate();
		pstmt.close();
		c.commit();
		c.close();
		System.out.println("Records created successfully");
	} catch (Exception e) {
		System.err.println(e.getClass().getName() + ": " + e.getMessage());
		System.exit(0);
	}
	System.out.println("Records created successfully");
}

/**
 * Deleting employee data with particular employee id.
 * 
 * @param id
 *            integer variable contain employee id.
 */
public static void deleteEmployee(int id) {
	String sql = "DELETE from employee where eid=?;";
	 deleteDB(id,sql);
}
//Variable info is a string array containing the information of employee
	// whose data has to be edited.
	public static String[] info = new String[3];
	// Variable eid stores id of employee whose data has to be edited.
	public static int eid;

	/**
	 * Updating employee data with particular employee id.
	 */
	public static void updateEmployee() {
		Connection c = null;
		PreparedStatement pstmt = null;
		try {
			c = Connector();
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");

			String sql = " UPDATE employee set name=?,email=?,position=? where eid=?;";

			pstmt = c.prepareStatement(sql);

			pstmt.setString(1, info[0]);
			pstmt.setString(2, info[1]);
			pstmt.setString(3, info[2]);
			pstmt.setInt(4, eid);

			pstmt.executeUpdate();
			pstmt.close();
			c.commit();
			c.close();
			System.out.println("edited successfully");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

	}
	
}
