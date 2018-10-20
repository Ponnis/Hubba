package com.example.nils_martin.hubba.ViewModel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.nils_martin.hubba.Model.Habit;
import com.example.nils_martin.hubba.Model.HubbaModel;
import com.example.nils_martin.hubba.Model.ThemableObserver;
import com.example.nils_martin.hubba.R;
import com.example.nils_martin.hubba.ViewModel.MainActivityVM;

import java.util.ArrayList;
import java.util.List;

public class HabitVM extends AppCompatActivity implements ThemableObserver {

    Themehandler themehandler = new Themehandler();

    TextView habitTitleTextView;
    TextView timeOfDayTextView;
    TextView frequencyTextView;
    TextView reminderTextView;
    TextView reminderTimeTextView;
    TextView streakTextView;
    TextView streakDaysTextView;

    Button deleteButton;
    Button editButton;

    Habit habit = new Habit("");

    protected void onCreate(Bundle savedInstanceState) {
        setTheme(themehandler.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.habit_view);
        themehandler.addThemeListener(this);
        habit = MainActivityVM.openHabit;
        initFindView();
        init(habit);
        update();
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
    }

    /**
     * set text to habit title and states.
     * @param habit
     */
    private void init(Habit habit){
        habitTitleTextView.setText(habit.getTitle());
        timeOfDayTextView.setText(toLowerCase(habit.getSTATE().toString()));
        frequencyTextView.setText(toLowerCase(habit.getFREQUENCY().toString()) + ": " + weekdays());
        setReminderTime();
        streakDaysTextView.setText(String.valueOf(habit.getStreak(habit)) + " days");
    }

    private void update(){

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HubbaModel.getInstance().getCurrentUser().removeHabit(habit);
                Intent intent = new Intent(HabitVM.this, MainActivityVM.class);
                startActivity(intent);
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HabitVM.this, EditHabitVM.class);
                startActivity(intent);
            }
        });
    }

    private String weekdays(){
        String[] days = {"sunday", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday"};
        StringBuilder stringBuilder = new StringBuilder();
        List<Integer> list = habit.getDaysToDo();
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
     * @param string
     * @return
     */
    private String toLowerCase(String string){
        String temp = string;
        char[] ch = temp.toLowerCase().toCharArray();
        ch[0] = Character.toUpperCase(ch[0]);
        temp = new String(ch);
        return temp;
    }

    private void setReminderTime(){
        List<String> list = new ArrayList<>();
        if(habit.isReminderOn()){
            if(habit.getReminderTime().get(0)<10){
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("0");
                stringBuilder.append(habit.getReminderTime().get(0));
                list.add(stringBuilder.toString());
            }
            if(habit.getReminderTime().get(1)<10){
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("0");
                stringBuilder.append(habit.getReminderTime().get(1));
                list.add(stringBuilder.toString());
            }
            if(list.size() == 2){
                reminderTimeTextView.setText(list.get(0) + " : " + list.get(1));
            } else{
                reminderTimeTextView.setText(habit.getReminderTime().get(0) + " : " + habit.getReminderTime().get(1));
            }

        } else {
            reminderTimeTextView.setText("None");
        }
    }

    @Override
    public void recreateActivity() {
        recreate();
    }
}
