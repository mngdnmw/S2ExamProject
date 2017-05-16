package GUI.Model;

import BE.User;
import BLL.BLLFacade;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginModel
  {

    private final static BLLFacade BLL_FAC = new BLLFacade();

    //Stand in until we have the user passed through
    private User currentUser = null;
    public void getUserFromLogin(String username, String password)
      {
        setCurrentUser(BLL_FAC.getUserFromLogin(username, password));
      }

    public User getCurrentUser()
      {
        return currentUser;
      }

    public void setCurrentUser(User currentUser)
      {
        this.currentUser = currentUser;

      }

    public Boolean logHours(String username, int hours, int guildId)
      {

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
        
        String dateString = sdf.format(date);
        try
          {
            BLL_FAC.logHours(username, dateString, hours, guildId);
          }
        catch (SQLException ex)
          {
            Logger.getLogger(LoginModel.class.getName()).log(Level.SEVERE, null, ex);
            return false;
          }
        return true;

      }
    
    public HashMap<String,String> loadSession() {
        return BLL_FAC.loadSession();
    }

  }
