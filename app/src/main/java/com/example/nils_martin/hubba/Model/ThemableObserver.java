package com.example.nils_martin.hubba.Model;

public interface ThemableObserver {
     /* To implement themes in you activity put setTheme(model.getTheme); before Super.Oncreate()
        and put model.addThemeListener(this); last in OnCreate.
        In Method recreateActivity simply put recreate();
     */
     void recreateActivity();
}
