package com.example.nils_martin.hubba;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddHabitController extends AppCompatActivity {



    EditText habitName;
    Button save;
    Button cancel;
    Button morning;
    Button midday;
    Button evening;
    Habit createdHabit;
    Button daily;
    Button weekly;
    Button monthly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit);
        init();
    }

    public void init() {
        habitName = (EditText) findViewById(R.id.habitInput);
        save = (Button) findViewById(R.id.saveBtn);
        cancel = (Button) findViewById(R.id.cancelBtn);
        morning = (Button) findViewById(R.id.morningBtn);
        midday  = (Button) findViewById(R.id.middayBtn);
        evening = (Button) findViewById(R.id.eveningBtn);
        daily = findViewById(R.id.dailyButton);
        weekly = findViewById(R.id.weeklyButton);
        monthly = findViewById(R.id.monthlyButton);

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
                createdHabit.setFrequency(Frequency.DAILY);
            }
        });

        weekly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createdHabit.setFrequency(Frequency.WEEKLY);
            }
        });

        monthly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createdHabit.setFrequency(Frequency.MONTHLY);
            }
        });


    }

    private void endActivity(){
        finish();
        Intent intent = new Intent(AddHabitController.this, MainActivityController.class);
        startActivity(intent);
    }
}
