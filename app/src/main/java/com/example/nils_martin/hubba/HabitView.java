package com.example.nils_martin.hubba;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class HabitView extends AppCompatActivity {

    TextView habitTextView;
    MainActivityController mainActivityController = new MainActivityController();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.habit_view);

        habitTextView = (TextView) findViewById(R.id.habitTextView);
        habitTextView.setText(MainActivityController.openHabit.getTitle(MainActivityController.openHabit));
    }
}
