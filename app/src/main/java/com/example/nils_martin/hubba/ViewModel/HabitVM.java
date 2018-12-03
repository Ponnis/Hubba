package com.example.nils_martin.hubba.ViewModel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.nils_martin.hubba.Model.Group;
import com.example.nils_martin.hubba.Model.Achievement;
import com.example.nils_martin.hubba.Model.Frequency;
import com.example.nils_martin.hubba.Model.Habit;
import com.example.nils_martin.hubba.Model.HubbaModel;
import com.example.nils_martin.hubba.Model.IFriend;
import com.example.nils_martin.hubba.Model.IHabit;
import com.example.nils_martin.hubba.Model.ThemableObserver;
import com.example.nils_martin.hubba.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HabitVM extends AppCompatActivity implements ThemableObserver {

    ThemeHandler themeHandler = new ThemeHandler();

    TextView habitTitleTextView;
    TextView timeOfDayTextView;
    TextView frequencyTextView;
    TextView reminderTextView;
    TextView reminderTimeTextView;
    TextView streakTextView;
    TextView streakDaysTextView;
    private ImageButton backButton;

    Button deleteButton;
    Button editButton;

    private HubbaModel model = HubbaModel.getInstance();
    IHabit currentHabit = new Habit("");


    protected void onCreate(Bundle savedInstanceState) {
        setTheme(themeHandler.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.habit_view);
        themeHandler.addThemeListener(this);
        setCurrentHabit();
        initFindView();
        init(currentHabit);
        update();
    }

    @Override
    protected void onResume() {
        setTheme(themeHandler.getTheme());
        super.onResume();
        themeHandler.addThemeListener(this);
        setCurrentHabit();
        init(currentHabit);
    }

    /**
     * connect textViews and buttons
     */
    private void initFindView(){
        habitTitleTextView = findViewById(R.id.habitTitleTextView);
        timeOfDayTextView = findViewById(R.id.timeOfDayTextView);
        frequencyTextView = findViewById(R.id.frequencyTextView);
        reminderTextView = findViewById(R.id.reminderTextView);
        reminderTimeTextView = findViewById(R.id.reminderTimeTextView);
        streakTextView = findViewById(R.id.streakTextView);
        streakDaysTextView = findViewById(R.id.streakDaysTextView);

        deleteButton = findViewById(R.id.deleteButton);
        editButton = findViewById(R.id.editButton);
        backButton = findViewById(R.id.backBtn);
    }

    /**
     * set text to habit title and states.
     * @param habit the habit
     */
    private void init(IHabit habit){
        habitTitleTextView.setText(habit.getTitle());
        timeOfDayTextView.setText(toLowerCase(habit.getSTATE().toString()));
        setFrequencyTextView(habit);
        setReminderTime();
        streakDaysTextView.setText(String.valueOf(habit.getStreak()) + " days");
    }

    private void setFrequencyTextView(IHabit habit){
        if(habit.getFREQUENCY().equals(Frequency.DAILY)){
            frequencyTextView.setText(toLowerCase(habit.getFREQUENCY().toString()));
        }else if(habit.getFREQUENCY().equals(Frequency.WEEKLY)){
            frequencyTextView.setText(toLowerCase(habit.getFREQUENCY().toString()) + ": " + weekdays());
        }else{
            frequencyTextView.setText(toLowerCase(habit.getFREQUENCY().toString()) + " on the " + habit.getDaysToDo().get(0));
        }
    }

    private void update(){

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.getCurrentUser().removeHabit(currentHabit);
                finish();
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HabitVM.this, EditHabitVM.class);
                startActivity(intent);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private String weekdays(){
        String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        StringBuilder stringBuilder = new StringBuilder();
        List<Integer> list = currentHabit.getDaysToDo();
        String prefix = "";
        for(int i = 0; i < list.size(); i++){
         stringBuilder.append(prefix);
         prefix = ", ";
         stringBuilder.append(days[(list.get(i))-1]);
        }
        return stringBuilder.toString();
    }

    /**
     * Turn string ENUM to lowercase
     * @param string the string
     * @return the string in lowercase
     */
    private String toLowerCase(String string){
        String temp = string;
        char[] ch = temp.toLowerCase().toCharArray();
        ch[0] = Character.toUpperCase(ch[0]);
        temp = new String(ch);
        return temp;
    }

    /**
     * set the reminder text view to rigth time
     */
    private void setReminderTime(){
        List<String> list = new ArrayList<>();
        if(currentHabit.isReminderOn()){
            if(currentHabit.getReminderTime().get(0)<10){
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("0");
                stringBuilder.append(currentHabit.getReminderTime().get(0));
                list.add(stringBuilder.toString());
            }
            if(currentHabit.getReminderTime().get(1)<10){
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("0");
                stringBuilder.append(currentHabit.getReminderTime().get(1));
                list.add(stringBuilder.toString());
            }
            if(list.size() == 2){
                reminderTimeTextView.setText(list.get(0) + " : " + list.get(1));
            } else{
                reminderTimeTextView.setText(currentHabit.getReminderTime().get(0) + " : " + currentHabit.getReminderTime().get(1));
            }

        } else {
            reminderTimeTextView.setText("None");
        }
    }

    /**
     * This method sets the current habit by looking which the last activity was.
     */
    private void setCurrentHabit() {

        if(getIntent().getStringExtra("from").equals("MenuHabitsVM")) {
            currentHabit = MenuHabitsVM.openHabit;
        }
        else if(getIntent().getStringExtra("from").equals("MainActivityVM")) {
            currentHabit = MainActivityVM.openHabit;
        }
    }

    @Override
    public void recreateActivity() {
        recreate();
    }

}
