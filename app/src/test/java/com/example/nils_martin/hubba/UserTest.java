package com.example.nils_martin.hubba;

import com.example.nils_martin.hubba.Model.Habit;
import com.example.nils_martin.hubba.Model.User;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class UserTest {

    @Test
    public void testCreateUser(){
        User user = new User("bla", "bla", "bla", "bla");
        assertNotNull(user);
    }
}
