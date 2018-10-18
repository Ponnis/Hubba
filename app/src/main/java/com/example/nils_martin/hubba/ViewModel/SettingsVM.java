package com.example.nils_martin.hubba.ViewModel;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
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

import com.example.nils_martin.hubba.Model.HubbaModel;
import com.example.nils_martin.hubba.Model.ThemableObserver;
import com.example.nils_martin.hubba.Model.Themes;
import com.example.nils_martin.hubba.R;

public class SettingsVM extends AppCompatActivity implements ThemableObserver {
    //we have to get the active user from the main class " Hubba ".
    //will control all the users menu_settings.

    Switch notificationSwitch;
    Spinner themeSpinner;
    Spinner moodSpinner;

    private boolean isUserInteracting;
    private HubbaModel model = HubbaModel.getInstance();
    Themehandler themehandler = new Themehandler();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(themehandler.getTheme());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_settings);
        init();
        model.addThemeListener(this);
        themeSpinner.setSelection(getIndex(themeSpinner,themehandler.getThemeToString()));
    }
    // Initiate the necessary.
    private void init(){
        initFindByView();
        initSpinners();
    }

    private void initSpinners() {
        themeSpinner.setAdapter(new ArrayAdapter<Themes>(this, android.R.layout.simple_list_item_1, Themes.values()));
        themeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Themes chosenOne = (Themes) themeSpinner.getSelectedItem();
                if(isUserInteracting) {
                    restartApp();
                    model.setTheme(chosenOne);
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
    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }

        return 0;
    }
    private void restartApp(){
        Intent i = new Intent(getApplicationContext(), SettingsVM.class);
        finish();
        startActivity(i);
    }

    @Override
    public void recreateActivity() {
        recreate();
    }
}
