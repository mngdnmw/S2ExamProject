package BLL;

import BE.User;
import DAL.DALFacade;

public class LoginHandler
  {

    private final static DALFacade DAL_FAC = new DALFacade();
    

    public User getUserFromLogin(String username, String password)
      {
        int id = DAL_FAC.getUserId(username);
        if (id != -1)
          {
            return DAL_FAC.getUserFromLogin(id, password);
          }
        return null;
      }
  }
