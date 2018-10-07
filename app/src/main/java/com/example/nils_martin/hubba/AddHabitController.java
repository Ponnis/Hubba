package com.example.nils_martin.hubba;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    private CheckBox monCxb, tueCxb, wedCxb, thuCxb, friCxb, satCxb, sunCxb;
    private CheckBox earlyMonthCbx, middleMonthCbx, lateMonthCbx;
    private TextView numberOfDaysTxtV;
    private Spinner numberOfDaysSpr;
    private Switch remainderSwitch;
    private List<CheckBox> cbxDayList = new ArrayList<>();
    private List<CheckBox> cbxMonthList = new ArrayList<>();



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
        numberOfDaysTxtV = findViewById(R.id.numTxtV);
        numberOfDaysSpr = findViewById(R.id.numSpr);
        earlyMonthCbx = findViewById(R.id.earlyMonthCbx);
        middleMonthCbx = findViewById(R.id.middleMonthCbx);
        lateMonthCbx = findViewById(R.id.lateMonthCbx);
        remainderSwitch = findViewById(R.id.remainderSwitch);
    }

    public void update() {

        createdHabit = new Habit("");
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createdHabit.setTitle(habitName.getText().toString());
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

      
    }

    private void dayVisible() {           //Set everything to invisible
        numberOfDaysTxtV.setVisibility(View.INVISIBLE);
        numberOfDaysSpr.setVisibility(View.INVISIBLE);

        for(int i = 0; i < cbxDayList.size(); i++) {
            cbxDayList.get(i).setVisibility(View.INVISIBLE);
        }

        for (int i = 0; i< cbxMonthList.size(); i++) {
            cbxMonthList.get(i).setVisibility(View.INVISIBLE);
        }


    }

    private void weekVisible () {          //Set the month-checkboxes to invisible and the week attribute to visible
        numberOfDaysTxtV.setVisibility(View.VISIBLE);
        numberOfDaysSpr.setVisibility(View.VISIBLE);

        for(int i = 0; i < cbxDayList.size(); i++) {
            cbxDayList.get(i).setVisibility(View.VISIBLE);
        }

        for (int i = 0; i< cbxMonthList.size(); i++) {
            cbxMonthList.get(i).setVisibility(View.INVISIBLE);
        }
    }

    private void monthVisible () {         //Set the week attribute to invisible and the month-checkboxes to visible
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
        cbxDayList.add(monCxb);
        cbxDayList.add(tueCxb);
        cbxDayList.add(wedCxb);
        cbxDayList.add(thuCxb);
        cbxDayList.add(friCxb);
        cbxDayList.add(satCxb);
        cbxDayList.add(sunCxb);
    }

    private void makeAListOfMonthCbx () {
        cbxMonthList.add(earlyMonthCbx);
        cbxMonthList.add(middleMonthCbx);
        cbxMonthList.add(lateMonthCbx);
    }

    private void endActivity(){
        finish();
        Intent intent = new Intent(AddHabitController.this, MainActivityController.class);
        startActivity(intent);
    }
}
