package DAL;

import BE.Day;
import BE.Guild;
import BE.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
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

    /**
     * Gets all hours worked for a guild for the current year
     *
     * @param guild
     * @return
     */
    public List<List<Day>> getHoursForGuild(Guild guild)
    {
        ArrayList<List<Day>> allHours = new ArrayList<>();
        ArrayList<Day> managerHours = new ArrayList<>();
        ArrayList<Day> volunteerHours = new ArrayList<>();
        GeneralInfoManager genMan = new GeneralInfoManager();
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        String periodOne = (year) + "-01-01";
        String periodTwo = year + "-12-31";
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
                String guildName =  guild.getName();
                int hour =rs.getInt("hours");
                if (genMan.getUserInfo(rs.getInt("userid")).getType() >= 1)
                {
                    managerHours.add(new Day(date,hour ,guildName));
                }
                else
                {
                    volunteerHours.add(new Day(date,hour ,guildName));
                }
                allHours.add(managerHours);
                allHours.add(volunteerHours);
                return allHours;
            }
            
        }
        catch (SQLException ex)
        {
            Logger.getLogger(HourManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
