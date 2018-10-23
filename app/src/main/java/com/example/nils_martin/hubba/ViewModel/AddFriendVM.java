package com.example.nils_martin.hubba.ViewModel;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.nils_martin.hubba.Model.HubbaModel;
import com.example.nils_martin.hubba.Model.ThemableObserver;
import com.example.nils_martin.hubba.Model.User;
import com.example.nils_martin.hubba.R;

public class AddFriendVM extends AppCompatActivity implements ThemableObserver {

    private HubbaModel model = HubbaModel.getInstance();
    private Themehandler themehandler = new Themehandler();

    private EditText friendsUserName;
    private Button addFriendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(themehandler.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_friends);
        init();
        themehandler.addThemeListener(this);
    }

    private void init(){
        initFindByView();
        initOnClickListeners();
    }

    private void initFindByView() {
        friendsUserName = (EditText) findViewById(R.id.txtFriendUsername);
        addFriendButton = (Button) findViewById(R.id.buttonAddFriend);
    }

    /**
     * Calls methods that set what actions the buttons have on click.
     */
    private void initOnClickListeners(){
        addFriendOnClick();
    }

    /**
     * Sets an onCLick listener for addFriend button and defines what happens when
     * that button is clicked.
     */
    private void addFriendOnClick(){
        addFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Uncomment row below when loadData is fixed.
                // addFriendToCurrentUser();
                finish();
            }
        });
    }

    /**
     * Checks if the user tries to add herself and if the list of users in HubbaModel is empty.
     * If they are no it gets the user added from HubbaModel.
     */
    private void addFriendToCurrentUser() {
        if (!model.getUsers().isEmpty() || !(model.getCurrentUser().getUserName().equals(friendsUserName.getText().toString()))) {
            User user = model.getUser(friendsUserName.getText().toString());
            model.getCurrentUser().addFriend(user);
        }
    }

    @Override
    public void recreateActivity() {
        recreate();
    }
}