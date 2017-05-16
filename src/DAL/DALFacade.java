package DAL;

import BE.Day;
import BE.Guild;
import BE.User;
import java.sql.SQLException;
import java.util.List;

public class DALFacade
  {

    private final static GeneralInfoManager GEN_INFO_MAN = new GeneralInfoManager();
    private final static LoginManager LOGIN_MAN = new LoginManager();
    private final static HourManager HR_MAN = new HourManager();

    public void logHours(String username, String date, int hours, int guildId) throws SQLException
      {
        int userid = -1;
        userid = getUserId(username);
        if (userid != -1)
          {
            LOGIN_MAN.logHours(userid, date, hours, guildId);
          }

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

    public List<Guild> getAllGuilds()
      {
        return GEN_INFO_MAN.getAllGuilds();
      }

    public int getUserId(String username)
      {
        return GEN_INFO_MAN.getUserId(username);
      }

    public User getUserFromLogin(int id, String password)
      {
        return LOGIN_MAN.getUserFromLogin(id, password);
      }

    public void changePassword(User user, String oldPassword, String newPassword)
      {
        LOGIN_MAN.changePassword(user, oldPassword, newPassword);
      }

    public void addUser(String name, String email, String password, int type, int phone, String residence, String note)
      {
        GEN_INFO_MAN.addUser(name, email, password, type, phone, residence, note);
      }

    public List<Day> getWorkedDays(User user)
      {
        return HR_MAN.getWorkedDays(user);
      }

    //public List<Guild> getGuildsForUser()
  }
