package com.example.nils_martin.hubba.ViewModel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.nils_martin.hubba.Model.HubbaModel;
import com.example.nils_martin.hubba.Model.ThemableObserver;
import com.example.nils_martin.hubba.R;

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
                //TODO remove group when save works
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    public void recreateActivity() {
        recreate();
    }
}
