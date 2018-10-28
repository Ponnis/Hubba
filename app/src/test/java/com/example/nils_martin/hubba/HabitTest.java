package com.example.nils_martin.hubba;

import com.example.nils_martin.hubba.Model.GroupHabitType;
import com.example.nils_martin.hubba.Model.Habit;
import com.example.nils_martin.hubba.Model.IHabit;
import com.example.nils_martin.hubba.Model.HabitTypeState;
import com.example.nils_martin.hubba.Model.SingleHabitType;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class HabitTest {

    @Test
    public void testCreateHabit() {
        IHabit habit = new Habit("Drink Water");
        assertNotNull(habit);
    }

    @Test
    public void testSetDone() {
        IHabit habit = new Habit("test");
        habit.setGroupDone();
        assertTrue(habit.getIsDone());
        assertTrue(habit.getStreak() == 1);
    }

    @Test
    public void testSetHabitTypeStateGroup() {
        IHabit habit = new Habit("test");
        HabitTypeState group = new GroupHabitType();
        habit.setHabitTypeState(group);
        assertSame(habit.getHabitTypeState().toString(), "GroupHabit");
    }

    @Test
    public void testSetHabitTypeStateSingle() {
        IHabit habit = new Habit("test");
        HabitTypeState single = new SingleHabitType();
        habit.setHabitTypeState(single);
        assertSame(habit.getHabitTypeState().toString(), "SingleHabit");
    }

}
