package com.example.nils_martin.hubba.Model;

public class SingleHabitType implements HabitTypeState {
    @Override
    public void updateHabit(Habit habit) {
        //TODO make checkSquare for habit to set done
        /*if(.isChecked){
          habit.setGroupDone(habit);
        }*/
    }
    @Override
    public String toString(){
        return "SingleHabit";
    }
}
