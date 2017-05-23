/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;

import BE.Day;
import BE.Guild;
import DAL.DALFacade;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mecaa
 */
public class GraphHandler
{

    private final static DALFacade DAL_FAC = new DALFacade();

    public ArrayList<HashMap<Integer, Integer>> sorter(Guild guild)
    {
        ArrayList<HashMap<Integer, Integer>> sortedData = new ArrayList<>();
        HashMap<Integer, Integer> sortedDataVolun = new HashMap<>();
        HashMap<Integer, Integer> sortedDataMan = new HashMap<>();
        for (int i = 0; i < 12; i++)
        {
            sortedDataVolun.put(i, 0);
            sortedDataMan.put(i, 0);
        }

        ArrayList<List<Day>> allHours = new ArrayList<>();
        allHours.addAll(DAL_FAC.getHoursForGuild(guild));
        for (int i = 0; i < allHours.size(); i++)
        {
            for (Day day : allHours.get(i))
            {
                try
                {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                    sdf.parse(day.getDate());
                    int month = sdf.getCalendar().get(Calendar.MONTH);
                    if (i == 0)
                    {
                        int addHours = sortedDataMan.get(month) + day.getHour();
                        sortedDataMan.put(month, addHours);
                    }
                    else
                    {
                        int addHours = sortedDataVolun.get(month) + day.getHour();
                        sortedDataVolun.put(month, addHours);
                    }

                }
                catch (ParseException ex)
                {
                    Logger.getLogger(GraphHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        sortedData.add(sortedDataMan);
        sortedData.add(sortedDataVolun);
        return sortedData;
    }
}
