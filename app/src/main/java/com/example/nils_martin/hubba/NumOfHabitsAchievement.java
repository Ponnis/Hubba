package com.example.nils_martin.hubba;

import android.widget.ImageView;

import java.util.Observable;

public class NumOfHabitsAchievement extends Achievement  {

    static AchievementType achievementType = AchievementType.NumOHabitsAchievement;

    NumOfHabitsAchievement(String title){
        super(title);
    }

    @Override
    public void assessAchievement(){

    }
}
