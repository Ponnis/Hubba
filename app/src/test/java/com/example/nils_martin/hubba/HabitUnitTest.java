package com.example.nils_martin.hubba;

import com.example.nils_martin.hubba.Model.GroupHabitType;
import com.example.nils_martin.hubba.Model.Habit;
import com.example.nils_martin.hubba.Model.HabitTypeState;
import com.example.nils_martin.hubba.Model.SingleHabitType;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class HabitUnitTest {

    @Test
    public void testCreateHabit() {
        Habit habit = new Habit("Drink Water");
        assertNotNull(habit);
    }

    @Test
    public void testSetDOne() {
        Habit habit = new Habit("test");
        habit.setDone();
        assertTrue(habit.getIsDone());
        assertTrue(habit.getStreak() == 1);
    }

    @Test
    public void testSetHabitTypeStateGroup() {
        Habit habit = new Habit("test");
        GroupHabitType group = new GroupHabitType();
        habit.setHabitTypeState(group);
        assertSame(habit.getHabitTypeState().toString(), "GroupHabit");
    }

    @Test
    public void testSetHabitTypeStateSingle() {
        Habit habit = new Habit("test");
        SingleHabitType single = new SingleHabitType();
        habit.setHabitTypeState(single);
        assertSame(habit.getHabitTypeState().toString(), "SingleHabit");
    }

}
