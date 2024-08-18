package dbpack;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import MainPackage.*;
import com.mysql.cj.xdevapi.PreparableStatement;
class CorrectString extends Exception
{
    public String getMessage(String s)
    {
        return s;
    }
}
public class Admin 
{
    final String DB_URL = "jdbc:mysql://localhost:3306/theatredb";
    final String USER = "root";
    final String PASS = "Srikrishn@n@mysql#2023#";
    public void changeDb(ArrayList<Movie> movielist) throws SQLException
    {
        Scanner input=new Scanner(System.in);
        int choice=0;
        do
        {
            System.out.print("0.Exit\n");
            System.out.print("1.Insert\n");
            System.out.print("2.Update\n");
            System.out.print("3.Delete\n");
            System.out.println();
            System.out.print("Enter the choice:");
            choice=input.nextInt();
            input.nextLine();
            if(choice==1)
            {
                String theatrename,location,filmname,tamil,english,hindi,inox,dolby;
                String[] showtime={"Morning","Afternoon","Evening","Night"};
                String[] screenname=new String[2];
                String[] language=new String[3];
                int[] seatcapacity={100,100,100,100};
                Double theatrerating,movierating;
                System.out.print("TheatreName:");
                theatrename=input.nextLine();
                System.out.print("TheatreRating:");
                theatrerating=input.nextDouble();
                input.nextLine();
                System.out.print("Location:");
                location=input.nextLine();
                System.out.print("Moviename:");
                filmname=input.nextLine();
                System.out.print("Movie Rating:");
                movierating=input.nextDouble();
                input.nextLine();
                try
                {
                    System.out.print("Tamil:");
                    tamil=input.nextLine();
                    if(!tamil.equals("YES") && !tamil.equals("NO"))
                    {
                        throw new CorrectString();
                    }
                }
                catch(CorrectString e)
                {
                    System.out.print(e.getMessage("No Valid String")+"\n");
                    tamil="NO";
                }
                language[0]=tamil;
                try
                {
                    System.out.print("English:");
                    english=input.nextLine();
                    if(!english.equals("YES") && !english.equals("NO"))
                    {
                        throw new CorrectString();
                    }
                }
                catch(CorrectString e)
                {
                    System.out.print(e.getMessage("No Valid String")+"\n");
                    english="NO";
                }
                language[1]=english;
                try
                {
                    System.out.print("Hindi:");
                    hindi=input.nextLine();
                    if(!hindi.equals("YES") && !hindi.equals("NO"))
                    {
                        throw new CorrectString();
                    }
                }
                catch(CorrectString e)
                {
                    System.out.print(e.getMessage("No Valid String")+"\n");
                    hindi="NO";
                }
                language[2]=hindi;
                try
                {
                    System.out.print("INOX:");
                    inox=input.nextLine();
                    if(!inox.equals("YES") && !inox.equals("NO"))
                    {
                        throw new CorrectString();
                    }
                }
                catch(CorrectString e)
                {
                    System.out.print(e.getMessage("No Valid String")+"\n");
                    inox="NO";
                }
                screenname[0]=inox;
                try
                {
                    System.out.print("Dolby:");
                    dolby=input.nextLine();
                    if(!dolby.equals("YES") && !dolby.equals("NO"))
                    {
                        throw new CorrectString();
                    }
                }
                catch(CorrectString e)
                {
                    System.out.print(e.getMessage("No Valid String")+"\n");
                    dolby="NO";
                }
                screenname[1]=dolby;
                System.out.print("Duration:");
                String duration=input.nextLine();
                System.out.print("Genre:");
                String genre=input.nextLine();
                movielist.add(new Movie(theatrename,location,showtime,theatrerating,screenname,seatcapacity,filmname,genre,duration,language,movierating));
                try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                PreparedStatement insertStatement = conn.prepareStatement("INSERT INTO theatredetails VALUES(?,?,?,?,?,?,?,?,?,?,?,?)")) 
                {
                    insertStatement.setString(1,theatrename);
                    insertStatement.setString(2,location);
                    insertStatement.setString(3,filmname);
                    insertStatement.setString(4,tamil);
                    insertStatement.setString(5,english);
                    insertStatement.setString(6,hindi);
                    insertStatement.setString(7,inox);
                    insertStatement.setString(8,dolby);
                    insertStatement.setDouble(9,theatrerating);
                    insertStatement.setDouble(10,movierating);
                    insertStatement.setString(11,duration);
                    insertStatement.setString(12,genre);
                    insertStatement.executeUpdate();
                }
                catch(SQLException e) 
                {
                    e.printStackTrace();
                }
                String DB_URL="jdbc:mysql://localhost:3306/theatreinfo",USER="root",PASS="Srikrishn@n@mysql#2023#";
                try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                    Statement stat = conn.createStatement()) 
                {
                    String insertQuery="INSERT INTO theatreinformation VALUES(?,?,?,?,?,?,?)";
                    try (Connection conns = DriverManager.getConnection(DB_URL, USER, PASS);
                    PreparedStatement insertStatement = conns.prepareStatement(insertQuery))
                    {
                        if(screenname[0].equals("YES"))
                        {
                            insertStatement.setString(1,theatrename);
                            insertStatement.setString(2,"INOX");
                            insertStatement.setInt(3,100);
                            insertStatement.setInt(4,100);
                            insertStatement.setInt(5,100);
                            insertStatement.setInt(6,100);
                            insertStatement.setString(7,location);
                            insertStatement.executeUpdate();
                        }
                    } 
                    catch (SQLException e) 
                    {
                        e.printStackTrace();
                    }
                    try (Connection conns = DriverManager.getConnection(DB_URL, USER, PASS);
                    PreparedStatement insertStatement = conns.prepareStatement(insertQuery))
                    {
                        if(screenname[1].equals("YES"))
                        {
                            insertStatement.setString(1,theatrename);
                            insertStatement.setString(2,"DOLBY");
                            insertStatement.setInt(3,100);
                            insertStatement.setInt(4,100);
                            insertStatement.setInt(5,100);
                            insertStatement.setInt(6,100);
                            insertStatement.setString(7,location);
                            insertStatement.executeUpdate();
                        } 
                    }
                catch(SQLException e)
                {
                    e.printStackTrace();
                }
            }
            }
            else if(choice==2)
            {
                int updatechoice;
                do
                {
                    System.out.print("0.Exit\n");
                    System.out.print("1.MovieName\n");
                    System.out.print("Enter your choice:");
                    updatechoice=input.nextInt();
                    input.nextLine();
                    if(updatechoice==1)
                    {
                        String moviename,tamil,english,hindi;
                        System.out.print("Enter the moviename:");
                        moviename=input.nextLine();
                        try
                        {
                            System.out.print("Enter the language avaiable or not in tamil:");
                            tamil=input.nextLine();
                            if(!tamil.equals("YES") && !tamil.equals("NO"))
                            {
                                throw new CorrectString();
                            }
                        }
                        catch(CorrectString e)
                        {
                            System.out.print(e.getMessage("No Valid String")+"\n");
                            tamil="NO";
                        }
                        try
                        {
                            System.out.print("Enter the language avaiable or not in english:");
                            english=input.nextLine();
                            if(!english.equals("YES") && !english.equals("NO"))
                            {
                                throw new CorrectString();
                            }
                        }
                        catch(CorrectString e)
                        {
                            System.out.print(e.getMessage("No Valid String")+"\n");
                            english="NO";
                        }
                        try
                        {
                            System.out.print("Enter the language avaiable or not in hindi:");
                            hindi=input.nextLine();
                            if(!hindi.equals("YES") && !hindi.equals("NO"))
                            {
                                throw new CorrectString();
                            }
                        }
                        catch(CorrectString e)
                        {
                            System.out.print(e.getMessage("No Valid String")+"\n");
                            hindi="NO";
                        }
                        for(int i=0;i<movielist.size();i++)
                        {
                            if(movielist.get(i).getMovieName().equals(moviename))
                            {
                                movielist.get(i).getLanguage()[0]=tamil;
                                movielist.get(i).getLanguage()[1]=english;
                                movielist.get(i).getLanguage()[2]=hindi;
                            }
                        }
                        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                        PreparedStatement updateStatement = conn.prepareStatement("UPDATE theatredetails SET Filmname=?, Tamil=?, English=?, Hindi=? WHERE Filmname=?")) 
                        {
                            updateStatement.setString(1,moviename);
                            updateStatement.setString(2,tamil);
                            updateStatement.setString(3,english);
                            updateStatement.setString(4,hindi);
                            updateStatement.setString(5,moviename);
                            updateStatement.executeUpdate();
                        }
                        catch(SQLException e) 
                        {
                            e.printStackTrace();
                        }
                    }
                }while(updatechoice!=0);
            }
            else if(choice==3)
            {
                int deletechoice;
                do
                {
                    System.out.print("0.Exit\n");
                    System.out.print("1.Theatrename\n");
                    System.out.print("2.Moviename\n");
                    System.out.print("Enter the choice:");
                    deletechoice=input.nextInt();
                    input.nextLine();
                    if(deletechoice==1)
                    {
                        String theatrename;
                        System.out.print("Enter the theatrename:");
                        theatrename=input.nextLine();
                        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                        PreparedStatement deleteStatement = conn.prepareStatement("DELETE FROM theatredetails WHERE Theatrename=? ")) 
                        {
                            deleteStatement.setString(1,theatrename);
                            deleteStatement.executeUpdate();
                        }
                        catch(SQLException e) 
                        {
                            e.printStackTrace();
                        }
                    }
                    else if(deletechoice==2)
                    {
                        String moviename;
                        System.out.print("Enter the moviename:");
                        moviename=input.nextLine();
                        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                        PreparedStatement deleteStatement = conn.prepareStatement("DELETE FROM theatredetails WHERE Filmname=? ")) 
                        {
                            deleteStatement.setString(1,moviename);
                            deleteStatement.executeUpdate();
                        }
                        catch(SQLException e) 
                        {
                            e.printStackTrace();
                        }
                    }
                }while(deletechoice!=0);
            }
            else if(choice==0)
            {
                return;            
            }
        }while(choice!=0);
        input.close();
    }
}
