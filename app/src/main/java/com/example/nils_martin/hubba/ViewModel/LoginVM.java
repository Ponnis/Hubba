package com.example.nils_martin.hubba.ViewModel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.nils_martin.hubba.Model.Acheievement;
import com.example.nils_martin.hubba.Model.AchievementFactory;
import com.example.nils_martin.hubba.Model.AchievementInstanceCreator;
import com.example.nils_martin.hubba.Model.AchievementType;
import com.example.nils_martin.hubba.Model.Frequency;
import com.example.nils_martin.hubba.Model.Group;
import com.example.nils_martin.hubba.Model.GroupHabitType;
import com.example.nils_martin.hubba.Model.Habit;
import com.example.nils_martin.hubba.Model.HubbaModel;
import com.example.nils_martin.hubba.Model.IFriend;
import com.example.nils_martin.hubba.Model.IHabit;
import com.example.nils_martin.hubba.Model.State;
import com.example.nils_martin.hubba.Model.Themes;
import com.example.nils_martin.hubba.Model.User;
import com.example.nils_martin.hubba.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class LoginVM extends AppCompatActivity {
    private HubbaModel model = HubbaModel.getInstance();
    private EditText Username;
    private EditText Password;
    private Button NewUser;
    private Button Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        User user1 = new User("Alex", "Alex@gmail.com", "losenord", new ArrayList<>());
        User friend = new User("Semlan", "semlan@gmail.com","ghj", new ArrayList<>());
        model.getUsers().add(user1);
        model.getUsers().add(friend);
        model.getUser("Alex").setAchievements(setAchivements());
        model.getUser("Semlan").setAchievements(setAchivements());
        model.setCurrentUser(model.getUser("Alex"));

        User addFriend = model.getUser("Semlan");
        model.getUser("Alex").addFriend(addFriend);
        model.getUser("Alex").addFriend(addFriend);

        IHabit habit = new Habit("I DO STUFF");
        habit.setHabitTypeState(new GroupHabitType());
        habit.setFREQUENCY(Frequency.DAILY);
        habit.setSTATE(State.MORNING);

        Group group = new Group("TestGroup", model.getUser("Alex").getFriends(), habit);

        model.getUser("Alex").getGroups().add(group);



        try {
            save();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        model.getUsers().clear();


        try {
            load();
        } catch (JSONException e) {
            initFirstUse();
            e.printStackTrace();

        } catch (NullPointerException v){
            initFirstUse();
            v.printStackTrace();
        }





        for (int i = 0, usersSize = model.getUsers().size(); i < usersSize; i++) {
            User user = model.getUsers().get(i);
            if (user.getUserName().equals("admin")) {
                break;
            }
            if (i == usersSize - 1) {
                model.getUsers().add(new User("admin", "testemail@gmail.com", "1234", new ArrayList<>()));
                model.getUser("admin").setAchievements(setAchivements());
            }
        }

        Username = (EditText) findViewById(R.id.txtUsername);
        Password = (EditText) findViewById(R.id.txtPassword);
        NewUser = (Button) findViewById(R.id.btnNewUser);
        Login = (Button) findViewById(R.id.btnLogin);

        NewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newUserButton();
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLoginAcceptance();
            }
        });

    }

    private void initList(User user) {
        user.initThemableObserver();
        user.initHabit();
        user.initFriends();
        if (user.getHabits().size() != 0) {
            for (IHabit habit : user.getHabits()) {
                habit.initDaysToDoList();
            }
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

    private void newUserButton() {
        Intent intent = new Intent(LoginVM.this, CreateUserVM.class);
        startActivity(intent);
    }

    private void checkLoginAcceptance() {
        for (User user : model.getUsers()) {
            if (user.getUserName().equals(Username.getText().toString())) {
                if (user.getPassword().equals(Password.getText().toString())) {
                    Intent intent = new Intent(LoginVM.this, com.example.nils_martin.hubba.ViewModel.MainActivityVM.class);
                    startActivity(intent);
                    model.setCurrentUser(user);
                    break;
                }
            }
        }
    }

    public EditText getUsername() {
        return Username;
    }

    private void initFirstUse(){
        model.setUsers(new ArrayList<>());
        model.getUsers().add(new User("admin", "testemail@gmail.com", "1234", new ArrayList<>()));
        model.getUser("admin").setAchievements(setAchivements());
    }

    public void load() throws JSONException, NullPointerException {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        String json = sharedPreferences.getString("userlist", null);
        Gson gson = new GsonBuilder().create();
        Type typeUser = new TypeToken<ArrayList<User>>() {
        }.getType();
        JSONObject jsonResponse = new JSONObject(json);
        model.setUsers(gson.fromJson(jsonResponse.getString("user"), typeUser));

        Type typeHabit = new TypeToken<ArrayList<Habit>>(){}.getType();
        Type typeAchievement = new TypeToken<ArrayList<Acheievement>>(){}.getType();

        JSONArray jsonTheme = jsonResponse.getJSONArray("user");
        for (int a = 0; a < jsonTheme.length(); a++) {
            String theme = jsonTheme.getJSONObject(a).get("theme").toString();
            initList(model.getUser(jsonTheme.getJSONObject(a).get("userName").toString()));
            if ("STANDARD".equals(theme)) {
                model.getUser(jsonTheme.getJSONObject(a).get("userName").toString()).setTheme(Themes.STANDARD);

            } else if ("PINKFLUFFY".equals(theme)) {
                model.getUser(jsonTheme.getJSONObject(a).get("userName").toString()).setTheme(Themes.PINKFLUFFY);

            } else if ("ELITE".equals(theme)) {
                model.getUser(jsonTheme.getJSONObject(a).get("userName").toString()).setTheme(Themes.ELITE);
            }
        }


        for (User user : model.getUsers()) {
            SharedPreferences sharedPreferences1 = getSharedPreferences(user.getUserName() + "habits", MODE_PRIVATE);
            String jsonHabit = sharedPreferences1.getString("habitslist", null);
            Gson gsonHabit = new GsonBuilder().create();
            JSONObject jsonResponseHabit = new JSONObject(jsonHabit);
            user.setHabits(gsonHabit.fromJson(jsonResponseHabit.getString("habit"), typeHabit));

            JSONArray jsonArray = jsonResponseHabit.getJSONArray("habit");
            for (int i = 0; i < jsonArray.length(); i++) {
                String string = jsonArray.getJSONObject(i).get("state").toString();
                if ("MORNING".equals(string)) {
                    user.getHabit(jsonArray.getJSONObject(i).get("title").toString()).setSTATE(State.MORNING);

                } else if ("MIDDAY".equals(string)) {
                    user.getHabit(jsonArray.getJSONObject(i).get("title").toString()).setSTATE(State.MIDDAY);

                } else if ("EVENING".equals(string)) {
                    user.getHabit(jsonArray.getJSONObject(i).get("title").toString()).setSTATE(State.EVENING);

                } else if ("NIGHT".equals(string)) {
                    user.getHabit(jsonArray.getJSONObject(i).get("title").toString()).setSTATE(State.NIGHT);

                } else if ("DONE".equals(string)) {
                    user.getHabit(jsonArray.getJSONObject(i).get("title").toString()).setSTATE(State.DONE);

                }
            }

            for (int i = 0; i < jsonArray.length(); i++) {
                String string = jsonArray.getJSONObject(i).get("frequency").toString();
                if ("DAILY".equals(string)) {
                    user.getHabit(jsonArray.getJSONObject(i).get("title").toString()).setFREQUENCY(Frequency.DAILY);

                } else if ("WEEKLY".equals(string)) {
                    user.getHabit(jsonArray.getJSONObject(i).get("title").toString()).setFREQUENCY(Frequency.WEEKLY);

                } else if ("MONTHLY".equals(string)) {
                    user.getHabit(jsonArray.getJSONObject(i).get("title").toString()).setFREQUENCY(Frequency.MONTHLY);

                }
            }
        }

        for (User user : model.getUsers()) {
            for (IHabit habit : user.getHabits()) {
                SharedPreferences sharedPreferences1 = getSharedPreferences(user.getUserName() + habit.getTitle() + "daysToInts", MODE_PRIVATE);
                String jsonDaysToDo = sharedPreferences1.getString("dayToIntList", null);

                JSONObject jsonResponseDaysToDo = new JSONObject(jsonDaysToDo);
                JSONArray jsonArray = new JSONArray();
                jsonArray.put(jsonResponseDaysToDo);
                habit.initDaysToDoList();
                for (int i = 0; i < jsonArray.length(); i++) {
                    char[] c = jsonArray.get(i).toString().toCharArray();
                    for (int j = 0; j < c.length; j++) {
                        String temp = Character.toString(c[j]);
                        if (Character.isDigit(c[j])) {
                            int a = Integer.parseInt(temp);
                            if (a > 0 && a < 32) {
                                habit.getDaysToDo().add(a);
                            }
                        }
                    }
                }
            }
        }

        for (User user : model.getUsers()) {
            SharedPreferences sharedPreferences1 = getSharedPreferences(user.getUserName() + "friends", MODE_PRIVATE);
            String jsonFriend = sharedPreferences1.getString("friendslist", null);

            char [] charArray = jsonFriend.toCharArray();
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < charArray.length; i++){
                if (Character.isLetter(charArray[i])){
                    stringBuilder.append(charArray[i]);
                    if (stringBuilder.toString().equals("friend")){
                        stringBuilder.setLength(0);
                        i = i + 4;
                    }

                    else if (stringBuilder.toString().equals("username")){
                        stringBuilder.setLength(0);
                        i = i + 4;
                        while(Character.isLetter(charArray[i])){
                            stringBuilder.append(charArray[i]);
                            i++;
                        }
                        user.getFriends().add(model.getUser(stringBuilder.toString()));
                        stringBuilder.setLength(0);
                    }
                }
            }
        }

        for(User user: model.getUsers()){
            SharedPreferences sharedPreferences1 = getSharedPreferences(user.getUserName() + "achievements", MODE_PRIVATE);
            String jsonAchievement = sharedPreferences1.getString("achievementslist",null);
            Gson gsonAchievement = new GsonBuilder().registerTypeAdapter(Acheievement.class, new AchievementInstanceCreator()).create();
            JSONObject jsonResponseAchievement = new JSONObject(jsonAchievement);
            user.setAchievements(gsonAchievement.fromJson(jsonResponseAchievement.getString("achievement"), typeAchievement));
        }


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

                SharedPreferences sharedPreferences2 = getSharedPreferences(user.getUserName() + group.getHabit().getTitle() + "groupHabit", MODE_PRIVATE);
                SharedPreferences.Editor editor2 = sharedPreferences2.edit();

                editor2.putString("groupHabit", groupHabitToJson(group));
                editor2.apply();

                SharedPreferences sharedPreferences3 = getSharedPreferences(user.getUserName() + group.getHabit().getTitle() + "groupHabitDayToDo", MODE_PRIVATE);
                SharedPreferences.Editor editor3 = sharedPreferences3.edit();

                System.out.println(daysToDoJson(group.getHabit()));
                editor3.putString("groupHabitDayToDo", daysToDoJson(group.getHabit()));
                editor3.apply();
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
        System.out.println("Skirver ut det sparade jsonObjectet: " + jsonObject.put("friend", jsonArray).toString());
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

    private String groupsToJson(User user) throws JSONException{
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (Group group: model.getUser(user.getUserName()).getGroups()){
            JSONObject jsonGroup = new JSONObject();
            jsonGroup.put("groupName", group.getGroupName());

            JSONArray usersInGroup = new JSONArray();
            jsonGroup.put("usersInGroup", usersInGroup);

            jsonGroup.put("groupHabit", group.getHabit());
            jsonArray.put(jsonGroup);
        }
        System.out.println("Groups " + user.getUserName() + " To JSON: " + jsonObject.put("group", jsonArray).toString());
        return jsonObject.put("group", jsonArray).toString();
    }

    private String groupFriendsToJson(Group group) throws JSONException{
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (IFriend iFriend: group.getUsersInGroup()){
            JSONObject jsonGroupFriends = new JSONObject();
            jsonGroupFriends.put("groupFriend", iFriend.getUserName());
            jsonArray.put(jsonGroupFriends);
        }
        System.out.println("Group Friends " + group.getGroupName() + " to JSOn: " + jsonObject.put("groupFriend", jsonArray).toString());
        return jsonObject.put("groupFriend", jsonArray).toString();
    }

    private String groupHabitToJson(Group group) throws JSONException{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("title", group.getHabit().getTitle());
        jsonObject.put("getGroupMembersCount", group.getHabit().getGroupMembersDoneCount());
        jsonObject.put("streak", group.getHabit().getStreak());
        jsonObject.put("isDone", group.getHabit().getIsDone());
        jsonObject.put("reminderOn", group.getHabit().isReminderOn());
        jsonObject.put("habitTypeState", group.getHabit().getHabitTypeState().toString());
        jsonObject.put("state", group.getHabit().getSTATE().toString());
        jsonObject.put("frequency", group.getHabit().getFREQUENCY());
        jsonObject.put("daysToDoSize", group.getHabit().getDaysToDoSize());

        JSONArray daysList = new JSONArray();
        jsonObject.put("daysInteger", daysList);

        System.out.println("Group Habit " + group.getGroupName() + " To JSON: " + jsonObject.toString());
        return jsonObject.toString();

    }

    private ArrayList<Acheievement> setAchivements(){
        ArrayList<Acheievement> startAchivements = new ArrayList<>();

        setHabitAchivements(startAchivements);
        setStreakAchivements(startAchivements);
        return startAchivements;
    }

    private void setHabitAchivements(ArrayList<Acheievement> startAcheievements){
        startAcheievements.add(AchievementFactory.getAchievement(AchievementType.NumOHabitsAchievement, "YOU'VE CREATED FIVE HABITS",5));
        startAcheievements.add(AchievementFactory.getAchievement(AchievementType.NumOHabitsAchievement, "YOU'VE CREATED TEN HABITS",10));
        startAcheievements.add(AchievementFactory.getAchievement(AchievementType.NumOHabitsAchievement, "YOU'VE CREATED FIFTEEN HABITS",15));

    }
    private void setStreakAchivements(ArrayList<Acheievement> startAcheievements){
        startAcheievements.add(AchievementFactory.getAchievement(AchievementType.StreakAchievement, "YOU GOTTEN A STREAK OF FIVE",5));
        startAcheievements.add(AchievementFactory.getAchievement(AchievementType.StreakAchievement, "YOU GOTTEN A STREAK OF TEN",10));
        startAcheievements.add(AchievementFactory.getAchievement(AchievementType.StreakAchievement, "YOU GOTTEN A STREAK OF FIFTEEN",15));
    }
}
