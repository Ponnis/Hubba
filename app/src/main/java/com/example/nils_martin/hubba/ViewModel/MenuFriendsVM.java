package com.example.nils_martin.hubba.ViewModel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.nils_martin.hubba.Model.Habit;
import com.example.nils_martin.hubba.Model.HubbaModel;
import com.example.nils_martin.hubba.Model.ThemableObserver;
import com.example.nils_martin.hubba.Model.User;
import com.example.nils_martin.hubba.R;

import java.util.ArrayList;
import java.util.List;

public class MenuFriendsVM extends AppCompatActivity implements ThemableObserver {

    private HubbaModel model = HubbaModel.getInstance();
    private Themehandler themehandler = new Themehandler();

    private List<User> friends = new ArrayList<>();
    private ArrayList<String> friendStrings = new ArrayList<>();
    private ListView yourFriendsListView;
    private ArrayAdapter<String> yourFriendsAdapter;
    private Button addFriendsButton;
    private User openFriend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(themehandler.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_friends);
        themehandler.addThemeListener(this);
        init();
    }

    /**
     * Calls on other functions that initialize ListViews, list OnClicks etc.
     */
    private void init(){
        initFindByView();
        initOnClickListeners();
        updateFriendsListView();
        listViewOnClick();
    }

    /**
     * Initialize all view attributes
     */
    private void initFindByView(){
        yourFriendsListView = (ListView) findViewById(R.id.yourFriendsListView);
        addFriendsButton = (Button) findViewById(R.id.addFriendBtn);
    }

    private void initOnClickListeners(){
        addFriendOnClick();
    }

    private void addFriendOnClick(){
        addFriendsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuFriendsVM.this, AddFriendVM.class);
                startActivity(intent);
            }
        });
    }

    private void getFriendsList(){
        friends = model.getCurrentUser().getFriends();
    }

    /**
     * Calls methods that update the list and the ListView in the interface.
     */
    private void updateFriendsListView(){
        getFriendsList();
        fillFriendStringsList();
        fillFriendListView();
    }

    /**
     * First clears the list friendStrings and then updates it with the
     * names of users in friends.
     */
    private void fillFriendStringsList(){
        friendStrings.clear();
        friendStrings.add("Katt"); //TODO when save work
        for(User friend : friends){
            friendStrings.add(friend.getUserName());
        }
    }

    /**
     * Fills the ListView with strings from friendStrings.
     */
    private void fillFriendListView(){
        yourFriendsAdapter = new ArrayAdapter<String>(
                this,
                R.layout.menu_list_item,
                friendStrings );
        yourFriendsListView.setAdapter(yourFriendsAdapter);
    }

    private void listViewOnClick () {
        yourFriendsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                findFriend(yourFriendsListView.getItemAtPosition(position).toString());
                Intent intent = new Intent(MenuFriendsVM.this, RemoveFriendVM.class);
                startActivity(intent);
            }
        });
    }

    private void findFriend(String string) {
        for(User friend: friends) {
            if(friend.getUserName().equals(string)) {
                setOpenFriend(friend);
            }
        }
    }

    private  void setOpenFriend(User openFriend) {
        this.openFriend = openFriend;
    }

    @Override
    public void recreateActivity() {
        recreate();
    }
}
