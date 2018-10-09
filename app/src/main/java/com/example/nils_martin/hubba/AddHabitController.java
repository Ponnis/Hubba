package com.example.nils_martin.hubba;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AddHabitController extends AppCompatActivity {


    private EditText habitName;
    private Button save, cancel, morning, midday, evening, daily, weekly, monthly;
    private Habit createdHabit;
    CheckBox monCxb, tueCxb, wedCxb, thuCxb, friCxb, satCxb, sunCxb;
    private CheckBox earlyMonthCbx, middleMonthCbx, lateMonthCbx;
    private TextView numberOfDaysTxtV, colontxtV, timeTxtV;
    private Spinner numberOfDaysSpr, hourSpr, minSpr;
    private Switch remainderSwitch;
    private List<CheckBox> cbxDayList = new ArrayList<>();
    private List<CheckBox> cbxMonthList = new ArrayList<>();
    List<Integer> calendarDaysList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit);
        init();
        makeAListOfDayCbx();
        makeAListOfMonthCbx();
        update();
    }

    public void init() {
        habitName = findViewById(R.id.habitInput);
        save = findViewById(R.id.saveBtn);
        cancel = findViewById(R.id.cancelBtn);
        morning = findViewById(R.id.morningBtn);
        midday = findViewById(R.id.middayBtn);
        evening = findViewById(R.id.eveningBtn);
        daily = findViewById(R.id.dailyBtn);
        weekly = findViewById(R.id.weeklyBtn);
        monthly = findViewById(R.id.monthlyBtn);
        monCxb = findViewById(R.id.monCbx);
        tueCxb = findViewById(R.id.tueCbx);
        wedCxb = findViewById(R.id.wedCbx);
        thuCxb = findViewById(R.id.thuCbx);
        friCxb = findViewById(R.id.friCbx);
        satCxb = findViewById(R.id.satCbx);
        sunCxb = findViewById(R.id.sunCbx);
        earlyMonthCbx = findViewById(R.id.earlyMonthCbx);
        middleMonthCbx = findViewById(R.id.middleMonthCbx);
        lateMonthCbx = findViewById(R.id.lateMonthCbx);
        numberOfDaysTxtV = findViewById(R.id.numTxtV);
        timeTxtV = findViewById(R.id.timeTxtV);
        colontxtV = findViewById(R.id.colontxtV);
        numberOfDaysSpr = findViewById(R.id.numSpr);
        hourSpr = findViewById(R.id.hourSpr);
        minSpr = findViewById(R.id.minSpr);
        remainderSwitch = findViewById(R.id.remainderSwitch);
    }

    public void update() {

        createdHabit = new Habit("", calendarDaysList);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeCalendarDaysList();
                createdHabit.setTitle(habitName.getText().toString());
                createdHabit.setDayToDo(calendarDaysList);
                MainActivityController.habits.add(createdHabit);
                endActivity();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endActivity();
            }
        });


        morning.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                createdHabit.setSTATE(Habit.State.MORNING);
            }
        });

        midday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createdHabit.setSTATE(Habit.State.MIDDAY);
            }
        });

        evening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createdHabit.setSTATE(Habit.State.EVENING);
            }
        });

        daily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dayVisible();
            }
        });

        weekly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weekVisible();
            }
        });

        monthly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monthVisible();
            }
        });

        remainderSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(remainderSwitch.isChecked()) {
                    hourSpr.setVisibility(View.VISIBLE);
                    minSpr.setVisibility(View.VISIBLE);
                    timeTxtV.setVisibility(View.VISIBLE);
                    colontxtV.setVisibility(View.VISIBLE);
                }
                else {
                    hourSpr.setVisibility(View.INVISIBLE);
                    minSpr.setVisibility(View.INVISIBLE);
                    timeTxtV.setVisibility(View.INVISIBLE);
                    colontxtV.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
    //Set everything to invisible
    private void dayVisible() {
        numberOfDaysTxtV.setVisibility(View.INVISIBLE);
        numberOfDaysSpr.setVisibility(View.INVISIBLE);

        for(int i = 0; i < cbxDayList.size(); i++) {
            cbxDayList.get(i).setVisibility(View.INVISIBLE);
        }

        for (int i = 0; i< cbxMonthList.size(); i++) {
            cbxMonthList.get(i).setVisibility(View.INVISIBLE);
        }


    }

    //Set the month-checkboxes to invisible and the week attribute to visible
    private void weekVisible () {
        numberOfDaysTxtV.setVisibility(View.VISIBLE);
        numberOfDaysSpr.setVisibility(View.VISIBLE);

        for(int i = 0; i < cbxDayList.size(); i++) {
            cbxDayList.get(i).setVisibility(View.VISIBLE);
        }

        for (int i = 0; i< cbxMonthList.size(); i++) {
            cbxMonthList.get(i).setVisibility(View.INVISIBLE);
        }
    }

    //Set the week attribute to invisible and the month-checkboxes to visible
    private void monthVisible () {
        numberOfDaysTxtV.setVisibility(View.INVISIBLE);
        numberOfDaysSpr.setVisibility(View.INVISIBLE);

        for(int i = 0; i < cbxDayList.size(); i++) {
            cbxDayList.get(i).setVisibility(View.INVISIBLE);
        }

        for (int i = 0; i< cbxMonthList.size(); i++) {
            cbxMonthList.get(i).setVisibility(View.VISIBLE);
        }
    }

    private void makeAListOfDayCbx() {
        cbxDayList.add(sunCxb);
        cbxDayList.add(monCxb);
        cbxDayList.add(tueCxb);
        cbxDayList.add(wedCxb);
        cbxDayList.add(thuCxb);
        cbxDayList.add(friCxb);
        cbxDayList.add(satCxb);
    }

    private void makeAListOfMonthCbx () {
        cbxMonthList.add(earlyMonthCbx);
        cbxMonthList.add(middleMonthCbx);
        cbxMonthList.add(lateMonthCbx);
    }

    //Making a list of the week days
    private void makeCalendarDaysList () {
        
        for (int i = 0; i < cbxDayList.size(); i++) {
           if(cbxDayList.get(i).isChecked()) {
               calendarDaysList.add(i+1);
           }
        }
    }

    private void endActivity(){
        finish();
        Intent intent = new Intent(AddHabitController.this, MainActivityController.class);
        startActivity(intent);
    }
}
