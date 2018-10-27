package com.example.nils_martin.hubba.Model;

import com.example.nils_martin.hubba.Model.Achievement;
import com.example.nils_martin.hubba.Model.AchievementType;
import com.example.nils_martin.hubba.Model.NumOfHabitsAchievement;
import com.example.nils_martin.hubba.Model.StreakAchievement;

public class AchievementFactory {

    /**
     * returns an achievement based on the AchievementType you asked it to return
     * */
    public static Achievement getAchievement(AchievementType achievementType, String title, int targetNmbr) {
        if (achievementType == AchievementType.NumOHabitsAchievement) {
            return new NumOfHabitsAchievement(title, targetNmbr);
        } else if (achievementType == AchievementType.StreakAchievement) {
            return new StreakAchievement(title, targetNmbr);
        } else {
            return null;
        }
    }
}
