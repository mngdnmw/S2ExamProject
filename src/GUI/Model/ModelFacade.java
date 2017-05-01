/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Model;

import BE.EnumCache;
import BE.User;
import java.util.List;
import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 *
 * @author Mecaa
 */
public class ModelFacade
  {

    private final static DataModel DATA_MOD = new DataModel();
    private final static LoginModel LOG_MOD = new LoginModel();
    private final static AnimationModel ANIM_MOD = new AnimationModel();
    private final static LanguageModel LANG_MOD = new LanguageModel();
    //Login Model
    public void logHours(int userId, int hours, int guildId)
      {

      }

    public void getUserFromLogin(String username)
      {
        LOG_MOD.getUserFromLogin(username);
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
        return DATA_MOD.getUserInfo(userId);
      }

    public List<User> getAllUsers()
      {
        return DATA_MOD.getAllUsers();
      }

    public List<User> getAllVolunteers()
      {
        return DATA_MOD.getAllVolunteers();
      }

    public List<User> getAllManagers()
      {
        return DATA_MOD.getAllManagers();
      }

    public List<User> getAllAdmins()
      {
        return DATA_MOD.getAllAdmins();
      }
    
    //Language Model
    public String getLang(String key) {
        return LANG_MOD.getLang(key);
    }
    
    public void setLang(EnumCache.Lang lang) {
        LANG_MOD.setLang(lang);
    }
  }
