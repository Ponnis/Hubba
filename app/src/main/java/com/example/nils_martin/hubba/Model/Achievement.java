package com.example.nils_martin.hubba.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Johannes Gustavsson & Nils-Marting Robeling
 *
 * This is a template for Achievement classes.
 */

public abstract class Achievement {
    protected IHubbaModel model = HubbaModel.getInstance();


    //private User user;
    private String title = "";
    private Boolean isAchieved;
    protected int targetNmbr;

    Achievement(String title, int targetNmbr){
        this.title = title;
        this.targetNmbr = targetNmbr;
    }

    protected void setAchieved(Boolean achieved) {
        isAchieved = achieved;
    }

    abstract public Boolean assessAchievement(List<IHabit> habits);

    /**
     *Calls on method assessAchievement
     * @return The outcome from AssessAchievement();
     */
    public boolean getsAchieved(ArrayList<IHabit> habits){
        return assessAchievement(habits);
    }
    public String getTitle() {
        return title;
    }
    abstract public AchievementType getAchievementType();

    public Boolean getAchieved() {
        return isAchieved;
    }
}
