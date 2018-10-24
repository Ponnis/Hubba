package com.example.nils_martin.hubba.ViewModel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.nils_martin.hubba.Model.Habit;
import com.example.nils_martin.hubba.Model.HubbaModel;
import com.example.nils_martin.hubba.Model.IHabit;
import com.example.nils_martin.hubba.Model.ThemableObserver;
import com.example.nils_martin.hubba.R;

import java.util.ArrayList;
import java.util.List;

public class MenuHabitsVM extends AppCompatActivity implements ThemableObserver {

    private HubbaModel model = HubbaModel.getInstance();
    private Themehandler themehandler = new Themehandler();

    private List<Habit> habits;
    private ArrayList<String> habitStrings;
    private ListView yourHabitsListView;
    private ArrayAdapter<String> yourHabitsAdapter;

    public static Habit openHabit = new Habit("");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(themehandler.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_habits);
        themehandler.addThemeListener(this);
        init();
    }

    @Override
    protected void onResume() {
        setTheme(themehandler.getTheme());
        super.onResume();
        themehandler.addThemeListener(this);
        updateHabitsListView();
    }

    /**
     * Calls on other functions that initialize ListViews, lists etc.
     */
    private void init(){
        initArrays();
        initFindByView();
        updateHabitsListView();
    }

    private void initArrays(){
        habits = new ArrayList<>();
        habitStrings = new ArrayList<>();
    }

    /**
     * Initialize all view attributes
     */
    private void initFindByView() {
        yourHabitsListView = (ListView) findViewById(R.id.yourHabitsListView);
    }

    /**
     * Gets the users habits from HubbaModel and adds them to habits list in this class
     */
    private void getHabitsList(){
        habits = model.getCurrentUser().getHabits();
    }

    /**
     * Calls methods that update the list and the ListView in the interface.
     */
    private void updateHabitsListView(){
        getHabitsList();
        fillHabitStringList();
        fillHabitListView();
        listViewOnClick();
    }

    /**
     * First clears the list habitStrings and then updates it with the
     * titles of habits in habits.
     */
    private void fillHabitStringList(){
        habitStrings.clear();
        for(Habit habit : habits){
            habitStrings.add(habit.getTitle());
        }
    }

    /**
     * Fills the ListView with strings from habitStrings.
     */
    private void fillHabitListView(){
        yourHabitsAdapter = new ArrayAdapter<String>(
                this,
                R.layout.menu_list_item,
                habitStrings );
        yourHabitsListView.setAdapter(yourHabitsAdapter);
    }

    private void listViewOnClick () {
        yourHabitsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                findHabit(yourHabitsListView.getItemAtPosition(position).toString());
                Intent intent = new Intent(MenuHabitsVM.this, HabitVM.class);
                startActivity(intent);
            }
        });
    }

    private void findHabit(String string) {
        for(Habit habit: habits) {
            if(habit.getTitle().equals(string)) {
                setOpenHabit(habit);
            }
        }
    }

    private  void setOpenHabit(Habit habit) {
        openHabit = habit;
    }

    @Override
    public void recreateActivity() {
        recreate();
    }
}
