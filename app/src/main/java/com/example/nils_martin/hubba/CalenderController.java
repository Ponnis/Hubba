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

    StringBuilder stringBuilder = new StringBuilder();

    void init() {
        makeAList();
        stringBuilder.append("");
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int dayOfMonth) {
                dateText.setText("Date: " + dayOfMonth + " / " + (month+1) + " / " + year);
                stringBuilder.setLength(0);
                for(int i = 0; i<fake.size(); i++) {
                    if(fake.get(i).date == dayOfMonth) {
                        if (stringBuilder.length() != 0) {          //If you have more than one activity on the same day,
                            stringBuilder.append("\n" + fake.get(i).name);
                        }
                        else {
                            stringBuilder.append(fake.get(i).name);
                        }
                    }
                }
                Toast.makeText(getApplicationContext(), stringBuilder.toString(), Toast.LENGTH_LONG).show();    //If you have a activity at the day, this will show as a toast
            }
        });
    }


    void makeAList () {
        fake.add(new FakeHabit("Träning", 7));
        fake.add(new FakeHabit("Lunch", 4));
        fake.add(new FakeHabit("Bio", 21));
        fake.add(new FakeHabit("Hundvakt", 21));
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