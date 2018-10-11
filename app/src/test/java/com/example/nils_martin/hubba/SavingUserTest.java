package com.example.nils_martin.hubba;
import android.os.Bundle;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SavingUserTest {
    @Test
    public void testSaveUser(){
        HubbaModel.getInstance().setUsers(new ArrayList<>());
        HubbaModel.addUser(new User("123","123","123"));
        MainActivityController mainActivityController = new MainActivityController();
        mainActivityController.onPause();
        assertNotNull(HubbaModel.getUsers());
    }

}
