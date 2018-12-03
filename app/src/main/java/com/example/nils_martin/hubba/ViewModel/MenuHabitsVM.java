package com.example.nils_martin.hubba.ViewModel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.nils_martin.hubba.Model.Habit;
import com.example.nils_martin.hubba.Model.HubbaModel;
import com.example.nils_martin.hubba.Model.IHabit;
import com.example.nils_martin.hubba.Model.ThemableObserver;
import com.example.nils_martin.hubba.R;
import com.example.nils_martin.hubba.Services.IPersistantDataHandler;
import com.example.nils_martin.hubba.Services.PersistantDataHandler;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Li Rönning and Camilla Söderlund
 */
public class MenuHabitsVM extends AppCompatActivity implements ThemableObserver {

    private IPersistantDataHandler service = PersistantDataHandler.getInstance();
    private HubbaModel model = HubbaModel.getInstance();
    private ThemeHandler themeHandler = new ThemeHandler();

    private List<IHabit> habits;
    private ArrayList<String> habitStrings;
    private ListView yourHabitsListView;
    private ArrayAdapter<String> yourHabitsAdapter;
    private ImageButton backButton;

    protected static IHabit openHabit = new Habit("");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(themeHandler.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_habits);
        themeHandler.addThemeListener(this);
        init();
    }

    @Override
    protected void onResume() {
        setTheme(themeHandler.getTheme());
        super.onResume();
        themeHandler.addThemeListener(this);
        updateHabitsListView();
    }

    /**
     * Calls on other functions that initialize ListViews, lists etc.
     */
    private void init(){
        initArrays();
        initFindByView();
        initOnClickListeners();
        updateHabitsListView();
    }

    private void initOnClickListeners() {
        backButtonOnClick();
    }

    private void backButtonOnClick() {
        backButton.setOnClickListener(v -> onBackPressed());
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
        backButton = findViewById(R.id.backBtn8);
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
        for(IHabit habit : habits){
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
                intent.putExtra("from", "MenuHabitsVM");
                startActivity(intent);
            }
        });
    }

    private void findHabit(String string) {
        for(IHabit habit: habits) {
            if(habit.getTitle().equals(string)) {
                setOpenHabit(habit);
            }
        }
    }

    private  void setOpenHabit(IHabit habit) {
        openHabit = habit;
    }

    @Override
    protected void onPause() {
        try {
            service.save(this.getApplicationContext());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        super.onPause();
    }

    @Override
    public void recreateActivity() {
        recreate();
    }

}
