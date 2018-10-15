package com.example.nils_martin.hubba.Model;

import com.example.nils_martin.hubba.Model.Habit;
import com.example.nils_martin.hubba.Model.User;

import java.util.ArrayList;
import java.util.List;

public class  Group {

    private String groupName;
    private List<User> usersInGroup;
   private  Habit habit;

    public Group(String groupName){
        this.groupName = groupName;
        usersInGroup = new ArrayList<>();
    }

    private void isHabitComplete(){
        if(habit.getGroupmembersDoneCount()==usersInGroup.size()){
            habit.setDone(habit);
        }
    }
    //TODO SPLIT THIS SHIT LIKE A CAKE ON YOUR BIRTHDAY

    private List<User> getUsersInGroup(){
        return this.usersInGroup;
    }
}
