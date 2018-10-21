package com.example.nils_martin.hubba;

import com.example.nils_martin.hubba.Model.Achievement;
import com.example.nils_martin.hubba.Model.AchievementFactory;
import com.example.nils_martin.hubba.Model.AchievementType;
import com.example.nils_martin.hubba.Model.Habit;
import com.example.nils_martin.hubba.Model.HubbaModel;
import com.example.nils_martin.hubba.Model.IHubbaModel;
import com.example.nils_martin.hubba.Model.StreakAchievement;
import com.example.nils_martin.hubba.Model.Themes;
import com.example.nils_martin.hubba.Model.User;

import org.junit.Test;

import static org.junit.Assert.*;

public class ExampleUnitTest {
    @Test
    public void testCreateHabit() {
        Habit habit = new Habit("Drink Water");
        assertNotNull(habit);
    }

    @Test
    public void testCreateUser(){
        User user = new User("Åke", "Åke@gmail.com","Ninja1337", "ABS");
        assertNotNull(user);
    }

    @Test
    public void testThemeChange(){
        IHubbaModel hubbaModel = HubbaModel.getInstance();
        hubbaModel.setCurrentUser(new User("Åke", "Åke@gmail.com", "Ninja1337", "ABS"));
        String themeOnStart = hubbaModel.themeEnumToString();
        hubbaModel.setTheme(Themes.ELITE);
        String themeAfterChange = hubbaModel.themeEnumToString();
        assert !themeOnStart.equals(themeAfterChange);

    }

    @Test
    public void testCreateStreakAchievement(){
        IHubbaModel model = HubbaModel.getInstance();
        Achievement streakAchievement = AchievementFactory.getAchievement(AchievementType.StreakAchievement,"You have achieved this habit 10 days in a row!", 10);
        assertNotNull(streakAchievement);
    }

    @Test
    public void testUpStreak() {
        Habit habit = new Habit("test");
        habit.setDone();
        assertTrue(habit.getStreak() == 1);
    }
    /*@Test
    public void testNewUserButton(){
    LoginVM loginView = new LoginVM();
    assertTrue(LoginVM.userList.isEmpty());
    }*/


}