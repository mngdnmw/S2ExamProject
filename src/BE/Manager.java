package BE;

public class Manager extends User
{

    public Manager(int id, String name, String email, int phone, String note, String residence)
    {
        super(id, name, email, phone, note, residence);
        setType(1);
        
    }
    
}
