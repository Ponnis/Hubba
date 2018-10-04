package com.example.nils_martin.hubba;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MenuController extends AppCompatActivity {
    TextView profileTextView;
    TextView settingsTextView;
    TextView habitsTextView;
    TextView groupsTextView;
    TextView helpTextView;
    Button logOutButton;
    ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        initFindView();
        init();
    }

    private void initFindView(){
        backButton = findViewById(R.id.backButton);
        profileTextView = findViewById(R.id.profileTextView);
        settingsTextView = findViewById(R.id.settingsTextView);
        habitsTextView = findViewById(R.id.habitsTextView);
        groupsTextView = findViewById(R.id.groupsTextView);
        helpTextView = findViewById(R.id.helpTextView);
        logOutButton = findViewById(R.id.logOutButton);

    }

    private void init(){
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuController.this, LoginView.class);
                startActivity(intent);
            }
        });
    }
}
