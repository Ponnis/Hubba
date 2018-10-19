package com.example.nils_martin.hubba;

import com.example.nils_martin.hubba.Model.HubbaModel;
import com.example.nils_martin.hubba.Model.User;
import com.example.nils_martin.hubba.ViewModel.HabitVM;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class HubbaModelTest {

    User user = new User("Sven","sven@sven.se", "123456789", "" );

    @Test
    public void testGetInstance(){
        assertNotNull(HubbaModel.getInstance());
    }

    @Test
    public void getUserTest(){
        HubbaModel.getInstance().addUser(user);
        assertEquals(HubbaModel.getInstance().getUser("Sven"), user);
    }

    @Test
    public void setCurrentUserTest(){
        HubbaModel.getInstance().setCurrentUser(user);
        assertEquals(HubbaModel.getInstance().getCurrentUser(), user);
    }
}
