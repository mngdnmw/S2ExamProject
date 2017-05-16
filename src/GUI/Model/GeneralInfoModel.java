package GUI.Model;

import BE.Guild;
import BE.User;
import BLL.BLLFacade;
import BLL.DataHandler;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
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
    public void updateUserInfo(int userId, String name, String email, int type, int phone, String note, String residence, String residence2) {
        dataHandler.updateUserInfo(userId, name, email, type, phone, note, residence, residence2);
    }
    
    public void updateUserImage(User user, File img) throws FileNotFoundException {
        dataHandler.updateUserImage(user, img);
    }
    
    public InputStream getUserImage(User user) {
        return dataHandler.getUserImage(user);
    }
    
    public void addUser(String name, String email, String password, int type, int phone, String residence, String residence2, String note)
    {
        BLL_FAC.addUser(name, email, password, type, phone, residence, residence2, note);
    }
}
