package com.example.nils_martin.hubba.Model;

public class GroupHabitType implements HabitTypeState {
    @Override
     void update    Habit(Habit habit) {
       /* if(.isChecked){
          habit.upGroupMembersDoneCount();
        }*/
    }
    @Override
    public String toString(){
        return "GroupHabit";
    }
}
