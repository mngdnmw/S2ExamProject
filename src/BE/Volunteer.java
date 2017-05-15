package BE;

import java.util.List;

public class Volunteer extends User
{

    public Volunteer(int id, String name, String email, int phone, String note, String residence, List<Guild> guilds )
    {
        super(id, name, email, phone, note, residence, guilds);
        setType(0);
    }
    
}
