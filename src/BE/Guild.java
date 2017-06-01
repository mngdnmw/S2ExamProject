package BE;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Guild
{

    private final IntegerProperty id = new SimpleIntegerProperty();

    private final StringProperty name = new SimpleStringProperty();

    public Guild(int id, String name)
    {
        setId(id);
        setName(name);
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

    public int getId()
    {
        return id.get();
    }

    public void setId(int value)
    {
        id.set(value);
    }

    public IntegerProperty idProperty()
    {
        return id;
    }

    @Override
    public String toString()
    {
        return getName();
    }
    

}
