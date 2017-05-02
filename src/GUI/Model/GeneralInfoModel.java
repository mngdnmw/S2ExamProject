package GUI.Model;

import BE.Guild;
import BE.User;
import BLL.BLLFacade;
import BLL.DataHandler;
import java.util.List;

public class GeneralInfoModel
{
    private final static BLLFacade BLL_FAC = new BLLFacade();
    DataHandler dataHandler = new DataHandler();
    
    public User getUserInfo(int userId)
    {
        return dataHandler.getUserInfo(userId);
    }
    
    public List<User> getAllUsers()
    {
        return dataHandler.getAllUsers();
    }
    
    public List<User> getAllVolunteers()
    {
        return dataHandler.getAllVolunteers();
    }
    
    public List<User> getAllManagers()
    {
        return dataHandler.getAllManagers();
    }
    
    public List<User> getAllAdmins()
    {
        return dataHandler.getAllAdmins();
    }
    public List<Guild> getAllGuilds()
      {
      return BLL_FAC.getAllGuilds();
      }
}
