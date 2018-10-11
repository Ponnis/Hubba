package com.example.nils_martin.hubba;

import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Habit extends Observable {
//TODO IMPLEMENT HERITAGE
    private HubbaModel model = HubbaModel.getInstance();
    private String title;
    private String timestamp;
    private int groupmembersDoneCount;
    private int streak;
    private int goalDays;
    private Frequency frequency;
    private boolean isDone;
    private boolean isActive;
    private boolean isGroupHabit;
    private boolean enableNofitications;
    private ImageView image;
    private State STATE;
    private Frequency FREQUENCY;
    private List<Integer> dayToDo = new ArrayList<>();
    //FIX OBSERVER PATTERN, TALK TO LI ABOUT THIS
    private ArrayList<Observer> observers;
    private ArrayList<Group> groupObservers;


    public Habit(String title){
        this.title = title;
        this.streak = 0;
        this.isDone = false;
        this.isActive = true;
        this.enableNofitications = false;
    }

    public Habit(String title, List<Integer> days) {
        this.title = title;
        this.streak = 0;
        this.isDone = false;
        this.isActive = true;
        this.enableNofitications = false;
        this.dayToDo = days;
    }

    enum State{
        MORNING,
        MIDDAY,
        EVENING,
        NIGHT,
        DONE
    }

    enum Frequency{
        DAILY,
        WEEKLY,
        MONTHLY
    }

    public int getGoalDays(Habit habit){
        return habit.goalDays;
    }

    public void setGoalDays(Habit habit, int days){
        habit.goalDays = days;
    }

    public void setDone(Habit habit){
        habit.isDone = !habit.isDone;
        upStreak(this);

    }
    //TODO make two different events?
    //Icke modulärt som fan att ändra beteende med en boolean, använd states, delegering eller arv. FRÅGA FORREST/GOOGLE
    public void notifyObservers(){
        if (this.isGroupHabit){
            //TODO update the userGroup
        }
        for (Observer observer:observers){
            observer.update(this, model.currentUser);
        }
    }

    public void upStreak(Habit habit){
        if(habit.isDone){
            habit.streak++;
        }
    }
    public Boolean getIsDone(){return isDone;
    }
    public boolean getIsGroupHabit(){return isGroupHabit;
    }
    public void upGroupMembersDoneCount(){
        groupmembersDoneCount++;
    }
    public void setActive(Habit habit){
        habit.isActive = !habit.isActive;
    }

    public void setNotifications(Habit habit){
        habit.enableNofitications = !habit.enableNofitications;
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
