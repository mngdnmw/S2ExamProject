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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginManager extends ConnectionManager
{

    private HashMap<String, String> session;

    public int changePassword(User user, String oldPassword, String newPassword)
    {
        try (Connection con = super.getConnection())
        {
            String query = "UPDATE [user] "
                    + " SET [user].[password] = ? "
                    + " WHERE [user].[userid] = ? "
                    + " AND [user].[password] = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, newPassword);
            pstmt.setInt(2, user.getId());
            pstmt.setString(3, oldPassword);
            return pstmt.executeUpdate();

        }
        catch (SQLException ex)
        {
            Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    public int changePasswordAdmin(User user, String newPassword) {
        try(Connection con = super.getConnection()) {
            String query = "update [user] set [password] = '"+newPassword+"' where [userid] = "+user.getId();
            Statement s = con.createStatement();
            
            return s.executeUpdate(query);
        } catch (SQLException e) {
            Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, e);
        }
        return 0;
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
                String residence2 = rs.getString("residence2");
                String lastWorked = "";
                List<Guild> guilds = new ArrayList<>();
                

                GeneralInfoManager genInfoMan = new GeneralInfoManager();
                switch (type)
                {
                    case 0:
                        guilds.addAll(genInfoMan.getGuildsForUser(id));
                        
                        return new Volunteer(id, name, email, phone, note, residence, residence2, guilds, lastWorked);
                    case 1:
                        guilds.addAll(genInfoMan.getGuildsForManager(id));
                        return new Manager(id, name, email, phone, note, residence, residence2, guilds, lastWorked);
                    case 2:
                        return new Admin(id, name, email, phone, note, residence, residence2, guilds,lastWorked);
                }
            }
        }
        catch (SQLException ex)
        {
            Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void saveSession(String username, int guildid, int hours)
    {
        props.setProperty("LAST_USER", username);
        props.setProperty("LAST_GUILD", String.valueOf(guildid));
        props.setProperty("LAST_HOURS", String.valueOf(hours));
        super.saveConfig(props);
    }

    public HashMap<String, String> loadSession()
    {
        if (props.getProperty("LAST_USER") != null && props.getProperty("LAST_GUILD") != null && props.getProperty("LAST_HOURS") != null)
        {
            if (!props.getProperty("LAST_USER").isEmpty() && !props.getProperty("LAST_GUILD").isEmpty() && !props.getProperty("LAST_HOURS").isEmpty())
            {
                session = new HashMap<>();
                session.put("lastuser", props.getProperty("LAST_USER"));
                session.put("lastguild", props.getProperty("LAST_GUILD"));
                session.put("lasthours", props.getProperty("LAST_HOURS"));
            }

        }
        return session;
    }
}
