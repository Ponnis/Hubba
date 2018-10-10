package com.example.nils_martin.hubba;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

public class MenuGroupsVM extends AppCompatActivity {

    LinearLayout yourGroupsLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_groups);
        init();
    }

    private void init(){
        initFindByView();
    }

    private void initFindByView(){
        yourGroupsLinearLayout = (LinearLayout) findViewById(R.id.yourHabitsLinearLayout);
    }
}
