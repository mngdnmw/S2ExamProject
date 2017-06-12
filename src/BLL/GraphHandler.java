/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;

import BE.Day;
import BE.Guild;
import DAL.DALFacade;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 *
 * @author Mecaa
 */
public class GraphHandler
{

    private final static DALFacade DAL_FAC = new DALFacade();

    public ArrayList<LinkedHashMap<String, Integer>> sorter(Guild guild, LocalDate periodOne, LocalDate periodTwo)
    {
        ArrayList<LinkedHashMap<String, Integer>> sortedData = new ArrayList<>();
        //creates the hashmap with all values between periodOne and periodTwo
        LinkedHashMap<String, Integer> sortedDataVolun = periodHashMap(periodOne, periodTwo);
        LinkedHashMap<String, Integer> sortedDataMan = periodHashMap(periodOne, periodTwo);

        ArrayList<List<Day>> allHours = new ArrayList<>();
        allHours.addAll(DAL_FAC.getHoursForGuild(guild, periodOne, periodTwo));

        for (int i = 0; i < allHours.size(); i++)
        {
            for (Day day : allHours.get(i))
            {
                String[] daySplit = day.getDate().split("-");
                String datePeriod = monthString(Integer.parseInt(daySplit[1]), Integer.parseInt(daySplit[0]));
               
                if (i == 0)
                {

                    int addHours = sortedDataMan.get(datePeriod) + day.getHour();
                    sortedDataMan.put(datePeriod, addHours);
                }
                else
                {
                    int addHours = sortedDataVolun.get(datePeriod) + day.getHour();
                    sortedDataVolun.put(datePeriod, addHours);
                }

            }
        }
        sortedData.add(sortedDataMan);
        sortedData.add(sortedDataVolun);
        return sortedData;
    }

    public String monthString(int month, int year)
    {
        switch (month)
        {
            case 1:
                return "Jan-" + year;
            case 2:
                return "Feb-" + year;
            case 3:
                return "Mar-" + year;
            case 4:
                return "Apr-" + year;
            case 5:
                return "May-" + year;
            case 6:
                return "Jun-" + year;
            case 7:
                return "Jul-" + year;
            case 8:
                return "Aug-" + year;
            case 9:
                return "Sep-" + year;
            case 10:
                return "Oct-" + year;
            case 11:
                return "Nov-" + year;
            case 12:
                return "Dec-" + year;
            default:
                return "";
        }
    }

    /**
     * Creates a HashMap that using two LocalDate, creates a point for each month in the periods between
     * @param periodOne
     * @param periodTwo
     * @return 
     */
    public LinkedHashMap<String, Integer> periodHashMap(LocalDate periodOne, LocalDate periodTwo)
    {
        LinkedHashMap<String, Integer> periodHashMap = new LinkedHashMap<>();
        if (periodOne.getYear() < periodTwo.getYear())
        {
            for (int i = periodOne.getMonthValue(); i <= 12; i++)
            {

                String month = monthString(i, periodOne.getYear());
                periodHashMap.put(month, 0);
            }
            int repeats = 0;

            for (int i = periodOne.getYear() + 1; i < periodTwo.getYear(); i++)
            {
                repeats++;
                for (int j = 1; j <= 12; j++)
                {
                    String month = monthString(j, periodOne.getYear() + repeats);
                    periodHashMap.put(month, 0);

                }
            }
            for (int i = 1; i <= periodTwo.getMonthValue(); i++)
            {
                String month = monthString(i, periodTwo.getYear());
                periodHashMap.put(month, 0);

            }

        }
        else if (periodOne.getYear() == periodTwo.getYear())
        {
            for (int i = periodOne.getMonthValue(); i <= periodTwo.getMonthValue(); i++)
            {
                String month = monthString(i, periodOne.getYear());
                periodHashMap.put(month, 0);
            }
        }
        return periodHashMap;
    }

}
