package com.example.nils_martin.hubba;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.Observable;
import java.util.Observer;

public class MainActivityController extends AppCompatActivity implements Observer {
    //TODO
    User user = new User("åke","åke","åke");
    //Ska få en user av login, ändras senare



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }


    public FloatingActionButton addBtn;
    public void init() {
        addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivityController.this, AddHabitController.class);
                startActivity(intent);
            }
        });
    }

    //TODO
    @Override
    public void update(Observable o, Object arg) {
        for (Object habit : user.getHabits()){

        }
    }
}
