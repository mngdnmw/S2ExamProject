package GUI.Model;

import BE.User;
import BLL.BLLFacade;



public class LoginModel
  {

    private final static BLLFacade BLL_FAC = new BLLFacade();
    private User currentUser;
    public void logHours(int userId, int hours, int guildId)
      {

      }

    public void getUserFromLogin(String username)
      {
          setCurrentUser(BLL_FAC.getUserFromLogin(username));
      }

    public User getCurrentUser()
      {
        return currentUser;
      }

    public void setCurrentUser(User currentUser)
      {
        this.currentUser = currentUser;
      }
    
    
  }
