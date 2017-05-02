package BE;

public class Admin extends User
{

    public Admin(int id, String name, String email, int type, int phone, String address, String note)
    {
        super(id, name, email, 2, phone, address, note);
    }
    
}
