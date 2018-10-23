package com.example.nils_martin.hubba.ViewModel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ImageButton;


import com.example.nils_martin.hubba.Model.Frequency;
import com.example.nils_martin.hubba.Model.Habit;
import com.example.nils_martin.hubba.Model.HubbaModel;
import com.example.nils_martin.hubba.Model.ThemableObserver;
import com.example.nils_martin.hubba.Model.User;
import com.example.nils_martin.hubba.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;


public class MainActivityVM extends AppCompatActivity implements ThemableObserver {
    HubbaModel model = HubbaModel.getInstance();
    private ListView morningListView;
    private ListView middayListView;
    private ListView eveningListView;
    private ListView nightListView;
    private ListView doneListView;

    private ArrayAdapter<String> morningAdapter;
    private ArrayAdapter<String> middayAdapter;
    private ArrayAdapter<String> eveningAdapter;
    private ArrayAdapter<String> nightAdapter;
    private ArrayAdapter<String> doneAdapter;

    private List<Habit> habits = model.getCurrentUser().getHabits();
    private List<String> habitMorningString = new ArrayList<>();
    private List<String> habitMiddayString = new ArrayList<>();
    private List<String> habitEveningString = new ArrayList<>();
    private List<String> habitNightString = new ArrayList<>();
    private List<String> habitDoneString = new ArrayList<>();
    public FloatingActionButton addBtn;
    private ImageButton calendarBtn;
    private ImageButton menuButton;
    public static Habit openHabit = new Habit("");
    private Themehandler themehandler = new Themehandler();

    private int listItemHeight = 115;
    private int dividerHeight = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(themehandler.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        loadData();
        themehandler.addThemeListener(this);
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

    /**
     *saves the userlist
     */
    private void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json =gson.toJson(model.getUsers());
        editor.putString("userlist",json);
        editor.apply();
    }

    /**
     * loads the userlist into hubbamodels userlist
     */
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

    /**
     * Instantiates the different views and buttons on the MainPage.
     */
    private void initView() {
        morningListView = findViewById(R.id.morningListView);
        middayListView = findViewById(R.id.middayListView);
        eveningListView = findViewById(R.id.eveningListView);
        nightListView = findViewById(R.id.nightListView);
        doneListView = findViewById(R.id.doneListView);

        morningAdapter = new Adapter(this, this);
        middayAdapter = new Adapter(this, this);
        eveningAdapter = new Adapter(this, this);
        nightAdapter = new Adapter(this, this);
        doneAdapter = new Adapter(this, this);

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

    /**
    Goes through the habit list and put the string title into the correct list depending on state.
    and then populates the ListViews with corresponding habits.
    */
    private void updateLists () {
        clearStrings();
        Iterator<Habit> habitIterator = habits.iterator();
        while(habitIterator.hasNext()) {
            Habit habit = habitIterator.next();
            if (checkIfEventIsToday(habit)) {
                if (habit.getIsDone()) {
                    habitDoneString.add(habit.getTitle());
                }
                else {
                    switch (habit.getSTATE()) {
                        case MORNING:
                            habitMorningString.add(habit.getTitle());
                            break;
                        case MIDDAY:
                            habitMiddayString.add(habit.getTitle());
                            break;
                        case EVENING:
                            habitEveningString.add(habit.getTitle());
                            break;
                        case NIGHT:
                            habitNightString.add(habit.getTitle());
                            break;

                    }
                }
            }
        }
        fillLists(morningListView, morningAdapter, habitMorningString);
        fillLists(middayListView, middayAdapter, habitMiddayString);
        fillLists(eveningListView, eveningAdapter, habitEveningString);
        fillLists(nightListView, nightAdapter, habitNightString);
        fillLists(doneListView, doneAdapter, habitDoneString);

        adjustListHeight(morningListView, habitMorningString);
        adjustListHeight(middayListView, habitMiddayString);
        adjustListHeight(eveningListView, habitEveningString);
        adjustListHeight(nightListView, habitNightString);
        adjustListHeight(doneListView, habitDoneString);
    }

    /**
     * This method is used to see if a event is today or not
     * @param habit - habit is the habit that you want to check
     * @return true the event is today otherwise return false
     */
    private boolean checkIfEventIsToday(Habit habit) {
        Calendar nowCalendar = Calendar.getInstance();
        if(habit.getFREQUENCY() == Frequency.MONTHLY) {
            if(habit.getDaysToDo().contains(nowCalendar.get(Calendar.DAY_OF_MONTH))) {
                return true;
            }
        }
        else if(habit.getFREQUENCY() == Frequency.DAILY || habit.getFREQUENCY() == Frequency.WEEKLY) {
            if(habit.getDaysToDo().contains(nowCalendar.get(Calendar.DAY_OF_WEEK))) {
                return true;
            }
        }
        return false;
    }

    /**
    Method to fill lists with habits by adding titles to adapters and setting to listviews
     */
    private void fillLists(ListView listView, ArrayAdapter<String> adapter, List<String> strings){
        Iterator<String> iterator = strings.iterator();
        ArrayList<String> temp = new ArrayList<>();
        while(iterator.hasNext()){
            String string = iterator.next();
            if(!string.equals(null)){
                temp.add(string);
            }
        }
        for(String string: temp) {
            adapter.add(string);
        }
        listView.setAdapter(adapter);
    }

    /**
    method clearing all lists of strings
     */
    private void clearStrings(){
        habitMorningString.clear();
        habitMiddayString.clear();
        habitEveningString.clear();
        habitNightString.clear();
        habitDoneString.clear();
    }

    /**
     * methods to adjust height of listviews depending on number of items
     * in list
     * @param listView
     * @param strings
     */
    private void adjustListHeight(ListView listView, List<String> strings){
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = calculateHeight(strings);
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    /**
     * method to calculate height and return int 
     * @param strings
     * @return int
     */
    private int calculateHeight(List<String> strings){
        int num = strings.size();
        int height = listItemHeight*(num +1)+ dividerHeight*num;
        return height;
    }

    /**
    When a list item is clicked on
     */
    public void clicked(View view){
        Intent intent = new Intent(MainActivityVM.this, HabitVM.class);
        startActivity(intent);
    }

    public void checked(View view){
        initView();
    }

    /**
    Find which habit is clicked on and set variable openhabit.
     */
    protected void findHabit(String string){
        for(Habit habit: habits){
            if(habit.getTitle().equals(string)){
                setOpenHabit(habit);
            }
        }
    }

    protected Habit getHabit(String string){
        for(Habit habit: habits){
            if (habit.getTitle().equals(string)) {
                return habit;
            }
        }
        return null;
    }

    private void setOpenHabit (Habit habit){
        this.openHabit = habit;
    }


    public void recreateActivity(){recreate();}

}





