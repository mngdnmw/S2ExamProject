package BLL;

import BE.EnumCache.Lang;
import DAL.LanguageManager;
import java.util.Properties;

public class Translation extends LanguageManager
{

    protected Properties language;

    protected Translation()
    {
        language = super.getLanguageFile();
    }

    protected String get(String key)
    {
        return language.getProperty(key);
    }

    protected void set(Lang lang)
    {
        language = super.getLanguageFile(lang);
    }

}
