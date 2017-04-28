/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import BE.EnumCache.Lang;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kristof
 */
public class LanguageManager extends ConfigManager{

    private static String file;
    public static Properties language = new Properties();

    public LanguageManager() {
        if(!props.getProperty("LANGUAGE").isEmpty()) {
            file = "src//Resources//"+props.getProperty("LANGUAGE")+".lang";
        } else {
            file = "src//Resources//en.lang";
        }
        getLanguageFile();
    }
    
    protected Properties getLanguageFile() {
        if(file != null) {
            try {
                language.load(new FileReader(file));
            } catch(IOException e) {
                Logger.getLogger(LanguageManager.class.getName()).log(Level.SEVERE, null, e);
            }
            
            return language;
        }
        System.out.println("no language file found.");
        return null;
    }
    
    protected Properties getLanguageFile(Lang lang) {

        if (lang != null) {
            if (lang == Lang.ENG) {
                file = "src//Resources//en.lang";
                props.setProperty("LANGUAGE", "en");
            } else if (lang == Lang.DAN) {
                file = "src//Resources//da.lang";
                props.setProperty("LANGUAGE", "da");
            }
        }

        try {
            language.load(new FileReader(file));
            saveConfig(props);
            return language;
        } catch (IOException e) {
            Logger.getLogger(LanguageManager.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("Language file not found or something?");
        }
        System.out.println("no language selected.");
        return null;
    }
}
