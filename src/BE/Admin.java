package BE;

import java.util.List;

public class Admin extends User
  {

    public Admin(int id, String name, String email, int phone, String note, String residence, List<Guild> guilds)
      {
        super(id, name, email, phone, note, residence, guilds);
          setType(2);
      }

  }
