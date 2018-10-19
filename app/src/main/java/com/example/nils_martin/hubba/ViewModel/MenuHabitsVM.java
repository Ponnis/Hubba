package com.example.nils_martin.hubba.ViewModel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import com.example.nils_martin.hubba.Model.ThemableObserver;
import com.example.nils_martin.hubba.R;

public class MenuHabitsVM extends AppCompatActivity implements ThemableObserver {

    ListView yourHabitsListView;
    Themehandler themehandler = new Themehandler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(themehandler.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_habits);
        init();
        themehandler.addThemeListener(this);
    }

    private void init(){
        initFindByView();
    }

    private void initFindByView() {
        yourHabitsListView = (ListView) findViewById(R.id.yourHabitsListView);
    }

    @Override
    public void recreateActivity() {
        recreate();
    }
}
