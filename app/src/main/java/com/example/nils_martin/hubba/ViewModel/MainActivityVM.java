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

import com.example.nils_martin.hubba.Model.Achievement;
import com.example.nils_martin.hubba.Model.Frequency;
import com.example.nils_martin.hubba.Model.Group;
import com.example.nils_martin.hubba.Model.Habit;
import com.example.nils_martin.hubba.Model.HubbaModel;
import com.example.nils_martin.hubba.Model.IFriend;
import com.example.nils_martin.hubba.Model.IHabit;
import com.example.nils_martin.hubba.Model.ThemableObserver;
import com.example.nils_martin.hubba.Model.User;
import com.example.nils_martin.hubba.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

    private List<IHabit> habits = model.getCurrentUser().getHabits();
    private List<String> habitMorningString = new ArrayList<>();
    private List<String> habitMiddayString = new ArrayList<>();
    private List<String> habitEveningString = new ArrayList<>();
    private List<String> habitNightString = new ArrayList<>();
    private List<String> habitDoneString = new ArrayList<>();
    public FloatingActionButton addBtn;
    private ImageButton calendarBtn;
    private ImageButton menuButton;
    protected static IHabit openHabit = new Habit("");
    private ThemeHandler themeHandler = new ThemeHandler();
    private Calendar nowCalendar = Calendar.getInstance();

    private int listItemHeight = 130;
    private int dividerHeight = 40;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(themeHandler.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        themeHandler.addThemeListener(this);
    }

    @Override
    protected void onResume() {
        setTheme(themeHandler.getTheme());
        super.onResume();
        themeHandler.addThemeListener(this);
        initView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
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
        Iterator<IHabit> habitIterator = habits.iterator();
        while(habitIterator.hasNext()){
            IHabit habit = habitIterator.next();
            habit.setDaysToDoSize(habit.getDaysToDo().size());
            if(checkIfEventIsToday(habit)) {
                if (habit.getIsDone() && haveYouDoneTheHabitToday(habit)) {
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
    private boolean checkIfEventIsToday(IHabit habit) {
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

    private boolean haveYouDoneTheHabitToday(IHabit habit) {
        if(habit.getTodayDate().equals(getTodayDate().toString())) {
            habit.setDoneTo(true);
            return true;
        }
        else {
            habit.setDoneTo(false);
            return false;
        }
    }

    private Date getTodayDate() {
        Date today = new Date();
        nowCalendar.set(Calendar.HOUR, 0);
        nowCalendar.set(Calendar.MINUTE, 0);
        nowCalendar.set(Calendar.SECOND, 0);
        today.setYear(nowCalendar.get(Calendar.YEAR));
        today.setMonth(nowCalendar.get(Calendar.MONTH));
        today.setDate(nowCalendar.get(Calendar.DAY_OF_MONTH));
        today.setHours(0);
        today.setMinutes(0);
        today.setSeconds(0);
        return today;
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
        intent.putExtra("from", "MainActivityVM");
        startActivity(intent);
    }

    public void checked(View view){
        initView();
    }

    /**
    Find which habit is clicked on and set variable openhabit.
     */
    protected void findHabit(String string){
        for(IHabit habit: habits){
            if(habit.getTitle().equals(string)){
                setOpenHabit(habit);
            }
        }
    }

    protected IHabit getHabit(String string){
        for(IHabit habit: habits){
            if (habit.getTitle().equals(string)) {
                return habit;
            }
        }
        return null;
    }

    private void setOpenHabit (IHabit habit){
        this.openHabit = habit;
    }


    public void recreateActivity(){recreate();}
}





