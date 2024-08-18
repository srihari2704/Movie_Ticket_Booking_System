package dbpack;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
public class CustomerBooking 
{
    public void storeDetails(String userid,String name,String location,String moviename,String date,String theatrename,int no_of_tickets,String screenname)
    {
        String DB_URL="jdbc:mysql://localhost:3306/customerdb";
        String USER="root";
        String PASS="Srikrishn@n@mysql#2023#";
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) 
        {
            String insertQuery = "INSERT INTO customerbookingdetails VALUES(?,?,?,?,?,?,?,?)";
            try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                insertStatement.setString(1,userid);
                insertStatement.setString(2,name);
                insertStatement.setString(3,location);
                insertStatement.setString(4,theatrename);
                insertStatement.setString(5,moviename);
                insertStatement.setInt(6,no_of_tickets);
                insertStatement.setString(7,screenname);
                insertStatement.setString(8,date);
                insertStatement.executeUpdate();
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }    
}
