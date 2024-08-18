package dbpack;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import MainPackage.*;

public class ReadDbData 
{
    public static final String DB_URL = "jdbc:mysql://localhost:3306/theatredb";
    public static final String USER = "root";
    public static final String PASS = "Srikrishn@n@mysql#2023#";
    public void extractMovieDetails(ArrayList<Movie> movielist)
    {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stat = conn.createStatement()) 
        {
            String selectQuery="SELECT * FROM theatredetails";
            try (ResultSet resultSet = stat.executeQuery(selectQuery)) 
            {
                while(resultSet.next())
                {
                    String showtimearr[]={"Morning","Afternoon","Evening","Night"};
                    String[] screenarr=new String[2];
                    String[] languagearr=new String[3];
                    for(int i=0;i<2;i++)
                    {
                        if(i==0)
                        {
                            if(resultSet.getString("INOX").equals("YES"))
                            {
                                screenarr[0]="INOX";
                            }
                            else{
                                screenarr[0]="NO";
                            }
                        }
                        else
                        {
                            if(resultSet.getString("DOLBY").equals("YES"))
                            {
                                screenarr[1]="DOLBY";
                            }
                            else
                            {
                                screenarr[1]="NO";
                            }
                        }
                    }
                    for(int i=0;i<3;i++)
                    {
                        if(i==0)
                        {
                            if(resultSet.getString("Tamil").equals("YES"))
                            {
                                languagearr[0]="Tamil";
                            }
                            else
                            {
                                languagearr[0]="NO";
                            }
                        }
                        else if(i==1)
                        {
                            if(resultSet.getString("English").equals("YES"))
                            {
                                languagearr[1]="English";
                            }
                            else{
                                languagearr[1]="NO";
                            }
                        }
                        else
                        {
                            if(resultSet.getString("Hindi").equals("YES"))
                            {
                                languagearr[2]="Hindi";
                            }
                            else{
                                languagearr[2]="NO";
                            }
                        }
                    }
                    int[] seatsarr={0,0,0,0};
                    String DB_URL1="jdbc:mysql://localhost:3306/theatreinfo",USER1="root",PASS1="Srikrishn@n@mysql#2023#";
                    movielist.add(new Movie(resultSet.getString("Theatrename"),resultSet.getString("Location"),showtimearr,resultSet.getDouble("Theatrerating"),screenarr,seatsarr,resultSet.getString("Filmname"),resultSet.getString("Genre"),resultSet.getString("Duration"),languagearr,resultSet.getDouble("Movierating")));
                    try (Connection conns = DriverManager.getConnection(DB_URL1, USER1, PASS1);
                        Statement stats = conns.createStatement()) 
                    {
                        String selectQuery1="SELECT * FROM theatreinformation";
                        try(ResultSet resultSet1 = stats.executeQuery(selectQuery1))
                        {
                            while(resultSet1.next())
                            {
                                if(resultSet1.getString("Theatrename").equals(movielist.get(movielist.size()-1).getTheatrename()) && resultSet1.getString("Location").equals(movielist.get(movielist.size()-1).getLocation()))
                                {
                                    if(resultSet1.getString("Screenname").equals("INOX") && resultSet.getString("INOX").equals("YES"))
                                    {
                                        movielist.get(movielist.size()-1).getseatsCapacity()[0]+=resultSet1.getInt("SeatM");
                                        movielist.get(movielist.size()-1).getseatsCapacity()[1]+=resultSet1.getInt("SeatAT");
                                        movielist.get(movielist.size()-1).getseatsCapacity()[2]+=resultSet1.getInt("SeatEV");
                                        movielist.get(movielist.size()-1).getseatsCapacity()[3]+=resultSet1.getInt("SeatNT");
                                    }
                                    if(resultSet1.getString("Screenname").equals("DOLBY") && resultSet.getString("DOLBY").equals("YES"))
                                    {
                                        movielist.get(movielist.size()-1).getseatsCapacity()[0]+=resultSet1.getInt("SeatM");
                                        movielist.get(movielist.size()-1).getseatsCapacity()[1]+=resultSet1.getInt("SeatAT");
                                        movielist.get(movielist.size()-1).getseatsCapacity()[2]+=resultSet1.getInt("SeatEV");
                                        movielist.get(movielist.size()-1).getseatsCapacity()[3]+=resultSet1.getInt("SeatNT");
                                    }
                                }
                            }
                        }
                    }
                    catch(SQLException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }
    public ArrayList<String> getUniqueMovieList(ArrayList<Movie> movielist)
    {
        ArrayList<String> uniquemovielists=new ArrayList<>();
        TreeMap<String,Double> movie_rating_map = new TreeMap<String,Double>();
        for(int i=0;i<movielist.size();i++)
        {
            movie_rating_map.put(movielist.get(i).getMovieName(),movielist.get(i).getMovieRating());
        }
        Iterator<Map.Entry<String,Double>> iterator = movie_rating_map.entrySet().iterator();                                
        while(iterator.hasNext())
        {
            Map.Entry<String,Double> entry=iterator.next();
            String key = entry.getKey();
            Double value = entry.getValue();
            uniquemovielists.add(key);
        }
        return uniquemovielists;
    }
    public boolean seatUpdate(String theatrename,String screenname,String theatrelocation,int no_of_seats,String showtime)
    {
        String DB_URL = "jdbc:mysql://localhost:3306/theatreinfo";
        String USER = "root";
        String PASS = "Srikrishn@n@mysql#2023#";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stat = conn.createStatement()) 
        {
            String selectQuery="SELECT * FROM theatreinformation";
            try (ResultSet resultSet = stat.executeQuery(selectQuery)) 
            {
                while(resultSet.next())
                {
                    if(resultSet.getString("Theatrename").equals(theatrename) && resultSet.getString("Location").equals(theatrelocation)
                    && resultSet.getString("Screenname").equals(screenname))
                    {
                        if(showtime.equals("Morning"))
                        {
                            if(no_of_seats<resultSet.getInt("SeatM"))
                            {
                                try (PreparedStatement updateStatement = conn.prepareStatement("UPDATE theatreinformation SET SeatM=? WHERE Theatrename=? AND Location=? AND Screenname=?")) 
                                {
                                    updateStatement.setInt(1,resultSet.getInt("SeatM")-no_of_seats);
                                    updateStatement.setString(2,theatrename);
                                    updateStatement.setString(3,theatrelocation);
                                    updateStatement.setString(4,screenname);
                                    updateStatement.executeUpdate();
                                }
                                return true;
                            }
                        }
                        else if(showtime.equals("Afternoon"))
                        {
                            if(no_of_seats<resultSet.getInt("SeatAT"))
                            {
                                try (PreparedStatement updateStatement = conn.prepareStatement("UPDATE theatreinformation SET SeatAT=? WHERE Theatrename=? AND Location=? AND Screenname=?")) 
                                {
                                    updateStatement.setInt(1,resultSet.getInt("SeatAT")-no_of_seats);
                                    updateStatement.setString(2,theatrename);
                                    updateStatement.setString(3,theatrelocation);
                                    updateStatement.setString(4,screenname);
                                    updateStatement.executeUpdate();
                                }
                                return true;
                            }
                        }
                        else if(showtime.equals("Evening"))
                        {
                            if(no_of_seats<resultSet.getInt("SeatEV"))
                            {
                                try (PreparedStatement updateStatement = conn.prepareStatement("UPDATE theatreinformation SET SeatEV=? WHERE Theatrename=? AND Location=? AND Screenname=?")) 
                                {
                                    updateStatement.setInt(1,resultSet.getInt("SeatEV")-no_of_seats);
                                    updateStatement.setString(2,theatrename);
                                    updateStatement.setString(3,theatrelocation);
                                    updateStatement.setString(4,screenname);
                                    updateStatement.executeUpdate();
                                }
                                return true;
                            }
                        }
                        else if(showtime.equals("Night"))
                        {
                            if(no_of_seats<resultSet.getInt("SeatNT"))
                            {
                                try (PreparedStatement updateStatement = conn.prepareStatement("UPDATE theatreinformation SET SeatNT=? WHERE Theatrename=? AND Location=? AND Screenname=?")) 
                                {
                                    updateStatement.setInt(1,resultSet.getInt("SeatNT")-no_of_seats);
                                    updateStatement.setString(2,theatrename);
                                    updateStatement.setString(3,theatrelocation);
                                    updateStatement.setString(4,screenname);
                                    updateStatement.executeUpdate();
                                }
                                return true;
                            }
                        }
                    }
                }
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        return false;
    }
}
