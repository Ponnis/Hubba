package com.example.nils_martin.hubba.ViewModel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nils_martin.hubba.Model.HubbaModel;
import com.example.nils_martin.hubba.Model.User;
import com.example.nils_martin.hubba.R;

public class ProfileVM extends AppCompatActivity {

    HubbaModel model = HubbaModel.getInstance();
    User user;
    TextView Username;
    TextView Email;
    ImageView ProfilePic;
    Button ChangePic;

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
        initButtons();
        initImage();
    }



    private void initFindByView(){
        Username = findViewById(R.id.userNameTextView);
        Email = findViewById(R.id.usersEmailTextView);
        ChangePic = findViewById(R.id.changePicBtn);
        ProfilePic = findViewById(R.id.profilePicImg);
    }

    private void getUserInformation(){
        user = model.getCurrentUser();
    }

    private void setUserInformaion(){
        Username.setText(user.getName());
        Email.setText(user.getEmail());
    }

    private void initButtons() {
        ChangePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initImage() {
        //TODO
        //ProfilePic.setImageResource(R.drawable.);
    }
}
