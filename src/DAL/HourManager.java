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
        String query = "DELETE FROM [hour] WHERE [userid]=? AND [date]=? AND [guildid]=?";

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
