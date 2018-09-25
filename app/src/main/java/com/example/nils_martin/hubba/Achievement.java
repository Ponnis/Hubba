package com.example.nils_martin.hubba;

import android.widget.ImageView;

public abstract class Achievement {

    private String title = "";
    private Boolean isAchieved = false;
    private ImageView achievementImage;
    private String imagePath;

    Achievement(String title, String imagePath){
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
