package com.example.nils_martin.hubba;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/* Calendar class not finish, waiting for more functionality of a habit */
public class CalendarController extends Activity {

    TextView dateText, activityTxtV;
    CalendarView calendarView;
    ImageButton backBtn;
    List<Habit> habitsList = MainActivityController.habits;
    Calendar cal = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        init();
        Update();
    }

    StringBuilder stringBuilder = new StringBuilder();

    void init() {
        calendarView = findViewById(R.id.calendarView);
        dateText = findViewById(R.id.dateText);
        activityTxtV = findViewById(R.id.activityTxtV);
        backBtn = findViewById(R.id.backBtn);
    }

    void Update() {

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
                stringBuilder.setLength(0);
                stringBuilder.append("Habits:");
                cal.set(year, month, dayOfMonth); //Set
                cal.get(Calendar.DAY_OF_WEEK);
                for (int i = 0; i < habitsList.size(); i++) {
                    if (habitsList.get(i).getDayToDo().contains(cal.get(Calendar.DAY_OF_WEEK))) {
                        stringBuilder.append("\n" + (habitsList.get(i).getTitle(habitsList.get(i))));
                        stringBuilder.append(" (" + habitsList.get(i).getSTATE().toString().toLowerCase() + ")");
                    }
                }
                activityTxtV.setText(stringBuilder.toString());    //If you have a activity at the day, this will show as a text under the calendar
            }
        });
    }
}