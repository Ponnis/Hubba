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
        calendarView = (CalendarView) findViewById(R.id.calendarView);
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
                    if(fake.get(i).dag == dayOfMonth) {
                        Toast.makeText(getApplicationContext(), "Selected Date:\n" + "Day = " + fake.get(i).name + "\n" + "Month = " + month + "\n" + "Year = " + year, Toast.LENGTH_LONG).show();

                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Selected Date:\n" + "Day = " + "test" + "\n" + "Month = " + month + "\n" + "Year = " + year, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }


    void makeAList () {
        fake.add(new FakeHabit("Katt", 4));
        fake.add(new FakeHabit("Kamel", 7));
    }




}

class FakeHabit {
    String name;
    int dag;

    FakeHabit (String name, int dag) {
        this.name = name;
        this.dag = dag;
    }
}