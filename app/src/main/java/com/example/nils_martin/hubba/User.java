package com.example.nils_martin.hubba;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class User implements Observer{

    private String name;
    private String email;
    private String password;
    private List<Habit> habits;
    private List<Achievement> achievements;
    private boolean allowNotifications;
    private boolean soundOn;

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

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
