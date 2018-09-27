package com.example.nils_martin.hubba;

import android.os.Bundle;
import android.app.Activity;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class CalenderController extends Activity {

    TextView dateText, activityTxtV;
    CalendarView calendarView;
    List<FakeHabit> fake = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        calendarView = findViewById(R.id.calendarView);
        dateText = findViewById(R.id.dateText);
        activityTxtV = findViewById(R.id.activityTxtV);

        init();
    }

    StringBuilder stringBuilder = new StringBuilder();

    void init() {
        makeAList();
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int dayOfMonth) {
                dateText.setText("Date: " + dayOfMonth + " / " + (month+1) + " / " + year);
                stringBuilder.setLength(0);
                stringBuilder.append("Habits:");
                for(int i = 0; i<fake.size(); i++) {
                    if(fake.get(i).date == dayOfMonth) {
                        if (stringBuilder.length() != 0) {          //If you have more than one activity on the same day,
                            stringBuilder.append("\n" + fake.get(i).time + "\b" + fake.get(i).name);
                        }
                        else {
                            stringBuilder.append(fake.get(i).name);
                        }
                    }
                }
                activityTxtV.setText(stringBuilder.toString());    //If you have a activity at the day, this will show as a text under the calendar
            }
        });
    }


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