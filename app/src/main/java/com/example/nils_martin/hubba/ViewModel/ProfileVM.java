package com.example.nils_martin.hubba.ViewModel;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nils_martin.hubba.Model.HubbaModel;
import com.example.nils_martin.hubba.Model.User;
import com.example.nils_martin.hubba.R;

public class ProfileVM extends AppCompatActivity {

    private static final int PERMISSION_REQUEST = 0;
    private static final int RESULT_LOAD_IMAGE = 1;


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

                /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED){
                    requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST);
                }*/


                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);

            }
        });
    }

    private void initImage() {
        ProfilePic.setImageResource(R.drawable.profilepic);
    }
}
