package com.example.nils_martin.hubba.Model;

import com.example.nils_martin.hubba.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public interface IUser {



    // Takes an ENUM from Themes and set
     void setTheme(Themes theme);
    //  Returns the int R.style associated with a specific theme
     String themeEnumToString();
     Themes getTheme();
    // Adds the ThemableObserver to the observer list themeObservers.
     void addThemeObserver(ThemableObserver observer );


     void addHabit (IHabit habit);

     void removeHabit (IHabit habit);

     String getUserName();

     void setUserName(String string);

     String getEmail();

     void setEmail(String string);

     String getPassword();

     void setPassword(String string);

     void setImagePath(String string);

     String getImagePath();

     ArrayList getHabits();

     void addAchivement(Achievement achievement);

     List getAchievements();


    // TODO: 2018-10-21 Fix below methods so they depend use Iuser instead of User 
    // Adds another user to the list of friends.
     void addFriend(User user);

    // Finds the friend to remove in friends list and then removes the friend.
     void removeFriend(User friend);

     List<User> getFriends();

    void initHabit();
}
