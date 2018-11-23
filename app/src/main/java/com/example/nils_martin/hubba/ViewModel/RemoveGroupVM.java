package com.example.nils_martin.hubba.ViewModel;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.nils_martin.hubba.Model.Achievement;
import com.example.nils_martin.hubba.Model.Group;
import com.example.nils_martin.hubba.Model.HubbaModel;
import com.example.nils_martin.hubba.Model.IFriend;
import com.example.nils_martin.hubba.Model.IHabit;
import com.example.nils_martin.hubba.Model.ThemableObserver;
import com.example.nils_martin.hubba.Model.User;
import com.example.nils_martin.hubba.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Li Rönning
 */
public class RemoveGroupVM extends AppCompatActivity implements ThemableObserver {

    private HubbaModel model = HubbaModel.getInstance();
    private ThemeHandler themeHandler = new ThemeHandler();

    private Button yesButton;
    private Button noButton;
    private TextView groupTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(themeHandler.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remove_group);
        themeHandler.addThemeListener(this);
        init();
    }

    private void init() {
        initFindByView();
        initOnClickListener();
        initTextView();
    }

    private void initFindByView() {
        yesButton = findViewById(R.id.yesGroupBtn);
        noButton = findViewById(R.id.noGroupBtn);
        groupTextView = findViewById(R.id.groupTextView);
    }

    private void initOnClickListener() {
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeGroup();
                finish();
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void removeGroup(){
        for (Group group : model.getCurrentUser().getGroups()){
            if(group.getGroupName().equals(getIntent().getStringExtra("GROUP"))){
                model.getCurrentUser().getGroups().remove(group);
                break;
            }
        }
    }

    private void initTextView(){groupTextView.setText(getIntent().getStringExtra("GROUP"));}

    @Override
    public void recreateActivity() {
        recreate();
    }
}
