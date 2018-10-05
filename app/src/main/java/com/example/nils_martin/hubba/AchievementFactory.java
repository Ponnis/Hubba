package com.example.nils_martin.hubba;

public class AchievementFactory {

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
