package BE;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class User extends RecursiveTreeObject<User>
{

    int id;
    int type;

    private final IntegerProperty phone = new SimpleIntegerProperty();
    private final StringProperty residence = new SimpleStringProperty();
    private final StringProperty residence2 = new SimpleStringProperty();
    private final StringProperty note = new SimpleStringProperty();
    private final StringProperty email = new SimpleStringProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty lastWorkedDay = new SimpleStringProperty();
    

    List<Guild> guilds;

    public User(int id, String name, String email, int phone, String note, String residence, String residence2, List<Guild> guilds, String lastWorked)
    {
        this.id = id;
        setName(name);
        setEmail(email);
        setPhone(phone);
        setNote(note);
        setResidence(residence);
        setResidence2(residence2);
        setGuilds(guilds);
        setLastWorkedDay(lastWorked);
    }

        public String getLastWorkedDay()
    {
        return lastWorkedDay.get();
    }

    public void setLastWorkedDay(String value)
    {
        lastWorkedDay.set(value);
    }

    public StringProperty lastWorkedDayProperty()
    {
        return lastWorkedDay;
    }
    public int getId()
    {
        return id;
    }

    public int getType()
    {
        return type;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public void setGuilds(List<Guild> guilds)
    {
        this.guilds = guilds;
    }

    public String getName()
    {
        return name.get();
    }

    public void setName(String value)
    {
        name.set(value);
    }

    public StringProperty nameProperty()
    {
        return name;
    }

    public String getEmail()
    {
        return email.get();
    }

    public void setEmail(String value)
    {
        email.set(value);
    }

    public StringProperty emailProperty()
    {
        return email;
    }

    public int getPhone()
    {
        return phone.get();
    }

    public void setPhone(int value)
    {
        phone.set(value);
    }

    public IntegerProperty phoneProperty()
    {
        return phone;
    }

    public String getNote()
    {
        return note.get();
    }

    public void setNote(String value)
    {
        note.set(value);
    }

    public StringProperty noteProperty()
    {
        return note;
    }

    public String getResidence()
    {
        return residence.get();
    }

    public String getResidence2()
    {
        return residence2.get();
    }

    public void setResidence(String value)
    {
        residence.set(value);
    }

    public void setResidence2(String value)
    {
        residence2.set(value);
    }

    public StringProperty residenceProperty()
    {
        return residence;
    }

    public StringProperty residence2Property()
    {
        return residence2;
    }

    public List<Guild> getGuildList()
    {
        return guilds;
    }

}
