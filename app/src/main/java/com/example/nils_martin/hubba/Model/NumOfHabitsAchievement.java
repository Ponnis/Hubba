package com.example.nils_martin.hubba.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Johannes Gustavsson & Nils-Martin Robelin
 */

public class NumOfHabitsAchievement extends Achievement {

    private static AchievementType achievementType = AchievementType.NumOHabitsAchievement;

    NumOfHabitsAchievement(String title, int targetNmbr){

        super(title, targetNmbr);
    }

    /**
     * Checks how many habits there is in current users Habit List and if there is equal or more then targetNmbr. 7
     * If true Also call on method to set the achivement to true
     *
     * @return returns true if the size of List<Ihabit> is larger then targetnmbr else returns false.
     */
    @Override
    public Boolean assessAchievement(List<IHabit> habits) {
        Boolean isAchived = false;
        try {
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
