package GUI.Model;

import BE.Day;
import BE.EnumCache;
import BE.EnumCache.Lang;
import BE.Guild;
import BE.User;
import BLL.BLLFacade;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class ModelFacade
  {

    private static ModelFacade ModFac;
    private final static GeneralInfoModel GEN_INFO_MOD = new GeneralInfoModel();
    private final static LoginModel LOG_MOD = new LoginModel();
    private final static AnimationModel ANIM_MOD = new AnimationModel();
    private final static LanguageModel LANG_MOD = new LanguageModel();
    private final static ViewChangerModel VIEW_CHANG_MOD = new ViewChangerModel();
    private final static VolunteerDataModel VOL_DATA_MOD = new VolunteerDataModel();
    private ArrayList<User> allUsers = new ArrayList();
    private final static BLLFacade BLL_FAC = new BLLFacade();

    //Login Model
    public Boolean logHours(String username, int hours, int guildId)
      {
        return LOG_MOD.logHours(username, hours, guildId);
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

    /**
     * Enters ModelFacade: A fade out transition that needs a Duration and it
     * fades out a given Node.
     *
     * @param dur
     * @param node
     * @return
     */
    //Animation Model
    public FadeTransition fadeInTransition(Duration dur, Node node)
      {
        return ANIM_MOD.fadeInTransition(dur, node);
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

    //Data Model
    public List<User> getAllSavedVolunteers()
      {

        return allUsers;
      }

    public User getUserInfo(int userId)
      {
        return GEN_INFO_MOD.getUserInfo(userId);
      }

    public List<User> getAllUsers()
      {
        return GEN_INFO_MOD.getAllUsers();
      }

    public void setAllVolunteersIntoArray()
      {
        allUsers.clear();
        allUsers.addAll(GEN_INFO_MOD.getAllVolunteers());
      }

    public List<User> getAllManagers()
      {
        return GEN_INFO_MOD.getAllManagers();
      }

    public List<User> getAllAdmins()
      {
        return GEN_INFO_MOD.getAllAdmins();
      }

    //Language Model
    public String getLang(String key)
      {
        return LANG_MOD.getLang(key);
      }

    public void setLang(EnumCache.Lang lang)
      {
        LANG_MOD.setLang(lang);
      }

    public Lang getLangProperty()
      {
        return LANG_MOD.getLangProperty();
      }

    public void updateUserInfo(int userId, String name, String email, int type, int phone, String note, String residence, String residence2)
      {
        GEN_INFO_MOD.updateUserInfo(userId, name, email, type, phone, note, residence, residence2);
      }

    public void updateUserImage(User user, File img) throws FileNotFoundException
      {
        GEN_INFO_MOD.updateUserImage(user, img);
      }

    public InputStream getUserImage(User user)
      {
        return GEN_INFO_MOD.getUserImage(user);
      }

    public List<Guild> getAllGuilds()
      {
        return GEN_INFO_MOD.getAllGuilds();
      }

    /**
     * Changes the view based on number. 0 goes to the UserInfoView, 1 goes to
     * ManagerEditView, 2 goes to ManagerView 3 goes to the hourLoginView
     *
     * @param GUINumb
     *
     */
    public void changeView(int GUINumb)
      {
        VIEW_CHANG_MOD.changeView(GUINumb);
      }

    public static ModelFacade getModelFacade()
      {
        return ModFac;
      }

    public static void setModelFacade(ModelFacade modelfacade)
      {
        ModFac = modelfacade;
      }

    public void addUser(String name, String email, String password, int type, int phone, String residence, String residence2, String note)
      {
        GEN_INFO_MOD.addUser(name, email, password, type, phone, residence, residence2, note);
      }

    public List<Day> getWorkedDays(User user)
      {
        return VOL_DATA_MOD.getWorkedDays(user);
      }

    public int changePassword(User user, String oldPassword, String newPassword)
      {
        return BLL_FAC.changePassword(user, oldPassword, newPassword);
      }

    public HashMap<String, String> loadSession()
      {
        return LOG_MOD.loadSession();
      }

    public Guild getGuild(int id)
      {
        return GEN_INFO_MOD.getGuild(id);
      }
  }
