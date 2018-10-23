package com.example.nils_martin.hubba.Model;

public class StreakAchievement extends Achievement implements IStreakAchievement {

    private AchievementType achievementType;


    StreakAchievement(String title, int targetnmbr) {
        super(title,targetnmbr);
        achievementType = AchievementType.StreakAchievement;

    }

    /**
     * Goes thru all habits and check if any of them have a streak higher then targetNmbr.
     * If so it returns true and sets Achived in superclass to true, else false.
     * @return
     */
    @Override
    public Boolean assessAchievement() {
        Boolean isAchived = false;
        try {
        for (Habit habit : model.getCurrentUser().getHabits()) {
            if (targetNmbr <= habit.getStreak()) {
                this.setAchieved(true);
                isAchived = true;
                break;
            }
        }
        }catch (NullPointerException e){
            System.out.println(e.toString());
        }
        return isAchived;
    }

    @Override
    public AchievementType getAchievementType() {
        return achievementType;
    }
}
