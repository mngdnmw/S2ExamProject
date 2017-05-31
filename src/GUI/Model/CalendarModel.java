package GUI.Model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
}
