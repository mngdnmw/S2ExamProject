package BLL;

import BE.Guild;
import BE.User;
import DAL.DALFacade;
import java.sql.SQLException;
import java.util.List;

public class BLLFacade
  {

    private final static DALFacade DAL_FAC = new DALFacade();
    private final static LoginHandler LOG_HAND = new LoginHandler();

    public User getUserFromLogin(String username)
      {
        return LOG_HAND.getUserFromLogin(username, username);
      }

    /**
     *
     * @param str
     * @param date
     * @param hours
     * @param guildId
     * @throws SQLException
     */
    public void logHours(String str, String date, int hours, int guildId) throws SQLException
      {
        DAL_FAC.logHours(str, date, hours, guildId);
      }

    public List<Guild> getAllGuilds()
      {
        return DAL_FAC.getAllGuilds();
      }
  }
