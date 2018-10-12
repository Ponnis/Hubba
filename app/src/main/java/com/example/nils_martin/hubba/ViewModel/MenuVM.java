package com.example.nils_martin.hubba.ViewModel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.nils_martin.hubba.R;

public class MenuVM extends AppCompatActivity {
    Button profileButton;
    Button settingsButton;
    Button habitsButton;
    Button groupsButton;
    Button helpButton;
    Button logOutButton;
    ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        init();
    }

    private void init(){
        initFindByView();
        initOnClickListeners();
    }

    private void initFindByView(){
        backButton = findViewById(R.id.backButton);
        profileButton = findViewById(R.id.profileBtn);
        settingsButton = findViewById(R.id.settingsBtn);
        habitsButton = findViewById(R.id.habitsBtn);
        groupsButton = findViewById(R.id.groupsBtn);
        helpButton = findViewById(R.id.helpBtn);
        logOutButton = findViewById(R.id.logOutButton);

    }

    // Calls methods that set what actions the buttons have on click.
    private void initOnClickListeners(){
        backButtonOnClick();
        profileButtonOnClick();
        settingsButtonOnClick();
        habitsButtonOnClick();
        groupsButtonOnClick();
        // helpButtonOnClick();
        logOutButtonOnClick();

    }

    private void backButtonOnClick(){
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void profileButtonOnClick(){
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuVM.this, ProfileVM.class);
                startActivity(intent);
            }
        });
    }

    private void settingsButtonOnClick(){
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuVM.this, SettingsVM.class);
                startActivity(intent);
            }
        });
    }

    private void habitsButtonOnClick(){
        habitsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuVM.this, MenuHabitsVM.class);
                startActivity(intent);
            }
        });
    }

    private void groupsButtonOnClick(){
        groupsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuVM.this, MenuGroupsVM.class);
                startActivity(intent);
            }
        });
    }

    /* private void helpButtonOnClick(){
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuVM.this, HelpVM.class);
                startActivity(intent);
            }
        });
    }*/

    private void logOutButtonOnClick(){
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuVM.this, LoginVM.class);
                startActivity(intent);
            }
        });
    }
}
