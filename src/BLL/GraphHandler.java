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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.converter.LocalDateStringConverter;

/**
 *
 * @author Mecaa
 */
public class GraphHandler
{

    private final static DALFacade DAL_FAC = new DALFacade();

    public ArrayList<HashMap<String, Integer>> sorter(Guild guild, LocalDate periodOne, LocalDate periodTwo)
    {
        ArrayList<HashMap<String, Integer>> sortedData = new ArrayList<>();

        HashMap<String, Integer> sortedDataVolun = new HashMap<>();
        HashMap<String, Integer> sortedDataMan = new HashMap<>();

        //creates the hashmap with all values between periodOne and periodTwo
        if (periodOne.getYear() < periodTwo.getYear())
        {
            for (int i = periodOne.getMonthValue(); i <= 12; i++)
            {

                for (int j = 1; j <= 12; j++)
                {
                    String month = monthString(j, periodOne.getYear());
                    sortedDataVolun.put(month, 0);
                    sortedDataMan.put(month, 0);
                }
            }
            int repeats = 0;
            
            for (int i = periodOne.getYear() + 1; i < periodTwo.getYear(); i++)
            {
                repeats++;
                for (int j = 1; j <= 12; j++)
                {
                    String month = monthString(j, periodOne.getYear() + repeats);
                    sortedDataVolun.put(month, 0);
                    sortedDataMan.put(month, 0);
                }
                System.out.println(repeats +"");
            }
            for (int i = 1; i <= periodTwo.getMonthValue(); i++)
            {
                String month = monthString(i, periodTwo.getYear() + repeats);
                sortedDataVolun.put(month, 0);
                sortedDataMan.put(month, 0);
            }

        }
        else if (periodOne.getYear() == periodTwo.getYear())
        {
            for (int i = periodOne.getMonthValue(); i <= periodTwo.getMonthValue(); i++)
            {
                String month = monthString(i, periodOne.getYear());
                sortedDataVolun.put(month, 0);
                sortedDataMan.put(month, 0);
            }
        }
        ArrayList<List<Day>> allHours = new ArrayList<>();
        allHours.addAll(DAL_FAC.getHoursForGuild(guild, periodOne, periodTwo));

        for (int i = 0; i < allHours.size(); i++)
        {
            for (Day day : allHours.get(i))
            {
                DateTimeFormatter format = DateTimeFormatter.ofPattern("MMM-yyyy");
                LocalDate date = LocalDate.parse(day.getDate(), format);
                String datePeriod = date.format(format);

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

}
