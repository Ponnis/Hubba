package com.example.nils_martin.hubba;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class HabitListItem extends AppCompatActivity{

    TextView habitListItemTextView;
    MainActivityController mainActivityController;
    View view;

    public HabitListItem(Habit habit){
        this.habitListItemTextView.setText(habit.getTitle(habit));
    }

    private void onClick(){
        mainActivityController.onClick(view);
    }

}
