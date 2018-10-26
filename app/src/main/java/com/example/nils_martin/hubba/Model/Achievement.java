package com.example.nils_martin.hubba.Model;


import java.util.List;

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

    public void setAchieved(Boolean achieved) {
        isAchieved = achieved;
        //Call for achievedAlert.
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
}
