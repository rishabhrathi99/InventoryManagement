package invmanagement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 *
 * @author rishabh
 */
public class QueryProcessor {
    public ResultSet getQuery(String query){
        try{
            DbConnection cont = new DbConnection();
            Connection conn = cont.Connect();
            Statement myStm = conn.createStatement();
            ResultSet res = myStm.executeQuery(query);
            return res;
        }
        catch (SQLException e)
        {
            System.out.println("Error");
            return null;
        }
    
    }
    
    public void setResult(String query){
        try{
            DbConnection cont = new DbConnection();
            Connection conn = cont.Connect();
            Statement myStm = conn.createStatement();
            myStm.executeUpdate(query);
        }
        catch (SQLException e)
        {
            System.out.println("Error");
        }
    
    }
    
}
