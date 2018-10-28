package com.example.nils_martin.hubba.Model;

import com.google.gson.InstanceCreator;

import java.lang.reflect.Type;

public class AchievementInstanceCreator implements InstanceCreator<Achievement> {
    public Achievement createInstance(Type type) {
        return new Achievement("title", 0) {
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
