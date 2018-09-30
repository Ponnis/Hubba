package com.example.nils_martin.hubba;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
    public static Habit openHabit = new Habit("");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initList();
    }




    private void initList(){
        ArrayAdapter<String> morningListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,habitMorningString);
        ArrayAdapter<String> middayListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,habitMiddayString);
        ArrayAdapter<String> eveningListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,habitEveningString);
        morningListView.setAdapter(morningListAdapter);
        middayListView.setAdapter(middayListAdapter);
        eveningListView.setAdapter(eveningListAdapter);


        morningListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                findOpenHabit(position, habitMorningString);

                Intent intent = new Intent(MainActivityController.this, HabitView.class);
                startActivity(intent);

            }
        });

        middayListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                findOpenHabit(position, habitMiddayString);

                Intent intent = new Intent(MainActivityController.this, HabitView.class);
                startActivity(intent);
            }
        });

        eveningListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                findOpenHabit(position, habitEveningString);

                Intent intent = new Intent(MainActivityController.this, HabitView.class);
                startActivity(intent);
            }
        });
    }



    private void initView() {
        morningListView = (ListView) findViewById( R.id.morningListView );
        middayListView = (ListView) findViewById( R.id.middayListView );
        eveningListView = (ListView) findViewById( R.id.eveningListView );
        addBtn = findViewById(R.id.addBtn);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivityController.this, AddHabitController.class);
                startActivity(intent);
            }
        });
        updateView();
    }

    private void updateView (){
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

    private void findOpenHabit (int position, List list){
        for (Habit habit: habits){
            if(list.get(position).equals(habit.getTitle(habit))){
                setOpenHabit(habit);
                System.out.println(openHabit.getTitle(openHabit));
            }
        }
    }

    public Habit getOpenHabit(){
        return this.openHabit;
    }

    private void setOpenHabit(Habit habit){
        this.openHabit = habit;
    }
}





