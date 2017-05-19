package DAL;

import BE.EnumCache.Lang;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LanguageManager extends ConfigManager
{

    private static String file;
    private static Properties language = new Properties();
    private static Lang langprop;

    public LanguageManager()
    {
        System.out.println(props.getProperty("LANGUAGE"));
        if (!props.getProperty("LANGUAGE").isEmpty() && props.getProperty("LANGUAGE") != null)
        {
            file = "src//Resources//" + props.getProperty("LANGUAGE") + ".lang";
            if (props.getProperty("LANGUAGE").equals("en"))
            {
                langprop = Lang.ENG;
            } else if (props.getProperty("LANGUAGE").equals("da"))
            {
                langprop = Lang.DAN;
            }
        } else
        {
            file = "src//Resources//en.lang";
            props.setProperty("LANGUAGE", "en");
            saveConfig(props);
        }
    }

    public Properties getLanguageFile()
    {
        if (file != null)
        {
            try
            {
                language.load(new FileReader(file));
            } catch (IOException e)
            {
                Logger.getLogger(LanguageManager.class.getName()).log(Level.SEVERE, null, e);
            }

            return language;
        }
        System.out.println("no language file found.");
        return null;
    }

    public Properties getLanguageFile(Lang lang)
    {

        if (lang != null)
        {
            if (lang == Lang.ENG)
            {
                langprop = Lang.ENG;
                file = "src//Resources//en.lang";
                props.setProperty("LANGUAGE", "en");
            } else if (lang == Lang.DAN)
            {
                langprop = Lang.DAN;
                file = "src//Resources//da.lang";
                props.setProperty("LANGUAGE", "da");
            }
        }

        try
        {
            language.load(new FileReader(file));
            saveConfig(props);
            return language;
        } catch (IOException e)
        {
            Logger.getLogger(LanguageManager.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("Language file not found or something?");
        }
        System.out.println("no language selected.");
        return null;
    }

    public Lang getLangProperty()
    {
        return langprop;
    }
}
