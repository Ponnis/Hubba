package com.example.nils_martin.hubba.Model;

import java.util.ArrayList;

public interface IHubbaModel {


    IUser getUser(String userName);

    ArrayList<IUser> getUsers();

    IUser getCurrentUser();

    void setCurrentUser(IUser user);

    void setUsers(ArrayList<IUser> users);

    Themes getTheme();

    String themeEnumToString();

    // Law of demeter methods for themes
    void setTheme(Themes theme);

    void addThemeListener(ThemableObserver observer);

    void addUser(IUser user);
}
