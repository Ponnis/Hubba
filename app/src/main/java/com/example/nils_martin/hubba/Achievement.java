package com.example.nils_martin.hubba;

import android.widget.ImageView;

public abstract class Achievement {

    private String title = "";
    private Boolean isAchieved = false;
    private ImageView achievementImage;

    Achievement(){

    }

    public void setAchieved(Boolean achieved) {
        isAchieved = achieved;
    }

    public Boolean getAchieved() {
        return isAchieved;
    }

    public void assessAchievement(){

    }
}
