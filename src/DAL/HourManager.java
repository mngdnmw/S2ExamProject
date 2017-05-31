package DAL;

import BE.Day;
import BE.Guild;
import BE.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HourManager extends ConnectionManager
{

    ErrorManager erMan = new ErrorManager();

    /**
     * Logs hours into the database using userId, guildId, hours and date.
     *
     * @param userId
     * @param date
     * @param hours
     * @param guildId
     */
    public void logHours(int userId, String date, int hours, int guildId)
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
        catch (SQLException ex)
        {
            erMan.setErrorCode(ex.getErrorCode());
            System.out.println("" + ex.getErrorCode());
        }
    }

    void editHours(int userId, String date, int hours, int guildId)
    {
        try (Connection con = super.getConnection())
        {
            String sqlCommand
                    = "UPDATE [hour] SET [hours] =?  WHERE userid =? AND [date] = ? AND [guildid]=?";

            PreparedStatement pstat = con.prepareStatement(sqlCommand);
            pstat.setInt(1, hours);
            pstat.setInt(2, userId);
            pstat.setString(3, date);
            pstat.setInt(4, guildId);
            pstat.executeUpdate();

        }
        catch (SQLException ex)
        {

            erMan.setErrorCode(ex.getErrorCode());
            Logger.getLogger(HourManager.class.getName()).log(Level.SEVERE, null, ex);
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

            erMan.setErrorCode(ex.getErrorCode());
            Logger.getLogger(HourManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        return workedDays;
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

            erMan.setErrorCode(ex.getErrorCode());
            Logger.getLogger(HourManager.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Could not delete worked day");

        }

    }

    /**
     * Gets all hours worked for a guild for the current year
     *
     * @param guild
     * @return
     */
    public List<List<Day>> getHoursForGuild(Guild guild, LocalDate periodOne, LocalDate periodTwo)
    {
        ArrayList<List<Day>> allHours = new ArrayList<>();
        ArrayList<Day> managerHours = new ArrayList<>();
        ArrayList<Day> volunteerHours = new ArrayList<>();
        GeneralInfoManager genMan = new GeneralInfoManager();

        try (Connection con = super.getConnection())
        {
            String query = "SELECT * FROM hour\n"
                    + "WHERE date BETWEEN '" + periodOne + "' AND '" + periodTwo + "' \n "
                    + "AND  guildid = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, guild.getId());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next())
            {
                String date = rs.getString("date");
                String guildName = guild.getName();
                int guildid = guild.getId();
                int hour = rs.getInt("hours");
                if (genMan.getUserInfo(rs.getInt("userid")).getType() == 1)
                {
                    managerHours.add(new Day(date, hour, guildName, guildid));

                }
                else
                {
                    volunteerHours.add(new Day(date, hour, guildName, guildid));
                }

            }

            allHours.add(managerHours);
            allHours.add(volunteerHours);
            return allHours;
        }
        catch (SQLException ex)
        {

            erMan.setErrorCode(ex.getErrorCode());
            Logger.getLogger(HourManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
