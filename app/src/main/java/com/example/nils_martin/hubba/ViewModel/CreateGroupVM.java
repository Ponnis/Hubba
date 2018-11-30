package com.example.nils_martin.hubba.ViewModel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.nils_martin.hubba.Model.Achievement;
import com.example.nils_martin.hubba.Model.IFriend;
import com.example.nils_martin.hubba.Model.Group;
import com.example.nils_martin.hubba.Model.HubbaModel;
import com.example.nils_martin.hubba.Model.IHabit;
import com.example.nils_martin.hubba.Model.IUser;
import com.example.nils_martin.hubba.Model.ThemableObserver;
import com.example.nils_martin.hubba.Model.User;
import com.example.nils_martin.hubba.R;
import com.example.nils_martin.hubba.Services.Service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nils-Martin Robeling
 */
public class CreateGroupVM extends AppCompatActivity implements ThemableObserver {

    private Service service = Service.getInstance();
    private HubbaModel model = HubbaModel.getInstance();
    private IUser user;
    private List<IFriend> friends;
    private List<IFriend> groupMembers = new ArrayList<>();
    private String groupName;
    private IHabit habit;
    private String friendNames;
    private List<String> friendsAsString = new ArrayList<>();
    private Button createNewGroupHabit;
    private ThemeHandler themeHandler = new ThemeHandler();
    private EditText groupNameEditText;
    private EditText friendNamesEditText;

    protected void onCreate(Bundle savedInstanceState) {
        setTheme(themeHandler.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_group);
        getUserToCurrent();
        getUserFriends();
        themeHandler.addThemeListener(this);
        initiateViewElements();



    }

    private void initiateViewElements(){
        groupNameEditText = ((EditText)findViewById(R.id.txtGroupName));
        friendNamesEditText = ((EditText) findViewById(R.id.txtGroupMembers));
        createNewGroupHabit = (Button) findViewById(R.id.btnCreateNewGroup);
        createNewGroupHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                groupName = groupNameEditText.getText().toString();
                friendNames = friendNamesEditText.getText().toString();
                setFriendsAsString();
                Intent intent = new Intent(CreateGroupVM.this, CreateGroupHabitVM.class);
                startActivity(intent);
            }
        });
    }

    /*@Override
    protected void onResume() {
        super.onResume();
    }*/

    private void getUserToCurrent() {
        user = model.getCurrentUser();
    }


    //Kollar så att usern finns med i vänlistan

    /**
     * Checks that the users written in the textfield are actual friends of the user.
     */
    private List<IFriend> checkUserNameToFriend() {
        for (int i = 0; i < friends.size(); i++) {
            for (String string : friendsAsString) {
                IFriend tempFriend = (IFriend) friends.get(i);
                if (tempFriend.getUserName().equals(string)) {
                    groupMembers.add(tempFriend);
                }

            }
        }
        return groupMembers;
    }

    private void setFriendsAsString() {
        if (friendNames == null) {
        } else if (!(friendNames.contains(","))) {
            friendsAsString.add(friendNames);
        } else {
            loopFriendsIntoList();
        }
    }

    /**
     * Loops the string of friends into a list
     */
    private void loopFriendsIntoList() {
        String[] tempFriends = friendNames.split(",");
        for (int i = 0; i < tempFriends.length; i++) {
            friendsAsString.add(tempFriends[i]);

        }
    }

    /**
     * Gets the users friends
     */
    private void getUserFriends() {
        if (user.getFriends().isEmpty()) {

        } else {
            friends = user.getFriends();
        }
    }

    /**
     * Sets the new groups habit to the one you created
     */
    private void setCreatedHabit(IHabit habit) {
        if (habit.getHabitTypeState().toString().equals("GroupHabit")) {
            this.habit = habit;
        }
    }

    @Override
    protected void onPause() {
        try {
            service.save(this.getApplicationContext());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        super.onPause();
    }

    /*@Override
    public void recreateActivity() {
        int size = model.getCurrentUser().getHabits().size();
        setCreatedHabit(model.getCurrentUser().getHabits().get(size));
        recreate();
    }*/

    //TODO create method to add checked friends to group list

    /**
     * Creates a new group based on you input parameters, also adds the group to the model.
     */
    private void createNewGroup() {
        checkUserNameToFriend();
        Group group = new Group(groupName, groupMembers, habit);
        user.getGroups().add(group);
    }

    @Override
    protected void onStop() {
        int size = user.getHabits().size();
        if (size!=0){
        setCreatedHabit(user.getHabits().get(size-1));}
        super.onStop();
        createNewGroup();
        finish();
    }

    @Override
    public void recreateActivity() {
        recreate();
    }

}
