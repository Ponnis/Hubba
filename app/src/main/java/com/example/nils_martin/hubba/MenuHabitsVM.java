package com.example.nils_martin.hubba;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

public class MenuHabitsVM extends AppCompatActivity {

    LinearLayout yourHabitsLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_habits);
        init();
    }

    private void init(){
        initFindByView();
    }

    private void initFindByView() {
        yourHabitsLinearLayout = (LinearLayout) findViewById(R.id.yourHabitsLinearLayout);
    }
}
