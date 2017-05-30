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

    public int logHours(String username, String date, int hours, int guildId)
    {
        int errorCode = -1;
        try
        {
            BLL_FAC.logHours(username, date, hours, guildId);
        }
        catch (SQLException ex)
        {
            errorCode = ex.getErrorCode();

        }
        finally
        {
            System.out.println(errorCode);
            return errorCode;
        }

    }

    int editHours(String username, String date, int hours, int guildId)
    {

        int errorCode = 0;
        try
        {
            BLL_FAC.editHours(username, date, hours, guildId);
        }
        catch (SQLException ex)
        {
            errorCode = ex.getErrorCode();

        }
        finally
        {
            System.out.println(errorCode);
            return errorCode;
        }

    }

    public HashMap<String, String> loadSession()
    {
        return BLL_FAC.loadSession();
    }

}
