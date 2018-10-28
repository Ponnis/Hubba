package com.example.nils_martin.hubba.ViewModel;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.example.nils_martin.hubba.Model.Acheievement;
import com.example.nils_martin.hubba.Model.Frequency;
import com.example.nils_martin.hubba.Model.GroupHabitType;
import com.example.nils_martin.hubba.Model.Habit;
import com.example.nils_martin.hubba.Model.HubbaModel;
import com.example.nils_martin.hubba.Model.IFriend;
import com.example.nils_martin.hubba.Model.IHabit;
import com.example.nils_martin.hubba.Model.State;
import com.example.nils_martin.hubba.Model.ThemableObserver;
import com.example.nils_martin.hubba.Model.User;
import com.example.nils_martin.hubba.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
/**
 * @author Nils-Martin Robeling
 * */

public class CreateGroupHabitVM extends AppCompatActivity implements ThemableObserver,ICreateGroupHabitVM{
     private EditText habitName;
     private Button save, cancel, morning, midday, evening, night, daily, weekly, monthly;
     private Habit createdHabit;
     CheckBox monCxb, tueCxb, wedCxb, thuCxb, friCxb, satCxb, sunCxb;
     private TextView numberOfDaysTxtV, colontxtV, timeTxtV, monthTxtV, wrongMesTxtV;
     private Spinner numberOfDaysSpr, hourSpr, minSpr, monthSpr;
     private Switch remainderSwitch;
     private ImageView nameWrongImgV, frequencyWrongImgV, stateWrongImgV, weekWrongImgV;
     private List<CheckBox> cbxDayList = new ArrayList<>();
     private List<CheckBox> cbxMonthList = new ArrayList<>();
    List<Integer> calendarDaysList = new ArrayList<>();
    HubbaModel model = HubbaModel.getInstance();
    ThemeHandler themeHandler = new ThemeHandler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(themeHandler.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_group_habit);
        init();
        makeAListOfDayCbx();
        themeHandler.addThemeListener(this);
        update();
}

    public void init() {
         habitName = findViewById(R.id.habitInput);
         save = findViewById(R.id.saveBtn);
         cancel = findViewById(R.id.cancelBtn);
         morning = findViewById(R.id.morningBtn);
         midday = findViewById(R.id.middayBtn);
         evening = findViewById(R.id.eveningBtn);
         night = findViewById(R.id.nightBtn);
         daily = findViewById(R.id.dailyBtn);
         weekly = findViewById(R.id.weeklyBtn);
         monthly = findViewById(R.id.monthlyBtn);
         monCxb = findViewById(R.id.monCbx);
         tueCxb = findViewById(R.id.tueCbx);
         wedCxb = findViewById(R.id.wedCbx);
         thuCxb = findViewById(R.id.thuCbx);
         friCxb = findViewById(R.id.friCbx);
         satCxb = findViewById(R.id.satCbx);
         sunCxb = findViewById(R.id.sunCbx);
         numberOfDaysTxtV = findViewById(R.id.numTxtV);
         timeTxtV = findViewById(R.id.timeTxtV);
         colontxtV = findViewById(R.id.colontxtV);
         monthTxtV = findViewById(R.id.monthTxtV);
         wrongMesTxtV = findViewById(R.id.wrongMesTxtV);
         numberOfDaysSpr = findViewById(R.id.numSpr);
         hourSpr = findViewById(R.id.hourSpr);
         minSpr = findViewById(R.id.minSpr);
         monthSpr = findViewById(R.id.monthSpr);
         remainderSwitch = findViewById(R.id.remainderSwitch);
         nameWrongImgV = findViewById(R.id.nameImgV);
         frequencyWrongImgV = findViewById(R.id.frequencyImgV);
         stateWrongImgV = findViewById(R.id.stateImgV);
         weekWrongImgV = findViewById(R.id.weekImgV);
     }

    public void update() {

        createdHabit = new Habit("", calendarDaysList);

        habitName.setOnClickListener(v -> takeAwayWrongMessage());

        save.setOnClickListener(v -> {
            makeCalendarDaysList();


            createdHabit.setTitle(habitName.getText().toString());
            createdHabit.setDaysToDo(calendarDaysList);

            if(checkIfAllFieldsFilled()) {
                model.getCurrentUser().getHabits().add(createdHabit);
                endActivity();
            }
            else {
                wrongMesTxtV.setVisibility(View.VISIBLE);
                wrongMesTxtV.setText("You must fill in everything");
                wrongMesTxtV.setTextColor(Color.RED);

                createdHabit.setTitle(habitName.getText().toString());
                createdHabit.setDaysToDo(calendarDaysList);

                if(checkIfAllFieldsFilled()) {
                    setToGroupHabit(createdHabit);
                    //MainActivityVM.habits.add(createdHabit);
                    HubbaModel.getInstance().getCurrentUser().addHabit(createdHabit);

                    endActivity();
                }
                else {
                    wrongMesTxtV.setVisibility(View.VISIBLE);
                    wrongMesTxtV.setText("You must fill in everything");
                    wrongMesTxtV.setTextColor(Color.RED);
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endActivity();
            }
        });

        morning.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                takeAwayWrongMessage();
                createdHabit.setSTATE(State.MORNING);
            }
        });

        midday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeAwayWrongMessage();
                createdHabit.setSTATE(State.MIDDAY);
            }
        });

        evening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeAwayWrongMessage();
                createdHabit.setSTATE(State.EVENING);
            }
        });

        night.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeAwayWrongMessage();
                createdHabit.setSTATE(State.NIGHT);
            }
        });

        daily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeAwayWrongMessage();
                dayVisible();
                createdHabit.setFREQUENCY(Frequency.DAILY);
            }
        });

        weekly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeAwayWrongMessage();
                weekVisible();
                createdHabit.setFREQUENCY(Frequency.WEEKLY);
            }
        });

        monthly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeAwayWrongMessage();
                monthVisible();
                createdHabit.setFREQUENCY(Frequency.MONTHLY);
            }
        });

        remainderSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeAwayWrongMessage();
                if(remainderSwitch.isChecked()) {
                    hourSpr.setVisibility(View.VISIBLE);
                    minSpr.setVisibility(View.VISIBLE);
                    timeTxtV.setVisibility(View.VISIBLE);
                    colontxtV.setVisibility(View.VISIBLE);
                    createdHabit.reminderEnabled();
                }
                else {
                    hourSpr.setVisibility(View.INVISIBLE);
                    minSpr.setVisibility(View.INVISIBLE);
                    timeTxtV.setVisibility(View.INVISIBLE);
                    colontxtV.setVisibility(View.INVISIBLE);
                    createdHabit.reminderDisabled();
                }
            }
        });
    }

    /**
     * This method set everything to invisible when you click on the month-button.
     */
    public void dayVisible() {
        numberOfDaysTxtV.setVisibility(View.INVISIBLE);
        numberOfDaysSpr.setVisibility(View.INVISIBLE);
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
    public void weekVisible() {
        numberOfDaysTxtV.setVisibility(View.VISIBLE);
        numberOfDaysSpr.setVisibility(View.VISIBLE);
        monthSpr.setVisibility(View.INVISIBLE);
        monthTxtV.setVisibility(View.INVISIBLE);

        for(int i = 0; i < cbxDayList.size(); i++) {
            cbxDayList.get(i).setVisibility(View.VISIBLE);
        }
    }

    /**
     * This method makes it easier to see what happens when clicking on the month-button.
     */
    public void monthVisible() {
         numberOfDaysTxtV.setVisibility(View.INVISIBLE);
         numberOfDaysSpr.setVisibility(View.INVISIBLE);
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
    public void makeCalendarDaysList() {

         calendarDaysList.clear();

        //When the frequency is daily every day is added in the list
         if(createdHabit.getFREQUENCY() == Frequency.DAILY) {
             for (int i = 0; i < 7; i++) {
                 calendarDaysList.add(i+1);
             }
         }

        // When the frequency is weekly, the selected days are added to the list
         else if(createdHabit.getFREQUENCY() == Frequency.WEEKLY) {
             for (int i = 0; i < cbxDayList.size(); i++) {
                 if (cbxDayList.get(i).isChecked()) {
                     calendarDaysList.add(i + 1);
                 }
             }
        }

        //When the frequency is monthly, the date is added in the list
         else if(createdHabit.getFREQUENCY() == Frequency.MONTHLY) {
             calendarDaysList.add(Integer.valueOf(monthSpr.getSelectedItem().toString()));
         }
    }
/**
 * walks through the user filled fields to check if they're filled in
 * @return true if all is filled in
 * */
     @Override
     public boolean checkIfAllFieldsFilled() {
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
    * removes the error message
    * */
   public void takeAwayWrongMessage() {
         wrongMesTxtV.setVisibility(View.INVISIBLE);
         frequencyWrongImgV.setVisibility(View.INVISIBLE);
         nameWrongImgV.setVisibility(View.INVISIBLE);
         stateWrongImgV.setVisibility(View.INVISIBLE);
         weekWrongImgV.setVisibility(View.INVISIBLE);
     }

     public void endActivity(){
         finish();
    }



    public void setToGroupHabit(Habit habit){
        habit.setHabitTypeState(new GroupHabitType());
    }

     @Override
     public void recreateActivity() {
        recreate();
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
