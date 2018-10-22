package com.example.nils_martin.hubba.ViewModel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.nils_martin.hubba.Model.HubbaModel;
import com.example.nils_martin.hubba.Model.ThemableObserver;
import com.example.nils_martin.hubba.Model.User;
import com.example.nils_martin.hubba.R;

public class CreateUserVM extends AppCompatActivity  {

    HubbaModel model = HubbaModel.getInstance();
    private EditText newUsername;
    private EditText newEmail;
    private EditText newPassword;

     protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_user);

        newUsername = (EditText)findViewById(R.id.txtNewUsername);
        newPassword =(EditText)findViewById(R.id.txtNewPassword);
        newEmail = (EditText)findViewById(R.id.txtNewEmail);
        Button createNewUser = (Button) findViewById(R.id.btnCreateNewUser);

        createNewUser.setOnClickListener(v -> {

            if(!newUsername.getText().toString().isEmpty() && !newEmail.getText().toString().isEmpty()
                    && !newPassword.getText().toString().isEmpty()){
                addUser();
            }
        });
    }

    private void addUser(){
        User user = new User(newUsername.getText().toString(), newEmail.getText().toString(), newPassword.getText().toString(), null);
        model.addUser(user);
        finish();
    }

}
