package com.example.nils_martin.hubba.ViewModel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.nils_martin.hubba.Model.ThemableObserver;
import com.example.nils_martin.hubba.R;

public class MyAchievementsVM extends AppCompatActivity implements ThemableObserver {
    Themehandler themehandler = new Themehandler();

    protected void onCreate(Bundle savedInstanceState) {
        setTheme(themehandler.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_myachievements);
        themehandler.addThemeListener(this);
    }

    @Override
    public void recreateActivity() {
        recreate();
    }
}
