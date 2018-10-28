package com.example.nils_martin.hubba.ViewModel;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.example.nils_martin.hubba.Model.Acheievement;
import com.example.nils_martin.hubba.Model.Habit;
import com.example.nils_martin.hubba.Model.HubbaModel;
import com.example.nils_martin.hubba.Model.IFriend;
import com.example.nils_martin.hubba.Model.IHabit;
import com.example.nils_martin.hubba.Model.IHubbaModel;
import com.example.nils_martin.hubba.Model.State;
import com.example.nils_martin.hubba.Model.ThemableObserver;
import com.example.nils_martin.hubba.Model.User;
import com.example.nils_martin.hubba.R;
import com.example.nils_martin.hubba.Model.Frequency;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddHabitVM extends AppCompatActivity implements ThemableObserver{


    private EditText habitName;
    private Button save, cancel, morning, midday, evening, night, daily, weekly, monthly;
    private IHabit createdHabit;
    private CheckBox monCxb, tueCxb, wedCxb, thuCxb, friCxb, satCxb, sunCxb;
    private TextView colontxtV, timeTxtV, monthTxtV, wrongMesTxtV;
    private Spinner  hourSpr, minSpr, monthSpr;
    private Switch remainderSwitch;
    private ImageView nameWrongImgV, frequencyWrongImgV, stateWrongImgV, weekWrongImgV;
    private List<CheckBox> cbxDayList = new ArrayList<>();
    private List<CheckBox> cbxMonthList = new ArrayList<>();
    private List<Integer> calendarDaysList = new ArrayList<>();
    private IHubbaModel model = HubbaModel.getInstance();
    private ThemeHandler themeHandler = new ThemeHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(themeHandler.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit);
        initFindByView();
        makeAListOfDayCbx();
        themeHandler.addThemeListener(this);
        createdHabit = new Habit("", calendarDaysList);
        update();
    }

    /**
     * Initialize all the view attribute
     */
    private void initFindByView() {
        initEditText();
        initButtons();
        initCheckBoxes();
        initTextViews();
        initSpinners();
        initSwitches();
        initImageViews();
    }

    /**
     * This method has all the setOnClickListener
     */
    private void update() {
        habitNameOnClickListener();
        morningbuttonOnClickListener();
        middaybuttonOnClickListener();
        evningbuttonOnClickListener();
        nightbuttonOnClickListener();
        dailybuttonOnClickListener();
        weeklybuttonOnClickListener();
        monthlybuttonOnClickListener();
        RemainderSwitchOnClickListener();
        cancelbuttonOnClickListener();
        savebuttonOnClickListener();
    }

    /**
     * This method is calls when remainderSwitch.setOnClickListener is activated and the
     * remainderswitch is on.
     */
    private void remainderOn () {
        hourSpr.setVisibility(View.VISIBLE);
        minSpr.setVisibility(View.VISIBLE);
        timeTxtV.setVisibility(View.VISIBLE);
        colontxtV.setVisibility(View.VISIBLE);
        createdHabit.reminderEnabled();
    }

    /**
     * This method is calls when remainderSwitch.setOnClickListener is activated and the
     * remainderswitch is off.
     */
    private void remainderOff () {
        hourSpr.setVisibility(View.INVISIBLE);
        minSpr.setVisibility(View.INVISIBLE);
        timeTxtV.setVisibility(View.INVISIBLE);
        colontxtV.setVisibility(View.INVISIBLE);
        createdHabit.reminderDisabled();
    }

    /**
     * This method set everything to invisible when you click on the month-button.
     */
    private void dayVisible() {
        monthTxtV.setVisibility(View.INVISIBLE);
        monthSpr.setVisibility(View.INVISIBLE);

        for(int i = 0; i < cbxDayList.size(); i++) {
            cbxDayList.get(i).setVisibility(View.INVISIBLE);
        }

        for (int i = 0; i< cbxMonthList.size(); i++) {
            cbxMonthList.get(i).setVisibility(View.INVISIBLE);
        }
    }

    /**
     * This method makes it easier to see what happens when clicking on the week-button.
     */
    private void weekVisible () {
        monthSpr.setVisibility(View.INVISIBLE);
        monthTxtV.setVisibility(View.INVISIBLE);

        for(int i = 0; i < cbxDayList.size(); i++) {
            cbxDayList.get(i).setVisibility(View.VISIBLE);
        }
    }

    /**
     * This method makes it easier to see what happens when clicking on the month-button.
     */
    private void monthVisible () {
        monthTxtV.setVisibility(View.VISIBLE);
        monthSpr.setVisibility(View.VISIBLE);

        for(int i = 0; i < cbxDayList.size(); i++) {
            cbxDayList.get(i).setVisibility(View.INVISIBLE);
        }
    }

    /**
     * This method makes a list of the checkboxes because is easier to treat them as a group
     * than individual
     */
    private void makeAListOfDayCbx() {
        cbxDayList.add(sunCxb);
        cbxDayList.add(monCxb);
        cbxDayList.add(tueCxb);
        cbxDayList.add(wedCxb);
        cbxDayList.add(thuCxb);
        cbxDayList.add(friCxb);
        cbxDayList.add(satCxb);
    }

    /**
     * The method making a list of which weekdays the habit is to be done an depending on which
     * frequency that is select it so it can be shown on the right day.
     */
    private void makeCalendarDaysList () {

        calendarDaysList.clear();

        switch (createdHabit.getFREQUENCY()) {
            case DAILY:
                //When the frequency is daily every day is added in the list
                for (int i = 0; i < 7; i++) {
                    calendarDaysList.add(i + 1);
                }
                break;

            case WEEKLY:
                // When the frequency is weekly, the selected days are added to the list
                for (int i = 0; i < cbxDayList.size(); i++) {
                    if (cbxDayList.get(i).isChecked()) {
                        calendarDaysList.add(i + 1);
                    }
                }
                break;

            case MONTHLY:
                //When the frequency is monthly, the date is added in the list
                calendarDaysList.add(Integer.valueOf(monthSpr.getSelectedItem().toString()));
                break;
        }
    }

    /**
     * This method go through all the fields and looks for all information you need when
     * creating a Habit, is selected
     *  @return True if everything is correct, else return false
     */
    private boolean checkIfAllIsFillIn () {
        if(createdHabit.getFREQUENCY() == null || createdHabit.getSTATE() == null
                || createdHabit.getDaysToDo().size() == 0 || createdHabit.getTitle().equals("")) {
            if (createdHabit.getFREQUENCY() == null) {
                frequencyWrongImgV.setVisibility(View.VISIBLE);
            }

            if (createdHabit.getDaysToDo().size() == 0 && createdHabit.getFREQUENCY() == Frequency.WEEKLY) {
                weekWrongImgV.setVisibility(View.VISIBLE);
            }

            if (createdHabit.getSTATE() == null) {
                stateWrongImgV.setVisibility(View.VISIBLE);
            }
            if (createdHabit.getTitle().equals("")) {
                nameWrongImgV.setVisibility(View.VISIBLE);
            }
            return false;
        }
        return true;
    }

    /**
     * This method is used because it is necessary so the wrong message disappears
     * when you editing
     */
    private void takeAwayWrongMessage () {
        wrongMesTxtV.setVisibility(View.INVISIBLE);
        frequencyWrongImgV.setVisibility(View.INVISIBLE);
        nameWrongImgV.setVisibility(View.INVISIBLE);
        stateWrongImgV.setVisibility(View.INVISIBLE);
        weekWrongImgV.setVisibility(View.INVISIBLE);
    }

    /**
     * This method return the start time that can be used in createNotifications and depends on
     * the current habits frequency
     * @return long - the start time in milliseconds
     */
    private long getStartDate() {
        Calendar remainderCalendar = Calendar.getInstance();
        Calendar nowCalendar = Calendar.getInstance();

        remainderCalendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(hourSpr.getSelectedItem().toString()));
        remainderCalendar.set(Calendar.MINUTE, Integer.valueOf(minSpr.getSelectedItem().toString()));

        switch (createdHabit.getFREQUENCY()) {
            case DAILY:
                if(remainderCalendar.getTimeInMillis() < nowCalendar.getTimeInMillis())  {
                    remainderCalendar.set(Calendar.DAY_OF_MONTH, (remainderCalendar.get(Calendar.DAY_OF_MONTH)+1));
                }
                break;
            case WEEKLY:
                if(remainderCalendar.getTimeInMillis() < nowCalendar.getTimeInMillis())  {
                    remainderCalendar.set(Calendar.DAY_OF_MONTH, (remainderCalendar.get(Calendar.DAY_OF_MONTH)+1));
                }
                break;
            case MONTHLY:
                remainderCalendar.set(Calendar.DAY_OF_MONTH, calendarDaysList.get(0));

                if(remainderCalendar.getTimeInMillis() < nowCalendar.getTimeInMillis()) {
                    remainderCalendar.set(Calendar.MONTH, (remainderCalendar.get(Calendar.MONTH)+1));
                }
                break;
        }
        return remainderCalendar.getTimeInMillis();
    }

    /**
     * This method return the interval that can be used in createNotifications and it depends on
     * the current habits frequency
     * @return long - interval in milliseconds
     */
    private long getInterval () {
        Calendar remainderCalendar = Calendar.getInstance();
        Calendar nowCalendar = Calendar.getInstance();
        switch (createdHabit.getFREQUENCY()) {
            case DAILY:
                return 86400000; //One day in millisecond
            case WEEKLY:
                return 86400000; //One day in millisecond
            case MONTHLY:
                remainderCalendar.set(Calendar.MONTH, remainderCalendar.get(Calendar.MONTH)+1);
                long oneMonth = remainderCalendar.getTimeInMillis()-nowCalendar.getTimeInMillis(); //One month in millisecond
                return oneMonth;
        }
        return -1;
    }

    /**
     * This method is creating a notification for a specific habit.
     * @param startdate - This is the time when the alarm will start i milliseconds
     * @param interval - The interval decides the time between the notifications
     */
    private void createNotifcation(long startdate, long interval){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent notificationIntent = new Intent(this, NotificationReceiver.class);

        Bundle extra = new Bundle();
        extra.putString("HabitNamn", createdHabit.getTitle());

        notificationIntent.putExtras(extra);

        PendingIntent broadcast = PendingIntent.getBroadcast(this, (int)System.currentTimeMillis(), notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, startdate, interval, broadcast);//interval, broadcast);
        }
    }

    @Override
    public void recreateActivity() {
        recreate();
    }

    private void endActivity(){
        finish();
        Intent intent = new Intent(AddHabitVM.this, MainActivityVM.class);
        startActivity(intent);
    }


    private void initButtons() {
        save = findViewById(R.id.saveBtn);
        cancel = findViewById(R.id.cancelBtn);
        morning = findViewById(R.id.morningBtn);
        midday = findViewById(R.id.middayBtn);
        evening = findViewById(R.id.eveningBtn);
        night = findViewById(R.id.nightBtn);
        daily = findViewById(R.id.dailyBtn);
        weekly = findViewById(R.id.weeklyBtn);
        monthly = findViewById(R.id.monthlyBtn);
    }

    private void initEditText() {
        habitName = findViewById(R.id.habitInput);
    }

    private void initCheckBoxes() {
        monCxb = findViewById(R.id.monCbx);
        tueCxb = findViewById(R.id.tueCbx);
        wedCxb = findViewById(R.id.wedCbx);
        thuCxb = findViewById(R.id.thuCbx);
        friCxb = findViewById(R.id.friCbx);
        satCxb = findViewById(R.id.satCbx);
        sunCxb = findViewById(R.id.sunCbx);
    }

    private void initTextViews() {
        timeTxtV = findViewById(R.id.timeTxtV);
        colontxtV = findViewById(R.id.colontxtV);
        monthTxtV = findViewById(R.id.monthTxtV);
        wrongMesTxtV = findViewById(R.id.wrongMesTxtV);
    }

    private void initSpinners() {
        hourSpr = findViewById(R.id.hourSpr);
        minSpr = findViewById(R.id.minSpr);
        monthSpr = findViewById(R.id.monthSpr);
    }

    private void initSwitches() {
        remainderSwitch = findViewById(R.id.remainderSwitch);
    }

    private void initImageViews() {
        nameWrongImgV = findViewById(R.id.nameImgV);
        frequencyWrongImgV = findViewById(R.id.frequencyImgV);
        stateWrongImgV = findViewById(R.id.stateImgV);
        weekWrongImgV = findViewById(R.id.weekImgV);
    }

    private void habitNameOnClickListener() {
        habitName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeAwayWrongMessage();
            }
        });
    }

    private void morningbuttonOnClickListener() {
        morning.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                takeAwayWrongMessage();
                createdHabit.setSTATE(State.MORNING);
            }
        });
    }

    private void middaybuttonOnClickListener() {
        midday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeAwayWrongMessage();
                createdHabit.setSTATE(State.MIDDAY);
            }
        });
    }

    private void evningbuttonOnClickListener() {
        evening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeAwayWrongMessage();
                createdHabit.setSTATE(State.EVENING);
            }
        });
    }

    private void nightbuttonOnClickListener() {
        night.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeAwayWrongMessage();
                createdHabit.setSTATE(State.NIGHT);
            }
        });
    }

    private void dailybuttonOnClickListener() {
        daily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeAwayWrongMessage();
                dayVisible();
                createdHabit.setFREQUENCY(Frequency.DAILY);
            }
        });
    }

    private void weeklybuttonOnClickListener() {
        weekly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeAwayWrongMessage();
                weekVisible();
                createdHabit.setFREQUENCY(Frequency.WEEKLY);
            }
        });
    }

    private void monthlybuttonOnClickListener() {
        monthly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeAwayWrongMessage();
                monthVisible();
                createdHabit.setFREQUENCY(Frequency.MONTHLY);
            }
        });
    }

    private void RemainderSwitchOnClickListener() {
        remainderSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeAwayWrongMessage();
                if(remainderSwitch.isChecked()) {
                    remainderOn();
                }
                else {
                    remainderOff();
                }
            }
        });
    }

    private void cancelbuttonOnClickListener() {
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endActivity();
            }
        });
    }

    private void savebuttonOnClickListener() {
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(createdHabit.getFREQUENCY() != null) {
                    makeCalendarDaysList();
                }
                createdHabit.setTitle(habitName.getText().toString());
                if(createdHabit != null) {
                    createdHabit.setDaysToDo(calendarDaysList);
                }

                List<Integer> temp = new ArrayList<>();
                temp.add(Integer.valueOf(hourSpr.getSelectedItem().toString()));
                temp.add(Integer.valueOf(minSpr.getSelectedItem().toString()));
                createdHabit.setReminderTime(temp);

                if(checkIfAllIsFillIn()) {
                    createNotifcation(getStartDate(), getInterval());
                    model.getCurrentUser().addHabit(createdHabit);
                    endActivity();
                }
                else {
                    wrongMesTxtV.setVisibility(View.VISIBLE);
                    wrongMesTxtV.setText("You must fill in everything");
                    wrongMesTxtV.setTextColor(Color.RED);
                }
            }
        });
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

    public void save() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (User user : model.getUsers()) {
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

        editor.putString("userlist", jsonObject.toString());
        editor.apply();

        for (User user : model.getUsers()) {
            SharedPreferences sharedPreferences1 = getSharedPreferences(user.getUserName() + "habits", MODE_PRIVATE);
            SharedPreferences.Editor editor1 = sharedPreferences1.edit();

            editor1.putString("habitslist", habitsToJson(user));
            editor1.apply();
        }

        for (User user : model.getUsers()) {
            SharedPreferences sharedPreferences1 = getSharedPreferences(user.getUserName() + "friends", MODE_PRIVATE);
            SharedPreferences.Editor editor1 = sharedPreferences1.edit();

            editor1.putString("friendslist", friendsToJson(user));
            editor1.apply();
        }


        for (User user : model.getUsers()) {
            for (IHabit habit : user.getHabits()) {
                SharedPreferences sharedPreferences1 = getSharedPreferences(user.getUserName() + habit.getTitle() + "daysToInts", MODE_PRIVATE);
                SharedPreferences.Editor editor1 = sharedPreferences1.edit();

                editor1.putString("dayToIntList", daysToDoJson(habit));
                editor1.apply();
            }
        }

        for (User user: model.getUsers()){
            SharedPreferences sharedPreferences1 = getSharedPreferences(user.getUserName() + "achievements", MODE_PRIVATE);
            SharedPreferences.Editor editor1 = sharedPreferences1.edit();

            editor1.putString("achievementslist", achievementsToJson(user));
            editor1.apply();
        }

    }

    private String habitsToJson(User user) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (IHabit habit : model.getUser(user.getUserName()).getHabits()) {
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
        return jsonObject.toString();
    }

    private String daysToDoJson(IHabit habit) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (Integer integer : habit.getDaysToDo()) {
            JSONObject jsonDays = new JSONObject();
            jsonDays.put("daysInt", integer);
            jsonArray.put(jsonDays);
        }
        return jsonObject.put("daysToInt", jsonArray).toString();
    }

    private String friendsToJson(User user) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (IFriend friend : model.getUser(user.getUserName()).getFriends()) {
            JSONObject jsonFriends = new JSONObject();
            jsonFriends.put("username", friend.getUserName());
            jsonArray.put(jsonFriends);
        }
        return jsonObject.put("friend", jsonArray).toString();
    }

    private String achievementsToJson (User user) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (Acheievement achievement: model.getUser(user.getUserName()).getAcheievements()){
            JSONObject jsonAchievement = new JSONObject();
            jsonAchievement.put("title", achievement.getTitle());
            jsonAchievement.put("isAcheived", achievement.getAchieved());
            jsonArray.put(jsonAchievement);
        }
        return jsonObject.put("achievement", jsonArray).toString();
    }
}