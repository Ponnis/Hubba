package com.example.nils_martin.hubba;

import android.widget.EditText;

import java.util.ArrayList;

public class HubbaModel {
    private static ArrayList<User> users = new ArrayList<>();
    LoginView loginView = new LoginView();
    EditText currentUserName = loginView.getUsername();
    User currentUser = getUser(currentUserName.toString());

    public User getUser(String userName){
        int index = 0;
        for (User user :users){
            if(user.getName().equals(userName)){
                index = users.indexOf(user);
            }
        }
        return users.get(index);
    }
    public static ArrayList<User> getUsers(){
        return users;
    }
    public User getCurrentUser(){return this.currentUser;}


    public void setUsers(ArrayList<User>users){
        this.users = users;
    }
    public static void addUser(User user){
        getUsers().add(user);
    }
}
