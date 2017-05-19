package GUI.Model;

import BE.Day;
import BE.User;
import BLL.BLLFacade;
import java.util.ArrayList;
import java.util.List;

public class VolunteerDataModel
{

    private final static BLLFacade BLL_FAC = new BLLFacade();
    
    private ArrayList<User> allUsers = new ArrayList();
    private ArrayList<User> allManagers = new ArrayList<>();

    public List<Day> getWorkedDays(User user)
    {
        return BLL_FAC.getWorkedDays(user);
    }

    public void setAllVolunteersIntoArray()
    {
        allUsers.clear();
        allUsers.addAll(BLL_FAC.getAllVolunteers());
    }

    public List<User> getAllSavedVolunteers()
    {
        return allUsers;
    }
    
    public void setAllManagersIntoArray() {
        allManagers.clear();
        allManagers.addAll(BLL_FAC.getAllManagers());
    }
    
    
    public List<User> getAllSavedManagers() {
        return allManagers;
    }
}
