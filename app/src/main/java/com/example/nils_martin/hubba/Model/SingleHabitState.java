package com.example.nils_martin.hubba.Model;

public class SingleHabitState implements HabitState {
    @Override
    public void updateHabit(Habit habit) {
        //TODO make checkSquare for habit to set done
        /*if(.isChecked){
          habit.setDone(habit);
        }*/
    }
    @Override
    public String toString(){
        return "SingleHabit";
    }
}
