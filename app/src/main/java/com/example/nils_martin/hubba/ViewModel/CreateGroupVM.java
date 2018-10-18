package com.example.nils_martin.hubba.ViewModel;

import android.widget.EditText;

import com.example.nils_martin.hubba.Model.Friend;
import com.example.nils_martin.hubba.Model.Group;
import com.example.nils_martin.hubba.Model.Habit;
import com.example.nils_martin.hubba.Model.HubbaModel;
import com.example.nils_martin.hubba.Model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateGroupVM {

    HubbaModel hubbaModel = HubbaModel.getInstance();
    User user;
    List friends = user.getFriends();
    List<Friend> groupMemebers;
    String groupName;
    private Habit habit;
    private EditText  friendUserName;
    private String friendNames;
    private ArrayList<String> friendsAsString =  (ArrayList<String>) Arrays.asList(friendNames.split(","));


    public CreateGroupVM(String friendNames, Habit habit, String groupName, User user){
        this.friendNames = friendNames;
        this.habit = habit;
        this.groupName = groupName;
        this.user=user;
    }
    //Kollar så att usern finns med i vänlistan
    private ArrayList<Friend> checkUserNameToFriend(){

        ArrayList<Friend> groupMembers = new ArrayList<Friend>();
        for (int i = 0;i<friends.size();i++){
            for (String string :friendsAsString){
                Friend tempFriend = (Friend) friends.get(i);
            if (tempFriend.getUserName().equals(string)){
                groupMembers.add(user);
            }
        }}
        return groupMembers;
    }

    private void createNewGroup(){
        Group group = new Group(groupName,groupMemebers,habit);
      //  friendUsername = (EditText)findViewById(R.id.txtGroupNewUsername);
        /*
        for()






          */

    }

    //TODO create method to add checked friends to group list

}
