/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;

import BE.User;
import DAL.DataManager;
import java.util.List;

/**
 *
 * @author Desmoswal
 */
public class DataHandler
{
    DataManager dataManager = new DataManager();
    
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
}
