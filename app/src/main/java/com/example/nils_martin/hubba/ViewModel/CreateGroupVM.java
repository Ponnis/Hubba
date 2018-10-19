package com.example.nils_martin.hubba.ViewModel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.nils_martin.hubba.Model.Friend;
import com.example.nils_martin.hubba.Model.Group;
import com.example.nils_martin.hubba.Model.Habit;
import com.example.nils_martin.hubba.Model.HubbaModel;
import com.example.nils_martin.hubba.Model.User;
import com.example.nils_martin.hubba.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateGroupVM extends AppCompatActivity {

    HubbaModel hubbaModel = HubbaModel.getInstance();
    User user;
    List friends;
    List<Friend> groupMembers;
    String groupName;
    private Habit habit;
    private String friendNames;
    private ArrayList<String> friendsAsString =  (ArrayList<String>) Arrays.asList(friendNames.split(","));
    private Button createNewGroupHabit;

//Doesn't need constructor but just saving it in case
  /*  public CreateGroupVM(String friendNames, Habit habit, String groupName, User user){
        this.friendNames = friendNames;
        this.habit = habit;
        this.groupName = groupName;
        this.user=user;
    }*/
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getUserToCurrent();
        getUserFriends();
        groupName = String.valueOf((EditText)findViewById(R.id.txtGroupName));
        friendNames  = String.valueOf((EditText)findViewById(R.id.txtGroupMembers));
        Button createNewGroupHabit = (Button) findViewById(R.id.btnCreateNewGroup);
       // createNewGroupHabit.setOnClickListener();
        createNewGroupHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateGroupVM.this, CreateGroupHabitVM.class);
                startActivity(intent);
            }
        });
        int listSize = hubbaModel.getCurrentUser().getHabits().size();
        habit= (Habit) hubbaModel.getCurrentUser().getHabits().get(listSize-1);
    }

    private void getUserToCurrent(){
        user = hubbaModel.getCurrentUser();
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
    private void getUserFriends() {
        if (user.getFriends().isEmpty()) {

        } else {
            friends = user.getFriends();
        }
    }
    private void createNewGroup(){
        Group group = new Group(groupName,groupMembers,habit);
      //  friendUsername = (EditText)findViewById(R.id.txtGroupNewUsername);
        /*
        for()






          */

    }

    //TODO create method to add checked friends to group list

}
