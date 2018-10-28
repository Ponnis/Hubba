package com.example.nils_martin.hubba.ViewModel;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.nils_martin.hubba.Model.Achievement;
import com.example.nils_martin.hubba.Model.AchievementFactory;
import com.example.nils_martin.hubba.Model.AchievementType;
import com.example.nils_martin.hubba.Model.Group;
import com.example.nils_martin.hubba.Model.HubbaModel;
import com.example.nils_martin.hubba.Model.IFriend;
import com.example.nils_martin.hubba.Model.IHabit;

import com.example.nils_martin.hubba.Model.User;
import com.example.nils_martin.hubba.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CreateUserVM extends AppCompatActivity  {

    private HubbaModel model = HubbaModel.getInstance();
    private EditText newUsername;
    private EditText newEmail;
    private EditText newPassword;
    private ImageButton backButton;
    private Button createNewUser;

     protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_user);
        initViews();
        initOnClickListeners();
    }

    private void initViews() {
        newUsername = findViewById(R.id.txtNewUsername);
        newPassword = findViewById(R.id.txtNewPassword);
        newEmail = findViewById(R.id.txtNewEmail);
        createNewUser = findViewById(R.id.btnCreateNewUser);
        backButton = findViewById(R.id.backBtn4);
    }

    private void initOnClickListeners() {
         backButton.setOnClickListener(v -> onBackPressed());
        createNewUser.setOnClickListener((View v) -> {
            if(!newUsername.getText().toString().isEmpty() && !newEmail.getText().toString().isEmpty()
                    && !newPassword.getText().toString().isEmpty()){
                addUser();
            }
        });
    }
    /**
     * Creates new user based on the input parameters
     * */
    private void addUser(){
        User user = new User(newUsername.getText().toString(), newEmail.getText().toString(), newPassword.getText().toString(),
                 setAchivements());
        model.addUser(user);
        finish();
    }


    private ArrayList<Achievement> setAchivements(){
        ArrayList<Achievement> startAchivements = new ArrayList<>();

        setHabitAchivements(startAchivements);
        setStreakAchivements(startAchivements);
        return startAchivements;
    }
    private void setHabitAchivements(ArrayList<Achievement> startAchievements){
        startAchievements.add(AchievementFactory.getAchievement(AchievementType.NumOHabitsAchievement, "YOU'VE CREATED FIVE HABITS",5));
        startAchievements.add(AchievementFactory.getAchievement(AchievementType.NumOHabitsAchievement, "YOU'VE CREATED TEN HABITS",10));
        startAchievements.add(AchievementFactory.getAchievement(AchievementType.NumOHabitsAchievement, "YOU'VE CREATED FIFTEEN HABITS",15));

    }
    private void setStreakAchivements(ArrayList<Achievement> startAchievements){
        startAchievements.add(AchievementFactory.getAchievement(AchievementType.StreakAchievement, "YOU GOTTEN A STREAK OF FIVE",5));
        startAchievements.add(AchievementFactory.getAchievement(AchievementType.StreakAchievement, "YOU GOTTEN A STREAK OF TEN",10));
        startAchievements.add(AchievementFactory.getAchievement(AchievementType.StreakAchievement, "YOU GOTTEN A STREAK OF FIFTEEN",15));
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
            SharedPreferences sharedPreferences1 = getSharedPreferences(user.getUserName() + "groups", MODE_PRIVATE);
            SharedPreferences.Editor editor1 = sharedPreferences1.edit();

            editor1.putString("groupslist", groupsToJson(user));
            editor1.apply();
        }

        for (User user: model.getUsers()){
            for (Group group: user.getGroups()){
                SharedPreferences sharedPreferences1 = getSharedPreferences(user.getUserName() + group.getGroupName() + "userInGroups", MODE_PRIVATE);
                SharedPreferences.Editor editor1 = sharedPreferences1.edit();

                editor1.putString("groupFriendslist", groupFriendsToJson(group));
                editor1.apply();
            }
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
            jsonHabits.put("state", habit.getSTATE().toString());
            jsonHabits.put("frequency", habit.getFREQUENCY());
            jsonHabits.put("daysToDoSize", habit.getDaysToDoSize());
            jsonHabits.put("previewsDayDone", habit.getPreviewsDayDone());
            jsonHabits.put("getTodayDate", habit.getTodayDate());

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

    private String groupsToJson(User user) throws JSONException{
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (Group group: model.getUser(user.getUserName()).getGroups()){
            JSONObject jsonGroup = new JSONObject();
            jsonGroup.put("groupName", group.getGroupName());

            JSONArray usersInGroup = new JSONArray();
            jsonGroup.put("usersInGroup", usersInGroup);

            jsonGroup.put("theGroupHabit", group.getHabit());
            jsonArray.put(jsonGroup);
        }
        return jsonObject.put("group", jsonArray).toString();
    }

    private String groupFriendsToJson(Group group) throws JSONException{
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (IFriend iFriend: group.getUsersInGroup()){
            JSONObject jsonGroupFriends = new JSONObject();
            jsonGroupFriends.put("GroupFriendUserName", iFriend.getUserName());
            jsonArray.put(jsonGroupFriends);
        }
        return jsonObject.put("groupFriend", jsonArray).toString();
    }

}
