package com.example.nils_martin.hubba.ViewModel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.example.nils_martin.hubba.R;
import com.example.nils_martin.hubba.Model.HubbaModel;
import com.example.nils_martin.hubba.Model.ThemableObserver;
import com.example.nils_martin.hubba.Model.User;
import java.util.List;

public class MenuFriendsVM extends AppCompatActivity implements ThemableObserver {

    HubbaModel model = HubbaModel.getInstance();
    Themehandler themehandler = new Themehandler();

    List<User> friends;

    LinearLayout yourFriendsLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_friends);
        init();

        setTheme(themehandler.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_friends);
        init();
        themehandler.addThemeListener(this);

    }

    private void init() {
        initFindByView();
        getFriendsList();
    }

    private void initFindByView() {
        yourFriendsLinearLayout = (LinearLayout) findViewById(R.id.yourFriendsLinearLayout);
    }

    private void getFriendsList() {
        friends = model.getCurrentUser().getFriends();
    }

    @Override
    public void recreateActivity() {
        recreate();
    }

}

