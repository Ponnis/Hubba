package com.example.nils_martin.hubba.Model;

import java.util.ArrayList;

public interface IHubbaModel {


    User getUser(String userName);

     ArrayList<User> getUsers();

     User getCurrentUser();

     void setCurrentUser(User user);

     void setUsers (ArrayList < User > users);

     Themes getTheme();
     String themeEnumToString();

    // Law of demeter methods for themes
     void setTheme(Themes theme);
     void addThemeListener(ThemableObserver observer);

     void addUser (User user);
     void addAchivementListener(AchivementObserver achivementObserver);
}
