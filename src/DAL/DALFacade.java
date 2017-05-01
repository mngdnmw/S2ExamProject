package DAL;

public class DALFacade
  {

    private final static DataManager DATA_MAN = new DataManager();
    private final static LoginManager LOGIN_MAN = new LoginManager();

    public void logHours(String str, String date, int hours, int guildId)
      {
        LOGIN_MAN.logHours(str, date, hours, guildId);

        DATA_MAN.getAllManagers();
        DATA_MAN.getAllUsers();
        DATA_MAN.getAllVolunteers();
        DATA_MAN.getUserInfo(guildId);
      }

    public void getAllAdmins()
      {
        DATA_MAN.getAllAdmins();
      }

    public void getAllManagers()
      {
        DATA_MAN.getAllManagers();
      }

    public void getAllVolunteers()
      {
        DATA_MAN.getAllVolunteers();
      }

  }
