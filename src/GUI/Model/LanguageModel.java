package GUI.Model;

import BE.EnumCache.Lang;
import BLL.Translation;


public class LanguageModel extends Translation
{
    public LanguageModel(Lang lang) {
        super(lang);
    }
    
    public String get(String key) {
        return super.get(key);
    }
    
    public void set(Lang lang) {
        super.set(lang);
    }
}
