package com.example.nils_martin.hubba.Model;

public class GroupHabitState implements HabitTypeState {
    @Override
    public void updateHabit(Habit habit) {
       /* if(.isChecked){
          habit.upGroupMembersDoneCount();
        }*/
    }
    @Override
    public String toString(){
        return "GroupHabit";
    }
}