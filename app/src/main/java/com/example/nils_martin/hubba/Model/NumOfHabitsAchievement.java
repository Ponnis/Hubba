package com.example.nils_martin.hubba.Model;
import java.util.List;

public class NumOfHabitsAchievement extends Achievement {
    private static AchievementType achievementType = AchievementType.NumOHabitsAchievement;

    NumOfHabitsAchievement(String title, int targetNmbr){
        super(title, targetNmbr);
    }

    /**
     * Checks how many habits there is in current users Habit List and if there is equal or more then targetNmbr.
     * If so, it returns true and sets Achiveed in super class to true, else returns false.
     * @return
     */
    @Override
    public Boolean assessAchievement(){
        Boolean isAchived = false;
        try {
            List<Habit> habits = model.getCurrentUser().getHabits();
            if (habits.size() >= targetNmbr) {
                isAchived = true;
                this.setAchieved(true);
            }
        }catch (NullPointerException e) {
            System.out.println(e.toString());
        }
        return isAchived;
    }

    @Override
    public AchievementType getAchievementType() {
        return achievementType;
    }
}
