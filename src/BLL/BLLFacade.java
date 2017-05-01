package BLL;

import BE.User;

public class BLLFacade
  {

    private final static LoginHandler LOG_HAND = new LoginHandler();

    public User getUserFromLogin(String username)
      {
        return LOG_HAND.getUserFromLogin(username);
      }
  }
