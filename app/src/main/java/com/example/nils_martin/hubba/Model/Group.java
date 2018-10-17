package com.example.nils_martin.hubba.Model;

import com.example.nils_martin.hubba.Model.Habit;
import com.example.nils_martin.hubba.Model.User;

import java.util.ArrayList;
import java.util.List;

public class  Group {

    private String groupName;
    private List<Friend> usersInGroup;
   private  Habit habit;

    public Group(String groupName, ArrayList<Friend> friends){
        this.groupName = groupName;
        usersInGroup = friends;
    }
    //Ska delegeras till GroupHabitType istället? Dock får den inte veta något om gruppen då det är uppåt
    
    private void isHabitComplete(){
        if(habit.getGroupmembersDoneCount()==usersInGroup.size()){
            habit.setDone(habit);
        }
    }

    private List<Friend> getUsersInGroup(){
        return this.usersInGroup;
    }
}
