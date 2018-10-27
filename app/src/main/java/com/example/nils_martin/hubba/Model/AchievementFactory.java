package com.example.nils_martin.hubba.Model;

public class AchievementFactory {
/**
 * returns an achievement based on the AchievementType you asked it to return
 * */
    public static Acheievement getAchievement(AchievementType achievementType, String title, int targetNmbr) {


        if (achievementType == AchievementType.NumOHabitsAchievement) {
            return new NumOfHabitsAcheievement(title, targetNmbr);
        } else if (achievementType == AchievementType.StreakAchievement) {
            return new StreakAcheievement(title, targetNmbr);
        } else {
            return null;
        }
    }
}
