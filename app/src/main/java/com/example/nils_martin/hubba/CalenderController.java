package com.example.nils_martin.hubba;

import android.os.Bundle;
import android.app.Activity;
<<<<<<< HEAD
=======
import android.widget.CalendarView;
import android.widget.EditText;
>>>>>>> parent of 4ca27b4... right month is showed


public class CalenderController extends Activity {

<<<<<<< HEAD
    CalendarModel calendarModel;
=======
    CalendarView cV;
    EditText hej;
>>>>>>> parent of 4ca27b4... right month is showed

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
<<<<<<< HEAD
        calendarModel.init();
    }

=======
        init();
    }



    void init() {
        cV = findViewById(R.id.calendarView);
        cV.setFirstDayOfWeek(2);
        hej = findViewById(R.id.dateText);

        cV.getDate();
        cV.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2) {
                hej.setText("Date: " + i2 + " / " + i1 + " / " + i);
            }
        });
    }
>>>>>>> parent of 4ca27b4... right month is showed
}