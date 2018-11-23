package com.example.nils_martin.hubba.ViewModel;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nils_martin.hubba.Model.Group;
import com.example.nils_martin.hubba.Model.Achievement;
import com.example.nils_martin.hubba.Model.HubbaModel;
import com.example.nils_martin.hubba.Model.IFriend;
import com.example.nils_martin.hubba.Model.IHabit;
import com.example.nils_martin.hubba.Model.ThemableObserver;
import com.example.nils_martin.hubba.Model.User;
import com.example.nils_martin.hubba.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProfileEditInformationVM extends AppCompatActivity implements ThemableObserver{
    private HubbaModel model = HubbaModel.getInstance();
    private ThemeHandler themehandler = new ThemeHandler();
    private ThemeHandler themeHandler = new ThemeHandler();
    private ImageButton backButton;



    private TextView Username;
    private TextView Email;
    private Button Cancel;
    private Button Save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(themeHandler.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_profile_edit_information);
        themeHandler.addThemeListener(this);
        init();
    }

    /**
     * A method that initializes the different objects that exists in the View, as well as giving listeners to the buttons
     */
    private void init() {
        Username = (TextView) findViewById(R.id.profileEditUsername);
        Email = (TextView) findViewById(R.id.profileEditEmail);
        Cancel = (Button) findViewById(R.id.profileEditCancel);
        Save = (Button) findViewById(R.id.profileEditSave);
        backButton = findViewById(R.id.backBtn12);

        Username.setText(model.getCurrentUser().getUserName());
        Email.setText(model.getCurrentUser().getEmail());

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
                    for (User user: model.getUsers()){
                        if (Username.getText().toString().equals(user.getUserName())){
                            Toast.makeText(ProfileEditInformationVM.this, "The username already exists, please choose another one!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    //TODO Ad changes to the User
                    model.getCurrentUser().setUserName(Username.getText().toString());
                    model.getCurrentUser().setEmail(Email.getText().toString());
                    finish();
                }
            }
        });
    }

    @Override
    public void recreateActivity() {
        recreate();
    }
}
