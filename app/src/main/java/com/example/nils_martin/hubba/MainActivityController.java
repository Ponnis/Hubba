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
    public static Habit selectedHabit = new Habit("");
    private int temp;

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

                findSelectedHabit(position, habitMorningString);

                Intent intent = new Intent(MainActivityController.this, HabitView.class);
                startActivity(intent);

            }
        });

        middayLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // findSelectedHabit(pos, habitEveningString);

                Intent intent = new Intent(MainActivityController.this, HabitView.class);
                startActivity(intent);
            }
        });


        eveningLinearLayout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                findSelectedHabit(position, habitEveningString);

                Intent intent = new Intent(MainActivityController.this, HabitView.class);
                startActivity(intent);
            }
        });*/
    }

    //Called from HabitListItem
    public void onClick(View view){
        temp = morningLinearLayout.getOrientation() -1;
        findSelectedHabit(temp, habitMorningString);

        Intent intent = new Intent(MainActivityController.this, HabitView.class);
        startActivity(intent);
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
                    // TODO: 2018-10-05 Add case night/done when they're implemented in habit
            }
        }
        populate(habitMorningString,morningLinearLayout);
        populate(habitMiddayString, middayLinearLayout);
        populate(habitEveningString, eveningLinearLayout);
        // TODO: 2018-10-05 populate nightLinearLayout and doneLinearLayout when habit have implemented night/done states
        // and the switch above have implemented the new cases.
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
    //A method that puts String s in an constraint layout habit_list_item and put's that inside of the chosen Linearlayout.
    private void addItem(String s, LinearLayout linearLayout) {
        ConstraintLayout consLayout = (ConstraintLayout) View.inflate(this, R.layout.habit_list_item, null);
        TextView textview = (TextView) consLayout.getViewById(R.id.habitListItemTextView);
        textview.setText(s);
        linearLayout.addView(consLayout);
    }

    private void findSelectedHabit(int position, List list){
        for (Habit habit: habits){
            if(list.get(position).equals(habit.getTitle(habit))){
                setSelectedHabit(habit);
            }
        }
    }

    private void setSelectedHabit(Habit habit){
        this.selectedHabit = habit;
    }

    }





