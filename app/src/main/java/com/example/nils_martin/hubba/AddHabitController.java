package com.example.nils_martin.hubba;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AddHabitController extends AppCompatActivity {
    Button saveBtn;
    Button undoBtn;
    TextInputLayout habitInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit);
        init();
    }

    public void init() {
        saveBtn = findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddHabitController.this, MainActivityController.class);
                startActivity(intent);
            }
        });

        undoBtn = findViewById(R.id.undoBtn);
        undoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddHabitController.this, MainActivityController.class);
                startActivity(intent);
            }
        });
    }
}
