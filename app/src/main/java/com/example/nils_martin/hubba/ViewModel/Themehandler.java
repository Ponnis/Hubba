package com.example.nils_martin.hubba.ViewModel;

import com.example.nils_martin.hubba.Model.HubbaModel;
import com.example.nils_martin.hubba.Model.ThemableObserver;
import com.example.nils_martin.hubba.Model.Themes;
import com.example.nils_martin.hubba.R;

// Gets ActiveTheme from User and depending on what theme is chosen it returns the right int.
public class Themehandler {
    HubbaModel model = HubbaModel.getInstance();
    public int getTheme(){
        Themes activeTheme = model.getTheme();
        int returntheme = 0;
        switch (activeTheme){
            case ELITE:
                returntheme = R.style.Elite;
                break;
            case STANDARD:
                returntheme = R.style.Standard;
                break;
            case PINKFLUFFY:
                returntheme = R.style.PinkFluffy;
                break;
        }
        return returntheme;
    }
    public String getThemeToString(){
        return model.getTheme().toString();
    }
    public void setTheme(Themes theme){
        model.setTheme(theme);
    }
    void addThemeListener(ThemableObserver observer){
        model.addThemeListener(observer);
    }
}
