package com.example.nils_martin.hubba;

import com.example.nils_martin.hubba.Model.HubbaModel;
import com.example.nils_martin.hubba.Model.IHubbaModel;
import com.example.nils_martin.hubba.Model.IUser;
import com.example.nils_martin.hubba.Model.Themes;


import org.junit.Test;

import java.util.ArrayList;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class HubbaModelTest {
    IHubbaModel model = HubbaModel.getInstance();
    IUser user = model.getNewUser("Sven","sven@sven.se", "123456789", new ArrayList<>() );
    ArrayList<IUser> users = new ArrayList<>();

    @Test
    public void testGetInstance(){
        assertNotNull(HubbaModel.getInstance());
    }

    @Test
    public void testGetUser(){
        HubbaModel.getInstance().addUser(user);
        assertEquals(HubbaModel.getInstance().getUser("Sven"), user);
    }

    @Test
    public void testGetCurrentUser(){
        HubbaModel.getInstance().setCurrentUser(user);
        assertEquals(HubbaModel.getInstance().getCurrentUser(), user);
    }

    @Test
    public void testGetUsers(){
        users.add(user);
        HubbaModel.getInstance().setUsers(users);
        assertEquals(HubbaModel.getInstance().getUsers(), users);
    }

    @Test
    public void testThemeChange() {
        IHubbaModel hubbaModel = HubbaModel.getInstance();
        hubbaModel.setCurrentUser(model.getNewUser("Åke", "Åke@gmail.com", "Ninja1337", new ArrayList<>()));
        String themeOnStart = hubbaModel.themeEnumToString();
        hubbaModel.setTheme(Themes.ELITE);
        String themeAfterChange = hubbaModel.themeEnumToString();
        assert !themeOnStart.equals(themeAfterChange);
    }
}
