package com.example.nils_martin.hubba;

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
    private void update(){
        for(User user : usersInGroup){
            ArrayList<Habit>tempHabits = user.getHabits();
            for(Habit habit : tempHabits){
                if (habit.getIsGroupHabit()&&habit.getIsDone()){
                    habit.upGroupMembersDoneCount();
                    //TODO Add if habit is done by all
                }

            }
        }
    }
    private void setHabitComplete(){}
    private void addUserToGroup(User user){
        usersInGroup.add(user);
    }

    private List<User> getUsersInGroup(){
        return this.usersInGroup;
    }

}
