package com.example.nils_martin.hubba.ViewModel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.nils_martin.hubba.Model.Group;
import com.example.nils_martin.hubba.Model.Achievement;
import com.example.nils_martin.hubba.Model.IFriend;
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
 * @author Li Rönning and Camilla Söderlund
 */
public class MenuFriendsVM extends AppCompatActivity implements ThemableObserver {

    private HubbaModel model = HubbaModel.getInstance();
    private ThemeHandler themeHandler = new ThemeHandler();

    private List<IFriend> friends = new ArrayList<>();
    private ArrayList<String> friendStrings = new ArrayList<>();
    private ListView yourFriendsListView;
    private ArrayAdapter<String> yourFriendsAdapter;
    private Button addFriendsButton;
    private ImageButton backButton;
    private IFriend openFriend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(themeHandler.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_friends);
        themeHandler.addThemeListener(this);
        init();
    }

    /**
     * Calls on other functions that initialize ListViews, list OnClicks etc.
     */
    private void init(){
        initFindByView();
        initOnClickListeners();
        updateFriendsListView();
        listViewOnClick();
    }

    /**
     * Initialize all view attributes
     */
    private void initFindByView(){
        yourFriendsListView = (ListView) findViewById(R.id.yourFriendsListView);
        addFriendsButton = (Button) findViewById(R.id.addFriendBtn);
        backButton = findViewById(R.id.backBtn6);
    }

    private void initOnClickListeners(){
        addFriendOnClick();
        backButtonOnClick();
    }

    private void backButtonOnClick() {
        backButton.setOnClickListener(v -> onBackPressed());
    }

    private void addFriendOnClick(){
        addFriendsButton.setOnClickListener(v -> {
            Intent intent = new Intent(MenuFriendsVM.this, AddFriendVM.class);
            startActivity(intent);
        });
    }

    private void getFriendsList() {
        if (model.getCurrentUser().getFriends() != null) {
            friends = model.getCurrentUser().getFriends();
        }
    }

    /**
     * Calls methods that update the list and the ListView in the interface.
     */
    private void updateFriendsListView(){
        getFriendsList();
        fillFriendStringsList();
        fillFriendListView();
    }

    /**
     * First clears the list friendStrings and then updates it with the
     * names of users in friends.
     */
    private void fillFriendStringsList(){
        friendStrings.clear();
        for(IFriend friend : friends){
            friendStrings.add(friend.getUserName());
        }
    }

    /**
     * Fills the ListView with strings from friendStrings.
     */
    private void fillFriendListView(){
        yourFriendsAdapter = new ArrayAdapter<String>(
                this,
                R.layout.menu_list_item,
                friendStrings );
        yourFriendsListView.setAdapter(yourFriendsAdapter);
    }

    /**
     * Gets the string from the item that is clicked and then finds a friend with a username
     * that matches the item. This friend is then opened on a new page.
     */
    private void listViewOnClick () {
        yourFriendsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                findFriend(yourFriendsListView.getItemAtPosition(position).toString());
                Intent intent = new Intent(MenuFriendsVM.this, RemoveFriendVM.class);
                intent.putExtra("FRIEND", openFriend.getUserName());
                startActivity(intent);
            }
        });
    }

    /**
     * Finds the friend corresponding to the string in the friends list
     * @param string is the string of a friends username
     */
    private void findFriend(String string) {
        for(IFriend friend: friends) {
            if(friend.getUserName().equals(string)) {
                setOpenFriend(friend);
            }
        }
    }

    private  void setOpenFriend(IFriend openFriend) {
        this.openFriend = openFriend;
    }

    @Override
    protected void onResume() {
        setTheme(themeHandler.getTheme());
        super.onResume();
        themeHandler.addThemeListener(this);
        updateFriendsListView();
    }

    @Override
    public void recreateActivity() {
        recreate();
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
