package com.example.nils_martin.hubba.ViewModel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.example.nils_martin.hubba.Model.HubbaModel;
import com.example.nils_martin.hubba.Model.ThemableObserver;
import com.example.nils_martin.hubba.R;

public class MenuHabitsVM extends AppCompatActivity implements ThemableObserver {

    LinearLayout yourHabitsLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(HubbaModel.getInstance().getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_habits);
        init();
        HubbaModel.getInstance().addThemeListener(this);
    }

    private void init(){
        initFindByView();
    }

    private void initFindByView() {
        yourHabitsLinearLayout = (LinearLayout) findViewById(R.id.yourHabitsLinearLayout);
    }

    @Override
    public void recreateActivity() {
        recreate();
    }
}
