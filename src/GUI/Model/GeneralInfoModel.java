package GUI.Model;

import BE.User;
import BLL.DataHandler;
import java.util.List;

public class GeneralInfoModel
{
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
    
    public void updateUserInfo(int userId, String name, String email, int type, int phone, String note, String residence) {
        dataHandler.updateUserInfo(userId, name, email, type, phone, note, residence);
    }
}
