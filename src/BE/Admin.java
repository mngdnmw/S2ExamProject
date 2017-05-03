package BE;

public class Admin extends User
  {

    public Admin(int id, String name, String email, int phone, String note, String residence)
      {
        super(id, name, email, phone, note, residence);
          setType(2);
      }

  }
