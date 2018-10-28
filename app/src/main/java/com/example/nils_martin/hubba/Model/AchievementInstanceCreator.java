package com.example.nils_martin.hubba.Model;

import com.google.gson.InstanceCreator;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class AchievementInstanceCreator implements InstanceCreator<Achievement> {
    public Achievement createInstance(Type type) {
        return new Achievement("title", 0) {
            @Override
            public Boolean assessAchievement(ArrayList<IHabit> habit) {
                return null;
            }

            @Override
            public AchievementType getAchievementType() {
                return null;
            }
        };
    }
}
