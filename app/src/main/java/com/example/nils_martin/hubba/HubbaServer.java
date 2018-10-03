package com.example.nils_martin.hubba;

import java.util.ArrayList;

public class HubbaServer {

    //A server to serve as a mock up for a remote server to hold all the users and control them
    private ArrayList<User> users = new ArrayList<>();

    private static HubbaServer hubbaServer = null;
    private HubbaServer(){

    }
    public User getUser(String userName){
       int index = 0;
        for (User user :users){
      if(user.getName().equals(userName)){
          index = users.indexOf(user);
      }
        }
        return users.get(index);
    }
    public ArrayList<User> getUsers(){
        return users;
    }
    /* get the singleton instance*/
    public static HubbaServer getInstance(){
        if (hubbaServer==null){
            hubbaServer = new HubbaServer();
        }
        return hubbaServer;
    }
    public void setUsers(ArrayList<User>users){
        this.users = users;
    }
}
