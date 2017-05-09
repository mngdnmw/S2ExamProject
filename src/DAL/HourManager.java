package DAL;

import BE.Day;
import BE.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HourManager extends ConnectionManager
  {

    public List<Day> getWorkedDays(User user)
      {
        ArrayList<Day> workedDays = new ArrayList<>();

        int userId = user.getId();

        String query = "SELECT [h].[date], [h].[hours], [h].[guildid], [g].[name], [g].[guildid] "
                + "FROM [hour] [h], [guild] [g] "
                + "WHERE [h].[userid] = " + userId + "AND [g].[guildid] = [h].[guildid]";

        try (Connection con = super.getConnection())
          {
            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next())
              {

                String dateString = rs.getDate("date").toString();
                int hours = rs.getInt("hours");
                String guild = rs.getString("name");

                //System.out.println("Date: "+dateString+" | Hours: "+hours+" | Guild: "+guild);
                Day dayworked = new Day(dateString, hours, guild);
                workedDays.add(dayworked);

              }
          }
        catch (SQLException ex)
          {
            Logger.getLogger(HourManager.class.getName()).log(Level.SEVERE, null, ex);
            System.err.print(ex + "Can't get list of days worked!!!");
          }

        return workedDays;
      }

  }
