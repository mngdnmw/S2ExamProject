package BE;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public abstract class User extends RecursiveTreeObject<User>
  {

    int id;
    String name;
    String email;
    int type;
    int phone;
    String note;
    String residence;

    public User(int id, String name, String email, int phone, String note, String residence)
      {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.note = note;
        this.residence = residence;
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

    public String getResidence()
      {
        return residence;
      }

    public void setResidence(String address)
      {
        this.residence = address;
      }
  }
