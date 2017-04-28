package BLL;

import DAL.LoginManager;

public class LoginHandler
{
    LoginManager loginManager = new LoginManager();
    
    public void logHours(int userId, String date, int hours, int guildId)
    {
        loginManager.logHours(userId, date, hours, guildId);
    }
}
