package com.example.nils_martin.hubba.ViewModel;

import com.example.nils_martin.hubba.Model.Frequency;
import com.example.nils_martin.hubba.Model.Habit;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.example.nils_martin.hubba.Model.HubbaModel;
import com.example.nils_martin.hubba.Model.State;
import com.example.nils_martin.hubba.R;

import java.util.ArrayList;
import java.util.List;

public class AddHabitVM extends AppCompatActivity {


    private EditText habitName;
    private Button save, cancel, morning, midday, evening, night, daily, weekly, monthly;
    private Habit createdHabit;
    CheckBox monCxb, tueCxb, wedCxb, thuCxb, friCxb, satCxb, sunCxb;
    private TextView numberOfDaysTxtV, colontxtV, timeTxtV, monthTxtV, wrongMesTxtV;
    private Spinner numberOfDaysSpr, hourSpr, minSpr, monthSpr;
    private Switch remainderSwitch;
    private ImageView nameWrongImgV, frequencyWrongImgV, stateWrongImgV, weekWrongImgV;
    private List<CheckBox> cbxDayList = new ArrayList<>();
    private List<CheckBox> cbxMonthList = new ArrayList<>();
    List<Integer> calendarDaysList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit);
        init();
        makeAListOfDayCbx();
        update();
    }

    public void init() {
        habitName = findViewById(R.id.habitInput);
        save = findViewById(R.id.saveBtn);
        cancel = findViewById(R.id.cancelBtn);
        morning = findViewById(R.id.morningBtn);
        midday = findViewById(R.id.middayBtn);
        evening = findViewById(R.id.eveningBtn);
        night = findViewById(R.id.nightBtn);
        daily = findViewById(R.id.dailyBtn);
        weekly = findViewById(R.id.weeklyBtn);
        monthly = findViewById(R.id.monthlyBtn);
        monCxb = findViewById(R.id.monCbx);
        tueCxb = findViewById(R.id.tueCbx);
        wedCxb = findViewById(R.id.wedCbx);
        thuCxb = findViewById(R.id.thuCbx);
        friCxb = findViewById(R.id.friCbx);
        satCxb = findViewById(R.id.satCbx);
        sunCxb = findViewById(R.id.sunCbx);
        numberOfDaysTxtV = findViewById(R.id.numTxtV);
        timeTxtV = findViewById(R.id.timeTxtV);
        colontxtV = findViewById(R.id.colontxtV);
        monthTxtV = findViewById(R.id.monthTxtV);
        wrongMesTxtV = findViewById(R.id.wrongMesTxtV);
        numberOfDaysSpr = findViewById(R.id.numSpr);
        hourSpr = findViewById(R.id.hourSpr);
        minSpr = findViewById(R.id.minSpr);
        monthSpr = findViewById(R.id.monthSpr);
        remainderSwitch = findViewById(R.id.remainderSwitch);
        nameWrongImgV = findViewById(R.id.nameImgV);
        frequencyWrongImgV = findViewById(R.id.frequencyImgV);
        stateWrongImgV = findViewById(R.id.stateImgV);
        weekWrongImgV = findViewById(R.id.weekImgV);
    }

    public void update() {

        createdHabit = new Habit("", calendarDaysList);

        habitName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeAwayWrongMessage();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeCalendarDaysList();

                createdHabit.setTitle(habitName.getText().toString());
                createdHabit.setDaysToDo(calendarDaysList);

                if(checkIfAllIsFillIn()) {
                    //MainActivityVM.habits.add(createdHabit);
                    HubbaModel.getInstance().getCurrentUser().addHabit(createdHabit);

                    endActivity();
                }
                else {
                    wrongMesTxtV.setVisibility(View.VISIBLE);
                    wrongMesTxtV.setText("You must fill in everything");
                    wrongMesTxtV.setTextColor(Color.RED);
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endActivity();
            }
        });

        morning.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                takeAwayWrongMessage();
                createdHabit.setSTATE(State.MORNING);
            }
        });

        midday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeAwayWrongMessage();
                createdHabit.setSTATE(State.MIDDAY);
            }
        });

        evening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeAwayWrongMessage();
                createdHabit.setSTATE(State.EVENING);
            }
        });

        night.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeAwayWrongMessage();
                createdHabit.setSTATE(State.NIGHT);
            }
        });
        
        daily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeAwayWrongMessage();
                dayVisible();
                createdHabit.setFREQUENCY(Frequency.DAILY);
            }
        });

        weekly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeAwayWrongMessage();
                weekVisible();
                createdHabit.setFREQUENCY(Frequency.WEEKLY);
            }
        });

        monthly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeAwayWrongMessage();
                monthVisible();
                createdHabit.setFREQUENCY(Frequency.MONTHLY);
            }
        });

        remainderSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeAwayWrongMessage();
                if(remainderSwitch.isChecked()) {
                    hourSpr.setVisibility(View.VISIBLE);
                    minSpr.setVisibility(View.VISIBLE);
                    timeTxtV.setVisibility(View.VISIBLE);
                    colontxtV.setVisibility(View.VISIBLE);
                    createdHabit.reminderEnabled();
                }
                else {
                    hourSpr.setVisibility(View.INVISIBLE);
                    minSpr.setVisibility(View.INVISIBLE);
                    timeTxtV.setVisibility(View.INVISIBLE);
                    colontxtV.setVisibility(View.INVISIBLE);
                    createdHabit.reminderDisabled();
                }
            }
        });
    }

    /**
     * This method set everything to invisible when you click on the month-button.
     */
    private void dayVisible() {
        numberOfDaysTxtV.setVisibility(View.INVISIBLE);
        numberOfDaysSpr.setVisibility(View.INVISIBLE);
        monthTxtV.setVisibility(View.INVISIBLE);
        monthSpr.setVisibility(View.INVISIBLE);

        for(int i = 0; i < cbxDayList.size(); i++) {
            cbxDayList.get(i).setVisibility(View.INVISIBLE);
        }

        for (int i = 0; i< cbxMonthList.size(); i++) {
            cbxMonthList.get(i).setVisibility(View.INVISIBLE);
        }
    }

    /**
     * This method makes it easier to see what happens when clicking on the week-button.
     */
    private void weekVisible () {
        numberOfDaysTxtV.setVisibility(View.VISIBLE);
        numberOfDaysSpr.setVisibility(View.VISIBLE);
        monthSpr.setVisibility(View.INVISIBLE);
        monthTxtV.setVisibility(View.INVISIBLE);

        for(int i = 0; i < cbxDayList.size(); i++) {
            cbxDayList.get(i).setVisibility(View.VISIBLE);
        }
    }

    /**
     * This method makes it easier to see what happens when clicking on the month-button.
     */
    private void monthVisible () {
        numberOfDaysTxtV.setVisibility(View.INVISIBLE);
        numberOfDaysSpr.setVisibility(View.INVISIBLE);
        monthTxtV.setVisibility(View.VISIBLE);
        monthSpr.setVisibility(View.VISIBLE);

        for(int i = 0; i < cbxDayList.size(); i++) {
            cbxDayList.get(i).setVisibility(View.INVISIBLE);
        }
    }

    /**
     * This method makes a list of the checkboxes because is easier to treat them as a group
     * than individual
     */
    private void makeAListOfDayCbx() {
        cbxDayList.add(sunCxb);
        cbxDayList.add(monCxb);
        cbxDayList.add(tueCxb);
        cbxDayList.add(wedCxb);
        cbxDayList.add(thuCxb);
        cbxDayList.add(friCxb);
        cbxDayList.add(satCxb);
    }

    /**
     * The method making a list of which weekdays the habit is to be done an depending on which
     * frequency that is select it so it can be shown on the right day.
     */
    private void makeCalendarDaysList () {

        calendarDaysList.clear();

        //When the frequency is daily every day is added in the list
        if(createdHabit.getFREQUENCY() == Frequency.DAILY) {
            for (int i = 0; i < 7; i++) {
                calendarDaysList.add(i+1);
            }
        }

        // When the frequency is weekly, the selected days are added to the list
        else if(createdHabit.getFREQUENCY() == Frequency.WEEKLY) {
            for (int i = 0; i < cbxDayList.size(); i++) {
                if (cbxDayList.get(i).isChecked()) {
                    calendarDaysList.add(i + 1);
                }
            }
        }

        //When the frequency is monthly, the date is added in the list
        else if(createdHabit.getFREQUENCY() == Frequency.MONTHLY) {
            calendarDaysList.add(Integer.valueOf(monthSpr.getSelectedItem().toString()));
        }
    }

    /**
     * This method go through all the fields and looks for all information you need when
     * creating a Habit, is selected
     *  @return True if everything is correct, else return false
     */
    private boolean checkIfAllIsFillIn () {
        if(createdHabit.getFREQUENCY() == null || createdHabit.getSTATE() == null
                || createdHabit.getDaysToDo().size() == 0 || createdHabit.getTitle(createdHabit).equals("")) {
            if (createdHabit.getFREQUENCY() == null) {
                frequencyWrongImgV.setVisibility(View.VISIBLE);
            }

            if (createdHabit.getDaysToDo().size() == 0 && createdHabit.getFREQUENCY() == Frequency.WEEKLY) {
                weekWrongImgV.setVisibility(View.VISIBLE);
            }

            if (createdHabit.getSTATE() == null) {
                stateWrongImgV.setVisibility(View.VISIBLE);
            }
            if (createdHabit.getTitle(createdHabit).equals("")) {
                nameWrongImgV.setVisibility(View.VISIBLE);
            }
            return false;
        }
        return true;
    }

    /**
     * This method is used because it is necessary that the wrong message disappears when you start editing
     */
    private void takeAwayWrongMessage () {
        wrongMesTxtV.setVisibility(View.INVISIBLE);
        frequencyWrongImgV.setVisibility(View.INVISIBLE);
        nameWrongImgV.setVisibility(View.INVISIBLE);
        stateWrongImgV.setVisibility(View.INVISIBLE);
        weekWrongImgV.setVisibility(View.INVISIBLE);
    }

    private void endActivity(){
        finish();
        Intent intent = new Intent(AddHabitVM.this, MainActivityVM.class);
        startActivity(intent);
    }
}
