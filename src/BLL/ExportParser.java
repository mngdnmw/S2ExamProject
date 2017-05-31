/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;

import BE.Day;
import BE.Guild;
import BE.User;
import DAL.ExportManager;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.input.KeyCode;

/**
 *
 * @author Kristof
 */
public class ExportParser {
    BLLFacade bll = new BLLFacade();
    public String parseExportUserdata(List<User> users) {
        String output = "";
        output += "Name,Email,Phone,Address Secondary Address"+"\r";
        for (User user : users) {
            //String note = ""; 
            //if(user.getNote() != null) {
            //    note += user.getNote().replace(System.lineSeparator(), "\r");
            //}
            String name = (user.getName() != null) ? user.getName() : "";
            String email = (user.getEmail() != null) ? user.getEmail() : "";
            String phone = (user.getPhone() != 0) ? String.valueOf(user.getPhone()) : "";
            String address = (user.getResidence() != null) ? user.getResidence() : "";
            String address2 = (user.getResidence2() != null) ? user.getResidence2() : "";
            output+= name+","+email+","+phone+","+address+","+address2+"\n";
        }

        return output;
    }
    
    public String parseExportHours(List<User> users) {
        String output = "";
        output += "Name,Email,Phone,Guild,Hours,Date"+String.format("%n");
        for (User user : users) {
            String name = (user.getName() != null) ? user.getName() : "";
            String email = (user.getEmail() != null) ? user.getEmail() : "";
            String phone = (user.getPhone() != 0) ? String.valueOf(user.getPhone()) : "";
            output += name+","+email+","+phone+",,,"+String.format("%n");
            for (Day day : bll.getWorkedDays(user)) {
                String guildName = (day.getGuild() != null) ? day.getGuild() : "";
                String date = (day.getDate() != null) ? day.getDate() : "";
                output += ",,,";
                output += guildName+","+day.getHour()+","+date+String.format("%n");
            }
        }
        return output;
    }
}
