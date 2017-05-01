/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;

import BE.User;
import BE.Volunteer;

/**
 *
 * @author Mecaa
 */
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
            return new Volunteer(1, "Daniel", "LOL@EMAIL.DK", 0, tester, "There is a note");
          }
        else
          {
            return new Volunteer(1, "Daniel", username, 0, 50458222, "There is a note");
            
          }
      }
  }
