package com.example.nils_martin.hubba;

import android.widget.ImageView;

public class Habit {

    private String title;
    private String timestamp;
    private int streak;
    private int goalDays;
    private Frequency frequency;
    private boolean isDone;
    private boolean isActive;
    private boolean enableNofitications;
    private ImageView image;

    public Habit(String title){
        this.title = title;
    }

    public int getGoalDays(Habit habit){
        return habit.goalDays;
    }

    public void setGoalDays(Habit habit, int days){
        habit.goalDays = days;
    }

    public void setDone(Habit habit){
        habit.isDone = !habit.isDone;
        if(habit.isDone){
            habit.streak++;
        }
    }

    public void setActive(Habit habit){
        habit.isActive = !habit.isActive;
    }

    private void setNotifications(Habit habit){
        habit.enableNofitications = !habit.enableNofitications;
    }
}
