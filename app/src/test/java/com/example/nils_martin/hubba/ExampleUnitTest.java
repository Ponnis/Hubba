package com.example.nils_martin.hubba;

import com.example.nils_martin.hubba.Model.Habit;
import com.example.nils_martin.hubba.Model.HubbaModel;
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
        HubbaModel hubbaModel = HubbaModel.getInstance();
        hubbaModel.setCurrentUser(new User("Åke", "Åke@gmail.com", "Ninja1337", "ABS"));
        String themeOnStart = hubbaModel.themeEnumToString();
        hubbaModel.setTheme(Themes.ELITE);
        String themeAfterChange = hubbaModel.themeEnumToString();
        assert !themeOnStart.equals(themeAfterChange);

    }

    /*@Test
    public void testCreateStreakAchievement(){
        StreakAchievement streakAchievement = new StreakAchievement("You have achieved this habit 10 days in a row!");
        assertNotNull(streakAchievement);
    }*/

    @Test
    public void testUpStreak() {
        Habit habit = new Habit("test");
        habit.setDone(habit);
        assertTrue(habit.getStreak(habit) == 1);
    }
    /*@Test
    public void testNewUserButton(){
    LoginVM loginView = new LoginVM();
    assertTrue(LoginVM.userList.isEmpty());
    }*/

}