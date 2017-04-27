/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kristof
 */
public class LanguageManager {

    public static enum Lang {ENG, DAN};
    private static String file;
    public static Properties language = new Properties();

    protected Properties getLanguageFile(Lang lang) {

        if (lang != null) {
            if (lang == Lang.ENG) {
                file = "src//Resources//en.lang";
            } else if (lang == Lang.DAN) {
                file = "src//Resources//da.lang";
            }
        }

        try {
            language.load(new FileReader(file));
            return language;
        } catch (IOException e) {
            Logger.getLogger(LanguageManager.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("Language file not found or something?");
        }
        System.out.println("no language selected.");
        return null;
    }
}