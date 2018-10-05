package com.example.nils_martin.hubba;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class AddHabitController extends AppCompatActivity {



    EditText habitName;
    Button save, cancel, morning, midday, evening, daily, weekly, monthly;
    Habit createdHabit;
    private CheckBox monCxb, tueCxb, wedCxb, thuCxb, friCxb, satCxb, sunCxb;
    List<CheckBox> checkBoxList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit);
        init();
        makeAListOfCheckboxes();
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
                
            }
        });

        weekly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                week();
            }
        });

        monthly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                month();
            }
        });


    }
    private void week () {
        for(int i = 0; i < checkBoxList.size(); i++) {
            checkBoxList.get(i).setVisibility(View.INVISIBLE);
        }
    }


    private void month () {
        for(int i = 0; i < checkBoxList.size(); i++) {
            checkBoxList.get(i).setVisibility(View.INVISIBLE);
        }
    }




    private void makeAListOfCheckboxes () {
        checkBoxList.add(monCxb);
        checkBoxList.add(tueCxb);
        checkBoxList.add(wedCxb);
        checkBoxList.add(thuCxb);
        checkBoxList.add(friCxb);
        checkBoxList.add(satCxb);
        checkBoxList.add(sunCxb);
    }

    private void endActivity(){
        finish();
        Intent intent = new Intent(AddHabitController.this, MainActivityController.class);
        startActivity(intent);
    }
}
