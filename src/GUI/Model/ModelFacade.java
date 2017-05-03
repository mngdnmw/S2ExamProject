package GUI.Model;

import BE.EnumCache;
import BE.Guild;
import BE.User;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class ModelFacade
  {

    private static ModelFacade ModFac;
    private final static GeneralInfoModel GEN_INFO_MOD = new GeneralInfoModel();
    private final static LoginModel LOG_MOD = new LoginModel();
    private final static AnimationModel ANIM_MOD = new AnimationModel();
    private final static LanguageModel LANG_MOD = new LanguageModel();
    private final static ViewChangerModel VIEW_CHANG_MOD = new ViewChangerModel();

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

    //Data Model
    public User getUserInfo(int userId)
      {
        return GEN_INFO_MOD.getUserInfo(userId);
      }

    public List<User> getAllUsers()
      {
        return GEN_INFO_MOD.getAllUsers();
      }

    public List<User> getAllVolunteers()
      {
        return GEN_INFO_MOD.getAllVolunteers();
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

    public void updateUserInfo(int userId, String name, String email, int type, int phone, String note, String residence)
      {
        GEN_INFO_MOD.updateUserInfo(userId, name, email, type, phone, note, residence);
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
  }
