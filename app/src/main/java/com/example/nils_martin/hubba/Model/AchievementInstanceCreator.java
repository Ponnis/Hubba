package com.example.nils_martin.hubba.Model;

import com.google.gson.InstanceCreator;

import java.lang.reflect.Type;

public class AchievementInstanceCreator implements InstanceCreator<Acheievement> {
    public Acheievement createInstance(Type type) {
        return new Acheievement("title", 0) {
            @Override
            public Boolean assessAchievement() {
                return null;
            }

            @Override
            public AchievementType getAchievementType() {
                return null;
            }
        };
    }
}
