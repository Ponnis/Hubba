package com.example.nils_martin.hubba.ViewModel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.nils_martin.hubba.Model.HubbaModel;
import com.example.nils_martin.hubba.Model.ThemableObserver;
import com.example.nils_martin.hubba.R;

public class MenuGroupsVM extends AppCompatActivity implements ThemableObserver {

    ListView yourGroupsListView;
    Button addGroupButton;
    Themehandler themehandler = new Themehandler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(themehandler.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_groups);
        init();
        themehandler.addThemeListener(this);
    }

    private void init(){
        initFindByView();
        initOnClickListeners();
    }

    private void initOnClickListeners(){
        addGroupOnClick();
    }

    private void addGroupOnClick(){
        addGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO add a page for adding group
            }
        });
    }

    private void initFindByView(){
        yourGroupsListView = (ListView) findViewById(R.id.yourGroupsListView);
        addGroupButton = (Button) findViewById(R.id.addGroupBtn);
    }

    @Override
    public void recreateActivity() {
        recreate();
    }
}
