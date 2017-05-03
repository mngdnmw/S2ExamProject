package DAL;

import BE.Guild;
import java.sql.SQLException;
import java.util.List;

public class DALFacade
  {

    private final static GeneralInfoManager GEN_INFO_MAN = new GeneralInfoManager();
    private final static LoginManager LOGIN_MAN = new LoginManager();

    public void logHours(String username, String date, int hours, int guildId) throws SQLException
      {
        int userid = -1;
        userid = getUserId(username);
        if (userid != -1)
          {
            LOGIN_MAN.logHours(userid, date, hours, guildId);
          }

//        DATA_MAN.getAllManagers();
//        DATA_MAN.getAllUsers();
//        DATA_MAN.getAllVolunteers();
//        DATA_MAN.getUserInfo(guildId);
      }

    public void getAllAdmins()
      {
        GEN_INFO_MAN.getAllAdmins();
      }

    public void getAllManagers()
      {
        GEN_INFO_MAN.getAllManagers();
      }

    public void getAllVolunteers()
      {
        GEN_INFO_MAN.getAllVolunteers();
      }

    public int getUserId(String username)
      {
        boolean parsable = true;
        int phoneNo = 0;

        try
          {
            phoneNo = Integer.parseInt(username);
          }
        catch (NumberFormatException e)
          {
            parsable = false;
            
          }
        if (parsable)
          {
            return GEN_INFO_MAN.getUserIdFromPhoneNumber(phoneNo);
          }
        else
          {
            return GEN_INFO_MAN.getUserIdFromEmail(username);
          }
      }
    public List<Guild> getAllGuilds()
      {
        return GEN_INFO_MAN.getAllGuilds();
      }

  }
