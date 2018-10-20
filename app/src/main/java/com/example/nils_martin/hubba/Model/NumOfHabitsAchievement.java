package com.example.nils_martin.hubba.Model;

public class NumOfHabitsAchievement extends Achievement {
    private Boolean isAchived;
    static AchievementType achievementType = AchievementType.NumOHabitsAchievement;

    public NumOfHabitsAchievement(String title){
        super(title);
    }

    NumOfHabitsAchievement(String title, String pathToImage){
        super(title, pathToImage);
    }

    @Override
    public Boolean assessAchievement(){
        return false;
    }
}
