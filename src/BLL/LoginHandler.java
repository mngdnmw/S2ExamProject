/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;

import DAL.LoginManager;

/**
 *
 * @author Desmoswal
 */
public class LoginHandler
{
    LoginManager loginManager = new LoginManager();
    
    public void logHours(int userId, String date, int hours, int guildId)
    {
        loginManager.logHours(userId, date, hours, guildId);
    }
}
