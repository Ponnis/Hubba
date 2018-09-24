package com.example.nils_martin.hubba;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

 public class CreateUser extends AppCompatActivity {

    private EditText NewUsername;
    private EditText NewEmail;
    private EditText NewPassword;
    private Button CreateNewUser;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_user);

        NewUsername = (EditText)findViewById(R.id.txtNewUsername);
        NewPassword  =(EditText)findViewById(R.id.txtNewPassword);
        NewEmail = (EditText)findViewById(R.id.txtNewEmail);
        CreateNewUser = (Button)findViewById(R.id.btnCreateNewUser);

        CreateNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!NewUsername.getText().toString().isEmpty() && !NewEmail.getText().toString().isEmpty()
                        && !NewPassword.getText().toString().isEmpty()){
                    addUser();
                }
            }
        });
    }

    private void addUser(){
        User user = new User(NewUsername.getText().toString(), NewEmail.getText().toString(), NewPassword.getText().toString());
        LoginView.userList.add(user);
        finish();
    }




}
