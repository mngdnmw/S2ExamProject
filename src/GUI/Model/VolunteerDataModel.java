package GUI.Model;

import BE.Day;
import BE.User;
import BLL.BLLFacade;
import java.util.List;

public class VolunteerDataModel
{

    private final static BLLFacade BLL_FAC = new BLLFacade();

    public List<Day> getWorkedDays(User user)
    {
        return BLL_FAC.getWorkedDays(user);
    }
}
