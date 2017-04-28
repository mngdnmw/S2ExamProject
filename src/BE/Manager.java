package BE;

public class Manager extends User
{

    public Manager(int id, String name, String email, int type, int phone, String note)
    {
        super(id, name, email, 1, phone, note);
    }
    
}
