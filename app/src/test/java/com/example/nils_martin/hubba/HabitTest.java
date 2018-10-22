package com.example.nils_martin.hubba;

import com.example.nils_martin.hubba.Model.Habit;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class HabitTest {

    @Test
    public void testCreateHabit() {
        Habit habit = new Habit("Drink Water");
        assertNotNull(habit);
    }

    @Test
    public void testUpStreak() {
        Habit habit = new Habit("test");
        habit.setDone();
        assertTrue(habit.getStreak() == 1);
    }
}
