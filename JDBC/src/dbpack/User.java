package dbpack;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
// import com.mysql.cj.xdevapi.PreparableStatement;
public class User
{
    final String DB_URL = "jdbc:mysql://localhost:3306/customerdb";
    final String USER = "root";
    final String PASS = "Srikrishn@n@mysql#2023#";
    public boolean login(String userid,String password)
    {
        TreeMap<String,String> customerlogindetails=new TreeMap<String,String>();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stat = conn.createStatement()) 
        {
            String selectQuery="SELECT * FROM customerdetails";
            try (ResultSet resultSet = stat.executeQuery(selectQuery)) 
            {
                while (resultSet.next()) 
                {
                    customerlogindetails.put(resultSet.getString("userid"),resultSet.getString("password"));
                }
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        if(customerlogindetails.containsKey(userid))
        {
            if((customerlogindetails.get(userid)).equals(password))
            {
                customerlogindetails.clear();
                return true;
            }
        }
        customerlogindetails.clear();
        return false;
    }
    public void signUp(String name,String password,String userid,String emailid,String phonenumber)
    {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        PreparedStatement insertStatement = conn.prepareStatement("INSERT INTO customerdetails VALUES(?,?,?,?,?)")) 
        {

            insertStatement.setString(1, userid);
            insertStatement.setString(2, name);
            insertStatement.setString(3, password);
            insertStatement.setString(4,phonenumber);
            insertStatement.setString(5,emailid);
            insertStatement.executeUpdate();
        }
        catch(SQLException e) 
        {
            e.printStackTrace();
        }
    }
}