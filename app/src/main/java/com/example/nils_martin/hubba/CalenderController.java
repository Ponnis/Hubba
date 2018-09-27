package com.example.nils_martin.hubba;

import android.os.Bundle;
import android.app.Activity;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class CalenderController extends Activity {


    EditText dateText;
    CalendarView calendarView;
    List<FakeHabit> fake = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        calendarView = findViewById(R.id.calendarView);
        dateText = findViewById(R.id.dateText);

        init();
    }

    void init() {
        makeAList();
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int dayOfMonth) {
                dateText.setText("Date: " + dayOfMonth + " / " + (month+1) + " / " + year);

                for(int i = 0; i<fake.size(); i++) {
                    if(fake.get(i).date == dayOfMonth) {            //If you have a activity, this will show as a toast
                       Toast.makeText(getApplicationContext(), fake.get(i).name, Toast.LENGTH_LONG).show();
                        break;
                    }
                    else {          //Show a toast even if you don't have a activity.
                 //       Toast.makeText(getApplicationContext(), fake.get(i).date + "Selected Date:\n" + "Day = " + "test" + "\n" + "Month = " + (month+1) + "\n" + "Year = " + year, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }


    void makeAList () {
        fake.add(new FakeHabit("Aktivitet", 7));
        fake.add(new FakeHabit("Lunch", 4));
        fake.add(new FakeHabit("Bio", 21));
    }
}



class FakeHabit {
    String name;
    int date;

    FakeHabit (String name, int date) {
        this.name = name;
        this.date = date;
    }
}