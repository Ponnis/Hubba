package com.example.nils_martin.hubba;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/* Calendar class not finish, waiting for more functionality of a habit */
public class CalendarController extends Activity {

    TextView dateText, activityTxtV;
    CalendarView calendarView;
    ImageButton backBtn;
    List<FakeHabit> fake = new ArrayList<>();
    List<Habit> habitsList = MainActivityController.habits;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        initFindView();
        init();
    }

    StringBuilder stringBuilder = new StringBuilder();

    void initFindView () {
        calendarView = findViewById(R.id.calendarView);
        dateText = findViewById(R.id.dateText);
        activityTxtV = findViewById(R.id.activityTxtV);
        backBtn = findViewById(R.id.backBtn);
    }

    void init() {
        makeAList();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarController.this, MainActivityController.class);
                startActivity(intent);
            }
        });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int dayOfMonth) {
                dateText.setText("Date: " + dayOfMonth + " / " + (month+1) + " / " + year);
                stringBuilder.setLength(0);
                stringBuilder.append("Habits:");
                for(int i = 0; i<habitsList.size(); i++) {
                    if(fake.get(i).date == dayOfMonth) {
                        stringBuilder.append("\n" + (habitsList.get(i).getTitle(habitsList.get(i))));
                        stringBuilder.append(" (" + habitsList.get(i).getSTATE().toString().toLowerCase() + ")");
                    }
                }
                activityTxtV.setText(stringBuilder.toString());    //If you have a activity at the day, this will show as a text under the calendar
            }
        });
    }

/*
    void makeHabitRecurring(Habit habit){
        switch (habit.getFrequency) {
            case eachday {
                addDays(1);
                break;
            } case each2day {
                addDays(2);
                break;
            } case each3days {
                addDays(3);
                break;
            } case each4days{
                addDays(4);
                break;
            } case each5days{
                addDays(5);
                break;
            } case each6days{
                addDays(6);
                break;
            } case eachweek{
                addDays(7);
                break;
            }break;
        }
    }

    void addDays(int interval){
        int times = 5;
        int date = calendarView.getDate();
        for(int i=0; i<times; i++){
            addTextInCalendar(date);
            date += interval;
        }
    } */


    void makeAList () {
        fake.add(new FakeHabit("Träning", 7, "12:00"));
        fake.add(new FakeHabit("Lunch", 4, "14:25"));
        fake.add(new FakeHabit("Bio", 21, "16:30"));
        fake.add(new FakeHabit("Hundvakt", 21, "09:05"));
    }
}



class FakeHabit {
    String name;
    int date;
    String time;

    FakeHabit (String name, int date, String time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }
}