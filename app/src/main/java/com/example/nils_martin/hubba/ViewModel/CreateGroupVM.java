package com.example.nils_martin.hubba.ViewModel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.nils_martin.hubba.Model.IFriend;
import com.example.nils_martin.hubba.Model.Group;
import com.example.nils_martin.hubba.Model.HubbaModel;
import com.example.nils_martin.hubba.Model.IHabit;
import com.example.nils_martin.hubba.Model.ThemableObserver;
import com.example.nils_martin.hubba.Model.User;
import com.example.nils_martin.hubba.R;

import java.util.ArrayList;
import java.util.List;

public class CreateGroupVM extends AppCompatActivity implements ThemableObserver {

    HubbaModel hubbaModel = HubbaModel.getInstance();
    private User user;
    private List friends;
    private List<IFriend> groupMembers = new ArrayList<>();
    private String groupName;
    private IHabit habit;
    private String friendNames;
    private List<String> friendsAsString = new ArrayList<>();
    private Button createNewGroupHabit;
    private ImageButton backButton;
    private Themehandler themehandler = new Themehandler();

//Doesn't need constructor but just saving it in case
  /*  public CreateGroupVM(String friendNames, Habit habit, String groupName, User user){
        this.friendNames = friendNames;
        this.habit = habit;
        this.groupName = groupName;
        this.user=user;
    }*/

    protected void onCreate(Bundle savedInstanceState) {
        setTheme(themehandler.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_group);
        getUserToCurrent();
        getUserFriends();
        themehandler.addThemeListener(this);
        groupName = String.valueOf((EditText) findViewById(R.id.txtGroupName));
        friendNames = String.valueOf((EditText) findViewById(R.id.txtGroupMembers));
        createNewGroupHabit = (Button) findViewById(R.id.btnCreateNewGroup);
        // createNewGroupHabit.setOnClickListener();
        createNewGroupHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFriendsAsString();
                Intent intent = new Intent(CreateGroupVM.this, CreateGroupHabitVM.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
    }



        private void getUserToCurrent () {
            user = hubbaModel.getCurrentUser();
        }

        //Kollar så att usern finns med i vänlistan
        private List<IFriend> checkUserNameToFriend () {
            for (int i = 0; i < friends.size(); i++) {
                for (String string : friendsAsString) {
                    IFriend tempFriend = (IFriend) friends.get(i);
                    if (tempFriend.getUserName().equals(string)) {
                        groupMembers.add(user);
                    }
                }
            }
            return groupMembers;
        }

        private void setFriendsAsString () {
            if (friendNames == null) {
            } else if (!(friendNames.contains(","))) {
                friendsAsString.add(friendNames);
            } else {
                loopFriendsIntoList();
            }
        }
        private void loopFriendsIntoList () {
            String[] tempFriends = friendNames.split(",");
            for (int i = 0; i < tempFriends.length; i++) {
                friendsAsString.add(tempFriends[i]);

            }
        }
        /**
         * Gets the users friends
         * */
        private void getUserFriends () {
            if (user.getFriends().isEmpty()) {

            } else {
                friends = user.getFriends();
            }
        }
        /**
         * Sets the new groups habit to the one you created
         * */
        private void setCreatedHabit (IHabit habit){
            if (habit.getHabitTypeState().toString().equals("GroupHabit")) {
                this.habit = habit;
            }
        }
        /**
         * Creates a new group based on you input parameters, also adds the group to the model.
         * */
        private void createNewGroup () {
            checkUserNameToFriend();
            Group group = new Group(groupName, groupMembers, habit);
            hubbaModel.getCurrentUser().getGroups().add(group);
        }

        @Override
        protected void onStop () {
            super.onStop();
            createNewGroup();
            finish();
        }

        @Override
        public void recreateActivity () {
            int size = hubbaModel.getCurrentUser().getHabits().size();
            setCreatedHabit(hubbaModel.getCurrentUser().getHabits().get(size));
            recreate();
        }

        //TODO create method to add checked friends to group list


}
