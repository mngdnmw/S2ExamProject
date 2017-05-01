package BLL;

import BE.User;
import BE.Volunteer;

public class LoginHandler
  {

    public User getUserFromLogin(String username)
      {
        boolean parsable = true;
        int tester = 0;
        try
          {
            tester = Integer.parseInt(username);

          }
        catch (NumberFormatException e)
          {
            parsable = false;
          }
        if (parsable)
          {
            return new Volunteer(1, "Daniel", "LOL@EMAIL.DK", 0, tester, "There is a note", "2131 dskhhkdfs road");
          }
        else
          {
            return new Volunteer(1, "Daniel", username, 0, 50458222, "There is a note", "123 parkvej");
            
          }
      }
  }
