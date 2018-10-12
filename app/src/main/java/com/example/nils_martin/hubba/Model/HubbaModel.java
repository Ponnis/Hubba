package com.example.nils_martin.hubba.Model;

import java.util.ArrayList;
//CLEAN HUBBAMODEL FROM STATIC
public class HubbaModel {
    private static HubbaModel instance = null;
    private ArrayList<User> users = new ArrayList<>();
    static User currentUser;

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

    public User getCurrentUser() {
        return this.currentUser;
    }

    public void setCurrentUser(User user) {
        currentUser = user;
    }


    public void setUsers(ArrayList<User> users) {

        public void setUsers (ArrayList < User > users) {

            this.users = users;
        }

        public void addUser (User user){
            getUsers().add(user);
        }
    }
}
