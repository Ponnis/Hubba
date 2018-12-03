package com.example.nils_martin.hubba.ViewModel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.nils_martin.hubba.Model.HubbaModel;
import com.example.nils_martin.hubba.Model.IFriend;
import com.example.nils_martin.hubba.Model.ThemableObserver;
import com.example.nils_martin.hubba.R;
import com.example.nils_martin.hubba.Services.IPersistantDataHandler;
import com.example.nils_martin.hubba.Services.PersistantDataHandler;

import org.json.JSONException;

/**
 * @author Li Rönning
 */
public class RemoveFriendVM extends AppCompatActivity implements ThemableObserver {

    private IPersistantDataHandler service = PersistantDataHandler.getInstance();
    private HubbaModel model = HubbaModel.getInstance();
    private ThemeHandler themeHandler = new ThemeHandler();


    private Button yesButton;
    private Button noButton;
    private TextView friendTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(themeHandler.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remove_friend);
        themeHandler.addThemeListener(this);
        init();
    }

    private void init() {
        initFindByView();
        initOnClickListener();
        initTextView();
    }

    private void initFindByView() {
        yesButton = findViewById(R.id.yesBtn);
        noButton = findViewById(R.id.noBtn);
        friendTextView = findViewById(R.id.friendTextView);
    }

    private void initOnClickListener() {
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeFriend();
                finish();
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initTextView(){
        friendTextView.setText(getIntent().getStringExtra("FRIEND"));
    }

    private void removeFriend(){
        for (IFriend friend : model.getCurrentUser().getFriends()){
            if(friend.getUserName().equals(getIntent().getStringExtra("FRIEND"))){
                model.getCurrentUser().getFriends().remove(friend);
                break;
            }
        }

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

