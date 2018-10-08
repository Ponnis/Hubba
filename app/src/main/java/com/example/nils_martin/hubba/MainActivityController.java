package com.example.nils_martin.hubba;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;


public class MainActivityController extends AppCompatActivity {
    private LinearLayout morningLinearLayout;
    private LinearLayout middayLinearLayout;
    private LinearLayout eveningLinearLayout;
    private LinearLayout nightLinearLayout;
    private LinearLayout doneLinearLayout;
    public static List<Habit> habits = new ArrayList<>();
    private List<String> habitMorningString = new ArrayList<>();
    private List<String> habitMiddayString = new ArrayList<>();
    private List<String> habitEveningString = new ArrayList<>();
    private List<String> habitNightString = new ArrayList<>();
    private List<String> habitDoneString = new ArrayList<>();
    public FloatingActionButton addBtn;
    private ImageButton calendarBtn;
    public static Habit openHabit = new Habit("");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }


    private void initList(){
        // TODO: 2018-10-05 Implement on click listener for the LinearLayouts that fetch position
        /*
        morningLinearLayout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                findOpenHabit(position, habitMorningString);

                Intent intent = new Intent(MainActivityController.this, HabitView.class);
                startActivity(intent);

            }
        });

        middayLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // findOpenHabit(pos, habitEveningString);

                Intent intent = new Intent(MainActivityController.this, HabitView.class);
                startActivity(intent);
            }
        });


        eveningLinearLayout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                findOpenHabit(position, habitEveningString);

                Intent intent = new Intent(MainActivityController.this, HabitView.class);
                startActivity(intent);
            }
        });*/
    }
    //Instantiates the different views and buttons on the MainPage.
    private void initView() {
        morningLinearLayout = (LinearLayout) findViewById( R.id.morningList);
        middayLinearLayout = (LinearLayout) findViewById( R.id.middayList);
        eveningLinearLayout = (LinearLayout) findViewById( R.id.eveningList);
        nightLinearLayout = (LinearLayout) findViewById(R.id.NightList);
        doneLinearLayout = (LinearLayout) findViewById(R.id.doneList);


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
        updateLists();
    }

    /*
    Goes thrue the habbit list and put the string title into the correct list depending on state.
    and then populates the Linearlayouts with corresponding habits.
    */
    private void updateLists (){
        clearLayout(morningLinearLayout);
        clearLayout(middayLinearLayout);
        clearLayout(eveningLinearLayout);
        clearLayout(nightLinearLayout);
        clearLayout(doneLinearLayout);

        for (Habit habit: habits){
            switch (habit.getSTATE()){
                case MORNING:
                    habitMorningString.add(habit.getTitle(habit));
                    break;
                case MIDDAY:
                    habitMiddayString.add(habit.getTitle(habit));
                    break;
                case EVENING:
                    habitEveningString.add(habit.getTitle(habit));
                    break;
                case NIGHT:
                    habitNightString.add(habit.getTitle(habit));
                    break;
                case DONE:
                    habitDoneString.add(habit.getTitle(habit));
                    break;
            }
        }
        populate(habitMorningString,morningLinearLayout);
        populate(habitMiddayString, middayLinearLayout);
        populate(habitEveningString, eveningLinearLayout);
        populate(habitNightString,nightLinearLayout);
        populate(habitDoneString,doneLinearLayout);
    }

    //Clearing all Views from the LinearLayout.
    private void clearLayout(LinearLayout layout) {
        layout.removeAllViews();
    }


    //Loops thru given list of strings and call for addItem with that string and given Linearlayout.
    private void populate(List<String> inputList, LinearLayout linearLayout){
        for (String string: inputList) {
            addItem(string,linearLayout);
        }
    }
    //A method that puts String s in an constraint layout habbit_list_item and put's that inside of the chosen Linearlayout.
    private void addItem(String s, LinearLayout linearLayout) {
        ConstraintLayout consLayout = (ConstraintLayout) View.inflate(this, R.layout.habbit_list_item, null);
        TextView textview = (TextView) consLayout.getViewById(R.id.habbit_list_item);
        textview.setText(s);
        linearLayout.addView(consLayout);
    }

    private void findOpenHabit (int position, List list){
        for (Habit habit: habits){
            if(list.get(position).equals(habit.getTitle(habit))){
                setOpenHabit(habit);
            }
        }
    }

    private void setOpenHabit(Habit habit){
        this.openHabit = habit;
    }

    }





