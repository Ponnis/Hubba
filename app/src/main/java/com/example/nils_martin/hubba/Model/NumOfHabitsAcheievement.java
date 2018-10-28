package com.example.nils_martin.hubba.Model;

import java.util.List;

/**
 * @author Johannes Gustavsson & Nils-Martin Robelin
 */

public class NumOfHabitsAcheievement extends Acheievement {
    private static AchievementType achievementType = AchievementType.NumOHabitsAchievement;

    NumOfHabitsAcheievement(String title, int targetNmbr) {
        super(title, targetNmbr);
    }

    /**
     * Checks how many habits there is in current users Habit List and if there is equal or more then targetNmbr. 7
     * If true Also call on method to set the achivement to true
     *
     * @return returns true if the size of List<Ihabit> is larger then targetnmbr else returns false.
     */
    @Override
    public Boolean assessAchievement() {
        Boolean isAchived = false;
        try {
            List<IHabit> habits = model.getCurrentUser().getHabits();
            if (habits.size() >= targetNmbr) {
                isAchived = true;
                this.setAchieved(true);
            }
        } catch (NullPointerException e) {
            System.out.println(e.toString());
        }
        return isAchived;
    }

    @Override
    public AchievementType getAchievementType() {
        return achievementType;
    }
}
