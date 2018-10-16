package com.example.nils_martin.hubba.Model;



public class Achievement {

    //private User user;
    private String title = "";
    private Boolean isAchieved = false;
    private String imagePath;

    Achievement(String title){
        this.title = title;
    }
    //Private so you can't create objects of superclass.
    private Achievement(String title, String imagePath){
        this.title = title;
        this.imagePath = imagePath;

    }

    public void setAchieved(Boolean achieved) {
        isAchieved = achieved;
        //Call for achievedAlert.
    }

    public void assessAchievement(){
        
    }
}
