package com.example.nils_martin.hubba;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivityController extends AppCompatActivity {

    private ListView morningListView;
    private ListView middayListView;
    private ListView eveningListView;
    private ArrayAdapter<String> listMorningAdapter;
    private ArrayAdapter<String> listMiddayAdapter;
    private ArrayAdapter<String> listEveningAdapter;
    public static List<Habit> habits = new ArrayList<>();
    private List<String> habitMorningString = new ArrayList<>();
    private List<String> habitMiddayString = new ArrayList<>();
    private List<String> habitEveningString = new ArrayList<>();
    public FloatingActionButton addBtn;
    private ImageButton calendarBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    public void init() {
        morningListView = (ListView) findViewById( R.id.morningListView );
        middayListView = (ListView) findViewById( R.id.middayListView );
        eveningListView = (ListView) findViewById( R.id.eveningListView );
        calendarBtn = findViewById(R.id.calendarBtn);
        addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivityController.this, AddHabitController.class);
                startActivity(intent);
            }
        });
        calendarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivityController.this, CalendarController.class);
                startActivity(intent);
            }
        });
        updateView();
    }

    public void updateView (){
        for (Habit habit: habits){
            if (habit.getSTATE() == Habit.State.MORNING){
                habitMorningString.add(habit.getTitle(habit));
            }
            else if (habit.getSTATE() == Habit.State.MIDDAY){
                habitMiddayString.add(habit.getTitle(habit));
            }
            else if (habit.getSTATE() == Habit.State.EVENING){
                habitEveningString.add(habit.getTitle(habit));
            }
        }

        listMorningAdapter = new ArrayAdapter<String>(this, R.layout.habit_layout, habitMorningString);
        listMiddayAdapter = new ArrayAdapter<String>(this, R.layout.habit_layout, habitMiddayString);
        listEveningAdapter = new ArrayAdapter<String>(this, R.layout.habit_layout, habitEveningString);

        morningListView.setAdapter( listMorningAdapter );
        middayListView.setAdapter(listMiddayAdapter);
        eveningListView.setAdapter(listEveningAdapter);


    }


}
