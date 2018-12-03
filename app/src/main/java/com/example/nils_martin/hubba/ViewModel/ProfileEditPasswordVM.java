package com.example.nils_martin.hubba.ViewModel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nils_martin.hubba.Model.HubbaModel;
import com.example.nils_martin.hubba.Model.ThemableObserver;
import com.example.nils_martin.hubba.R;
import com.example.nils_martin.hubba.Services.IPersistantDataHandler;
import com.example.nils_martin.hubba.Services.PersistantDataHandler;

import org.json.JSONException;

public class ProfileEditPasswordVM extends AppCompatActivity implements ThemableObserver {

    private IPersistantDataHandler service = PersistantDataHandler.getInstance();
    private HubbaModel model = HubbaModel.getInstance();
    private ThemeHandler themeHandler = new ThemeHandler();
    private HubbaModel hubbaModel = HubbaModel.getInstance();



    private TextView CurrentPassword;
    private TextView NewPassword;
    private TextView ConfirmPassword;
    private Button Cancel;
    private Button Save;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(themeHandler.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_profile_edit_password);
        themeHandler.addThemeListener(this);
        init();
    }

    /**
     * A method that initializes the different objects that exists in the View, as well as giving listeners to the buttons
     */
    private void init() {
        CurrentPassword = (TextView) findViewById(R.id.profileEditPassword);
        NewPassword = (TextView) findViewById(R.id.profileNewPassword);
        ConfirmPassword = (TextView) findViewById(R.id.profileConfirmPassword);
        Cancel = (Button) findViewById(R.id.profileEditCancel2);
        Save = (Button) findViewById(R.id.profileEditSave2);

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CurrentPassword.getText().toString().matches("") ||
                        NewPassword.getText().toString().matches("") ||
                        ConfirmPassword.getText().toString().matches("")){
                    Toast.makeText(ProfileEditPasswordVM.this, "You have to fill in all fields!", Toast.LENGTH_LONG).show();
                    return;
                }
                if (CurrentPassword.getText().toString().equals(model.getCurrentUser().getPassword()) &&
                        NewPassword.getText().toString().equals(ConfirmPassword.getText().toString())){

                    model.getCurrentUser().setPassword(NewPassword.getText().toString());
                }

                else {
                    Toast.makeText(ProfileEditPasswordVM.this, "Check and see that all fields are correct", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public void recreateActivity() {
        recreate();
    }

    @Override
    protected void onPause() {
        try {
            service.save(this.getApplicationContext());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        super.onPause();
    }

}
