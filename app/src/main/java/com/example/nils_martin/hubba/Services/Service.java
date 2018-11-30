package com.example.nils_martin.hubba.Services;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.example.nils_martin.hubba.Model.Frequency;
import com.example.nils_martin.hubba.Model.Group;
import com.example.nils_martin.hubba.Model.Habit;
import com.example.nils_martin.hubba.Model.HubbaModel;
import com.example.nils_martin.hubba.Model.IFriend;
import com.example.nils_martin.hubba.Model.IHabit;
import com.example.nils_martin.hubba.Model.IUser;
import com.example.nils_martin.hubba.Model.State;
import com.example.nils_martin.hubba.Model.Themes;
import com.example.nils_martin.hubba.Model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class Service extends AppCompatActivity implements IService{

    private static Service service = null;
    HubbaModel model = HubbaModel.getInstance();

    public Service(){

    }

    public static Service getInstance(){
        if(service == null){
            service = new Service();
            return service;
        } else {
            return service;
        }
    }

    public void save(Context ctx) throws JSONException {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences("shared preferences", MODE_PRIVATE);


        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (IUser user : model.getUsers()) {
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

        //SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("userlist", jsonObject.toString());
        editor.apply();

        for (IUser user : model.getUsers()) {
            // SharedPreferences sharedPreferences1 = getSharedPreferences(user.getUserName() + "habits", MODE_PRIVATE);
            SharedPreferences sharedPreferences1 = ctx.getSharedPreferences(user.getUserName() + "habits", MODE_PRIVATE);
            SharedPreferences.Editor editor1 = sharedPreferences1.edit();

            editor1.putString("habitslist", habitsToJson((User) user));
            editor1.apply();
        }

        for (IUser user : model.getUsers()) {
            SharedPreferences sharedPreferences1 = ctx.getSharedPreferences(user.getUserName() + "friends", MODE_PRIVATE);
            SharedPreferences.Editor editor1 = sharedPreferences1.edit();

            editor1.putString("friendslist", friendsToJson((User) user));
            editor1.apply();
        }


        for (IUser user : model.getUsers()) {
            for (IHabit habit : user.getHabits()) {
                SharedPreferences sharedPreferences1 = ctx.getSharedPreferences(user.getUserName() + habit.getTitle() + "daysToInts", MODE_PRIVATE);
                SharedPreferences.Editor editor1 = sharedPreferences1.edit();

                editor1.putString("dayToIntList", daysToDoJson(habit));
                editor1.apply();
            }
        }

        for (IUser user: model.getUsers()){
            SharedPreferences sharedPreferences1 = ctx.getSharedPreferences(user.getUserName() + "groups", MODE_PRIVATE);
            SharedPreferences.Editor editor1 = sharedPreferences1.edit();

            editor1.putString("groupslist", groupsToJson((User) user));
            editor1.apply();
        }

        for (IUser user: model.getUsers()){
            for (Group group: user.getGroups()){
                SharedPreferences sharedPreferences1 = ctx.getSharedPreferences(user.getUserName() + group.getGroupName() + "userInGroups", MODE_PRIVATE);
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

    public void load(Context ctx) throws JSONException, NullPointerException {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences("shared preferences", MODE_PRIVATE);

        String json = sharedPreferences.getString("userlist", null);
        Gson gson = new GsonBuilder().create();
        Type typeUser = new TypeToken<ArrayList<User>>() {
        }.getType();
        JSONObject jsonResponse = new JSONObject(json);
        model.setUsers(gson.fromJson(jsonResponse.getString("user"), typeUser));

        Type typeHabit = new TypeToken<ArrayList<Habit>>() {
        }.getType();
        Type typeGroup = new TypeToken<List<Group>>(){}.getType();

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


        for (IUser user : model.getUsers()) {
            SharedPreferences sharedPreferences1 = ctx.getSharedPreferences(user.getUserName() + "habits", MODE_PRIVATE);
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

        for (IUser user : model.getUsers()) {
            for (IHabit habit : user.getHabits()) {
                SharedPreferences sharedPreferences1 = ctx.getSharedPreferences(user.getUserName() + habit.getTitle() + "daysToInts", MODE_PRIVATE);
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

        for (IUser user : model.getUsers()) {
            SharedPreferences sharedPreferences1 = ctx.getSharedPreferences(user.getUserName() + "friends", MODE_PRIVATE);
            String jsonFriend = sharedPreferences1.getString("friendslist", null);

            extractString(jsonFriend, "friend", "username", user.getFriends());
        }

        for (IUser user: model.getUsers()) {
            SharedPreferences sharedPreferences1 = ctx.getSharedPreferences(user.getUserName() + "groups", MODE_PRIVATE);
            String jsonGroup = sharedPreferences1.getString("groupslist", null);
            Gson gsonGroup = new GsonBuilder().create();
            JSONObject jsonResponseGroup = new JSONObject(jsonGroup);
            user.setGroup(gsonGroup.fromJson(jsonResponseGroup.getString("group"), typeGroup));
            for (Group group: user.getGroups()){
                SharedPreferences sharedPreferences2 = ctx.getSharedPreferences(user.getUserName() + group.getGroupName() + "userInGroups", MODE_PRIVATE);
                String jsonGroupFriends = sharedPreferences2.getString("groupFriendslist", null);
                extractString(jsonGroupFriends,"groupFriend", "GroupFriendUserName", group.getUsersInGroup());
            }
        }
    }

    private void extractString(String source, String listName, String target, List<IFriend> list){
        char [] charArray = source.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < charArray.length; i++){
            if (Character.isLetter(charArray[i])){
                stringBuilder.append(charArray[i]);

                if (stringBuilder.toString().equals(listName)){
                    stringBuilder.setLength(0);
                }

                else if (stringBuilder.toString().equals(target)){
                    stringBuilder.setLength(0);
                    i = i + 3;
                    if (Character.isLetter(charArray[i])){
                        while(Character.isLetter(charArray[i])){
                            stringBuilder.append(charArray[i]);
                            i++;
                        }
                    }
                    else{
                        i++;
                        while(Character.isLetter(charArray[i])){
                            stringBuilder.append(charArray[i]);
                            i++;
                        }
                    }

                    list.add((IFriend) model.getUser(stringBuilder.toString()));
                    stringBuilder.setLength(0);
                }
            }
        }
    }

    private void initList(IUser user) {
        user.initThemableObserver();
        user.initHabit();
        user.initFriends();
        if (user.getHabits().size() != 0) {
            for (IHabit habit : user.getHabits()) {
                habit.initDaysToDoList();
            }
        }
    }
}
