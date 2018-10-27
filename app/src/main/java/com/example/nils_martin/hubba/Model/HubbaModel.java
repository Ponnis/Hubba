package com.example.nils_martin.hubba.Model;

import java.util.ArrayList;
/**
 * @author Nils-Martin Robeling, Li Rönning
 * */
public class HubbaModel implements IHubbaModel {
    private static HubbaModel instance = null;
    private ArrayList<User> users;
    private User currentUser;

    public static HubbaModel getInstance() {
        if (instance == null) {
            instance = new HubbaModel();
        }
        return instance;
    }

    private HubbaModel() {
        users = new ArrayList<>();
    }
    /**
     * Returns a user based on a string Parameter for the users name
     * */
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

    public Themes getTheme(){
        return currentUser.getTheme();
    }
    // Law of demeter methods for themes
    public void setTheme(Themes theme){
        currentUser.setTheme(theme);
    }

    public void addThemeListener(ThemableObserver observer){
        currentUser.addThemeObserver(observer);
    }

    public void addUser (User user){
        this.users.add(user);
    }

    public String themeEnumToString(){
        return currentUser.themeEnumToString();
    }
}

