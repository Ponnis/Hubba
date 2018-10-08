package com.example.nils_martin.hubba;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HabitViewTest {

    @Test
    public void testString(){
        String string = "MORNING";
        HabitView habitView = new HabitView();
        System.out.print(habitView.toLowerCase(string));
        assertEquals(habitView.toLowerCase(string), "Morning");
    }

}

