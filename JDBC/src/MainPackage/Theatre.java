package MainPackage;
import java.util.*;
import java.util.Collection;
public class Theatre 
{
    private String theatrename,location,showtime[];
    private Double theatrerating;
    public Theatre(){}
    public Theatre(String theatrename,String location,String showtime[],Double theatrerating)
    {
        this.theatrename=theatrename;
        this.location=location;
        this.showtime=showtime;
        this.theatrerating=theatrerating;
    }
    public String getTheatrename()
    {
        return this.theatrename;
    }
    public String getLocation()
    {
        return this.location;
    }
    public String[] getShowtime()
    {
        return this.showtime;
    }
    public Double getTheatreRating()
    {
        return this.theatrerating;
    }
} 