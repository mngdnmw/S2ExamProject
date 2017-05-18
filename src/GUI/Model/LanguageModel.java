package GUI.Model;

import BE.EnumCache.Lang;
import BLL.Translation;

public class LanguageModel extends Translation
{

    public String getLang(String key)
    {
        return super.get(key);
    }

    public void setLang(Lang lang)
    {
        super.set(lang);
    }

    @Override
    public Lang getLangProperty()
    {
        return super.getLangProperty();
    }
}
