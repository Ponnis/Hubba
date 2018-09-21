package com.example.nils_martin.hubba;

import android.widget.ImageView;

public class Habit {

    private String title;
    private String timestamp;
    private int streak;
    private int goalDays;
    private Frequency frequency;
    private boolean isDone;
    private boolean isActive;
    private boolean enableNofitications;
    private ImageView image;


    public Habit(String title){
        this.title = title;
    }
}
