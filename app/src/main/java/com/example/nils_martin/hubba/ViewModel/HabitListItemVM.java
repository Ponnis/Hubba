package com.example.nils_martin.hubba.ViewModel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nils_martin.hubba.R;

import java.util.ArrayList;
import java.util.List;

public class HabitListItemVM extends AppCompatActivity{

    String name;
    TextView textView;
    CheckBox checkBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView(){
        textView = findViewById(R.id.listItemTextView);
        checkBox = findViewById(R.id.checkboxIsDone);
    }

    public void setName(String string){
        name = string;
    }

}
