package com.example.nils_martin.hubba;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

public class HabitView extends AppCompatActivity {

    TextView habitTitleTextView;
    TextView timeOfDayTextView;
    TextView frequencyTextView;
    TextView reminderTextView;
    TextView reminderTimeTextView;
    TextView streakTextView;
    TextView streakDaysTextView;

    Button backButton;
    Button editButton;

    Habit habit = new Habit("");

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.habit_view);

        habit = MainActivityController.selectedHabit;
        initFindView();
        init(habit);
    }

    //connect textViews and buttons
    private void initFindView(){
        habitTitleTextView = findViewById(R.id.habitTitleTextView);
        timeOfDayTextView = findViewById(R.id.timeOfDayTextView);
        frequencyTextView = findViewById(R.id.frequencyTextView);
        reminderTextView = findViewById(R.id.reminderTextView);
        reminderTimeTextView = findViewById(R.id.reminderTimeTextView);
        streakTextView = findViewById(R.id.streakTextView);
        streakDaysTextView = findViewById(R.id.streakDaysTextView);

        backButton = findViewById(R.id.backButton);
        editButton = findViewById(R.id.editButton);
    }

    //set text
    private void init(Habit habit){

        habitTitleTextView.setText(habit.getTitle(habit));
        timeOfDayTextView.setText(toLowerCase(habit.getSTATE().toString()));
        //frequencyTextView.setText(habit.);
        //reminderTimeTextView
        streakDaysTextView.setText(String.valueOf(habit.getStreak(habit)) + " days");
    }

    public String toLowerCase(String string){
        String temp = string;
        char[] ch = temp.toLowerCase().toCharArray();
        ch[0] = Character.toUpperCase(ch[0]);
        temp = new String(ch);
        return temp;
    }

}
