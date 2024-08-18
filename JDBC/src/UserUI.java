import dbpack.*;
import MainPackage.*;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

class InvalidEmailException extends Exception 
{
    public String getMessage(String s) 
    {
        return s;
    }
}
class EmailValidator
{
    private final String EMAIL_REGEX ="^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    public boolean isValidEmail(String email) throws InvalidEmailException {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) 
        {
            return true;
        } 
        return false;
    }
}
class InvalidPhoneNumberException extends Exception
{
    String msg;

    public InvalidPhoneNumberException(String msg)
    {
        this.msg=msg;
    }
}
class InvalidChoice extends Exception
{
    public String getMessage(String s)
    {
        return s;
    }
}
public class UserUI 
{
    public static void main(String args[]) throws SQLException, ParseException
    {
        ArrayList<Movie> movielist=new ArrayList<>();
        ArrayList<String> uniquemovielist=new ArrayList<>();
        ReadDbData read=new ReadDbData();
        int choice;
        String name,userid,password,emailid,phonenumber;
        Scanner input=new Scanner(System.in);
        do
        {
            // Load the database data to an arraylist
            System.out.print("0.Exit\n");
            System.out.print("1.Admin Login\n");
            System.out.print("2.User Login\n");
            System.out.print("3.Signup");
            System.out.println();
            System.out.print("Enter your choice:");
            choice=input.nextInt();
            input.nextLine();
            User person=new User();
            try
            {
                if(choice==1)
                {
                    System.out.print("Enter your userid:");
                    userid=input.nextLine();
                    System.out.print("Enter your password:");
                    password=input.nextLine();
                    if(userid.equals("Admin") && password.equals("admin"))
                    {
                        Admin admin=new Admin();
                        admin.changeDb(movielist);
                    }
                }
                else if(choice==2)
                {
                    System.out.print("Enter your userid:");
                    userid=input.nextLine();
                    System.out.print("Enter your password:");
                    password=input.nextLine();
                    if(person.login(userid,password))
                    {
                        System.out.print("Logged In Successfully.\n");
                        read.extractMovieDetails(movielist);
                        uniquemovielist=read.getUniqueMovieList(movielist);
                        // System.out.print(movielist.get(0));
                        int setcondition;
                        do
                        {
                            System.out.print("0.Exit\n");
                            System.out.print("1.Search Movie\n");
                            System.out.print("2.Display Movie List\n");
                            System.out.print("3.Display Movie List Based on MovieRating\n");
                            System.out.print("4.Ticket Booking:\n");
                            System.out.println();
                            System.out.print("Enter a choice:");
                            setcondition=input.nextInt();
                            input.nextLine();
                            if(setcondition==1)
                            {
                                String searchmoviename;
                                System.out.print("Enter a movie to search:");
                                searchmoviename=input.nextLine();
                                for(int i=0;i<movielist.size();i++)
                                {
                                    if(movielist.get(i).getMovieName().equals(searchmoviename))
                                    {
                                        System.out.print("-------------------------------------------------\n");
                                        System.out.print("Theatre name:"+movielist.get(i).getTheatrename()+"\n");
                                        System.out.print("Theatre location:"+movielist.get(i).getLocation()+"\n");
                                        System.out.print("Theatre Rating:"+movielist.get(i).getTheatreRating()+"\n");
                                        System.out.print("Movie seats based on showtime\n");
                                        for(int j=0;j<movielist.get(i).getseatsCapacity().length;j++)
                                        {
                                            if(j==0)
                                            {
                                                System.out.print("Morning Show:"+movielist.get(i).getseatsCapacity()[j]+"\n");
                                            }
                                            else if(j==1)
                                            {
                                                System.out.print("Afternoon Show:"+movielist.get(i).getseatsCapacity()[j]+"\n");
                                            }
                                            else if(j==2)
                                            {   
                                                System.out.print("Evening Show:"+movielist.get(i).getseatsCapacity()[j]+"\n");
                                            }
                                            else
                                            {
                                                System.out.print("Night Show:"+movielist.get(i).getseatsCapacity()[j]);
                                            }
                                        }
                                        System.out.print("\nScreenname:");
                                        for(int j=0;j<movielist.get(i).getScreenName().length;j++)
                                        {
                                            if(!movielist.get(i).getScreenName()[j].equals("NO"))
                                            {
                                                System.out.print(movielist.get(i).getScreenName()[j]+" ");
                                            }
                                        }
                                        System.out.print("\n");
                                        System.out.print("Moviename:"+movielist.get(i).getMovieName()+"\n");
                                        System.out.print("Movie Rating:"+movielist.get(i).getMovieRating()+"\n");
                                        System.out.print("Movie Duration:"+movielist.get(i).getDuration()+"\n");
                                        System.out.print("Movie langauge:");
                                        for(int j=0;j<movielist.get(i).getLanguage().length;j++)
                                        {
                                            if(!movielist.get(i).getLanguage()[j].equals("NO"))
                                            {
                                                System.out.print(movielist.get(i).getLanguage()[j]+" ");
                                            }
                                        }
                                        System.out.print("\nGenre of the movie:"+movielist.get(i).getGenre()+"\n");
                                        System.out.print("-------------------------------------------------\n");
                                    }
                                }
                            }
                            else if(setcondition==2)
                            {
                                ArrayList<Double> movierating=new ArrayList<Double>();
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
                                    movierating.add(value);
                                }
                                Movie movie=new Movie();
                                movie.sortMovies(uniquemovielist);
                                movie.display(uniquemovielist,movierating);
                            }
                            else if(setcondition==3)
                            {
                                ArrayList<Double> movierating=new ArrayList<Double>();
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
                                    movierating.add(value);
                                }
                                Movie movie=new Movie();
                                movie.sortMovies(uniquemovielist,movierating);
                                movie.display(uniquemovielist,movierating);
                            }
                            else if(setcondition==4)
                            {
                                System.out.println("enter the name of the movie:");
                                String moviename=input.nextLine();
                                System.out.print("Enter theatre name:");
                                String theatrename = input.nextLine();
                                System.out.println("Enter the loction for the theatre:");
                                String theatrelocation = input.nextLine();
                                System.out.print("Enter the language:");
                                String langauge=input.nextLine();
                                System.out.print("Enter the screenname:");
                                String screenname=input.nextLine();
                                System.out.print("Enter the showtime:");
                                String showtime=input.nextLine();
                                System.out.print("Enter the number of seats you want to book:");
                                Integer no_of_seats=input.nextInt();
                                input.nextLine();
                                Movie Found=null;
                                for(Movie movie:movielist)
                                {
                                    if(movie.getLocation().equals(theatrelocation) && movie.getTheatrename().equals(theatrename) && movie.getMovieName().equals(moviename))
                                    {
                                        int condition=0;
                                        for(int i=0;i<movie.getLanguage().length;i++)
                                        {
                                            if(movie.getLanguage()[i].equals(langauge))
                                            {
                                                condition++;
                                            }
                                        }
                                        for(int i=0;i<movie.getScreenName().length;i++)
                                        {
                                            if(movie.getScreenName()[i].equals(screenname))
                                            {
                                                condition++;
                                            }
                                        }
                                        for(int i=0;i<movie.getShowtime().length;i++)
                                        {
                                            if(movie.getShowtime()[i].equals(showtime))
                                            {
                                                if(read.seatUpdate(theatrename,screenname,theatrelocation,no_of_seats,showtime))
                                                {
                                                    condition++;
                                                    if(showtime.equals("Morning"))
                                                    {
                                                        movie.getseatsCapacity()[0]-=no_of_seats;
                                                    }
                                                    else if(showtime.equals("Afternoon"))
                                                    {
                                                        movie.getseatsCapacity()[1]-=no_of_seats;
                                                    }
                                                    else if(showtime.equals("Evening"))
                                                    {
                                                        movie.getseatsCapacity()[2]-=no_of_seats;
                                                    }
                                                    else if(showtime.equals("Night"))
                                                    {
                                                        movie.getseatsCapacity()[3]-=no_of_seats;
                                                    }
                                                }
                                            }
                                        }
                                        if(condition==3)
                                        {
                                            Found=movie;
                                        }
                                    }
                                }
                                if (Found != null) 
                                {
                                    String username;
                                    System.out.print("Enter your name:");
                                    username=input.nextLine();
                                    LocalDateTime currentDateTime=LocalDateTime.now();
                                    DateTimeFormatter formatter1=DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a");
                                    DateTimeFormatter formatter2=DateTimeFormatter.ofPattern("hh:mm:ss a");
                                    String bookedTime=currentDateTime.format(formatter1);
                                    String date=currentDateTime.format(formatter2);
                                    String bookedday=currentDateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
                                    // Create TicketBooking object with the found movie details and user input
                                    TicketBooking ticket = new TicketBooking(
                                        Found.getTheatrename(),
                                        Found.getLocation(),
                                        showtime,  
                                        screenname, 
                                        Found.getMovieName(),
                                        Found.getGenre(),
                                        Found.getDuration(),
                                        langauge,  
                                        username,
                                        bookedTime,
                                        bookedday,
                                        no_of_seats*200.00
                                    );
                                    // Print the ticket details
                                    ticket.printTicket();
                                    CustomerBooking update=new CustomerBooking();
                                    update.storeDetails(userid,username,theatrelocation,moviename,date,theatrename,no_of_seats,screenname);
                                }
                                else
                                {
                                    System.out.println("Based on your request it is not avaiable:");
                                }
                            }
                        }while(setcondition!=0);
                    }
                    else
                    {
                        System.out.print("Invalid Login Credintals.");
                    }
                }
                else if(choice==3)
                {
                    System.out.print("Enter your name:");
                    name=input.nextLine();
                    System.out.print("Enter the userid:");
                    userid=input.nextLine();
                    System.out.print("Enter the password:");
                    password=input.nextLine();
                    try
                    {
                        System.out.print("Enter the phonenumber:");
                        phonenumber=input.nextLine();
                        if (!phonenumber.matches("\\d{10}")) 
                        {
                            throw new InvalidPhoneNumberException("Invalid phone number. It should contain exactly 10 digits.\n");
                        } 
                    }
                    catch (InvalidPhoneNumberException e) 
                    {
                        System.out.print(e);
                        phonenumber="0000000000";
                    }
                    try
                    {
                        System.out.print("Enter the email id:");
                        emailid=input.nextLine();
                        EmailValidator emailvalidator=new EmailValidator();
                        if(!emailvalidator.isValidEmail(emailid))
                        {
                            throw new InvalidEmailException();
                        }
                    }
                    catch(InvalidEmailException e)
                    {
                        System.out.print(e.getMessage("Not a valid Email ID")+"\n");
                        emailid="sample@gmail.com";


                    }
                    person.signUp(name,password,userid,emailid,phonenumber);
                }
                else if(choice!=0 && choice>3)
                {
                    throw new InvalidChoice();
                }
            }
            catch(InvalidChoice e)
            {   
                System.out.print(e.getMessage("Invalid Choice")+"\n");
            }
        }while(choice!=0);
        input.close();
        movielist.clear();
        uniquemovielist.clear();
        // update the database;
        //  After finishing the entire process the arraylist should be cleared
    }
}
