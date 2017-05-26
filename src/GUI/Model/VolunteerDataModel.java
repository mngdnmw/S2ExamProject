package GUI.Model;

import BE.Day;
import BE.Guild;
import BE.User;
import BLL.BLLFacade;
import java.util.ArrayList;
import java.util.List;

public class VolunteerDataModel
{

    private final static BLLFacade BLL_FAC = new BLLFacade();

    private ArrayList<User> allUsers = new ArrayList();
    private ArrayList<User> allVolunters = new ArrayList();
    private ArrayList<User> allManagers = new ArrayList<>();
    private ArrayList<User> allAdmins = new ArrayList<>();
    private ArrayList<Guild> allGuilds = new ArrayList<>();

    public List<Day> getWorkedDays(User user)
    {
        return BLL_FAC.getWorkedDays(user);
    }

    public void setAllVolunteersIntoArray()
    {
        allVolunters.clear();
        allVolunters.addAll(BLL_FAC.getAllVolunteers());
    }

    public List<User> getAllSavedVolunteers()
    {
        return allVolunters;
    }

    public void setAllUsersIntoArray()
    {
        allUsers.clear();
        allUsers.addAll(BLL_FAC.getAllUsers());
    }

    public List<User> getAllSavedUsers()
    {
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

    public List<Guild> getAllSavedGuilds()
    {
        return allGuilds;
    }

    public void setAllAdminsIntoArray()
    {
        allAdmins.clear();
        allAdmins.addAll(BLL_FAC.getAllAdmins());
    }

    public List<User> getAllSavedManagers()
    {
        return allManagers;
    }

    public List<User> getAllSavedAdmins()
    {
        return allAdmins;
    }
}
