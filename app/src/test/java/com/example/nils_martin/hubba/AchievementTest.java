package com.example.nils_martin.hubba;

import com.example.nils_martin.hubba.Model.Achievement;
import com.example.nils_martin.hubba.Model.AchievementFactory;
import com.example.nils_martin.hubba.Model.AchievementType;
import com.example.nils_martin.hubba.Model.HubbaModel;
import com.example.nils_martin.hubba.Model.NumOfHabitsAchievement;
import com.example.nils_martin.hubba.Model.StreakAchievement;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class AchievementTest {

    @Test
    public void testStreakAchievement(){
        HubbaModel model = HubbaModel.getInstance();

        Achievement streak = AchievementFactory.getAchievement(AchievementType.StreakAchievement, "Streak Test", 2);
        assertNotNull(streak);
    }

    @Test
    public void testNumOfHabitsAchievement(){
        Achievement num = AchievementFactory.getAchievement(AchievementType.NumOHabitsAchievement, "Numofhabittest", 5);
        assertNotNull(num);
    }
}
