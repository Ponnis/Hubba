package com.example.nils_martin.hubba.Model;
/**
 * @author Johannes Gustavsson & Nils-Marting Robeling
 *
 * This is a template for Achievement classes.
 */

public abstract class Achievement {

    IHubbaModel model = HubbaModel.getInstance();


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

    abstract public Boolean assessAchievement();

    /**
     *Calls on method assessAchievement
     * @return The outcome from AssessAchievement();
     */
    public boolean getsAchieved( ){
        return assessAchievement();
    }
    public String getTitle() {
        return title;
    }
    abstract public AchievementType getAchievementType();

    public Boolean getAchieved() {
        return isAchieved;
    }
}
