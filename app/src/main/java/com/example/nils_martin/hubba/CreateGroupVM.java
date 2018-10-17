package com.example.nils_martin.hubba;

import android.widget.EditText;

import com.example.nils_martin.hubba.Model.Group;
import com.example.nils_martin.hubba.Model.Habit;
import com.example.nils_martin.hubba.Model.HubbaModel;
import com.example.nils_martin.hubba.Model.User;

import java.util.ArrayList;
import java.util.Arrays;

public class CreateGroupVM {

    HubbaModel hubbaModel = HubbaModel.getInstance();
    User user;


    private Habit habit;
    private EditText  friendUserName;
    private String friendNames;
    private ArrayList<String> friendsAsString =  (ArrayList<String>) Arrays.asList(friendNames.split(","));

    //Kollar så att usern finns med i vänlistan
    private ArrayList<Friend> checkUserNameToFriend(){

        ArrayList<Friend> groupMembers = new ArrayList<Friend>();
        for (Friend friend : friend){
            for (String string :friendsAsString){
            if (friend.getName().equals(string)){
                groupMembers.add(user);
            }
        }}
        return groupMembers;
    }

    private void createNewGroup(){
        Group group = new Group(checkUserNameToFriend();,habit);
      //  friendUsername = (EditText)findViewById(R.id.txtGroupNewUsername);
        /*
        for()






          */

    }

    //TODO create method to add checked friends to group list

}
