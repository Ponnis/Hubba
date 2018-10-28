package com.example.nils_martin.hubba.Model;

import java.util.List;
/**
 * @author Nils-Martin Robeling
 * */
public class  Group {

    private String groupName;
    private List<IFriend> usersInGroup;
    private IHabit habit;

    public Group(String groupName, List<IFriend> usersInGroup, IHabit habit) {
        this.groupName = groupName;
        this.usersInGroup = usersInGroup;
        this.habit = habit;
    }


    public String getGroupName() {
        return this.groupName;
    }




    /**
     * Checks with all the groupmembers if the habit is done, sets habit to done if true.
     */


    private void isHabitComplete() {
        if (habit.getGroupMembersDoneCount() == usersInGroup.size()) {

            habit.setDone();
        }

    }

    public List<IFriend> getUsersInGroup() {
        return this.usersInGroup;
    }

    public IHabit getHabit(){
        return this.habit;
    }


    public void setUsersInGroup(List<IFriend> iFriendList) {
        this.usersInGroup = iFriendList;
    }
}
