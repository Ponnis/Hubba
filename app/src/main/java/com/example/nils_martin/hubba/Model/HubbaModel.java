package com.example.nils_martin.hubba.Model;

import java.util.ArrayList;

public class HubbaModel {
    private static HubbaModel instance = null;
    private ArrayList<User> users = new ArrayList<>();
    private User currentUser;

    public static HubbaModel getInstance() {
        if (instance == null) {
            instance = new HubbaModel();
        }
        return instance;
    }

    private HubbaModel() {

    }

    public User getUser(String userName) {
        int index = 0;
        for (User user : users) {
            if (user.getUserName().equals(userName)) {
                index = users.indexOf(user);
            }
        }
        return users.get(index);
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) {
        currentUser = user;
    }

    public void setUsers (ArrayList < User > users) {

        this.users = users;
    }
    public int getTheme(){
        return currentUser.getTheme();
    }
    public String themeEnumToString(){
        return currentUser.themeEnumToString();
    }
    // Law of demeter methods for themes
    public void setTheme(Themes theme){
        currentUser.setTheme(theme);
    }
    public void addThemeListener(ThemableObserver observer){
        currentUser.addThemeObserver(observer);
    }

    public void addUser (User user){
        getUsers().add(user);
    }
}

