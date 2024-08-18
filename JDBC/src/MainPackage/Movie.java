package MainPackage;
import MainPackage.Screen;
import java.util.*;
public class Movie extends Screen
{
    private String moviename,genre,duration,language[];
    private Double movierating;
    public Movie()
    {
        super();
    }
    public Movie(String theatrename,String location,String showtime[],Double theatrerating,String screenname[],int[] seatcapacity,String moviename,String genre,String duration,String language[],Double movierating)
    {
        super(theatrename,location,showtime,theatrerating,screenname,seatcapacity);
        this.genre=genre;
        this.moviename=moviename;
        this.movierating=movierating;
        this.duration=duration;
        this.language=language;
    }
    public void sortMovies(ArrayList<String> movielist)
    {
        Collections.sort(movielist);
    }
    public void sortMovies(ArrayList<String> movielist,ArrayList<Double> movieratinglist)
    {
        String tempstring;
        Double tempdouble;
        for(int i=0;i<movieratinglist.size();i++)
        {
            for(int j=0;j<movieratinglist.size()-i-1;j++)
            {
                if(movieratinglist.get(j)>movieratinglist.get(j+1))
                {
                    // Sorting based on rating list
                    tempdouble=movieratinglist.get(j);
                    movieratinglist.set(j,movieratinglist.get(j+1));
                    movieratinglist.set(j+1,tempdouble);
                    // Sorting based in movies list
                    tempstring=movielist.get(j);
                    movielist.set(j,movielist.get(j+1));
                    movielist.set(j+1,tempstring);
                }
            }
        }
    }
    public void display(ArrayList<String> movielist,ArrayList<Double> movierating)
    {
        for(int i=0;i<movielist.size();i++)
        {
            System.out.print(movielist.get(i)+"  "+movierating.get(i)+"\n");
        }
    }
    public String getMovieName()
    {
        return this.moviename;
    }
    public Double getMovieRating()
    {
        return this.movierating;
    }
    public String getGenre()
    {
        return this.genre;
    }
    public String getDuration()
    {
        return this.duration;
    }
    public String[] getLanguage()
    {
        return this.language;
    }
}
