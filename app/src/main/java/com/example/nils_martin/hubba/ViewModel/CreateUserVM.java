package com.example.nils_martin.hubba.ViewModel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.nils_martin.hubba.Model.Acheievement;
import com.example.nils_martin.hubba.Model.AchievementFactory;
import com.example.nils_martin.hubba.Model.AchievementType;
import com.example.nils_martin.hubba.Model.HubbaModel;
import com.example.nils_martin.hubba.Model.User;
import com.example.nils_martin.hubba.R;

import java.util.ArrayList;

public class CreateUserVM extends AppCompatActivity  {

    HubbaModel model = HubbaModel.getInstance();
    private EditText newUsername;
    private EditText newEmail;
    private EditText newPassword;
    private ImageButton backButton;
    private Button createNewUser;

     protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_user);
        initViews();
        initOnClickListeners();
    }

    private void initViews() {
        newUsername = findViewById(R.id.txtNewUsername);
        newPassword = findViewById(R.id.txtNewPassword);
        newEmail = findViewById(R.id.txtNewEmail);
        createNewUser = findViewById(R.id.btnCreateNewUser);
        backButton = findViewById(R.id.backBtn4);
    }

    private void initOnClickListeners() {
         backButton.setOnClickListener(v -> onBackPressed());
        createNewUser.setOnClickListener((View v) -> {
            if(!newUsername.getText().toString().isEmpty() && !newEmail.getText().toString().isEmpty()
                    && !newPassword.getText().toString().isEmpty()){
                addUser();
            }
        });
    }
    /**
     * Creates new user based on the input parameters
     * */
    private void addUser(){
        User user = new User(newUsername.getText().toString(), newEmail.getText().toString(), newPassword.getText().toString(),
                 setAchivements());
        model.addUser(user);
        finish();
    }


    private ArrayList<Acheievement> setAchivements(){
        ArrayList<Acheievement> startAchivements = new ArrayList<>();

        setHabitAchivements(startAchivements);
        setStreakAchivements(startAchivements);
        return startAchivements;
    }
    private void setHabitAchivements(ArrayList<Acheievement> startAcheievements){
        startAcheievements.add(AchievementFactory.getAchievement(AchievementType.NumOHabitsAchievement, "YOU'VE CREATED FIVE HABITS",5));
        startAcheievements.add(AchievementFactory.getAchievement(AchievementType.NumOHabitsAchievement, "YOU'VE CREATED TEN HABITS",10));
        startAcheievements.add(AchievementFactory.getAchievement(AchievementType.NumOHabitsAchievement, "YOU'VE CREATED FIFTEEN HABITS",15));

    }
    private void setStreakAchivements(ArrayList<Acheievement> startAcheievements){
        startAcheievements.add(AchievementFactory.getAchievement(AchievementType.StreakAchievement, "YOU GOTTEN A STREAK OF FIVE",5));
        startAcheievements.add(AchievementFactory.getAchievement(AchievementType.StreakAchievement, "YOU GOTTEN A STREAK OF TEN",10));
        startAcheievements.add(AchievementFactory.getAchievement(AchievementType.StreakAchievement, "YOU GOTTEN A STREAK OF FIFTEEN",15));
    }

}
