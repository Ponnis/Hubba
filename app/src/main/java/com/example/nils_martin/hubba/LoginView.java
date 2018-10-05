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
    //Temporär lösning!! Skall inte känna till modellen. Vart ska den annars få användarna från?
    static List<User> userList = HubbaModel.getUsers();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(savedInstanceState !=null){}
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        //Should be in controller?
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
    //Should also be in controller?
    private void checkLoginAcceptance(){
        for(User user: userList){
            if(user.getName().equals(Username.getText().toString())){
                if(user.getPassword().equals(Password.getText().toString())){
                    Intent intent = new Intent(LoginView.this, MainActivityController.class);
                    startActivity(intent);
                }
            }
        }
    }
    public EditText getUsername(){
return Username;
    }
}
