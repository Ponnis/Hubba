package com.example.nils_martin.hubba.Model;

import com.example.nils_martin.hubba.Model.Achievement;
import com.example.nils_martin.hubba.Model.AchievementType;
import com.example.nils_martin.hubba.Model.NumOfHabitsAchievement;
import com.example.nils_martin.hubba.Model.StreakAchievement;

public abstract class AchievementFactory {

    public static Achievement getAchievement(AchievementType achievementType, String title) {
        if (achievementType == AchievementType.NumOHabitsAchievement) {
            return new NumOfHabitsAchievement(title);
        } else if (achievementType == AchievementType.StreakAchievement) {
            return new StreakAchievement(title);
        } else {
            return null;
        }
    }
}
