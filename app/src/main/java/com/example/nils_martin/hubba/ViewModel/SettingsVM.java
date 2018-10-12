package com.example.nils_martin.hubba.ViewModel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;

import com.example.nils_martin.hubba.R;

public class SettingsVM extends AppCompatActivity {
    //we have to get the active user from the main class " Hubba ".
    //will control all the users menu_settings.

    Switch notificationSwitch;
    Spinner themeSpinner;
    Spinner moodSpinner;

    private boolean isUserInteracting;

    private enum ColorThemes{
        ELITE,
        PINKFLUFFY,
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_settings);
        init();
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("position", Context.MODE_PRIVATE);
        int spinnerValue = sharedPreferences.getInt("spinnerItem", -1);
        if(spinnerValue != -1){
            themeSpinner.setSelection(spinnerValue, true);
        }
    }
    // Initiate the necessary.
    private void init(){
        initFindByView();
        initSpinners();
    }

    private void initSpinners() {
        themeSpinner.setAdapter(new ArrayAdapter<ColorThemes>(this, android.R.layout.simple_list_item_1, ColorThemes.values()));
        themeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ColorThemes chosenOne = (ColorThemes) themeSpinner.getSelectedItem();
                int chosenThemePos = themeSpinner.getSelectedItemPosition();
                SharedPreferences sharedPreferences = getApplication().getSharedPreferences("position", 0);
                SharedPreferences.Editor prefeditor = sharedPreferences.edit();
                prefeditor.putInt("spinnerItem", chosenThemePos);
                prefeditor.apply();
                if(isUserInteracting) {
                    switch (chosenOne) {
                        case ELITE:
                            setTheme(R.style.AppTheme);
                            restartApp();
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initFindByView(){
        
        notificationSwitch = (Switch) findViewById(R.id.notificationSwitch);
        themeSpinner = (Spinner) findViewById(R.id.themeSpinner);
        moodSpinner = (Spinner) findViewById(R.id.moodSpinner);
    }
    @Override
    public void onUserInteraction(){
        super.onUserInteraction();
        isUserInteracting = true;
    }
    private void restartApp(){
        Intent i = new Intent(getApplicationContext(), SettingsVM.class);
        startActivity(i);
        finish();
    }
}
