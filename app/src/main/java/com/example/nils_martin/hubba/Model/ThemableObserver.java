package com.example.nils_martin.hubba.Model;

/**
 * @author Johannes Gustavsson
 */
public interface ThemableObserver {
     /* To implement themes in you activity first you need to implement the interface ThemableObserver.
        In method recreateActivity that comes with the interface you put recreate();.
        You should then use the deligation class ThemeHandler.
        Before superOnCreate(); Put the method setTheme(themehandler.getTheme),
        you also in onCreate need to put themehandler.addThemeListener(this).
     */
     void recreateActivity();
}
