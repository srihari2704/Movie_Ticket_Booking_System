package MainPackage;
import MainPackage.Theatre;
public class Screen extends Theatre
{
    private String screenname[];
    private int seatscapacity[];
    public Screen()
    {
        super();
    }
    public Screen(String theatrename,String location,String showtime[],Double theatrerating,String screenname[],int[] seatcapacity)
    {
        super(theatrename,location,showtime,theatrerating);
        this.screenname=screenname;
        this.seatscapacity=seatcapacity;
    }
    public String[] getScreenName()
    {
        return this.screenname;
    }
    public int[] getseatsCapacity()
    {
        return this.seatscapacity;
    }
}
