package com.example.nils_martin.hubba.Model;

public class StreakAchievement extends Achievement {

    StreakAchievement(String title) {
        super(title);
        AchievementType achievementType = AchievementType.StreakAchievement;
    }

    StreakAchievement(String title, String pathToImage) {
        super(title, pathToImage);

        AchievementType achievementType = AchievementType.StreakAchievement;
    }
}
