package com.example.nils_martin.hubba;

import com.example.nils_martin.hubba.Model.Achievement;
import com.example.nils_martin.hubba.Model.AchievementFactory;
import com.example.nils_martin.hubba.Model.AchievementType;
import com.example.nils_martin.hubba.Model.Habit;
import com.example.nils_martin.hubba.Model.HubbaModel;
import com.example.nils_martin.hubba.Model.IHabit;
import com.example.nils_martin.hubba.Model.IHubbaModel;
import com.example.nils_martin.hubba.Model.NumOfHabitsAchievement;
import com.example.nils_martin.hubba.Model.StreakAchievement;
import com.example.nils_martin.hubba.Model.User;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class AchievementTest {

    @Test
    public void testStreakAchievement(){
        IHubbaModel model = HubbaModel.getInstance();
        model.setCurrentUser(new User("Erik", "Fisk@gmail.com","0000", "ABS"));
        List<Integer> days =  new ArrayList<Integer>();
        days.add(1);
        days.add(2);
        model.getCurrentUser().addHabit(new Habit("Fiska",days));
        List<IHabit> habits = model.getCurrentUser().getHabits();
        for (IHabit i: habits) {
            i.setDone();
        }

        Achievement streak = AchievementFactory.getAchievement(AchievementType.StreakAchievement, "Streak Test", 1);
        streak.assessAchievement();
        assertTrue(streak.getsAchieved());
    }
    @Test
    public void testStreakAchievementFalse(){
        IHubbaModel model = HubbaModel.getInstance();
        model.setCurrentUser(new User("Erik", "Fisk@gmail.com","0000", "ABS"));
        List<Integer> days =  new ArrayList<Integer>();
        days.add(1);
        days.add(2);
        model.getCurrentUser().addHabit(new Habit("Fiska",days));

        Achievement streak = AchievementFactory.getAchievement(AchievementType.StreakAchievement, "Streak Test", 1);
        streak.assessAchievement();
        assertFalse(streak.getsAchieved());
    }

    @Test
    public void testNumOfHabitsAchievement(){
        IHubbaModel model = HubbaModel.getInstance();
        model.setCurrentUser(new User("Erik", "Fisk@gmail.com","0000", "ABS"));
        List<Integer> days =  new ArrayList<Integer>();
        days.add(1);
        days.add(2);
        model.getCurrentUser().addHabit(new Habit("Fiska",days));
        model.getCurrentUser().addHabit(new Habit("Ramla",days));
        List<IHabit> habits = model.getCurrentUser().getHabits();
        Achievement num = AchievementFactory.getAchievement(AchievementType.NumOHabitsAchievement, "Numofhabittest", 2);
        assertTrue(num.getsAchieved());
    }
    @Test
    public void testNumOfHabitsAchievementFalse(){
        IHubbaModel model = HubbaModel.getInstance();
        model.setCurrentUser(new User("Erik", "Fisk@gmail.com","0000", "ABS"));
        List<Integer> days =  new ArrayList<Integer>();
        days.add(1);
        days.add(2);
        List<IHabit> habits = model.getCurrentUser().getHabits();
        Achievement num = AchievementFactory.getAchievement(AchievementType.NumOHabitsAchievement, "Numofhabittest", 2);
        assertFalse(num.getsAchieved());
    }
}
