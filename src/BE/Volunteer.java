package BE;

public class Volunteer extends User
{

    public Volunteer(int id, String name, String email, int phone, String note, String residence )
    {
        super(id, name, email, phone, note, residence );
        setType(0);
    }
    
}
