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
    private boolean isActive;
    private boolean enableNotifications;
    private HabitTypeState habitTypeState;
    private State STATE;
    private Frequency FREQUENCY;
    private List<Integer> dayToDo = new ArrayList<>();
    private ArrayList<Observer> observers;
    public Habit(String title){
        this.title = title;
        this.streak = 0;
        this.isDone = false;
        this.isActive = true;
        this.enableNotifications = false;
    }

    public Habit(String title, List<Integer> days) {
        this.title = title;
        this.streak = 0;
        this.isDone = false;
        this.isActive = true;
        this.enableNotifications = false;
        this.dayToDo = days;
    }


    public void setHabitTypeState(HabitTypeState habitTypeState){
        this.habitTypeState = habitTypeState;
    }
    public HabitTypeState getHabitTypeState(){
        return this.habitTypeState;
    }
    public void setDone(Habit habit){
        habit.isDone = !habit.isDone;
        upStreak(this);

    }
    //TODO make two different events?
    public void notifyObservers(){
        if (habitTypeState.toString().equals("GroupHabit")){
            //TODO update the userGroup
        }
        else if(habitTypeState.toString().equals("SingleHabit")){
        for (Observer observer:observers){
            observer.update(this, model.currentUser);
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
    public void setActive(Habit habit){
        habit.isActive = !habit.isActive;
    }

    public void setNotifications(Habit habit){
        habit.enableNotifications = !habit.enableNotifications;
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

    public void setDayToDo (List<Integer> dayToDo) {
        this.dayToDo = dayToDo;
    }

    public List<Integer> getDaysToDo() {
        return dayToDo;
    }
}
