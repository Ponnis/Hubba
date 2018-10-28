package com.example.nils_martin.hubba.Model;

import java.util.ArrayList;

/**
 * @author Johannes Gustavsson & Nils-Martin Robeling
 */

public class StreakAchievement extends Achievement implements IStreakAchievement {

    private AchievementType achievementType;




    StreakAchievement(String title, int targetnmbr) {
        super(title,targetnmbr);

        achievementType = AchievementType.StreakAchievement;

    }

    /**
     * Goes thru all habits and check if any of them have a streak higher then targetNmbr. If it's true call on method setAchivedtrue on this.
     *
     * @return If a habit streak i equal or higher then targetnmbr it returns true, else false.
     */
    @Override
    public Boolean assessAchievement(ArrayList<IHabit> habits) {
        Boolean isAchived = false;
        try {
            for (IHabit habit : habits) {
                if (targetNmbr <= habit.getStreak()) {
                    this.setAchieved(true);
                    isAchived = true;
                    break;
                }
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
