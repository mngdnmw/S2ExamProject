package GUI.Model;

import BE.Day;
import BE.EnumCache.Lang;
import BE.Event;
import BE.Guild;
import BE.User;
import BLL.BLLFacade;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javafx.animation.FadeTransition;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.chart.XYChart;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ModelFacade
{

    private static ModelFacade ModFac;
    private final static LoginModel LOG_MOD = new LoginModel();
    private final static AnimationModel ANIM_MOD = new AnimationModel();
    private final static LanguageModel LANG_MOD = new LanguageModel();
    private final static ViewChangerModel VIEW_CHANG_MOD = new ViewChangerModel();
    private final static VolunteerDataModel VOL_DATA_MOD = new VolunteerDataModel();
    private final static GraphSorterModel GRAPH_MOD = new GraphSorterModel();
    private final static CalendarModel CAL_MOD = new CalendarModel();
    private final static BLLFacade BLL_FAC = new BLLFacade();

    public static ModelFacade getModelFacade()
    {
        return ModFac;
    }

    public static void setModelFacade(ModelFacade modelfacade)
    {
        ModFac = modelfacade;
    }

    //Login Model
    public void logWorkDay(String username, String date, int hours, int guildId)
    {
        VOL_DATA_MOD.logWorkDay(username, date, hours, guildId);
    }

    public HashMap<String, String> loadSession()
    {
        return LOG_MOD.loadSession();
    }

    public void getUserFromLogin(String username, String password)
    {
        LOG_MOD.getUserFromLogin(username, password);
    }

    public User getCurrentUser()
    {
        return LOG_MOD.getCurrentUser();
    }

    public void setCurrentUser(User currentUser)
    {
        LOG_MOD.setCurrentUser(currentUser);
    }
    
    public User getSelectedUser()
    {
        return LOG_MOD.getSelectedUser();
    }

    public void setSelectedUser(User selectedUser)
    {
        LOG_MOD.setSelectedUser(selectedUser);
    }

    public void resetSelectedUser()
    {
        LOG_MOD.resetSelectedUser();
    }

    //Animation Model
    /**
     * Enters ModelFacade: A fade out transition that needs a Duration and it
     * fades out a given Node.
     *
     * @param dur
     * @param node
     * @return
     */
    public FadeTransition fadeInTransition(Duration dur, Node node)
    {
        return ANIM_MOD.fadeInTransition(dur, node);
    }

    public void timedSnackbarPopup(String str, Pane parent, int time)
    {
        ANIM_MOD.timedSnackbarPopup(str, parent, time);
    }

    public void snackbarPopup(String str, Pane parent)
    {
        ANIM_MOD.snackbarPopup(str, parent);
    }

    /**
     * Enters ModelFacade: A fade out transition that needs a Duration and it
     * fades out a given Node.
     *
     * @param dur
     * @param node
     * @return
     */
    public FadeTransition fadeOutTransition(Duration dur, Node node)
    {

        return ANIM_MOD.fadeOutTransition(dur, node);
    }

    public Image getLoaderImg()
    {
        return ANIM_MOD.getLoaderImage();
    }

    public StackPane getLoadingScreen()
    {
        return ANIM_MOD.getLoadingScreen();
    }

    //Language Model
    public String getLang(String key)
    {
        return LANG_MOD.getLang(key);
    }

    public void setLang(Lang lang)
    {
        LANG_MOD.setLang(lang);
    }

    public Lang getLangProperty()
    {
        return LANG_MOD.getLangProperty();
    }

    public List<User> getAllVolunteers()
    {
        return BLL_FAC.getAllVolunteers();
    }

    public List<Guild> getAllGuilds()
    {
        return BLL_FAC.getAllGuilds();
    }

    public User getUserInfo(int userId)
    {
        return BLL_FAC.getUserInfo(userId);
    }

    public List<User> getAllUsers()
    {
        return BLL_FAC.getAllUsers();
    }

    public List<User> getAllManagers()
    {
        return BLL_FAC.getAllManagers();
    }

    public List<User> getAllAdmins()
    {
        return BLL_FAC.getAllAdmins();
    }

    public void updateUserInfo(int userId, String name, String email, int type, int phone, String note, String residence, String residence2)
    {
        BLL_FAC.updateUserInfo(userId, name, email, type, phone, note, residence, residence2);
    }

    public void updateUserImage(User user, File img) throws FileNotFoundException
    {
        BLL_FAC.updateUserImage(user, img);
    }

    public InputStream getUserImage(User user)
    {
        return BLL_FAC.getUserImage(user);
    }

    public void addUser(String name, String email, String password, int type, int phone, String residence, String residence2, String note)
    {
        BLL_FAC.addUser(name, email, password, type, phone, residence, residence2, note);
    }

    public Guild getGuild(int id)
    {
        return BLL_FAC.getGuild(id);
    }

    /**
     * Changes the view based on number. 0 goes to the UserInfoView, 1 goes to
     * ManagerEditView, 2 goes to ManagerView 3 goes to the hourLoginView
     *
     * @param GUINumb
     *
     */
    //Change view model
    public void changeView(int GUINumb)
    {
        VIEW_CHANG_MOD.changeView(GUINumb);
    }
    public Stage getCurrentStage()
    {
    return VIEW_CHANG_MOD.getNxtStage();
    }
    //Volunteer data model
    public ObservableList<Day> getWorkedDays(User user)
    {
        return VOL_DATA_MOD.getWorkedDays(user);
    }

    public ObservableList<User> getAllSavedVolunteers()
    {
        return VOL_DATA_MOD.getAllSavedVolunteers();
    }

    public ObservableList<User> getAllSavedManagers()
    {
        return VOL_DATA_MOD.getAllSavedManagers();
    }

    public ObservableList<User> getAllSavedAdmins()
    {
        return VOL_DATA_MOD.getAllSavedAdmins();
    }

    public ObservableList<Guild> getAllSavedGuilds()
    {
        return VOL_DATA_MOD.getAllSavedGuilds();
    }

    public ObservableList<User> getAllSavedUsers()
    {
        return VOL_DATA_MOD.getAllSavedUsers();
    }

    public void setAllVolunteersIntoArray()
    {
        VOL_DATA_MOD.setAllVolunteersIntoArray();
    }

    public void setAllManagersIntoArray()
    {
        VOL_DATA_MOD.setAllManagersIntoArray();
    }

    public void setAllGuildsIntoArray()
    {
        VOL_DATA_MOD.setAllGuildsIntoArray();
    }

    public void setAllAdminsIntoArray()
    {
        VOL_DATA_MOD.setAllAdminsIntoArray();
    }

    //BLL facade
    public int changePassword(User user, String oldPassword, String newPassword)
    {
        return BLL_FAC.changePassword(user, oldPassword, newPassword);
    }

    public int changePasswordAdmin(User user, String newPassword)
    {
        return BLL_FAC.changePasswordAdmin(user, newPassword);
    }

    public List<Guild> getGuildsForUser(User user)
    {
        return BLL_FAC.getGuildsForUser(user);
    }

    public void addGuild(String name)
    {
        BLL_FAC.addGuild(name);
    }

    public void deleteGuild(int guildId)
    {
        BLL_FAC.deleteGuild(guildId);
    }

    public void updateGuild(int guildId, String name)
    {
        BLL_FAC.updateGuild(guildId, name);
    }

    public String parseExportUsers(List<User> users)
    {
        return BLL_FAC.parseExportUserdata(users);
    }

    public void writeExport(File file, String input)
    {
        BLL_FAC.writeExport(file, input);
    }

    public void deleteWorkedDay(User user, Day day)
    {
        VOL_DATA_MOD.deleteWorkedDay(user, day);
    }

    public List<XYChart.Series<String, Number>> graphSort(Guild guild, LocalDate periodOne, LocalDate periodTwo)
    {
        return GRAPH_MOD.sortGraph(guild, periodOne, periodTwo);
    }

//    public void editHours(String username, String date, int hours, int guildId)
//    {
//        VOL_DATA_MOD.editWorkDay(username, date, hours, guildId);
//    }

    public void logEvent(Event event)
    {
        BLL_FAC.logEvent(event);
    }

    public Event getEvent(int id)
    {
        return BLL_FAC.getEvent(id);
    }

    public List<Event> getAllEvents()
    {
        return BLL_FAC.getAllEvents();
    }

    public User getUserFromUsername(String username)
    {
        return BLL_FAC.getUserFromUsername(username);
    }
    
    public ObservableList<Day> editWorkedDay(String username, String date, int hrs, int guildId){
        return VOL_DATA_MOD.editWorkedDay(username, date, hrs, guildId); 
    }

    //ErrorManager functions
    public void setErrorCode(int eCode)
    {
        BLL_FAC.setErrorCode(eCode);
    }

    public String getErrorString()
    {
        return BLL_FAC.getErrorString();
    }
    
    public void formatCalendar(DatePicker datePicker){
        CAL_MOD.formatCalendar(datePicker);
    }
    public boolean activeLastYear(String lastWorked){
        return CAL_MOD.activeLastYear(lastWorked);
    }
    
    
    public boolean isUserInfoView()
    {
        return VIEW_CHANG_MOD.isUserInfoView();
    }

    public String parseExportHours(List<User> users) {
        return BLL_FAC.parseExportHours(users);
    }
    
}
