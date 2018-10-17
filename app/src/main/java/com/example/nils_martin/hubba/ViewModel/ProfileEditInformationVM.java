package com.example.nils_martin.hubba.ViewModel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nils_martin.hubba.Model.HubbaModel;
import com.example.nils_martin.hubba.Model.User;
import com.example.nils_martin.hubba.R;

public class ProfileEditInformationVM extends AppCompatActivity{

    private HubbaModel hubbaModel = HubbaModel.getInstance();

    private TextView Username;
    private TextView Email;
    private Button Cancel;
    private Button Save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_profile_edit_information);

        init();
    }

    private void init() {
        Username = (TextView) findViewById(R.id.profileEditUsername);
        Email = (TextView) findViewById(R.id.profileEditEmail);
        Cancel = (Button) findViewById(R.id.profileEditCancel);
        Save = (Button) findViewById(R.id.profileEditSave);

        Username.setText(hubbaModel.getCurrentUser().getUserName());
        Email.setText(hubbaModel.getCurrentUser().getEmail());

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Username.getText().toString().matches("") || Email.getText().toString().matches("")){
                    Toast.makeText(ProfileEditInformationVM.this,"You have to fill in all fields!", Toast.LENGTH_LONG).show();
                }
                else {
                    for (User user: hubbaModel.getUsers()){
                        if (Username.getText().toString().equals(user.getUserName())){
                            Toast.makeText(ProfileEditInformationVM.this, "The username already exists, please choose another one!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    //TODO Ad changes to the User
                    //hubbaModel.getCurrentUser().
                }
            }
        });
    }
}
