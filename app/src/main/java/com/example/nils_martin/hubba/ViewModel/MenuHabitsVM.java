package com.example.nils_martin.hubba.ViewModel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.nils_martin.hubba.Model.Habit;
import com.example.nils_martin.hubba.Model.HubbaModel;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(themehandler.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_habits);
        themehandler.addThemeListener(this);
        init();
    }

    private void init(){
        initArrays();
        initFindByView();
        getHabitsList();
        updateHabitsListView();
    }

    private void initArrays(){
        habits = new ArrayList<>();
        habitStrings = new ArrayList<>();
    }

    private void initFindByView() {
        yourHabitsListView = (ListView) findViewById(R.id.yourHabitsListView);
    }

    private void getHabitsList(){
        habits = model.getCurrentUser().getHabits();
    }

    private void updateHabitsListView(){
        habitStrings.clear();
        fillHabitStringList();
        fillHabitListView();
    }

    private void fillHabitStringList(){
        for(Habit habit : habits){
            habitStrings.add(habit.getTitle());
        }
    }

    private void fillHabitListView(){
        yourHabitsAdapter = new ArrayAdapter<String>(
                this,
                R.layout.menu_list_item,
                habitStrings );
        yourHabitsListView.setAdapter(yourHabitsAdapter);
    }

    @Override
    public void recreateActivity() {
        recreate();
    }
}
