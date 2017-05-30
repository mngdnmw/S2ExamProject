package GUI.Model;

import BE.User;
import BLL.BLLFacade;
import java.sql.SQLException;
import java.util.HashMap;
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

    public void logHours(String username, String date, int hours, int guildId)
    {
       
        BLL_FAC.logHours(username, date, hours, guildId);

    }

    public void editHours(String username, String date, int hours, int guildId)
    {

        BLL_FAC.editHours(username, date, hours, guildId);
    }

    public HashMap<String, String> loadSession()
    {
        return BLL_FAC.loadSession();
    }

}
