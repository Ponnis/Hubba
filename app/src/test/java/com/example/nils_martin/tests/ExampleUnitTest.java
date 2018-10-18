package com.example.nils_martin.tests;

import com.example.nils_martin.hubba.Model.Habit;
import com.example.nils_martin.hubba.Model.User;

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
        User user = new User("bla", "bla", "bla", "bla");
        assertNotNull(user);
    }

    /*@Test
    public void testStreakAchievement(){
        Achievement streak = new StreakAchievement("");
        assertNotNull(streak);
    }

    @Test
    public void testNumOfHabitsAchievement(){
        Achievement num = new NumOfHabitsAchievement("");
        assertNotNull(num);
    }*/

    @Test
    public void testUpStreak() {
        Habit habit = new Habit("test");
        habit.setDone();
        assertTrue(habit.getStreak(habit) == 1);
    }
}