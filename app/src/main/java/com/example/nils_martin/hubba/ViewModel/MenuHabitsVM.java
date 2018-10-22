package com.example.nils_martin.hubba.ViewModel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.nils_martin.hubba.Model.Group;
import com.example.nils_martin.hubba.Model.Habit;
import com.example.nils_martin.hubba.Model.HubbaModel;
import com.example.nils_martin.hubba.Model.ThemableObserver;
import com.example.nils_martin.hubba.R;

import java.util.ArrayList;
import java.util.List;

public class MenuHabitsVM extends AppCompatActivity implements ThemableObserver {

    private HubbaModel model = HubbaModel.getInstance();
    private Themehandler themehandler = new Themehandler();

    private List<Habit> habits = new ArrayList<>();
    private ArrayList<String> habitStrings = new ArrayList<>();
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
        initFindByView();
        getHabitsList();
        updateList();
    }

    private void initFindByView() {
        yourHabitsListView = (ListView) findViewById(R.id.yourHabitsListView);
    }

    private void getHabitsList(){
        habits = model.getCurrentUser().getHabits();
    }

    private void updateList(){
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
