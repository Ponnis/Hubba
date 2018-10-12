package com.example.nils_martin.hubba.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class User implements Observer{

    private String name;
    private String email;
    private String password;
    private ArrayList<Habit> habits;
    private ArrayList<Achievement> achievements;
    private boolean allowNotifications;
    private boolean soundOn;

    public User (String name,String email,String password){
        this.name = name;
        this.email = email;
        this.password = password;
    }
    public ArrayList getHabits(){
        return habits;
    }
    public List getAchievements(){
        return achievements;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
//TODO test the update method
    @Override
    public void update(Observable o, Object arg) {
        for (Habit habit : habits){
            if(habit.getStreak(habit)%10==0){
                achievements.add(AchievementFactory.getAchievement(AchievementType.StreakAchievement,habit.getStreak(habit)+" Days!"));
            }
            if (habits.size()%10==0){
                achievements.add(AchievementFactory.getAchievement(AchievementType.NumOHabitsAchievement,habits.size()+" Habits!"));
            }
        }
    }

}
