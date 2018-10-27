package com.example.nils_martin.hubba.Model;

import java.util.List;

public class  Group {

    private String groupName;
    private List<Friend> usersInGroup;
   private  IHabit habit;

    public Group(String groupName,List<Friend> usersInGroup,IHabit habit){
        this.groupName = groupName;
        this.usersInGroup = usersInGroup;
        this.habit = habit;
    }


    public String getGroupName(){
        return this.groupName;
    }
    /**
     * Checks with all the groupmembers if the habit is done, sets habit to done if true.
     * */
    private void isHabitComplete(){
        if(habit.getGroupMembersDoneCount()==usersInGroup.size()){
            habit.setDone();
        }
    }

    private List<Friend> getUsersInGroup(){
        return this.usersInGroup;
    }
}
