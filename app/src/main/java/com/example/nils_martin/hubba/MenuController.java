package com.example.nils_martin.hubba;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MenuController extends AppCompatActivity {
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
        setContentView(R.layout.menu_xml);
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

    private void initOnClickListeners(){
        backButtonOnClick();
        logOutButtonOnClick();
        settingsButtonOnClick();

    }

    private void backButtonOnClick(){
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void logOutButtonOnClick(){
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuController.this, LoginView.class);
                startActivity(intent);
            }
        });
    }

    private void settingsButtonOnClick(){
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuController.this, Settings.class);
                startActivity(intent);
            }
        });
    }
}
