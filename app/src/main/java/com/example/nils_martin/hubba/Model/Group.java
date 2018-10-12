package com.example.nils_martin.hubba;

import com.example.nils_martin.hubba.Model.Habit;
import com.example.nils_martin.hubba.Model.User;

import java.util.ArrayList;
import java.util.List;

public class  Group {

    String groupName;
    List<User> usersInGroup;
    ArrayList<Habit> groupHabits;

    public Group(String groupName){
        this.groupName = groupName;
        usersInGroup = new ArrayList<>();
    }

    private void isHabitComplete(){

    }
    //TODO SPLIT THIS SHIT LIKE A CAKE ON YOUR BIRTHDAY
 /*   private void update(){
        for(User user : usersInGroup){
            List tempHabits = user.getHabits();
            for (int i = 0; i < tempHabits.size(); i++) {
                if (tempHabits.get(i).getIsGroupHabit()&&habit.getIsDone()){
                    habit.upGroupMembersDoneCount();
                    //TODO Add if habit is done by all
                }

            }
        }
    }
    private void setHabitComplete(){}
    private void addUserToGroup(User user){
        usersInGroup.add(user);
    }*/

    private List<User> getUsersInGroup(){
        return this.usersInGroup;
    }

}
