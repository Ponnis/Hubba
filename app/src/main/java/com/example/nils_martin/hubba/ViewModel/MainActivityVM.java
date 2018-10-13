package com.example.nils_martin.hubba.ViewModel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ImageButton;


import com.example.nils_martin.hubba.Model.Habit;
import com.example.nils_martin.hubba.Model.HubbaModel;
import com.example.nils_martin.hubba.Model.User;
import com.example.nils_martin.hubba.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class MainActivityVM extends AppCompatActivity {
    HubbaModel model = HubbaModel.getInstance();
    private ListView morningListView;
    private ListView middayListView;
    private ListView eveningListView;
    private ListView nightListView;
    private ListView doneListView;

    private ArrayAdapter<String> listAdapter; // = new ArrayAdapter<String>(this, R.layout.habit_list_item);

    public static List<Habit> habits = new ArrayList<>();
    private List<String> habitMorningString = new ArrayList<>();
    private List<String> habitMiddayString = new ArrayList<>();
    private List<String> habitEveningString = new ArrayList<>();
    private List<String> habitNightString = new ArrayList<>();
    private List<String> habitDoneString = new ArrayList<>();
    public FloatingActionButton addBtn;
    private ImageButton calendarBtn;
    private ImageButton menuButton;
    public static Habit openHabit = new Habit("");

    private HabitListItemVM morning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initList();
        loadData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    protected void onPause() {
        saveData();
        super.onPause();
    }

    //saves the userlist
    private void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json =gson.toJson(model.getUsers());
        editor.putString("userlist",json);
        editor.apply();
    }
    //loads the userlist into hubbamodels userlist
    private void loadData(){
        SharedPreferences sharedPreferences=getSharedPreferences("shared preferences",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("userlist",null);
        Type type = new TypeToken<ArrayList<User>>(){}.getType();
        HubbaModel.getInstance().setUsers(gson.fromJson(json,type));
         if(model.getUsers() == null){
             HubbaModel.getInstance().setUsers(new ArrayList<User>());
         }
    }

    private void initList() {
        // TODO: 2018-10-05 Implement on click listener for the LinearLayouts that fetch position
    }

    //Instantiates the different views and buttons on the MainPage.
    private void initView() {
       morningListView = findViewById(R.id.morningListView);
       middayListView = findViewById(R.id.middayListView);
       eveningListView = findViewById(R.id.eveningListView);
       nightListView = findViewById(R.id.eveningListView);
       doneListView = findViewById(R.id.doneListView);

        menuButton = findViewById((R.id.menuBtn));
        calendarBtn = findViewById(R.id.calendarBtn);
        addBtn = findViewById(R.id.addBtn);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivityVM.this, AddHabitVM.class);
                startActivity(intent);
            }
        });
        calendarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivityVM.this, CalendarVM.class);
                startActivity(intent);
            }
        });
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivityVM.this, MenuVM.class);
                startActivity(intent);
            }
        });

        updateLists();
    }

    /*
    Goes through the habit list and put the string title into the correct list depending on state.
    and then populates the ListViews with corresponding habits.
    */
    private void updateLists () {
        /*morningListView.removeAllViews();
        clearList(middayListView);
        clearList(eveningListView);
        clearList(nightListView);
        clearList(doneListView);*/

        for (Habit habit : habits) {
            switch (habit.getSTATE()) {
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
        /*populate(habitMorningString, morningListView);
        populate(habitMiddayString, middayListView);
        populate(habitEveningString, eveningListView);
        populate(habitNightString, nightListView);
        populate(habitDoneString, doneListView);*/

        //morning = new HabitListItemVM();
        //morning.populateHabitList(habitMorningString, morningListView);
        listAdapter = new ArrayAdapter<>(this, R.layout.habit_list_item, R.id.listItemTextView, habitMorningString);
        for(String string: habitMorningString){
            listAdapter.add(string);
        }
        morningListView.setAdapter(listAdapter);
    }

    //clicked on item
    public void clicked(View view){
        TextView textView = findViewById(R.id.listItemTextView);
        findHabit(textView.getText().toString());
        Intent intent = new Intent(MainActivityVM.this, HabitVM.class);
        startActivity(intent);
    }

    private void findHabit(String string){
        for(Habit habit: habits){
            if(habit.getTitle(habit).equals(string)){
                setOpenHabit(habit);
            }
        }
    }

    //Clearing all Views from the LinearLayout.
    private void clearList (ListView listView){
        listView.removeAllViews();
    }

    //Loops through given list of strings and call for addItem with that string and given ListView.
    private void populate (List < String > inputList, ListView listView){
        for (String string : inputList) {
            addItem(string, listView);
        }
    }

    //A method that puts String s in an constraint layout habit_list_item and put's that inside of the chosen ListView.
    private void addItem (String s, ListView listView){
        ConstraintLayout consLayout = (ConstraintLayout) View.inflate(this, R.layout.habit_list_item, null);
        TextView textview = (TextView) consLayout.getViewById(R.id.habit_list_item);
        textview.setText(s);
        //linearLayout.addView(consLayout);
    }

    private void findOpenHabit ( int position, List list){
        for (Habit habit : habits) {
            if (list.get(position).equals(habit.getTitle(habit))) {
                setOpenHabit(habit);
            }
        }
    }

    private void setOpenHabit (Habit habit){
        this.openHabit = habit;
    }
}





