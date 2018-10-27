package com.example.nils_martin.hubba.ViewModel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.nils_martin.hubba.Model.HubbaModel;
import com.example.nils_martin.hubba.Model.User;
import com.example.nils_martin.hubba.R;

import java.util.ArrayList;
import java.util.List;

public class LoginVM extends AppCompatActivity {
    HubbaModel model = HubbaModel.getInstance();
    private EditText Username;
    private EditText Password;
    private Button NewUser;
    private Button Login;
    private List<User> userList = model.getUsers();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        //Should be in controller?
        if (userList.size() == 0){
            userList.add(new User("admin", "testemail@gmail.com", "1234", new ArrayList<>()));
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
        Intent intent = new Intent(LoginVM.this, CreateUserVM.class);
        startActivity(intent);
    }
    //Should also be in controller?
    private void checkLoginAcceptance(){
        for(User user: userList){
            if(user.getUserName().equals(Username.getText().toString())){
                if(user.getPassword().equals(Password.getText().toString())){
                    Intent intent = new Intent(LoginVM.this, MainActivityVM.class);
                    startActivity(intent);
                    model.setCurrentUser(user);
                    break;
                }
            }
        }
    }
    public EditText getUsername(){
return Username;
    }


}
