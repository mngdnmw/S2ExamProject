package BE;

public class Volunteer extends User
{

    public Volunteer(int id, String name, String email, int type, int phone, String note)
    {
        super(id, name, email, 0, phone, note);
    }
    
}
