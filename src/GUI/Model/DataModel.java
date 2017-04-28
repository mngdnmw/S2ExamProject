/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Model;

import BE.User;
import BLL.DataHandler;
import java.util.List;

/**
 *
 * @author Desmoswal
 */
public class DataModel
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
}
