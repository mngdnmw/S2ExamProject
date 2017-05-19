package DAL;

import BE.User;
import BE.Volunteer;
import BE.Admin;
import BE.Guild;
import BE.Manager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GeneralInfoManager extends ConnectionManager
{

    /**
     * Gets user information from the database using userId. Returns volunteer,
     * manager or admin depending on type variable.
     *
     * @param userId
     * @return
     */
    public User getUserInfo(int userId)
    {
        //String query = "select [user].[name], [user].[email],[user].phone From [user] where [user].userid ="+userId;
        String query = "select [user].* from [user] where [user].userid =" + userId;

        try (Connection con = super.getConnection())
        {
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(query);

            while (rs.next())
            {
                int id = rs.getInt("userid");
                String name = rs.getString("name");
                String email = rs.getString("email");
                int type = rs.getInt("type");
                int phone = rs.getInt("phone");
                String note = rs.getString("note");
                String residence = rs.getString("residence");
                String residence2 = rs.getString("residence2");

                List<Guild> guilds = GeneralInfoManager.this.getGuildsForUser(userId);

                //If it's a volunteer
                if (type == 0)
                {

                    Volunteer volunteer = null;
                    //(id, name, email, password, type, phone, note, residence, guilds);
                    volunteer = new Volunteer(id, name, email, phone, note, residence, residence2, guilds);

                    return volunteer;
                }

                //If it's a manager
                if (type == 1)
                {

                    Manager manager = null;
                    //(id, name, email, password, type, phone, note);
                    manager = new Manager(id, name, email, phone, note, residence, residence2, guilds);

                    return manager;
                }

                //If it's an admin
                if (type == 2)
                {

                    Admin admin = null;
                    //(id, name, email, password, type, phone, note);
                    admin = new Admin(id, name, email, phone, note, residence, residence2, guilds);

                    return admin;
                }
            }

        } catch (SQLException e)
        {
            System.out.println("Exception in DataManager: getUserInfo method.");
            System.out.println(e);
        }
        return null;
    }

    public List<User> getAllUsers()
    {
        ArrayList<User> users = new ArrayList<>();
        String query = "select * from [user]";
        try (Connection con = super.getConnection())
        {
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(query);
            while (rs.next())
            {

                int id = rs.getInt("userid");
                String name = rs.getString("name");
                String email = rs.getString("email");
                int type = rs.getInt("type");
                int phone = rs.getInt("phone");
                String note = rs.getString("note");
                String residence = rs.getString("residence");
                String residence2 = rs.getString("residence2");

                List<Guild> guilds = GeneralInfoManager.this.getGuildsForUser(id);

                switch (type)
                {
                    case 0:

                        Volunteer volunteer = null;
                        //(id, name, email, password, type, phone, note);
                        volunteer = new Volunteer(id, name, email, phone, note, residence, residence2, guilds);
                        users.add(volunteer);

                        break;

                    case 1:

                        Manager manager = null;
                        //(id, name, email, password, type, phone, note);
                        manager = new Manager(id, name, email, phone, note, residence, residence2, guilds);
                        users.add(manager);

                        break;

                    case 2:

                        Admin admin = null;
                        //(id, name, email, password, type, phone, note);
                        admin = new Admin(id, name, email, phone, note, residence, residence2, guilds);
                        users.add(admin);

                        break;

                    default:
                        break;
                }
            }
            return users;
        } catch (SQLException e)
        {
            System.err.println("Exception in: DataManager: getAllUsers method.");
            System.out.println(e);
        }

        return null;
    }

    public List<User> getAllVolunteers()
    {
        ArrayList<User> volunteers = new ArrayList<>();
        String query = "select * from [user] where [user].[type] = 0";
        try (Connection con = super.getConnection())
        {
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(query);
            while (rs.next())
            {

                int id = rs.getInt("userid");
                String name = rs.getString("name");
                String email = rs.getString("email");
                //int type = rs.getInt("type");
                int phone = rs.getInt("phone");
                String note = rs.getString("note");
                String residence = rs.getString("residence");
                String residence2 = rs.getString("residence2");

                List<Guild> guilds = GeneralInfoManager.this.getGuildsForUser(id);

                Volunteer volunteer = null;
                //(id, name, email, password, type, phone, note);
                volunteers.add(new Volunteer(id, name, email, phone, note, residence, residence2, guilds));

            }
        } catch (SQLException e)
        {
            System.err.println("Exception in: DataManager: getAllVolunteers method.");
            System.out.println(e);
        }

        return volunteers;
    }

    public List<User> getAllManagers()
    {
        ArrayList<User> managers = new ArrayList<>();
        String query = "select * from [user] where [user].[type] = 1";
        try (Connection con = super.getConnection())
        {
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(query);
            while (rs.next())
            {

                int id = rs.getInt("userid");
                String name = rs.getString("name");
                String email = rs.getString("email");
                //int type = rs.getInt("type");
                int phone = rs.getInt("phone");
                String note = rs.getString("note");
                String residence = rs.getString("residence");
                String residence2 = rs.getString("residence2");

                List<Guild> guilds = GeneralInfoManager.this.getGuildsForUser(id);

                Manager manager = null;
                //(id, name, email, password, type, phone, note);
                managers.add(new Manager(id, name, email, phone, note, residence, residence2, guilds));

            }
        } catch (SQLException e)
        {
            System.err.println("Exception in: DataManager: getAllManagers method.");
            System.out.println(e);
        }

        return managers;
    }

    public List<User> getAllAdmins()
    {
        ArrayList<User> admins = new ArrayList<>();
        String query = "select * from [user] where [user].[type] = 2";
        try (Connection con = super.getConnection())
        {
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(query);
            while (rs.next())
            {

                int id = rs.getInt("userid");
                String name = rs.getString("name");
                String email = rs.getString("email");
                int phone = rs.getInt("phone");
                String note = rs.getString("note");
                String residence = rs.getString("residence");
                String residence2 = rs.getString("residence2");

                List<Guild> guilds = GeneralInfoManager.this.getGuildsForUser(id);

                Admin admin = null;
                //(id, name, email, password, type, phone, note);
                admins.add(new Admin(id, name, email, phone, note, residence, residence2, guilds));

            }
        } catch (SQLException e)
        {
            System.err.println("Exception in: DataManager: getAllManagers method.");
            System.out.println(e);
        }

        return admins;
    }

    public int getUserId(String username)
    {
        boolean parsable = true;
        int phoneNo = 0;

        try
        {
            phoneNo = Integer.parseInt(username);
        } catch (NumberFormatException e)
        {
            parsable = false;

        }
        if (parsable)
        {
            return getUserIdFromPhoneNumber(phoneNo);
        } else
        {
            return getUserIdFromEmail(username);
        }
    }

    private int getUserIdFromEmail(String username)
    {
        try (Connection con = super.getConnection())
        {
            String query = "SELECT * FROM [user] WHERE [user].[email] = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next())
            {
                return rs.getInt("userid");
            }
        } catch (SQLException ex)
        {
            Logger.getLogger(GeneralInfoManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        return -1;
    }

    private int getUserIdFromPhoneNumber(int username)
    {

        try (Connection con = super.getConnection())
        {
            String query = "SELECT * FROM[user] WHERE phone = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next())
            {
                return rs.getInt("userid");

            }
        } catch (SQLException ex)
        {
            Logger.getLogger(GeneralInfoManager.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return -1;

    }

    public List<Guild> getAllGuilds()
    {
        List<Guild> guilds = new ArrayList<>();
        try (Connection con = super.getConnection())

          {
            String query = "SELECT * FROM [guild]";
            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next())
            {
                guilds.add(new Guild(rs.getInt("guildid"), rs.getString("name")));
            }

        } catch (SQLException ex)
        {
            Logger.getLogger(GeneralInfoManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        return guilds;
    }

    public void updateUserInfo(int userId, String name, String email, int type, int phone, String note, String residence, String residence2)
    {
        try (Connection con = super.getConnection())
        {
            String sqlCommand
                    = "UPDATE [user] SET name=?, email=?, type=?, phone=?, residence=?, residence2=?, note=? WHERE userid=?";
            PreparedStatement pstat = con.prepareStatement(sqlCommand);
            pstat.setString(1, name);
            pstat.setString(2, email);
            pstat.setInt(3, type);
            pstat.setInt(4, phone);
            pstat.setString(5, residence);
            pstat.setString(6, residence2);
            pstat.setString(7, note);
            pstat.setInt(8, userId);
            pstat.executeUpdate();
        } catch (SQLException sqle)
        {
            System.out.println("Exception in: DataManager: updateInfo method");
            System.err.println(sqle);
        }
    }

    public void updateUserImage(User user, File img) throws FileNotFoundException 
    {
        List<Integer> hasImg = new ArrayList<>();
        String checkQuery = "select [userid] from [image]";
        try (Connection con = super.getConnection())
        {
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(checkQuery);
            while (rs.next())
            {
                hasImg.add(rs.getInt("userid"));
            }
        } catch (SQLException e)
        {
            System.out.println(e);
        }

        String query;
        PreparedStatement ps;
        long len = img.length();
        try (Connection con = super.getConnection())
        {
            if (hasImg.contains(user.getId()))
            {
                query = "update [image] set [img] = ? where [userid] = '" + user.getId() + "'";
                ps = con.prepareStatement(query);
                ps.setBinaryStream(1, new FileInputStream(img), len);
            } else
            {
                query = "insert into [image]([userid],[img]) values (?,?)";
                ps = con.prepareStatement(query);
                ps.setInt(1, user.getId());
                ps.setBinaryStream(2, new FileInputStream(img), len);
            }
            ps.execute();

        } catch (SQLException e)
        {
            System.out.println(e);
        }
    }

    public InputStream getUserImage(User user)
    {
        String query = "select [img] from [image] where [image].[userid] = " + user.getId();
        try (Connection con = super.getConnection())
        {
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(query);
            while (rs.next())
            {

                return rs.getBlob("img").getBinaryStream();
            }
        } catch (SQLException e)
        {
            System.out.println(e);
        }
        return null;
    }

    public void addUser(String name, String email, String password, int type, int phone, String residence, String residence2, String note)
    {
        try (Connection con = super.getConnection())
        {
            String sqlCommand
                    = "insert into [user] ([name], [email], [password], [type], [phone], [residence], [residence2], [note]) values (?,?,?,?,?,?,?,?)";
            PreparedStatement pstat = con.prepareStatement(sqlCommand);
            pstat.setString(1, name);
            pstat.setString(2, email);
            pstat.setString(3, password);
            pstat.setInt(4, type);
            pstat.setInt(5, phone);
            pstat.setString(6, residence);
            pstat.setString(7, residence2);
            pstat.setString(8, note);
            pstat.executeUpdate();

        } catch (SQLException sqle)
        {
            System.out.println("Exception in: GeneralInfoManager: addUser method");
            System.err.println(sqle);
        }
    }

    public void updateUserInfo(String name, String email, String password, int type, int phone, String residence, String residence2, String note, int userid)
    {
        try (Connection con = super.getConnection())
        {
            String sqlCommand
                    = "UPDATE [user] SET name=?, email=?, password=?, type=?, phone=?, residence=?, residence2=?, note=? WHERE userid=?";
            PreparedStatement pstat = con.prepareStatement(sqlCommand);
            pstat.setString(1, name);
            pstat.setString(2, email);
            pstat.setString(3, password);
            pstat.setInt(4, type);
            pstat.setInt(5, phone);
            pstat.setString(6, residence);
            pstat.setString(7, residence2);
            pstat.setString(8, note);
            pstat.setInt(9, userid);
            pstat.executeUpdate();
        } catch (SQLException sqle)
        {
            System.out.println("Exception in: DataManager: updateInfo method");
            System.err.println(sqle);
        }
    }

    public List<Guild> getGuildsForUser(int userId)
    {
        List<Guild> guilds = new ArrayList<>();
        try (Connection con = super.getConnection())
        {
            String query = "SELECT DISTINCT a.guildid, a.name\n"
                    + "FROM    guild a,\n"
                    + "[hour] b \n"
                    + "WHERE   b.guildid = a.guildid AND b.userid = " + userId;
            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next())
            {
                guilds.add(new Guild(rs.getInt("guildid"), rs.getString("name")));
            }

        } catch (SQLException ex)
        {
            Logger.getLogger(GeneralInfoManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        return guilds;
    }

    public Guild getGuild(int id)
    {
        String query = "select * from [guild] where [guildid] = " + id;
        try (Connection con = super.getConnection())
        {
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(query);
            Guild guild = null;
            while (rs.next())
            {
                guild = new Guild(rs.getInt("guildid"), rs.getString("name"));
            }
            return guild;
        } catch (SQLException e)
        {
            Logger.getLogger(GeneralInfoManager.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    
    public void addGuild(String name)
      {
        try (Connection con = super.getConnection())
          {
            String sqlCommand
                    = "insert into [guild] ([name]) values (?)";
            PreparedStatement pstat = con.prepareStatement(sqlCommand);
            pstat.setString(1, name);
            pstat.executeUpdate();
          }
        catch (SQLException sqle)
          {
            System.out.println("Exception in: GeneralInfoManager: addGuild method");
            System.err.println(sqle);
          }
      }

    public void deleteGuild(int guildId)
    {
        try (Connection con = super.getConnection())
          {
            String sqlCommand
                    = "DELETE FROM [guild] WHERE guildid=" + guildId;
            PreparedStatement pstat = con.prepareStatement(sqlCommand);
            pstat.executeUpdate();
          }
        catch (SQLException sqle)
          {
            System.out.println("Exception in: GeneralInfoManager: deleteGuild method");
            System.err.println(sqle);
          }
    }
    
    public void updateGuild(int guildId, String name)
    {
        try (Connection con = super.getConnection())
          {
            String sqlCommand
                    = "UPDATE [guild] SET name=? WHERE guildid=?";
            PreparedStatement pstat = con.prepareStatement(sqlCommand);
            pstat.setString(1, name);
            pstat.setInt(2, guildId);
            pstat.executeUpdate();
          }
        catch (SQLException sqle)
          {
            System.out.println("Exception in: GeneralInfoManager: updateGuild method");
            System.err.println(sqle);
          }
    }
  }
