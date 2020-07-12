package invmanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
	
public class DbConnection {

	public Connection Connect() {
		
		try {
			String url = "jdbc:mysql://localhost:3306/INVENTORY";
			String user="root";
			String password = "Nat98101";
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn=DriverManager.getConnection(url, user, password);
			
			return conn;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return null;	
	}

}
