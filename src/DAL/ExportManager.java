/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kristof
 */
public class ExportManager {
    public void write(File file, String input) {
        if(file != null && !input.isEmpty()) {
            try(BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                bw.write(input);
            } 
            catch (IOException ex) {
                Logger.getLogger(ExportManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
