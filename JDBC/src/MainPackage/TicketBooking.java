package MainPackage;
public class TicketBooking
{
    private String name,bookedtime,bookedday,theatrename,location,showtime,screenname,moviename,genre,duration,language;
    private Double price;
    public TicketBooking(String theatrename,String location,String showtime,String screenname,String moviename,String genre,String duration,String language,String name,String bookedtime,String bookedday,Double price)
    {
        this.theatrename=theatrename;
        this.location=location;
        this.showtime=showtime;
        this.screenname=screenname;
        this.moviename=moviename;
        this.genre=genre;
        this.duration=duration;
        this.language=language;
        this.name=name;
        this.bookedtime=bookedtime;
        this.bookedday=bookedday;
        this.price=price;
    }
    public void printTicket()
    {
        System.out.print("-------------------------------------------------\n");
        System.out.print("            "+this.theatrename+"           \n");
        System.out.print("            "+this.location+"              \n");
        System.out.print("Moviename:"+this.moviename+"\n");
        System.out.print("Movie Duration:"+this.duration+"\n");
        System.out.print("Langauge:"+this.language+"\n");
        System.out.print("Genre:"+this.genre+"\n");
        System.out.print("Screenname:"+this.screenname+"\n");
        System.out.print("Showtime:"+this.showtime+"\n");
        System.out.print("bookedtime:"+this.bookedtime+"\n");
        System.out.print("bookedday:"+this.bookedday+"\n");
        System.out.print("Name:"+this.name+"\n");
        // System.out.print("Email ID:"+this.emailid);
        // System.out.print("Phone number:"+this.phonenumber);
        System.out.print("Price:"+this.price+"\n");
        System.out.print("-------------------------------------------------\n");
    }
}