package com.example.nils_martin.hubba.Model;



public abstract class Achievement {



    //private User user;
    private String title = "";
    private Boolean isAchieved;



    private String imagePath;

    Achievement(String title){
        this.title = title;
    }
    //Private so you can't create objects of superclass.
    Achievement(String title, String imagePath){
        this.title = title;
        this.imagePath = imagePath;
        this.isAchieved = false;

    }

    public void setAchieved(Boolean achieved) {
        isAchieved = achieved;
        //Call for achievedAlert.
    }

    abstract public Boolean assessAchievement();

    public boolean getisAchived(){
        isAchieved = assessAchievement();
        if(isAchieved){ return true; }
        else return false;
    }
    public String getTitle() {
        return title;
    }
    public String getImagePath() {
        return imagePath;
    }
}
