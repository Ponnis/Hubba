package com.example.nils_martin.hubba;

import java.util.ArrayList;

public class HubbaServer {
    //A server to serve as a mock up for a remote server to hold all the users and control them
    private ArrayList<User> users = new ArrayList<>();


    public User getUser(String userName){
       int index = 0;
        for (User user :users){
      if(user.getName().equals(userName)){
          index = users.indexOf(user);
      }
        }
        return users.get(index);
    }
}
