package com.example.nils_martin.hubba.ViewModel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.nils_martin.hubba.Model.HubbaModel;
import com.example.nils_martin.hubba.Model.ThemableObserver;
import com.example.nils_martin.hubba.Model.User;
import com.example.nils_martin.hubba.R;

import java.util.List;

public class MenuFriendsVM extends AppCompatActivity implements ThemableObserver {

    HubbaModel model = HubbaModel.getInstance();
    Themehandler themehandler = new Themehandler();

    List<User> friends;
    ListView yourFriendsListView;
    Button addFriendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(themehandler.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_friends);
        init();
        themehandler.addThemeListener(this);
    }

    private void init(){
        initFindByView();
        getFriendsList();
    }

    private void initFindByView(){
        yourFriendsListView = (ListView) findViewById(R.id.yourFriendsListView);
        addFriendButton = (Button) findViewById(R.id.addFriendBtn);
    }

    private void getFriendsList(){
        friends = model.getCurrentUser().getFriends();
    }

    @Override
    public void recreateActivity() {
        recreate();
    }
}
