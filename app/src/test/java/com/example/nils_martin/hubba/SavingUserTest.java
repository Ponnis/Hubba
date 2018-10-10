package com.example.nils_martin.hubba;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SavingUserTest {
    @Test
    public void testSaveUser(){
        LoginView loginView= new LoginView();
        HubbaModel.getInstance().setUsers(new ArrayList<>());
        HubbaModel.addUser(new User("123","123","123"));

    }

}
