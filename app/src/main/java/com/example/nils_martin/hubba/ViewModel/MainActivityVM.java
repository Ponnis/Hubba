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

    @Override
    protected void onPause() {
        try {
            save();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        super.onPause();
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

    public void save () throws JSONException {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (User user: model.getUsers()){
            JSONObject jsonUser = new JSONObject();
            jsonUser.put("userName", user.getUserName());
            jsonUser.put("password", user.getPassword());
            jsonUser.put("email", user.getEmail());
            jsonUser.put("imagePath", user.getImagePath());

            JSONArray friendsList = new JSONArray();
            jsonUser.put("friendsList", friendsList);

            JSONArray habitsList = new JSONArray();
            jsonUser.put("habit", habitsList);

            JSONArray achievementsList = new JSONArray();
            jsonUser.put("achievements", achievementsList);

            jsonUser.put("theme", user.getTheme());

            //jsonUser.put("isUsed", user.isUsed());

            jsonArray.put(jsonUser);
        }

        jsonObject.put("user", jsonArray);

        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("userlist",jsonObject.toString());
        editor.apply();

        for (User user: model.getUsers()){
            SharedPreferences sharedPreferences1 = getSharedPreferences(user.getUserName() + "habits", MODE_PRIVATE);
            SharedPreferences.Editor editor1 = sharedPreferences1.edit();

            editor1.putString("habitslist", habitsToJson(user));
            editor1.apply();
        }

        for (User user: model.getUsers()){
            for (IHabit habit: user.getHabits()){
                SharedPreferences sharedPreferences1 = getSharedPreferences(user.getUserName() + habit.getTitle() + "daysToInts", MODE_PRIVATE);
                SharedPreferences.Editor editor1 = sharedPreferences1.edit();

                editor1.putString("dayToIntList", daysToDoJson(habit));
                System.out.println("Skkriver ut  days to do:" + daysToDoJson(habit));
                editor1.apply();
            }
        }

        for (User user: model.getUsers()){
            SharedPreferences sharedPreferences1 = getSharedPreferences(user.getUserName() + "friends", MODE_PRIVATE);
            SharedPreferences.Editor editor1 = sharedPreferences1.edit();

            editor1.putString("friendslist", friendsToJson(user));
            editor1.apply();
        }

        /*for (User user: model.getUsers()){
            SharedPreferences sharedPreferences1 = getSharedPreferences(user.getUserName() + "achievements", MODE_PRIVATE);
            SharedPreferences.Editor editor1 = sharedPreferences1.edit();

            editor1.putString("achievementslist", achievementsToJson(user));
            editor1.apply();
        }*/
    }

    private String habitsToJson (User user) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (IHabit habit: model.getUser(user.getUserName()).getHabits()){
            JSONObject jsonHabits = new JSONObject();
            jsonHabits.put("title", habit.getTitle());
            jsonHabits.put("getGroupMembersCount", habit.getGroupMembersDoneCount());
            jsonHabits.put("streak", habit.getStreak());
            jsonHabits.put("isDone", habit.getIsDone());
            jsonHabits.put("reminderOn", habit.isReminderOn());
            //jsonHabits.put("habitTypeState", habit.getHabitTypeState().toString());
            jsonHabits.put("state", habit.getSTATE().toString());
            jsonHabits.put("frequency", habit.getFREQUENCY());
            jsonHabits.put("daysToDoSize", habit.getDaysToDoSize());

            JSONArray daysList = new JSONArray();
            jsonHabits.put("daysInteger", daysList);

            jsonArray.put(jsonHabits);
        }
        jsonObject.put("habit", jsonArray);
        return  jsonObject.toString();
    }

    private String daysToDoJson (IHabit habit) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (Integer integer: habit.getDaysToDo()){
            JSONObject jsonDays = new JSONObject();
            jsonDays.put("daysInt", integer);
            jsonArray.put(jsonDays);
        }
        System.out.println(jsonObject.put("daysToInt", jsonArray).toString());
        return jsonObject.put("daysToInt", jsonArray).toString();
    }

    private String friendsToJson (User user) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (IFriend friend: model.getUser(user.getUserName()).getFriends()){
            JSONObject jsonFriends = new JSONObject();
            jsonFriends.put("username", friend.getUserName());
            jsonArray.put(jsonFriends);
        }
        return jsonObject.put("friend", jsonArray).toString();
    }

    /*private String achievementsToJson (User user) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        System.out.println(user.getAchievements().size());
        for (Achievement achievement: model.getUser(user.getUserName()).getAchievements()){
            JSONObject jsonAchievement = new JSONObject();
            jsonAchievement.put("title", achievement.getTitle());
            jsonAchievement.put("isAcheived", achievement.getsAchieved());
            jsonArray.put(jsonAchievement);
        }
        return jsonObject.put("achievement", jsonArray).toString();
    }*/

}





