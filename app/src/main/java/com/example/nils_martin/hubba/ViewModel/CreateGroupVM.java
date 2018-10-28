package com.example.nils_martin.hubba.ViewModel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.nils_martin.hubba.Model.Achievement;
import com.example.nils_martin.hubba.Model.IFriend;
import com.example.nils_martin.hubba.Model.Group;
import com.example.nils_martin.hubba.Model.HubbaModel;
import com.example.nils_martin.hubba.Model.IHabit;
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
 */
public class CreateGroupVM extends AppCompatActivity implements ThemableObserver {

    private HubbaModel model = HubbaModel.getInstance();
    private User user;
    private List friends;
    private List<IFriend> groupMembers = new ArrayList<>();
    private String groupName;
    private IHabit habit;
    private String friendNames;
    private List<String> friendsAsString = new ArrayList<>();
    private Button createNewGroupHabit;
    private ThemeHandler themeHandler = new ThemeHandler();

    protected void onCreate(Bundle savedInstanceState) {
        setTheme(themeHandler.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_group);
        getUserToCurrent();
        getUserFriends();
        themeHandler.addThemeListener(this);
        groupName = (findViewById(R.id.txtGroupName)).toString();
        System.out.println(groupName);
        friendNames = String.valueOf((EditText) findViewById(R.id.txtGroupMembers));
        createNewGroupHabit = (Button) findViewById(R.id.btnCreateNewGroup);
        // createNewGroupHabit.setOnClickListener();
        createNewGroupHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFriendsAsString();
                Intent intent = new Intent(CreateGroupVM.this, CreateGroupHabitVM.class);
                startActivity(intent);
            }
        });
    }

    /*@Override
    protected void onResume() {
        super.onResume();
    }*/

    private void getUserToCurrent() {
        user = model.getCurrentUser();
    }


    //Kollar så att usern finns med i vänlistan

    /**
     * Checks that the users written in the textfield are actual friends of the user.
     */
    private List<IFriend> checkUserNameToFriend() {
        for (int i = 0; i < friends.size(); i++) {
            for (String string : friendsAsString) {
                IFriend tempFriend = (IFriend) friends.get(i);
                if (tempFriend.getUserName().equals(string)) {
                    groupMembers.add(user);
                }

            }
        }
        return groupMembers;
    }

    private void setFriendsAsString() {
        if (friendNames == null) {
        } else if (!(friendNames.contains(","))) {
            friendsAsString.add(friendNames);
        } else {
            loopFriendsIntoList();
        }
    }

    /**
     * Loops the string of friends into a list
     */
    private void loopFriendsIntoList() {
        String[] tempFriends = friendNames.split(",");
        for (int i = 0; i < tempFriends.length; i++) {
            friendsAsString.add(tempFriends[i]);

        }
    }

    /**
     * Gets the users friends
     */
    private void getUserFriends() {
        if (user.getFriends().isEmpty()) {

        } else {
            friends = user.getFriends();
        }
    }

    /**
     * Sets the new groups habit to the one you created
     */
    private void setCreatedHabit(IHabit habit) {
        if (habit.getHabitTypeState().toString().equals("GroupHabit")) {
            this.habit = habit;
        }
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

    /*@Override
    public void recreateActivity() {
        int size = model.getCurrentUser().getHabits().size();
        setCreatedHabit(model.getCurrentUser().getHabits().get(size));
        recreate();
    }*/

    //TODO create method to add checked friends to group list

    /**
     * Creates a new group based on you input parameters, also adds the group to the model.
     */
    private void createNewGroup() {
        checkUserNameToFriend();
        Group group = new Group(groupName, groupMembers, habit);
        user.getGroups().add(group);
    }

    @Override
    protected void onStop() {
        int size = user.getHabits().size();
        if (size!=0){
        setCreatedHabit(user.getHabits().get(size-1));}
        super.onStop();
        createNewGroup();
        finish();
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

    private String achievementsToJson (User user) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (Achievement achievement: model.getUser(user.getUserName()).getAchievements()){
            JSONObject jsonAchievement = new JSONObject();
            jsonAchievement.put("title", achievement.getTitle());
            jsonAchievement.put("isAcheived", achievement.getAchieved());
            jsonArray.put(jsonAchievement);
        }
        return jsonObject.put("achievement", jsonArray).toString();
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
