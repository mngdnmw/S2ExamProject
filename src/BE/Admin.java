package BE;

import java.util.List;

public class Admin extends User
{

    public Admin(int id, String name, String email, int phone, String note, String residence, String residence2, List<Guild> guilds, String lastWorked)
    {
        super(id, name, email, phone, note, residence, residence2, guilds, lastWorked);
        setType(2);
    }

}
