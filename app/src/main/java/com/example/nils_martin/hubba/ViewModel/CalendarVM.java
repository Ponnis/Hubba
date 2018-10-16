package com.example.nils_martin.hubba.ViewModel;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.nils_martin.hubba.Model.Frequency;
import com.example.nils_martin.hubba.Model.Habit;
import com.example.nils_martin.hubba.R;

import java.util.Calendar;
import java.util.List;

public class CalendarVM extends Activity {

    private TextView dateText, activityTxtV;
    private CalendarView calendarView;
    private ImageButton backBtn;
    private List<Habit> habitsList = MainActivityVM.habits;
    private Calendar cal = Calendar.getInstance();
    private int currentMonth = cal.get(Calendar.MONTH) + 1;
    private int currentYear = cal.get(Calendar.YEAR);
    private int currentDayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
    private StringBuilder stringBuilder = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        init();
        setActivityTxtV(currentYear, currentMonth, currentDayOfMonth);
        Update();
    }


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
                Intent intent = new Intent(CalendarVM.this, MainActivityVM.class);
                startActivity(intent);
            }
        });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int dayOfMonth) {
                setActivityTxtV(year, month, dayOfMonth);
       /*         stringBuilder.setLength(0);
                stringBuilder.append("Habits:");
                cal.set(year, month, dayOfMonth); //Take in the date from the listener and set it as "current date"


                for (int i = 0; i < habitsList.size(); i++) {
                    if(habitsList.get(i).getFREQUENCY() == Frequency.MONTHLY) {         //Month contains the date in a different form than day and week.
                        monthStringBuilder(i, dayOfMonth);
                    }
                    else {
                        dayAndWeekStringBuilder(i);
                    }
                }
                activityTxtV.setText(stringBuilder.toString());    //If you have a activity at the day, this will show as a text under the calendar
         */   }
        });
    }


    public void monthStringBuilder(int i, int dayOfMonth) {
        if (habitsList.get(i).getDaysToDo().contains(dayOfMonth)) {
            stringBuilder.append("\n" + (habitsList.get(i).getTitle(habitsList.get(i))));
            stringBuilder.append(" (" + habitsList.get(i).getSTATE().toString().toLowerCase() + ")");
        }
    }

    private void dayAndWeekStringBuilder(int i) {
        if (habitsList.get(i).getDaysToDo().contains(cal.get(Calendar.DAY_OF_WEEK))) {       //If the "current date"-day is the same as any day in the habitlist do this
            stringBuilder.append("\n" + (habitsList.get(i).getTitle(habitsList.get(i))));
            stringBuilder.append(" (" + habitsList.get(i).getSTATE().toString().toLowerCase() + ")");
        }
    }

    private void setActivityTxtV (int year, int month, int dayOfMonth ) {
        stringBuilder.setLength(0);
        stringBuilder.append("Habits:");
        cal.set(year, month, dayOfMonth); //Take in the date from the listener and set it as "current date"


        for (int i = 0; i < habitsList.size(); i++) {
            //Month contains the date in a different form than day and week.
            if(habitsList.get(i).getFREQUENCY() == Frequency.MONTHLY) {
                monthStringBuilder(i, dayOfMonth);
            }
            else {
                dayAndWeekStringBuilder(i);
            }
        }
        activityTxtV.setText(stringBuilder.toString());    //If you have a activity at the day, this will show as a text under the calendar

    }


}