package com.example.nils_martin.hubba;

import android.os.Bundle;
import android.app.Activity;


public class CalenderController extends Activity {

    CalendarModel calendarModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        calendarModel.init();
    }

}