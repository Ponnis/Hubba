package com.example.nils_martin.hubba.ViewModel;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

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
    private Button addFriendButton;

    Context context = MenuFriendsVM.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(themehandler.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_friends);
        themehandler.addThemeListener(this);
        init();
    }

    private void init(){
        initFindByView();
        getFriendsList();
        fillFriendStringsList();
        yourFriendsAdapter = new ArrayAdapter<String>(
                this,
                R.layout.menu_list_item,
                friendStrings );
        yourFriendsListView.setAdapter(yourFriendsAdapter);
    }

    private void initFindByView(){
        yourFriendsListView = (ListView) findViewById(R.id.yourFriendsListView);
        addFriendButton = (Button) findViewById(R.id.addFriendBtn);
    }

    private void getFriendsList(){
        friends.add(new User("Li","","",""));
        friends.add(new User("Camilla","","",""));
        friends.add(new User("Jian","","",""));
        // friends = model.getCurrentUser().getFriends();
    }

    private void fillFriendStringsList(){
        for(User friend : friends){
            friendStrings.add(friend.getUserName());
        }
    }

    @Override
    public void recreateActivity() {
        recreate();
    }
}
