package com.example.nils_martin.hubba.Model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Stack;

public class Habit extends Observable implements IHabit {

    private HubbaModel model = HubbaModel.getInstance();
    private String title;
    private int groupMembersDoneCount;
    private int streak;
    private int daysToDoSize;
    private boolean isDone;
    private boolean reminderOn;
    private List<Integer> reminderTime;
    private IHabitTypeState habitTypeState;
    private State STATE;
    private Frequency FREQUENCY;
    private List<Integer> daysToDo = new ArrayList<>();
    private ArrayList<Observer> observers;
    private Stack<Date> lastDateDoneStack = new Stack<>();
    private Date lastDateDone;

    public Habit(String title) {
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

    public void setHabitTypeState(IHabitTypeState habitTypeState) {
        this.habitTypeState = habitTypeState;
    }

    public IHabitTypeState getHabitTypeState() {
        return this.habitTypeState;
    }

    public void setGroupDone(){
        this.isDone = true;
        upStreak(this);
    }

    public void setDoneTo(boolean b){
        this.isDone=b;
    }

    public Boolean getIsDone(){return isDone; }

    public void isDone(){
        this.isDone = true;
        this.streak++;
        setCurrentDay();
        lastDateDoneStack.push(lastDateDone);
    }


    public void notDone() {
        this.isDone = false;
        this.streak--;
        lastDateDoneStack.pop();
    }

    /**
     * notifies the appropriate observer depending on HabitType
     */
    public void notifyObservers() {
        if (habitTypeState.toString().equals("GroupHabit")) {
            //How to notify user group without wrecking dependency?
            //TODO update the userGroup
        } else if (habitTypeState.toString().equals("SingleHabit")) {
            for (Observer observer : observers) {
                observer.update(this, model.getCurrentUser());
            }
        }
    }

    /**
     * Sets the streak to +1 if the habit is completed
     */
    public void upStreak(Habit habit) {
        if (habit.isDone) {
            habit.streak++;
        }
    }

    public void upGroupMembersDoneCount() {
        groupMembersDoneCount++;
    }

    public void reminderEnabled() {
        this.reminderOn = true;
    }

    public void reminderDisabled() {
        this.reminderOn = false;
    }

    public boolean isReminderOn() {
        return reminderOn;
    }

    public int getGroupMembersDoneCount() {
        return groupMembersDoneCount;
    }

    public Habit getHabit() {
        return this;
    }

    public int getStreak() {
        return streak;
    }

    public void setTitle(String string) {
        title = string;
    }

    public String getTitle() {
        return title;
    }

    public void setSTATE(State state) {
        this.STATE = state;
    }

    public State getSTATE() {
        return STATE;
    }

    /**
     * Method to set a Frequency to the current Habit
     *
     * @param FREQUENCY The desired Frequency
     */
    public void setFREQUENCY(Frequency FREQUENCY) {
        this.FREQUENCY = FREQUENCY;
    }

    /**
     * Method for seeing what Frequency the object is set to
     *
     * @return Returns the Frequency of the Habit
     */
    public Frequency getFREQUENCY() {
        return FREQUENCY;
    }

    public void setDaysToDo(List<Integer> daysToDo) {
        this.daysToDo = daysToDo;
    }

    public List<Integer> getDaysToDo() {
        return daysToDo;
    }

    public void setReminderTime(List<Integer> time) {
        this.reminderTime = time;
    }

    public List<Integer> getReminderTime() {
        return reminderTime;
    }

    private void setCurrentDay() {
        Calendar nowCalendar = Calendar.getInstance();
        lastDateDone = new Date();
        lastDateDone.setDate(nowCalendar.get(Calendar.DAY_OF_MONTH));
        lastDateDone.setMonth(nowCalendar.get(Calendar.MONTH));
        lastDateDone.setYear(nowCalendar.get(Calendar.YEAR));
        lastDateDone.setHours(0);
        lastDateDone.setMinutes(0);
        lastDateDone.setSeconds(0);
    }

    public Stack<Date> getLastDateDoneStack() {
        return lastDateDoneStack;
    }

    public int getDaysToDoSize() {
        return daysToDoSize;
    }

    public void setDaysToDoSize(int i) {
        this.daysToDoSize = i;
    }

    public void initDaysToDoList() {
        this.daysToDo = new ArrayList<>();

    }
}
