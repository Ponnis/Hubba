package com.example.nils_martin.hubba.ViewModel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.example.nils_martin.hubba.Model.HubbaModel;
import com.example.nils_martin.hubba.Model.User;
import com.example.nils_martin.hubba.R;

import java.util.List;

public class MenuFriendsVM extends AppCompatActivity {

    HubbaModel model = HubbaModel.getInstance();
    List<User> friends;
    LinearLayout yourFriendsLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_friends);
        init();
    }

    private void init(){
        initFindByView();
        getFriendsList();
    }

    private void initFindByView(){
        yourFriendsLinearLayout = (LinearLayout) findViewById(R.id.yourFriendsLinearLayout);
    }

    private void getFriendsList(){
        friends = model.getCurrentUser().getFriends();
    }
}
