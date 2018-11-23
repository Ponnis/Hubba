package com.example.nils_martin.hubba.ViewModel;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;


import com.example.nils_martin.hubba.Model.Group;
import com.example.nils_martin.hubba.Model.Achievement;
import com.example.nils_martin.hubba.Model.HubbaModel;
import com.example.nils_martin.hubba.Model.IFriend;
import com.example.nils_martin.hubba.Model.IHabit;
import com.example.nils_martin.hubba.Model.ThemableObserver;
import com.example.nils_martin.hubba.Model.User;
import com.example.nils_martin.hubba.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class AddFriendVM extends AppCompatActivity implements ThemableObserver {

    private HubbaModel model = HubbaModel.getInstance();
    private ThemeHandler themeHandler = new ThemeHandler();

    private EditText friendsUserName;
    private Button addFriendButton;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(themeHandler.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_friends);
        init();
        themeHandler.addThemeListener(this);
    }

    private void init(){
        initFindByView();
        initOnClickListeners();
    }

    private void initFindByView() {
        friendsUserName = (EditText) findViewById(R.id.txtFriendUsername);
        addFriendButton = (Button) findViewById(R.id.buttonAddFriend);
        backButton = findViewById(R.id.backBtn2);
    }

    /**
     * Calls methods that set what actions the buttons have on click.
     */
    private void initOnClickListeners(){
        addFriendOnClick();
        backButtonOnClick();

    }

    /**
     * Sets an onCLick listener for addFriend button and defines what happens when
     * that button is clicked.
     */
    private void addFriendOnClick(){
        addFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFriendToCurrentUser();
                finish();
            }
        });
    }
    private void backButtonOnClick(){
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    /**
     * Checks if the user tries to add herself and if the list of users in HubbaModel is empty.
     * If they are no it gets the user added from HubbaModel.
     */
    private void addFriendToCurrentUser() {
        if (!model.getUsers().isEmpty() && !(model.getCurrentUser().getUserName().equals(friendsUserName.getText().toString()))) {
            User user = model.getUser(friendsUserName.getText().toString());
            model.getCurrentUser().addFriend(user);
        }
    }

    @Override
    public void recreateActivity() {
        recreate();
    }
}
