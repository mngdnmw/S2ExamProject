package BLL;

import BE.Day;
import BE.EnumCache.Lang;
import BE.Event;
import BE.Guild;
import BE.User;
import DAL.DALFacade;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BLLFacade
{

    private final static DALFacade DAL_FAC = new DALFacade();
    private final static LoginHandler LOG_HAND = new LoginHandler();
    private final static LanguageHandler LANG_HAND = new LanguageHandler();
    private final static ExportParser exportParser = new ExportParser();
    private final static GraphHandler GRAPH_HAND = new GraphHandler();

    public User getUserFromLogin(String username, String password)
    {
        return LOG_HAND.getUserFromLogin(username, password);
    }

    /**
     *
     * @param str
     * @param date
     * @param hours
     * @param guildId
     * @throws SQLException
     */
    public void logHours(String str, String date, int hours, int guildId) throws SQLException
    {
        DAL_FAC.logHours(str, date, hours, guildId);
    }

    public List<Guild> getAllGuilds()
    {
        return DAL_FAC.getAllGuilds();
    }

    public void addUser(String name, String email, String password, int type, int phone, String residence, String residence2, String note)
    {
        DAL_FAC.addUser(name, email, password, type, phone, residence, residence2, note);
    }

    public List<Day> getWorkedDays(User user)
    {
        return DAL_FAC.getWorkedDays(user);
    }

    public int changePassword(User user, String oldPassword, String newPassword)
    {
        return DAL_FAC.changePassword(user, oldPassword, newPassword);
    }
    
    public int changePasswordAdmin(User user, String newPass) {
        return DAL_FAC.changePasswordAdmin(user, newPass);
    }

    public HashMap<String, String> loadSession()
    {
        return DAL_FAC.loadSession();
    }

    public Guild getGuild(int id)
    {
        return DAL_FAC.getGuild(id);

    }

    public void addGuild(String name)
    {
        DAL_FAC.addGuild(name);
    }

    public void deleteGuild(int guildId)
    {
        DAL_FAC.deleteGuild(guildId);
    }

    public void updateGuild(int guildId, String name)
    {
        DAL_FAC.updateGuild(guildId, name);
    }

    public List<User> getAllVolunteers()
    {

        return DAL_FAC.getAllVolunteers();
    }

    public User getUserInfo(int userId)
    {
        return DAL_FAC.getUserInfo(userId);
    }

    public List<User> getAllUsers()
    {
        return DAL_FAC.getAllUsers();
    }

    public List<User> getAllManagers()
    {
        return DAL_FAC.getAllManagers();
    }

    public List<User> getAllAdmins()
    {
        return DAL_FAC.getAllAdmins();
    }

    public void updateUserInfo(int userId, String name, String email, int type, int phone, String note, String residence, String residence2)
    {
        DAL_FAC.updateUserInfo(userId, name, email, type, phone, note, residence, residence2);
    }

    public void updateUserImage(User user, File img) throws FileNotFoundException
    {
        DAL_FAC.updateUserImage(user, img);
    }

    public InputStream getUserImage(User user)
    {
        return DAL_FAC.getUserImage(user);
    }

    public List<Guild> getGuildsForUser(User user)
    {
        return DAL_FAC.getGuildsForUser(user);
    }

    public Lang getLangProperty()
    {
        return LANG_HAND.getLangProperty();
    }

    public String getLang(String key)
    {
        return LANG_HAND.getLang(key);
    }

    public void setLang(Lang lang)
    {
        LANG_HAND.setLang(lang);
    }
    
    public String parseExport(List<User> users) {
        return exportParser.parseUsers(users);
    }
    
    public void writeExport(File file,String input) {
        DAL_FAC.writeExport(file, input);
    }
    public void deleteWorkedDay(User user, Day day)
    {
        DAL_FAC.deleteWorkedDay(user, day);
    }

    public ArrayList<HashMap<Integer, Integer>> graphSorter(Guild guild)
    {
        return GRAPH_HAND.sorter(guild);
    }

    public void editHours(String username, String date, int hours, int guildId) throws SQLException
    {
        DAL_FAC.editHours(username, date, hours, guildId);
    }
    
    public void logEvent(Event event) {
        DAL_FAC.logEvent(event);
    }
    
    public Event getEvent(int id) {
        return DAL_FAC.getEvent(id);
    }
    
    public List<Event> getAllEvents() {
        return DAL_FAC.getAllEvents();
    }
    
    public User getUserFromUsername(String username) {
        return LOG_HAND.getUserFromUsername(username);
    }
}
