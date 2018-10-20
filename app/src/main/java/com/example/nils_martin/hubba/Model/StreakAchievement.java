package com.example.nils_martin.hubba.Model;

public class StreakAchievement extends Achievement implements IStreakAchievement {

    AchievementType achievementType;

    public StreakAchievement(String title) {
        super(title);
        achievementType = AchievementType.StreakAchievement;
    }

    StreakAchievement(String title, String pathToImage) {
        super(title, pathToImage);
        achievementType = AchievementType.StreakAchievement;
    }

    @Override
    public Boolean assessAchievement() {
        return false;
    }
}
