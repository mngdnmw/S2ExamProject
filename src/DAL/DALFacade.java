package DAL;

import BE.Day;
import BE.EnumCache.Lang;
import BE.Guild;
import BE.User;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class DALFacade
{

    private final static GeneralInfoManager GEN_INFO_MAN = new GeneralInfoManager();
    private final static LoginManager LOGIN_MAN = new LoginManager();
    private final static HourManager HR_MAN = new HourManager();
    private final static LanguageManager LANG_MAN = new LanguageManager();

    public void logHours(String username, String date, int hours, int guildId) throws SQLException
    {
        int userid = -1;
        userid = getUserId(username);
        if (userid != -1)
        {
            HR_MAN.logHours(userid, date, hours, guildId);
            LOGIN_MAN.saveSession(username, guildId, hours);
        }

    }

    public List<User> getAllAdmins()
    {
        return GEN_INFO_MAN.getAllAdmins();
    }

    public List<User> getAllManagers()
    {
        return GEN_INFO_MAN.getAllManagers();
    }

    public List<User> getAllVolunteers()
    {
        return GEN_INFO_MAN.getAllVolunteers();
    }

    public List<Guild> getAllGuilds()
    {
        return GEN_INFO_MAN.getAllGuilds();
    }

    public int getUserId(String username)
    {
        return GEN_INFO_MAN.getUserId(username);
    }

    public User getUserFromLogin(int id, String password)
    {
        return LOGIN_MAN.getUserFromLogin(id, password);
    }

    public int changePassword(User user, String oldPassword, String newPassword)
    {
        return LOGIN_MAN.changePassword(user, oldPassword, newPassword);
    }

    public void addUser(String name, String email, String password, int type, int phone, String residence, String residence2, String note)
    {
        GEN_INFO_MAN.addUser(name, email, password, type, phone, residence, residence2, note);
    }

    public List<Day> getWorkedDays(User user)
    {
        return HR_MAN.getWorkedDays(user);
    }

    public HashMap<String, String> loadSession()
    {
        return LOGIN_MAN.loadSession();
    }

    public Guild getGuild(int id)
    {
        return GEN_INFO_MAN.getGuild(id);
    }

    public void addGuild(String name)
    {
        GEN_INFO_MAN.addGuild(name);
    }

    public void deleteGuild(int guildId)
    {
        GEN_INFO_MAN.deleteGuild(guildId);
    }

    public void updateGuild(int guildId, String name)
    {
        GEN_INFO_MAN.updateGuild(guildId, name);
    }

    public User getUserInfo(int userId)
    {
        return GEN_INFO_MAN.getUserInfo(userId);
    }

    public List<User> getAllUsers()
    {
        return GEN_INFO_MAN.getAllUsers();
    }

    public void updateUserInfo(int userId, String name, String email, int type, int phone, String note, String residence, String residence2)
    {
        GEN_INFO_MAN.updateUserInfo(userId, name, email, type, phone, note, residence, residence2);
    }

    public void updateUserImage(User user, File img) throws FileNotFoundException
    {
        GEN_INFO_MAN.updateUserImage(user, img);
    }

    public InputStream getUserImage(User user)
    {
        return GEN_INFO_MAN.getUserImage(user);
    }

    public List<Guild> getGuildsForUser(User user)
    {
        return GEN_INFO_MAN.getGuildsForUser(user.getId());
    }
    
    public Properties getLanguageFile() {
        return LANG_MAN.getLanguageFile();
    }
    
    public Properties getLanguageFile(Lang lang) {
        return LANG_MAN.getLanguageFile(lang);
    }
    
    public Lang getLangProperty() {
        return LANG_MAN.getLangProperty();
    }
    
    public void deleteWorkedDay(User user, Day day){
        HR_MAN.deleteWorkedDay(user, day);
    }
}
