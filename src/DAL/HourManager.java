package DAL;

import BE.Day;
import BE.User;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HourManager extends ConnectionManager
{

    public List<Day> getWorkedDays(int userId) throws SQLException
    {
        ArrayList<Day> workedDays = new ArrayList<>();

        //int userId = user.getId();

        String query = (""
                + "SELECT h.[date], h.[hours], h.[guildid], g.[name]"
                + "FROM [hour] h, [guild] g"
                + "WHERE h.[userid] = "+ userId);               

        try (Connection con = super.getConnection())
        {
            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next())
            {
                String dateString = rs.getDate("date").toString();
                int hours = rs.getInt("hours");
                String guild = rs.getString("name");
                
                System.out.println("Date: "+dateString+" | Hours: "+hours+" | Guild: "+guild);
                Day dayworked = new Day(dateString, hours, guild);
                workedDays.add(dayworked);
                
//                for (Day workedDay : workedDays)
//                {
//                    System.out.println("Date: "+dateString+" | Hours: "+hours+" | Guild: "+guild);
//                }
            }
        }

        return null;
    }

}
