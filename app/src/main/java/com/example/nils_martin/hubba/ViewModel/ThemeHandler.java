package com.example.nils_martin.hubba.ViewModel;

import com.example.nils_martin.hubba.Model.HubbaModel;
import com.example.nils_martin.hubba.Model.IHubbaModel;
import com.example.nils_martin.hubba.Model.ThemableObserver;
import com.example.nils_martin.hubba.Model.Themes;
import com.example.nils_martin.hubba.R;

/**
 * @author Johannes Gustavsson
 * Delegation Class for all classes that should be Themable.
 */
public class ThemeHandler {
    private IHubbaModel model = HubbaModel.getInstance();

    /**
     * Takes the Active Themes Enum from User (through model) and uses a switch to find what style that is
     * corresponding to that Enum.
     * @return R.style.ThemeName where ThemeName is depending on what is the Active theme in User.
     */
    public int getTheme(){
        Themes activeTheme;
        if(model.getTheme() != null) {
             activeTheme = model.getTheme();
        } else {
            model.setTheme(Themes.STANDARD);
        }

        activeTheme = model.getTheme();
        int returntheme = 0;
        switch (activeTheme) {
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

    /**
     * adds a Themableobserver to the observer List in User (Through Hubbamodel)
     * @param observer the object that is to be added to the observer List in User.
     */
    void addThemeListener(ThemableObserver observer){
        model.addThemeListener(observer);
    }
}
