package com.example.nils_martin.hubba;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Spinner;
import android.widget.Switch;

public class SettingsVM extends AppCompatActivity {
    //we have to get the active user from the main class " Hubba ".
    //will control all the users menu_settings.

    Switch notificationSwitch;
    Spinner themeSpinner;
    Spinner moodSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_settings);
        init();
    }

    private void init(){
        initFindByView();
    }

    private void initFindByView(){
        
        notificationSwitch = (Switch) findViewById(R.id.notificationSwitch);
        themeSpinner = (Spinner) findViewById(R.id.themeSpinner);
        moodSpinner = (Spinner) findViewById(R.id.moodSpinner);
    }

}
