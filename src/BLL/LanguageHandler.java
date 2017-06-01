package BLL;

import BE.EnumCache.Lang;
import DAL.DALFacade;
import java.util.Properties;

public class LanguageHandler
{
    private final static DALFacade dalFac = new DALFacade();
    protected Properties language;

    protected LanguageHandler()
    {
        language = dalFac.getLanguageFile();
    }

    protected String getLang(String key)
    {
        return language.getProperty(key);
    }

    protected void setLang(Lang lang)
    {
        language = dalFac.getLanguageFile(lang);
    }
    
    public Lang getLangProperty() {
        return dalFac.getLangProperty();
    }

}
