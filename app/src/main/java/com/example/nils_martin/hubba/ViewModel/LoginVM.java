package com.example.nils_martin.hubba.ViewModel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.nils_martin.hubba.Model.Achievement;
import com.example.nils_martin.hubba.Model.AchievementFactory;
import com.example.nils_martin.hubba.Model.AchievementType;
import com.example.nils_martin.hubba.Model.Frequency;
import com.example.nils_martin.hubba.Model.Habit;
import com.example.nils_martin.hubba.Model.HubbaModel;
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


        /*User user1 = new User("Alex", "Alex@gmail.com", "losenord","");
        model.getUsers().add(user1);
        user1.setAchievements(setAchivements());
        System.out.println(user1.getAchievements().size());

        try {
            save();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        model.getUsers().clear();*/

        try {
            load();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayList<User> users = model.getUsers();

        for (int i = 0, usersSize = users.size(); i < usersSize; i++) {
            User user = users.get(i);
            if (user.getUserName().equals("admin")) {
                break;
            }
            if (i == usersSize - 1){
                model.getUsers().add(new User ("admin", "testemail@gmail.com", "1234",""));
                System.out.println("Skapar ny admin");
                System.out.println(model.getUser("admin").getTheme());
            }
        }

        Username = (EditText)findViewById(R.id.txtUsername);
        Password = (EditText)findViewById(R.id.txtPassword);
        NewUser = (Button)findViewById(R.id.btnNewUser);
        Login = (Button)findViewById(R.id.btnLogin);

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
        if (user.getHabits().size() != 0){
            for (IHabit habit: user.getHabits()){
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

    private void newUserButton (){
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
                }
            }
        }
    }

    public EditText getUsername(){ return Username; }

    public void load () throws JSONException {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        String json = sharedPreferences.getString("userlist",null);
        Gson gson = new GsonBuilder().create();
        Type typeUser = new TypeToken<ArrayList<User>>(){}.getType();
        JSONObject jsonResponse = new JSONObject(json);
        model.setUsers(gson.fromJson(jsonResponse.getString("user"), typeUser));

        Type typeHabit = new TypeToken<ArrayList<Habit>>(){}.getType();
        Type typeFriend = new TypeToken<ArrayList<User>>(){}.getType();
        //Type typeAchievement = new TypeToken<ArrayList<Achievement>>(){}.getType();
        Type typeDaysToDo = new TypeToken<ArrayList<Integer>>(){}.getType();

        JSONArray jsonTheme = jsonResponse.getJSONArray("user");
        for (int a = 0; a < jsonTheme.length(); a++){
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


        for(User user: model.getUsers()){
            SharedPreferences sharedPreferences1 = getSharedPreferences(user.getUserName() + "habits", MODE_PRIVATE);
            String jsonHabit = sharedPreferences1.getString("habitslist",null);
            Gson gsonHabit = new GsonBuilder().create();
            JSONObject jsonResponseHabit = new JSONObject(jsonHabit);
            user.setHabits(gsonHabit.fromJson(jsonResponseHabit.getString("habit"), typeHabit));

            System.out.println("Jämför med Hur det ser ut i habit " + jsonHabit);

            JSONArray jsonArray = jsonResponseHabit.getJSONArray("habit");
            for (int i = 0; i < jsonArray.length(); i++){
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

            for (int i = 0; i < jsonArray.length(); i++){
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

        for (User user: model.getUsers()){
            for (IHabit habit: user.getHabits()){
                System.out.println(habit.getDaysToDoSize());
                SharedPreferences sharedPreferences1 = getSharedPreferences(user.getUserName() + habit.getTitle() + "daysToInts", MODE_PRIVATE);
                String jsonDaysToDo = sharedPreferences1.getString("dayToIntList", null);

                System.out.println(jsonDaysToDo + " Kollar om felet är här");

                JSONObject jsonResponseDaysToDo = new JSONObject(jsonDaysToDo);
                JSONArray jsonArray = new JSONArray();
                jsonArray.put(jsonResponseDaysToDo);
                System.out.println("jsonArray: " + jsonArray);
                habit.initDaysToDoList();
                for(int i = 0; i < jsonArray.length(); i++){
                    char [] c = jsonArray.get(i).toString().toCharArray();
                    for (int j = 0; j < c.length; j++){
                        String temp = Character.toString(c[j]);
                        if(Character.isDigit(c[j])){
                            int a = Integer.parseInt(temp);
                            if (a > 0 && a < 32){
                                habit.getDaysToDo().add(a);
                            }
                        }
                    }
                }

                System.out.println(habit.getDaysToDo().size());
            }
        }

        for(User user: model.getUsers()){
            SharedPreferences sharedPreferences1 = getSharedPreferences(user.getUserName() + "friends", MODE_PRIVATE);
            String jsonFriend = sharedPreferences1.getString("friendslist",null);
            Gson gsonFriend = new GsonBuilder().create();
            JSONObject jsonResponseFriend = new JSONObject(jsonFriend);
            user.setFriends(gsonFriend.fromJson(jsonResponseFriend.getString("friend"), typeFriend));
        }

        /*for(User user: model.getUsers()){
            SharedPreferences sharedPreferences1 = getSharedPreferences(user.getUserName() + "achievements", MODE_PRIVATE);
            String jsonAchievement = sharedPreferences1.getString("achievementslist",null);
            Gson gsonAchievement = new GsonBuilder().create();
            JSONObject jsonResponseAchievement = new JSONObject(jsonAchievement);
            user.setAchievements(gsonAchievement.fromJson(jsonResponseAchievement.getString("achievement"), typeAchievement));
        }*/


    }

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
            SharedPreferences sharedPreferences1 = getSharedPreferences(user.getUserName() + "friends", MODE_PRIVATE);
            SharedPreferences.Editor editor1 = sharedPreferences1.edit();

            editor1.putString("friendslist", friendsToJson(user));
            editor1.apply();
        }


        for (User user: model.getUsers()){
            for (IHabit habit: user.getHabits()){
                SharedPreferences sharedPreferences1 = getSharedPreferences(user.getUserName() + habit.getTitle() + "daysToInts", MODE_PRIVATE);
                SharedPreferences.Editor editor1 = sharedPreferences1.edit();

                editor1.putString("dayToIntList", daysToDoJson(habit));
                editor1.apply();
            }
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
        return jsonObject.put("daysToInt", jsonArray).toString();
    }

    private String friendsToJson (User user) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (User user1: model.getUser(user.getUserName()).getFriends()){
            JSONObject jsonFriends = new JSONObject();
            jsonFriends.put("username", user1.getUserName());
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


    private ArrayList<Achievement> setAchivements(){
        ArrayList<Achievement> startAchievement = new ArrayList<>();
        setHabitAchivements(startAchievement);
        setStreakAchivements(startAchievement);
        return startAchievement;
    }

    private void setHabitAchivements(ArrayList<Achievement> achievements){
        achievements.add(AchievementFactory.getAchievement(AchievementType.NumOHabitsAchievement,"5Habtis",5));
        achievements.add(AchievementFactory.getAchievement(AchievementType.NumOHabitsAchievement,"10Habtis", 10));
        achievements.add(AchievementFactory.getAchievement(AchievementType.NumOHabitsAchievement,"15Habtis",15));
    }

    private void setStreakAchivements(ArrayList<Achievement> achivements){
        achivements.add(AchievementFactory.getAchievement(AchievementType.StreakAchievement,"5Streak",5));
        achivements.add(AchievementFactory.getAchievement(AchievementType.StreakAchievement,"10Streak",10));
        achivements.add(AchievementFactory.getAchievement(AchievementType.StreakAchievement,"15Streak",15));
    }

}
