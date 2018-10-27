package com.example.nils_martin.hubba.Model;

import java.util.ArrayList;

public abstract class Achievement {

    IHubbaModel model = HubbaModel.getInstance();


    //private User user;
    private String title = "";
    private Boolean isAchieved;
    protected int targetNmbr;
    ArrayList<AchivementObserver> achivementObservers;


    Achievement(String title, int targetNmbr){
        this.title = title;
        this.targetNmbr = targetNmbr;
        achivementObservers = new ArrayList<>();
    }

    public void setAchieved(Boolean achieved) {
        isAchieved = achieved;
        notifyListeners();
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

    public void addAchivementListener(AchivementObserver observer){
        achivementObservers.add(observer);
    }
    private void notifyListeners(){
        for (AchivementObserver i: achivementObservers) {
            i.update(this.title);
        }

    }

}
