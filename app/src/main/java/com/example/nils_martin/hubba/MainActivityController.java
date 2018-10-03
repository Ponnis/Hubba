package com.example.nils_martin.hubba;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivityController extends AppCompatActivity {

    private ListView morningListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final LinearLayout morningLinearLayout = (LinearLayout) findViewById(R.id.morningList);
        LinearLayout middayLinearLayout = (LinearLayout) findViewById(R.id.middayList);
        LinearLayout eveningLinearLayout = (LinearLayout) findViewById(R.id.eveningList);
        LinearLayout doneLinearLayout = (LinearLayout) findViewById(R.id.doneList);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            addItem(i + 1 + "", morningLinearLayout);
            addItem(i + 1 + "", middayLinearLayout);
            addItem(i + 1 + "", eveningLinearLayout);
            addItem(i + 1 + "", doneLinearLayout);
        }

        init();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    private void addItem(String s, LinearLayout linearLayout) {
        ConstraintLayout consLayout = (ConstraintLayout) View.inflate(this, R.layout.habbit_list_item, null);
        TextView textview = (TextView) consLayout.getViewById(R.id.habbit_list_item);
        textview.setText(s);
        linearLayout.addView(consLayout);
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


}
