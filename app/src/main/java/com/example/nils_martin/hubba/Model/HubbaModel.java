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
            if (user.getName().equals(userName)) {
                index = users.indexOf(user);
            }
        }
        return users.get(index);
    }

    public ArrayList<User> getUsers() {
        return users;
    }
    //Defensive copying.
    public User getCurrentUser() {
        return new User(this.currentUser.getName(), this.currentUser.getEmail(),this.currentUser.getPassword());
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

