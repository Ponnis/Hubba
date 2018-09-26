package com.example.nils_martin.hubba;

import android.icu.util.Calendar;
import android.app.Activity;
import android.widget.CalendarView;
import android.widget.EditText;

import java.util.ArrayList;

public class CalendarModel extends Activity{

    CalendarView calendarView;
    EditText showDate;
    Calendar calendar;

    public static ArrayList<TemporaryClassForEvents> events;

    void secondInit(){
        events.add(new TemporaryClassForEvents("2018-09-19", "Vatten"));
        events.add(new TemporaryClassForEvents("2018-09-21", "Vatten"));

        calendar.;
    }

    void init() {
        calendarView = findViewById(R.id.calendarView);
        calendarView.setFirstDayOfWeek(2);
        showDate = findViewById(R.id.dateText);
        dateChange();
        //calendarView.setMaxDate(calendarView.getDate()+604800000); //Only one week is clickable
        //calendarView.setMinDate(calendarView.getDate());
    }

    void dateChange() {
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2) {
                showDate.setText("Date: " + i2 + " / " + (i1 + 1) + " / " + i); //Write date as dd-mm-yy
            }
        });

        }
    }



}