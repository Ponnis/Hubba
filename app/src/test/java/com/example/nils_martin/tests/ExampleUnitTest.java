package com.example.nils_martin.tests;

import com.example.nils_martin.hubba.Achievement;
import com.example.nils_martin.hubba.Habit;
import com.example.nils_martin.hubba.NumOfHabitsAchievement;
import com.example.nils_martin.hubba.StreakAchievement;
import com.example.nils_martin.hubba.User;

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
        User user = new User("bla", "bla", "bla");
        assertNotNull(user);
    }

    @Test
    public void testStreakAchievement(){
        Achievement streak = new StreakAchievement("You have achieved this habit 10 days in a row!");
        assertNotNull(streak);
    }

    @Test
    public void testNumOfHabitsAchievement(){
        Achievement num = new NumOfHabitsAchievement("");
        assertNotNull(num);
    }

    @Test
    public void testUpStreak() {
        Habit habit = new Habit("test");
        habit.setDone(habit);
        assertTrue(habit.getStreak(habit) == 1);
    }
}