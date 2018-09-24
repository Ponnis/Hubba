package com.example.nils_martin.hubba;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class LoginView extends AppCompatActivity{
    private EditText Username;
    private EditText Password;
    private Button NewUser;
    private Button Login;
    static List<User> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        if (userList.size() == 0){
            userList.add(new User("admin", "testemail@gmail.com", "1234"));
        }

        Username = (EditText)findViewById(R.id.txtUsername);
        Password = (EditText)findViewById(R.id.txtPassword);
        NewUser = (Button)findViewById(R.id.btnNewUser);
        Login = (Button)findViewById(R.id.btnLogin);

        NewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newUserButton();
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLoginAcceptance();
            }
        });
    }

    private void newUserButton (){
        Intent intent = new Intent(LoginView.this, CreateUser.class);
        startActivity(intent);
    }

    private void checkLoginAcceptance(){
        for(User user: userList){
            if(user.name.equals(Username.getText().toString())){
                if(user.password.equals(Password.getText().toString())){
                    Intent intent = new Intent(LoginView.this, MainActivityController.class);
                    startActivity(intent);
                }
            }
        }
    }
}
