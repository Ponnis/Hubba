package com.example.nils_martin.hubba;

import com.example.nils_martin.hubba.Model.Achievement;
import com.example.nils_martin.hubba.Model.NumOfHabitsAchievement;
import com.example.nils_martin.hubba.Model.StreakAchievement;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class AchievementTest {

    @Test
    public void testStreakAchievement(){
        Achievement streak = new StreakAchievement("Streak");
        assertNotNull(streak);
    }

    @Test
    public void testNumOfHabitsAchievement(){
        Achievement num = new NumOfHabitsAchievement("Number Of Habits");
        assertNotNull(num);
    }
}
