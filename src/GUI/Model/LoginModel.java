package GUI.Model;

import BE.User;
import BE.Volunteer;
import BLL.BLLFacade;



public class LoginModel
  {

    private final static BLLFacade BLL_FAC = new BLLFacade();
    
    //Stand in until we have the user passed through
    private User currentUser = new Volunteer(1, "Daniel", "LOL@EMAIL.DK", 0, 9329329, "There is a note", "2131 dskhhkdfs road");;
    
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
