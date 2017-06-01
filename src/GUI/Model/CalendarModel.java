package GUI.Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class CalendarModel
{

    public void formatCalendar(DatePicker datePicker)
    {
        StringConverter converter = new StringConverter<LocalDate>()
        {
            DateTimeFormatter dateFormatter
                    = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            @Override
            public String toString(LocalDate date)
            {
                if (date != null)
                {
                    return dateFormatter.format(date);
                }
                else
                {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string)
            {
                if (string != null && !string.isEmpty())
                {
                    return LocalDate.parse(string, dateFormatter);
                }
                else
                {
                    return null;
                }
            }
        };
        datePicker.setConverter(converter);

        // Create a day cell factory
        datePicker.setDayCellFactory(new Callback<DatePicker, DateCell>()
        {
            @Override
            public DateCell call(final DatePicker datepicker)
            {
                return new DateCell()
                {
                    @Override
                    public void updateItem(LocalDate item, boolean empty)

                    {
                        // Must call super
                        super.updateItem(item, empty);
                        // Disable all future date cells
                        if (item.isAfter(LocalDate.now()))
                        {
                            this.setDisable(true);
                        }
                    }
                };
            }
        });
    }

    public boolean activeLastYear(String lastWorked) 
    {

        try
        {
            SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
            
            Date today = new Date();
            
            Date lastActive = myFormat.parse(lastWorked);
            
            long diff = today.getTime() - lastActive.getTime();
            
            long daysDiff = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            
            if (daysDiff > 366)
            {
                return false;
            }
            
            return true;
        }
        catch (ParseException ex)
        {
            Logger.getLogger(CalendarModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
