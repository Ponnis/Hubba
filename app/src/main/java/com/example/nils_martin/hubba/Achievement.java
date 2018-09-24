package com.example.nils_martin.hubba;

import android.widget.ImageView;

public abstract class Achievement {

    private String title = "";
    private Boolean isAchieved = false;
    private ImageView achievementImage;

    public Achievement(String title){
        this.title = title;
    }

    public void setAchieved(Boolean achieved) {
        isAchieved = achieved;
    }

    public void assessAchievement(){
        
    }
}
