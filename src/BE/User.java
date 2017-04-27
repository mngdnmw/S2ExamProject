/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BE;

/**
 *
 * @author Desmoswal
 */
public abstract class User
{
    int id;
    String name;
    String email;
    int type;
    int phone;
    String note;

    public User(int id, String name, String email, int type, int phone, String note)
    {
        this.id = id;
        this.name = name;
        this.email = email;
        this.type = type;
        this.phone = phone;
        this.note = note;
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getEmail()
    {
        return email;
    }


    public int getType()
    {
        return type;
    }

    public int getPhone()
    {
        return phone;
    }

    public String getNote()
    {
        return note;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public void setPhone(int phone)
    {
        this.phone = phone;
    }

    public void setNote(String note)
    {
        this.note = note;
    }
}
