package com.example.nils_martin.hubba.ViewModel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.nils_martin.hubba.Model.ThemableObserver;
import com.example.nils_martin.hubba.R;

public class MenuVM extends AppCompatActivity implements ThemableObserver {
    private Button profileButton;
    private Button settingsButton;
    private Button habitsButton;
    private Button groupsButton;
    private Button friendsButton;
    private Button helpButton;
    private Button logOutButton;
    private Button myAchievementsButton;
    private ImageButton backButton;
    private ThemeHandler themeHandler = new ThemeHandler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(themeHandler.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        init();
        themeHandler.addThemeListener(this);
    }

    /**
     * Calls on other functions that initialize buttons and OnCLickListeners
     */
    private void init(){
        initFindByView();
        initOnClickListeners();
    }

    /**
     * Connects variables to their viewID:s
     */
    private void initFindByView(){
        backButton = findViewById(R.id.backBtn5);
        profileButton = findViewById(R.id.profileBtn);
        settingsButton = findViewById(R.id.settingsBtn);
        habitsButton = findViewById(R.id.habitsBtn);
        groupsButton = findViewById(R.id.groupsBtn);
        friendsButton = findViewById(R.id.friendsBtn);
        helpButton = findViewById(R.id.helpBtn);
        logOutButton = findViewById(R.id.logOutButton);
        myAchievementsButton = findViewById(R.id.myAchievementsBtn);

    }

    /**
     * Calls methods that set OnClickListeners for all buttons what actions they have on click.
     */
    private void initOnClickListeners(){
        backButtonOnClick();
        profileButtonOnClick();
        settingsButtonOnClick();
        habitsButtonOnClick();
        groupsButtonOnClick();
        friendsButtonOnClick();
        helpButtonOnClick();
        logOutButtonOnClick();
        myAchievementsButtonOnClick();

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

    private void friendsButtonOnClick(){
        friendsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuVM.this, MenuFriendsVM.class);
                startActivity(intent);
            }
        });
    }

    private void helpButtonOnClick(){
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuVM.this, HelpVM.class);
                startActivity(intent);
            }
        });
    }

    private void logOutButtonOnClick(){
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuVM.this, LoginVM.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
    private void myAchievementsButtonOnClick(){
        myAchievementsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuVM.this, MyAchievementsVM.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void recreateActivity() {
        recreate();
    }
}
