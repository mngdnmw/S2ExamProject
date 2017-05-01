package DAL;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class LoginManager extends ConnectionManager
  {

    /**
     * Logs hours into the database using userId, guildId, hours and date.
     *
     * @param userId
     * @param date
     * @param hours
     * @param guildId
     */
    public void logHours(String username, String date, int hours, int guildId)
      {
        //int userid = 
        try (Connection con = super.getConnection())
          {
            String sqlCommand
                    = "INSERT into [hour](userid, date, hours, guildid) values (?, ?, ?, ?)";
            PreparedStatement pstat = con.prepareStatement(sqlCommand);
            //  pstat.setInt(1, userid);
            pstat.setString(2, date);
            pstat.setInt(3, hours);
            pstat.setInt(4, guildId);
            pstat.executeUpdate();
          }
        catch (SQLException sqle)
          {
            System.out.println("Exception in: LoginManager: logHours method");
            System.err.println(sqle);
          }
      }

    
  }
