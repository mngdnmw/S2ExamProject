package DAL;

import BE.Admin;
import BE.Guild;
import BE.Manager;
import BE.User;
import BE.Volunteer;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    public void logHours(int userId, String date, int hours, int guildId) throws SQLException
      {
        //int userid = 
        try (Connection con = super.getConnection())
          {
            String sqlCommand
                    = "INSERT into [hour](userid, date, hours, guildid) values (?, ?, ?, ?)";
            PreparedStatement pstat = con.prepareStatement(sqlCommand);
            pstat.setInt(1, userId);
            pstat.setString(2, date);
            pstat.setInt(3, hours);
            pstat.setInt(4, guildId);
            pstat.executeUpdate();

          }
      }

    public User getUserFromLogin(int userid, String password)
      {
        try (Connection con = super.getConnection())
          {
            String query = "SELECT * FROM [user] WHERE [user].[userid] = ? AND [user].[password] = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, userid);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next())
              {
                int id = rs.getInt("userid");
                String name = rs.getString("name");
                String email = rs.getString("email");
                int type = rs.getInt("type");
                int phone = rs.getInt("phone");
                String note = rs.getString("note");
                String residence = rs.getString("residence");
                List<Guild> guilds = new ArrayList<>();
                switch(type){
                    case 0:
                        return new Volunteer(id, name, email, phone, note, residence, guilds);
                    case 1: 
                        return new Manager(id, name, email, phone, note, residence, guilds);
                    case 2: 
                        return new Admin(id, name, email, phone, note, residence, guilds);
                        
                }
              }
          }
        catch (SQLException ex)
          {
            Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
          }
        return null;
      }
  }
