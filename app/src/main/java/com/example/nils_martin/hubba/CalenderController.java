package com.example.nils_martin.hubba;

import android.icu.util.Calendar;
import android.os.Bundle;
import android.app.Activity;
import android.widget.CalendarView;
import android.widget.EditText;

import java.util.Map;


public class CalenderController extends Activity {

    CalendarView cV;
    EditText hej;
    Calendar calendar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        init();
    }


    void init() {
        cV = findViewById(R.id.calendarView);
        cV.setFirstDayOfWeek(2);
        hej = findViewById(R.id.dateText);
        dateChange();
        //cV.setMaxDate(cV.getDate()+604800000); //Only one week is clickable
        //cV.setMinDate(cV.getDate());
    }

    void dateChange() {
        cV.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2) {
                hej.setText("Date: " + i2 + " / " + (i1+1) + " / " + i); //Write date as dd-mm-yy
            }
        });
    }



}