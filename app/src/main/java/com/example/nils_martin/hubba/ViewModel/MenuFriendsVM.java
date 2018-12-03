package com.example.nils_martin.hubba.ViewModel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.nils_martin.hubba.Model.IFriend;
import com.example.nils_martin.hubba.Model.HubbaModel;
import com.example.nils_martin.hubba.Model.ThemableObserver;
import com.example.nils_martin.hubba.R;
import com.example.nils_martin.hubba.Services.IPersistantDataHandler;
import com.example.nils_martin.hubba.Services.PersistantDataHandler;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Li Rönning and Camilla Söderlund
 */
public class MenuFriendsVM extends AppCompatActivity implements ThemableObserver {

    private IPersistantDataHandler service = PersistantDataHandler.getInstance();
    private HubbaModel model = HubbaModel.getInstance();
    private ThemeHandler themeHandler = new ThemeHandler();

    private List<IFriend> friends = new ArrayList<>();
    private ArrayList<String> friendStrings = new ArrayList<>();
    private ListView yourFriendsListView;
    private ArrayAdapter<String> yourFriendsAdapter;
    private Button addFriendsButton;
    private ImageButton backButton;
    private IFriend openFriend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(themeHandler.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_friends);
        themeHandler.addThemeListener(this);
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
        backButton = findViewById(R.id.backBtn6);
    }

    private void initOnClickListeners(){
        addFriendOnClick();
        backButtonOnClick();
    }

    private void backButtonOnClick() {
        backButton.setOnClickListener(v -> onBackPressed());
    }

    private void addFriendOnClick(){
        addFriendsButton.setOnClickListener(v -> {
            Intent intent = new Intent(MenuFriendsVM.this, AddFriendVM.class);
            startActivity(intent);
        });
    }

    private void getFriendsList() {
        if (model.getCurrentUser().getFriends() != null) {
            friends = model.getCurrentUser().getFriends();
        }
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
        for(IFriend friend : friends){
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

    /**
     * Gets the string from the item that is clicked and then finds a friend with a username
     * that matches the item. This friend is then opened on a new page.
     */
    private void listViewOnClick () {
        yourFriendsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                findFriend(yourFriendsListView.getItemAtPosition(position).toString());
                Intent intent = new Intent(MenuFriendsVM.this, RemoveFriendVM.class);
                intent.putExtra("FRIEND", openFriend.getUserName());
                startActivity(intent);
            }
        });
    }

    /**
     * Finds the friend corresponding to the string in the friends list
     * @param string is the string of a friends username
     */
    private void findFriend(String string) {
        for(IFriend friend: friends) {
            if(friend.getUserName().equals(string)) {
                setOpenFriend(friend);
            }
        }
    }

    private  void setOpenFriend(IFriend openFriend) {
        this.openFriend = openFriend;
    }

    @Override
    protected void onResume() {
        setTheme(themeHandler.getTheme());
        super.onResume();
        themeHandler.addThemeListener(this);
        updateFriendsListView();
    }

    @Override
    public void recreateActivity() {
        recreate();
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
}
