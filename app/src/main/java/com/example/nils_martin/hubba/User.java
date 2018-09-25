package com.example.nils_martin.hubba;

import java.util.List;

public class User {

    private String name;
    private String email;
    private String password;
    private List<Habit> habits;
    private List<Achievement> achievements;

    public User (String name,String email,String password){
        this.name = name;
        this.email = email;
        this.password = password;
    }
    public List getHabits(){
        return habits;
    }
    public List getAchievements(){
        return achievements;
    }


}
