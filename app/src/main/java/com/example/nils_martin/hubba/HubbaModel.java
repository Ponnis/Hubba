package com.example.nils_martin.hubba;

import android.widget.EditText;

import java.util.ArrayList;
import java.util.Observer;

public class HubbaModel {
    private static ArrayList<User> users = new ArrayList<>();
    HubbaServer hubbaServer = HubbaServer.getInstance();
    static LoginView loginView = new LoginView();
   static EditText currentUserName = loginView.getUsername();
    private static User currentUser = getUser(currentUserName.toString());



    public static User getUser(String userName){
        int index = 0;
        for (User user :users){
            if(user.getName().equals(userName)){
                index = users.indexOf(user);
            }
        }
        return users.get(index);
    }
    public static User getCurrentUser(){return currentUser;}
    public static ArrayList<User> getUsers(){
        return users;
    }


    public void setUsers(ArrayList<User>users){
        this.users = users;
    }
    public void addUser(User user){
        users.add(user);
    }

}
