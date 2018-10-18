package com.example.nils_martin.hubba.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Habit extends Observable {

    private HubbaModel model = HubbaModel.getInstance();
    private String title;
    private int groupmembersDoneCount;
    private int streak;
    private boolean isDone;
    private boolean reminderOn;
    private HabitTypeState habitTypeState;
    private State STATE;
    private Frequency FREQUENCY;
    private List<Integer> daysToDo = new ArrayList<>();
    private ArrayList<Observer> observers;

    public Habit(String title){
        this.title = title;
        this.streak = 0;
        this.isDone = false;
        this.reminderOn = false;
    }

    public Habit(String title, List<Integer> days) {
        this.title = title;
        this.streak = 0;
        this.isDone = false;
        this.reminderOn = false;
        this.daysToDo = days;
    }

    public void setHabitTypeState(HabitTypeState habitTypeState){
        this.habitTypeState = habitTypeState;
    }
    public HabitTypeState getHabitTypeState(){
        return this.habitTypeState;
    }

    public void setDone(){
        this.isDone = true;
        upStreak(this);
    }

    public void isDone(){
        this.isDone = true;
    }
    public void notDone(){
        this.isDone = false;
    }

    //TODO make two different events?
    public void notifyObservers(){
        if (habitTypeState.toString().equals("GroupHabit")){
            //How to notify user group without wrecking dependency?
            //TODO update the userGroup
        }
        else if(habitTypeState.toString().equals("SingleHabit")){
        for (Observer observer:observers){
            observer.update(this, model.getCurrentUser());
        }
        }
    }

    public void upStreak(Habit habit){
        if(habit.isDone){
            habit.streak++;
        }
    }
    public Boolean getIsDone(){return isDone;
    }
    public void upGroupMembersDoneCount(){
        groupmembersDoneCount++;
    }

    public void reminderEnabled() {
        this.reminderOn = true;
    }

    public void reminderDisabled(){
        this.reminderOn = false;
    }

    public boolean isReminderOn() {
        return reminderOn;
    }

    public int getGroupmembersDoneCount(){
        return groupmembersDoneCount;
    }

    public Habit getHabit(){return this;}

    public int getStreak(Habit habit){
        return habit.streak;
    }

    public void setTitle(String string){title = string;}

    public String getTitle(Habit habit) {return habit.title;}

    public void setSTATE(State state){
        this.STATE = state;
    }

    public State getSTATE (){return STATE;}

    /**
     * Method to set a Frequency to the current Habit
     * @param FREQUENCY The desired Frequency
     */
    public void setFREQUENCY (Frequency FREQUENCY){
        this.FREQUENCY = FREQUENCY;
    }

    /**
     * Method for seeing what Frequency the object is set to
     * @return Returns the Frequency of the Habit
     */
    public Frequency getFREQUENCY(){
        return FREQUENCY;
    }

    public void setDaysToDo(List<Integer> daysToDo) {
        this.daysToDo = daysToDo;
    }

    public List<Integer> getDaysToDo() {
        return daysToDo;
    }
}
