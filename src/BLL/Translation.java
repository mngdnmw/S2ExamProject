/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;

import BE.EnumCache.Lang;
import DAL.LanguageManager;
import java.util.Properties;

/**
 *
 * @author Kristof
 */
public class Translation extends LanguageManager {
    protected Properties language;
    
    protected Translation(){
        language = super.getLanguageFile();
    }
    
    protected String get(String key) {
        return language.getProperty(key);
    }
    
    protected void set(Lang lang) {
        language = super.getLanguageFile(lang);
    }
}
