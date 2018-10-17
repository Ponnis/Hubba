package com.example.nils_martin.hubba.Model;

public class StreakAchievement extends Achievement {

    AchievementType achievementType;

    StreakAchievement(String title) {
        super(title);
        achievementType = AchievementType.StreakAchievement;
    }

    StreakAchievement(String title, String pathToImage) {
        super(title, pathToImage);
        achievementType = AchievementType.StreakAchievement;
    }

    @Override
    public void assessAchievement() {

    }
}
