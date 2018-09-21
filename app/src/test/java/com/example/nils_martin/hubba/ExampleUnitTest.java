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
        User user = new User();
        assertNotNull(user);
    }

    @Test
    public void testCreateStreakAchievement(){
        StreakAchievement streakAchievement = new StreakAchievement("You have achieved this habit 10 days in a row!");
        assertNotNull(streakAchievement);
    }
}