package com.example.nils_martin.hubba;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ProfileVM extends AppCompatActivity {

    HubbaModel model = HubbaModel.getInstance();
    User user;
    TextView userName;
    TextView userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_profile);
        init();
    }

    private void init(){
        initFindByView();
        getUserInformation();
        setUserInformaion();
    }

    private  void initFindByView(){
        userName = findViewById(R.id.userNameTextView);
        userEmail = findViewById(R.id.usersEmailTextView);
    }

    private void getUserInformation(){
        user = model.getCurrentUser();
    }

    private void setUserInformaion(){
        userName.setText(user.getName());
        userEmail.setText(user.getEmail());
    }
}
