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
    public void updateUserInfo(int userId, String name, String email, int type, int phone, String note, String residence) {
        dataHandler.updateUserInfo(userId, name, email, type, phone, note, residence);
    }
}
