package com.example.nils_martin.hubba.Model;

public class StreakAchievement extends Achievement implements IStreakAchievement {

    private AchievementType achievementType;


    StreakAchievement(String title, int targetnmbr) {
        super(title,targetnmbr);
        achievementType = AchievementType.StreakAchievement;

    }

    @Override
    public Boolean assessAchievement() {
        Boolean isAchived = false;
        for (Habit habit: model.getCurrentUser().getHabits()) {
            if(targetNmbr == habit.getStreak()){
                this.setAchieved(true);
                isAchived = true;
                break;
            }
        }
        return isAchived;
    }

    @Override
    public AchievementType getAchievementType() {
        return achievementType;
    }
}
