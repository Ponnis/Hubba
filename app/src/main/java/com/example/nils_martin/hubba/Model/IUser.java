package com.example.nils_martin.hubba.Model;

import com.example.nils_martin.hubba.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public interface IUser {
     String userName = null;
     String email = null;
     String password = null;
     String imagePath = null;
     List<User> friends = null;
     ArrayList<Habit> habits = new ArrayList<>();
     ArrayList<Achievement> achievements= null;
     ArrayList<ThemableObserver> themeObservers = null;
    //User Settings
     boolean allowNotifications= Boolean.parseBoolean(null);
     boolean soundOn= Boolean.parseBoolean(null);
    Themes ActiveTheme = null;


    // Takes an ENUM from Themes and set
    public void setTheme(Themes theme);
    //  Returns the int R.style associated with a specific theme
    public String themeEnumToString();
    public Themes getTheme();
    // Adds the ThemableObserver to the observer list themeObservers.
    public void addThemeObserver(ThemableObserver observer );


    public void addHabit (Habit habit);

    public void removeHabit (Habit habit);

    public String getUserName();

    public void setUserName(String string);

    public String getEmail();

    public void setEmail(String string);

    public String getPassword();

    public void setPassword(String string);

    public void setImagePath(String string);

    public String getImagePath();

    public ArrayList getHabits();

    public List getAchievements();




    // Adds another user to the list of friends.
    public void addFriend(User user);

    // Finds the friend to remove in friends list and then removes the friend.
    public void removeFriend(User friend);

    public List<User> getFriends();

}
