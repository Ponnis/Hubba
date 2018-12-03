package com.example.nils_martin.hubba;

import com.example.nils_martin.hubba.Model.HubbaModel;
import com.example.nils_martin.hubba.Model.IFriend;
import com.example.nils_martin.hubba.Model.Habit;
import com.example.nils_martin.hubba.Model.IHubbaModel;
import com.example.nils_martin.hubba.Model.IUser;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class UserTest {
    IHubbaModel hubbaModel = HubbaModel.getInstance();
    IFriend friend =  hubbaModel.getNewFriend("IFriend", "friend@friend.com", "friendly1234", new ArrayList<>());
    IUser user = hubbaModel.getNewUser("Åke", "Åke@gmail.com","Ninja1337", new ArrayList<>());
    Habit habit = new Habit("Drink Water");

    @Test
    public void testCreateUser(){
        IUser user = hubbaModel.getNewUser("Åke", "Åke@gmail.com","Ninja1337", new ArrayList<>());
        assertNotNull(user);
    }

    @Test
    public void testAddHabit(){
        user.addHabit(habit);
        assertTrue(user.getHabits().size() != 0);
    }

    @Test
    public void testRemoveHabit(){
        user.removeHabit(habit);
        assertTrue(user.getHabits().isEmpty());
    }

    @Test
    public void testAddFriend(){
        user.addFriend(friend);
        assertTrue(user.getFriends().size() == 1);
    }

    @Test
    public void testRemoveFriend(){
        user.removeFriend(friend);
        assertTrue(user.getFriends().isEmpty());
    }
}
