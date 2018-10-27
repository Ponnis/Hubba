package com.example.nils_martin.hubba.Model;

public class AchievementFactory {
    //USER SHOULD NOT HAVE FACTORY(?), MODEL SHOULD (?)

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
