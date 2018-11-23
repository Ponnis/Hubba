package com.example.nils_martin.hubba.ViewModel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.nils_martin.hubba.Model.Achievement;
import com.example.nils_martin.hubba.Model.AchievementFactory;
import com.example.nils_martin.hubba.Model.AchievementInstanceCreator;
import com.example.nils_martin.hubba.Model.AchievementType;
import com.example.nils_martin.hubba.Model.Frequency;
import com.example.nils_martin.hubba.Model.Group;
import com.example.nils_martin.hubba.Model.GroupHabitType;
import com.example.nils_martin.hubba.Model.Habit;
import com.example.nils_martin.hubba.Model.HubbaModel;
import com.example.nils_martin.hubba.Model.IFriend;
import com.example.nils_martin.hubba.Model.IHabit;
import com.example.nils_martin.hubba.Model.State;
import com.example.nils_martin.hubba.Model.Themes;
import com.example.nils_martin.hubba.Model.User;
import com.example.nils_martin.hubba.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class LoginVM extends AppCompatActivity {
    private HubbaModel model = HubbaModel.getInstance();
    private EditText Username;
    private EditText Password;
    private Button NewUser;
    private Button Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        for (int i = 0, usersSize = model.getUsers().size(); i < usersSize; i++) {
            User user = model.getUsers().get(i);
            if (user.getUserName().equals("admin")) {
                break;
            }
            if (i == usersSize - 1) {
                model.getUsers().add(new User("admin", "testemail@gmail.com", "1234", new ArrayList<>()));
                model.getUser("admin").setAchievements(setAchivements());
            }
        }

        Username = (EditText) findViewById(R.id.txtUsername);
        Password = (EditText) findViewById(R.id.txtPassword);
        NewUser = (Button) findViewById(R.id.btnNewUser);
        Login = (Button) findViewById(R.id.btnLogin);

        NewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newUserButton();
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLoginAcceptance();
            }
        });

    }

    private void initList(User user) {
        user.initThemableObserver();
        user.initHabit();
        user.initFriends();
        if (user.getHabits().size() != 0) {
            for (IHabit habit : user.getHabits()) {
                habit.initDaysToDoList();
            }
        }
    }

    private void newUserButton() {
        Intent intent = new Intent(LoginVM.this, CreateUserVM.class);
        startActivity(intent);
    }

    private void checkLoginAcceptance() {
        for (User user : model.getUsers()) {
            if (user.getUserName().equals(Username.getText().toString())) {
                if (user.getPassword().equals(Password.getText().toString())) {
                    Intent intent = new Intent(LoginVM.this, com.example.nils_martin.hubba.ViewModel.MainActivityVM.class);
                    startActivity(intent);
                    model.setCurrentUser(user);
                    model.getCurrentUser().setAchievements(setAchivements());
                    break;
                }
            }
        }
    }

    public EditText getUsername() {
        return Username;
    }

    private void initFirstUse(){
        model.setUsers(new ArrayList<>());
        model.getUsers().add(new User("admin", "testemail@gmail.com", "1234", new ArrayList<>()));
        model.getUser("admin").setAchievements(setAchivements());
    }


    private void extractString(String source, String listName, String target, List<IFriend> list){
        char [] charArray = source.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < charArray.length; i++){
            if (Character.isLetter(charArray[i])){
                stringBuilder.append(charArray[i]);

                if (stringBuilder.toString().equals(listName)){
                    stringBuilder.setLength(0);
                }

                else if (stringBuilder.toString().equals(target)){
                    stringBuilder.setLength(0);
                    i = i + 3;
                    if (Character.isLetter(charArray[i])){
                        while(Character.isLetter(charArray[i])){
                            stringBuilder.append(charArray[i]);
                            i++;
                        }
                    }
                    else{
                        i++;
                        while(Character.isLetter(charArray[i])){
                            stringBuilder.append(charArray[i]);
                            i++;
                        }
                    }

                    list.add(model.getUser(stringBuilder.toString()));
                    stringBuilder.setLength(0);
                }
            }
        }
    }


    private ArrayList<Achievement> setAchivements(){
        ArrayList<Achievement> startAchivements = new ArrayList<>();
        setHabitAchivements(startAchivements);
        setStreakAchivements(startAchivements);
        return startAchivements;
    }

    private void setHabitAchivements(ArrayList<Achievement> startAchievements){
        startAchievements.add(AchievementFactory.getAchievement(AchievementType.NumOHabitsAchievement, "YOU'VE CREATED FIVE HABITS",5));
        startAchievements.add(AchievementFactory.getAchievement(AchievementType.NumOHabitsAchievement, "YOU'VE CREATED TEN HABITS",10));
        startAchievements.add(AchievementFactory.getAchievement(AchievementType.NumOHabitsAchievement, "YOU'VE CREATED FIFTEEN HABITS",15));

    }
    private void setStreakAchivements(ArrayList<Achievement> startAchievements){
        startAchievements.add(AchievementFactory.getAchievement(AchievementType.StreakAchievement, "YOU GOTTEN A STREAK OF FIVE",5));
        startAchievements.add(AchievementFactory.getAchievement(AchievementType.StreakAchievement, "YOU GOTTEN A STREAK OF TEN",10));
        startAchievements.add(AchievementFactory.getAchievement(AchievementType.StreakAchievement, "YOU GOTTEN A STREAK OF FIFTEEN",15));
    }
}
