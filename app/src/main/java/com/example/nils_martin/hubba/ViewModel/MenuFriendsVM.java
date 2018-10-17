package com.example.nils_martin.hubba.ViewModel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.example.nils_martin.hubba.R;

public class MenuFriendsVM extends AppCompatActivity {

    LinearLayout yourFriendsLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_friends);
        init();
    }

    private void init(){
        initFindByView();
    }

    private void initFindByView(){
        yourFriendsLinearLayout = (LinearLayout) findViewById(R.id.yourFriendsLinearLayout);
    }
}
