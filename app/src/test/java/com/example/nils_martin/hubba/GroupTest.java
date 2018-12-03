package com.example.nils_martin.hubba;

import com.example.nils_martin.hubba.Model.HubbaModel;
import com.example.nils_martin.hubba.Model.IFriend;
import com.example.nils_martin.hubba.Model.Group;
import com.example.nils_martin.hubba.Model.Habit;
import com.example.nils_martin.hubba.Model.IHabit;
import com.example.nils_martin.hubba.Model.IHubbaModel;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertNotNull;



public class GroupTest {
    IHubbaModel model = HubbaModel.getInstance();
    List<IFriend> friends = new ArrayList<>();
    IHabit habit = new Habit("Drink Water");

    @Test
    public void testCreateGroup(){
        friends.add(model.getNewFriend("1", "1","1",new ArrayList<>()));
        friends.add(model.getNewFriend("2", "2","2",new ArrayList<>()));
        friends.add(model.getNewFriend("3", "3","3",new ArrayList<>()));

        Group group = new Group("Test Group", friends, habit);

        assertNotNull(group);
    }
    
}
