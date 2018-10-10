package com.example.nils_martin.hubba;

import java.util.ArrayList;
import java.util.List;

public class Group {

    String groupName;
    List<User> usersInGroup;

    public Group(String groupName){
        this.groupName = groupName;
        usersInGroup = new ArrayList<>();
    }

    private void addUserToGroup(User user){
        usersInGroup.add(user);
    }

    private List<User> getUsersInGroup(){
        return this.usersInGroup;
    }
}
