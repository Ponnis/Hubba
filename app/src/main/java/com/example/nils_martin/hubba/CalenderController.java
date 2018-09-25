package com.example.nils_martin.hubba;

import android.icu.util.Calendar;
import android.os.Bundle;
import android.app.Activity;
import android.widget.CalendarView;

public class CalenderController extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        cV = findViewById(R.id.calendarView);
        cV.setFirstDayOfWeek(2);

    }

    CalendarView cV;



}
