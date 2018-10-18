package com.example.nils_martin.hubba.Model;

import java.util.ArrayList;

public interface IHubbaModel {


    public User getUser(String userName);

    public ArrayList<User> getUsers();

    public User getCurrentUser();

    public void setCurrentUser(User user);

    public void setUsers (ArrayList < User > users);

    public Themes getTheme();

    // Law of demeter methods for themes
    public void setTheme(Themes theme);
    public void addThemeListener(ThemableObserver observer);

    public void addUser (User user);
}
