package BE;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Day extends RecursiveTreeObject<Day>
{

    //private final StringProperty date;
    private final StringProperty date = new SimpleStringProperty();
    private final IntegerProperty hour = new SimpleIntegerProperty();
    private final StringProperty guild = new SimpleStringProperty();
    

    public Day(String date, int hours, String guild)
    {
         //this.date = new SimpleStringProperty(date);
        this.date.set(date);
        this.hour.set(hours);
        this.guild.set(guild);
        
    }
    
    private String getDate()
    {
        return date.get();
    }

    private void setDate(String value)
    {
        date.set(value);
    }

    private StringProperty dateProperty()
    {
        return date;
    }

    public int getHour()
    {
        return hour.get();
    }

    public void setHour(int value)
    {
        hour.set(value);
    }

    public IntegerProperty hourProperty()
    {
        return hour;
    }

    private String getGuild()
    {
        return guild.get();
    }

    private void setGuild(String guildName)
    {
        guild.set(guildName);
    }

    private StringProperty guildProperty()
    {
        return guild;
    }

}
