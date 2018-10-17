package com.example.nils_martin.hubba.ViewModel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.nils_martin.hubba.Model.HubbaModel;
import com.example.nils_martin.hubba.Model.User;
import com.example.nils_martin.hubba.R;

public class CreateUserVM extends AppCompatActivity {

    HubbaModel model = HubbaModel.getInstance();
    private EditText NewUsername;
    private EditText NewEmail;
    private EditText NewPassword;

     protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_user);

        NewUsername = (EditText)findViewById(R.id.txtNewUsername);
        NewPassword  =(EditText)findViewById(R.id.txtNewPassword);
        NewEmail = (EditText)findViewById(R.id.txtNewEmail);
        Button createNewUser = (Button) findViewById(R.id.btnCreateNewUser);

        createNewUser.setOnClickListener(v -> {

            if(!NewUsername.getText().toString().isEmpty() && !NewEmail.getText().toString().isEmpty()
                    && !NewPassword.getText().toString().isEmpty()){
                addUser();
            }
        });
    }

    private void addUser(){
        User user = new User(NewUsername.getText().toString(), NewEmail.getText().toString(), NewPassword.getText().toString());
        model.addUser(user);
        finish();
    }
}
