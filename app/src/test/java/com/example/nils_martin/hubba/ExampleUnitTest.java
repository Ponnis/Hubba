package com.example.nils_martin.hubba;

import org.junit.Test;

import static org.junit.Assert.*;

public class ExampleUnitTest {
    @Test
    public void testCreateHabit() {
        Habit habit = new Habit("Drink Water");
        assertNotNull(habit);
    }

    @Test
    public void testCreateUser(){
        User user = new User("Åke", "Åke@gmail.com","Ninja1337");
        assertNotNull(user);
    }

    @Test
    public void testCreateStreakAchievement(){
        StreakAchievement streakAchievement = new StreakAchievement("You have achieved this habit 10 days in a row!");
        assertNotNull(streakAchievement);
    }

    @Test
    public void testUpStreak() {
        Habit habit = new Habit("test");
        habit.setDone(habit);
        assertTrue(habit.getStreak(habit) == 1);
    }
    @Test
    public void testNewUserButton(){
    LoginView loginView = new LoginView();
    assertTrue(LoginView.userList.isEmpty());
    }

}