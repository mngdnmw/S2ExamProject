/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;

import BE.User;
import DAL.GeneralInfoManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

/**
 *
 * @author Desmoswal
 */
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
    
    public void updateUserInfo(int userId, String name, String email, int type, int phone, String note, String residence) {
        dataManager.updateUserInfo(userId, name, email, type, phone, note, residence);
    }
    
    public void updateUserImage(User user, File img) throws FileNotFoundException {
        dataManager.updateUserImage(user, img);
    }
    
    public InputStream getUserImage(User user) {
        return dataManager.getUserImage(user);
    }
}
