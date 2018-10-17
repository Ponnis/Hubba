package com.example.nils_martin.hubba.ViewModel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nils_martin.hubba.R;

import java.util.ArrayList;
import java.util.List;

public class HabitListItemVM extends AppCompatActivity{

    TextView textView;
    CheckBox checkBox;
    MainActivityVM mainActivityVM;


    public HabitListItemVM(){
        textView = findViewById(R.id.listItemTextView);
        checkBox = findViewById(R.id.checkboxIsDone);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.habit_list_item);
        initView();
        update();
    }

    private void initView(){
        textView = findViewById(R.id.listItemTextView);
        checkBox = findViewById(R.id.checkboxIsDone);
    }

    private void update(){
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivityVM.clicked(v, textView.getText().toString());
            }
        });
    }

    protected void setTextView(String string){
        textView.setText(string);
    }

    public void isClicked(View v){
        mainActivityVM.clicked(v, this.textView.getText().toString());
    }

}
