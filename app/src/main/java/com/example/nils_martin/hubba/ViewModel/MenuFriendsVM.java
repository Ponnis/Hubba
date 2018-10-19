package com.example.nils_martin.hubba.ViewModel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.nils_martin.hubba.R;
import com.example.nils_martin.hubba.Model.HubbaModel;
import com.example.nils_martin.hubba.Model.ThemableObserver;
import com.example.nils_martin.hubba.Model.User;
import java.util.List;

public class MenuFriendsVM extends AppCompatActivity implements ThemableObserver {

    HubbaModel model = HubbaModel.getInstance();
    Themehandler themehandler = new Themehandler();

    List<User> friends;

    ListView yourFriendsListView;
    Button addFriendsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(themehandler.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_friends);
        init();
        themehandler.addThemeListener(this);
    }

    private void init() {
        initFindByView();
        initOnClickListeners();
        getFriendsList();
    }

    private void initFindByView() {
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
                // TODO add a page for adding friends
            }
        });
    }

    /**
     * Gets the list of friend from current user in HubbaModel and
     * add it to the friends list in this class.
     */
    private void getFriendsList() {
        friends = model.getCurrentUser().getFriends();
    }

    @Override
    public void recreateActivity() {
        recreate();
    }

}

