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

        String query = "SELECT [h].[date], [h].[hours], [h].[guildid], [g].[name], [g].[guildid] "
                + "FROM [hour] [h], [guild] [g] "
                + "WHERE [h].[userid] = " + user.getId() + "AND [g].[guildid] = [h].[guildid]";

        try (Connection con = super.getConnection())
        {
            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next())
            {

                String dateString = rs.getDate("date").toString();
                int hours = rs.getInt("hours");
                String guild = rs.getString("name");
                int guildId = rs.getInt("guildId");
                Day dayworked = new Day(dateString, hours, guild, guildId);
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

    public void editWorkedDay(Day day, User user, String date, int hour, String guild)
    {

        try (Connection con = super.getConnection())
        {

        }
        catch (SQLException ex)
        {
            Logger.getLogger(HourManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void deleteWorkedDay(User user, Day day)
    {
        String query ="DELETE FROM [hour] WHERE [userid]=? AND [date]=? AND [guildid]=?";
        
        try (Connection con = super.getConnection())
        {
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, user.getId());
            pstmt.setString(2, day.getDate());
            pstmt.setInt(3, day.getGuildId());
            
            pstmt.execute();
            
        }

        catch (SQLException ex)
        {
            Logger.getLogger(HourManager.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Could not delete worked day");

        }

    }
}
