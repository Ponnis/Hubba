package com.example.nils_martin.hubba.Model;
import java.util.List;

public class NumOfHabitsAchievement extends Achievement {
    private static AchievementType achievementType = AchievementType.NumOHabitsAchievement;

    NumOfHabitsAchievement(String title, int targetNmbr){
        super(title, targetNmbr);
    }

    @Override
    public Boolean assessAchievement(){
        Boolean isAchived = false;
        List<Habit> habits = model.getCurrentUser().getHabits();
        if(habits.size() >= targetNmbr) {
            isAchived = true;
            this.setAchieved(true);
        }
        return isAchived;
    }

    @Override
    public AchievementType getAchievementType() {
        return achievementType;
    }
}
