/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;

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
    public String parse(List<User> users) {
        String output = "";
        output += "Name;Email;Phone;Address;Secondary Address;Notes"+"\r";
        for (User user : users) {
            String note = ""; 
            if(user.getNote() != null) {
                note += user.getNote().replace(System.lineSeparator(), "\r");
            }
            output+= user.getName()+";"+user.getEmail()+";"+user.getPhone()+";"+user.getResidence()+";"+user.getResidence2()+";\""+note+"\""+"\n";
        }

        return output;
    }
    
}
