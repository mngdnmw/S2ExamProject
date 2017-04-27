/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kristof
 */
public class ConfigManager {
    private final String file = "src//Resources//Config.cfg";
    protected final Properties props = new Properties();
    public ConfigManager() {
        try {
            props.load(new FileReader(file));
        } catch(IOException e) {
            Logger.getLogger(ConfigManager.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    protected void saveConfig(Properties props) {
        if(props != null) {
            try {
                props.store(new FileWriter(file),null);
            } catch(IOException e) {
                Logger.getLogger(ConfigManager.class.getName()).log(Level.SEVERE, null, e);
                System.out.println("file not found or something?");
            }
        }
    }
}
