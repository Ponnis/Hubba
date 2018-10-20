package com.example.nils_martin.hubba.ViewModel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.nils_martin.hubba.Model.HubbaModel;
import com.example.nils_martin.hubba.Model.ThemableObserver;
import com.example.nils_martin.hubba.R;

public class MenuGroupsVM extends AppCompatActivity implements ThemableObserver {

    ListView yourGroupsListView;
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
    }

    private void initFindByView(){
        yourGroupsListView = (ListView) findViewById(R.id.yourGroupsListView);
    }

    @Override
    public void recreateActivity() {
        recreate();
    }
}
