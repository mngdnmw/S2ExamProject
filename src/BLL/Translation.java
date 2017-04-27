/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;

import DAL.LanguageManager;
import DAL.LanguageManager.Lang;
import java.util.Properties;

/**
 *
 * @author Kristof
 */
public class Translation extends LanguageManager {
    Properties language;
    
    public Translation(Lang lang){
        language = super.getLanguageFile(lang);
    }
    
    public String get(String key) {
        return language.getProperty(key);
    }
    
    public void set(Lang lang) {
        language = super.getLanguageFile(lang);
    }
}
