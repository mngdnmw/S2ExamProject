package GUI.Model;

import BE.User;
import BLL.BLLFacade;
import java.util.HashMap;

public class LoginModel
{

    private final static BLLFacade BLL_FAC = new BLLFacade();

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


    public HashMap<String, String> loadSession()
    {
        return BLL_FAC.loadSession();
    }

}
