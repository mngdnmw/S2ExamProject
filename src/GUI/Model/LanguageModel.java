package GUI.Model;

import BE.EnumCache.Lang;
import BLL.BLLFacade;

public class LanguageModel
{

    private final static BLLFacade bllFac = new BLLFacade();
    
    public String getLang(String key)
    {
        return bllFac.getLang(key);
    }

    public void setLang(Lang lang)
    {
        bllFac.setLang(lang);
    }

    public Lang getLangProperty()
    {
        return bllFac.getLangProperty();
    }
}
