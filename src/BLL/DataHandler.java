package BLL;

import BE.User;
import DAL.GeneralInfoManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class DataHandler
{
    GeneralInfoManager dataManager = new GeneralInfoManager();
    
    public User getUserInfo(int userId)
    {
        return dataManager.getUserInfo(userId);
    }
    
    public List<User> getAllUsers()
    {
        return dataManager.getAllUsers();
    }
    
    public List<User> getAllVolunteers()
    {
        return dataManager.getAllVolunteers();
    }
    
    public List<User> getAllManagers()
    {
        return dataManager.getAllManagers();
    }
    
    public List<User> getAllAdmins()
    {
        return dataManager.getAllAdmins();
    }
    
    public void updateUserInfo(int userId, String name, String email, int type, int phone, String note, String residence, String residence2) {
        dataManager.updateUserInfo(userId, name, email, type, phone, note, residence, residence2);
    }
    
    public void updateUserImage(User user, File img) throws FileNotFoundException {
        dataManager.updateUserImage(user, img);
    }
    
    public InputStream getUserImage(User user) {
        return dataManager.getUserImage(user);
    }
}
