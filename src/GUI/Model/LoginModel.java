
package GUI.Model;


import BLL.LoginHandler;

public class LoginModel
{
    LoginHandler loginHandler = new LoginHandler();
    
    public void logHours(int userId, String date, int hours, int guildId)
    {
        loginHandler.logHours(userId, date, hours, guildId);
    }
}
