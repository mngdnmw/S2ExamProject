/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Model;

import BLL.LoginHandler;

/**
 *
 * @author Desmoswal
 */
public class LoginModel
{
    LoginHandler loginHandler = new LoginHandler();
    
    public void logHours(int userId, String date, int hours, int guildId)
    {
        loginHandler.logHours(userId, date, hours, guildId);
    }
}
