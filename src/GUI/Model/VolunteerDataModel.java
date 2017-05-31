package GUI.Model;

import BE.Day;
import BE.Guild;
import BE.User;
import BLL.BLLFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class VolunteerDataModel
{

    private final static BLLFacade BLL_FAC = new BLLFacade();

    private final ObservableList allUsers = FXCollections.observableArrayList();
    private final ObservableList allVolunters = FXCollections.observableArrayList();
    private final ObservableList allManagers = FXCollections.observableArrayList();
    private final ObservableList allAdmins = FXCollections.observableArrayList();
    private final ObservableList allGuilds = FXCollections.observableArrayList();
    private final ObservableList workDays = FXCollections.observableArrayList();

    public ObservableList<Day> getWorkedDays(User user)
    {
        workDays.clear();
        workDays.addAll(BLL_FAC.getWorkedDays(user));
        
        return workDays;
    }

    public ObservableList<Day> deleteWorkedDay(User user, Day deleteDay)
    {
       workDays.remove(deleteDay);
       
       BLL_FAC.deleteWorkedDay(user, deleteDay);
       
       return workDays;
       
    }
    
    
        public void logWorkDay(String username, String date, int hours, int guildId)
    {
      
      
            BLL_FAC.logHours(username, date, hours, guildId);
            workDays.clear();
            workDays.addAll(BLL_FAC.getWorkedDays(BLL_FAC.getUserInfo(BLL_FAC.getUserId(username))));
       
       

    }

   public void editWorkDay(String username, String date, int hours, int guildId)
    {
        
            BLL_FAC.editHours(username, date, hours, guildId);
            workDays.clear();
            workDays.addAll(BLL_FAC.getWorkedDays(BLL_FAC.getUserInfo(BLL_FAC.getUserId(username))));
        
    }
    
    
    public void setAllVolunteersIntoArray()
    {
        allVolunters.clear();
        allVolunters.addAll(BLL_FAC.getAllVolunteers());
    }

    public ObservableList<User> getAllSavedVolunteers()
    {
        return allVolunters;
    }


    public ObservableList<User> getAllSavedUsers()
    {
        allUsers.clear();
        allUsers.addAll(allVolunters);
        allUsers.addAll(allManagers);
        allUsers.addAll(allAdmins);
        return allUsers;
    }

    public void setAllManagersIntoArray()
    {
        allManagers.clear();
        allManagers.addAll(BLL_FAC.getAllManagers());
    }

    public void setAllGuildsIntoArray()
    {
        allGuilds.clear();
        allGuilds.addAll(BLL_FAC.getAllGuilds());
    }

    public ObservableList<Guild> getAllSavedGuilds()
    {
        return allGuilds;
    }

    public void setAllAdminsIntoArray()
    {
        allAdmins.clear();
        allAdmins.addAll(BLL_FAC.getAllAdmins());
    }

    public ObservableList<User> getAllSavedManagers()
    {
        return allManagers;
    }

    public ObservableList<User> getAllSavedAdmins()
    {
        return allAdmins;
    }
}
