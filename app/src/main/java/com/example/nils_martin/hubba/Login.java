package com.example.nils_martin.hubba;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class Login extends AppCompatActivity{
    private EditText Username;
    private EditText Password;
    private Button NewUser;
    private Button Login;
    static List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        /*if (userList.size() == 0){
            userList.add(new User("admin", "email", "1234"));
            System.out.println("HEJ");
        }*/

        System.out.println("HEJ");

        Username = (EditText)findViewById(R.id.txtNewUsername);
        Password = (EditText)findViewById(R.id.txtPassword);
        NewUser = (Button)findViewById(R.id.btnNewUser);
        Login = (Button)findViewById(R.id.btnLogin);

        NewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newUserButton();
            }
        });



    }

    private void newUserButton (){
        Intent intent = new Intent(Login.this, CreateUser.class);
        startActivity(intent);
    }

}
