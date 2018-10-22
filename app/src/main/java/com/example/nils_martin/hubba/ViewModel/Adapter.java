package com.example.nils_martin.hubba.ViewModel;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.nils_martin.hubba.Model.Habit;
import com.example.nils_martin.hubba.R;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Custom adapter to get habit list items to show i listView
 */

public class Adapter extends ArrayAdapter<String>  {

    private LayoutInflater layoutInflater;
    private MainActivityVM mainActivityVM;
    private String title;
    private TextView streakNmbrView;
    private TextView textView;
    private CheckBox checkBox;

    public Adapter(Context context, MainActivityVM mainActivityVM){
        super(context, 0);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mainActivityVM = mainActivityVM;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        view = layoutInflater.inflate(R.layout.habit_list_item, parent, false);
        title = getItem(position);

        initView(view);


        textView.setText(title);
        setCheckbox(title);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivityVM.findHabit(title);
                mainActivityVM.clicked(v);
            }
        });

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Habit habit = mainActivityVM.getHabit(title);
                if(habit.getIsDone()){
                    habit.notDone();
                    checkBox.setChecked(false);
                }else{
                    habit.isDone();
                    checkBox.setChecked(true);
                }
                mainActivityVM.checked(v);
            }
        });

        return view;
    }

    private void initView(View view){
        textView = view.findViewById(R.id.listItemTextView);
        checkBox = view.findViewById(R.id.checkboxIsDone);
        streakNmbrView = view.findViewById(R.id.streakDaysTextView);
    }

    private void setCheckbox(String string){
        Habit habit = mainActivityVM.getHabit(string);
        Boolean bool = habit.getIsDone();
        checkBox.setChecked(bool);
    }


}
