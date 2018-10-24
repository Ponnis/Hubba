package com.example.nils_martin.hubba.ViewModel;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.example.nils_martin.hubba.Model.Frequency;
import com.example.nils_martin.hubba.Model.Habit;
import com.example.nils_martin.hubba.Model.HubbaModel;
import com.example.nils_martin.hubba.Model.State;
import com.example.nils_martin.hubba.Model.ThemableObserver;
import com.example.nils_martin.hubba.R;

import java.util.ArrayList;
import java.util.List;

public class EditHabitVM extends AppCompatActivity implements ThemableObserver {

    private EditText habitName;
    private Button save, cancel, morning, midday, evening, night, daily, weekly, monthly;
    private Habit currentHabit;
    CheckBox mondayCheckbox, tuesdayCheckbox, wednesdayCheckbox, thursdayCheckbox, fridayCheckbox, saturdayCheckbox, sundayCheckbox;
    private TextView numberOfDaysTextView, colonTextView, timeTextView, monthTextView, errorMsgTextView;
    private Spinner numberOfDaysSpinner, hourSpinner, minSpinner, monthSpinner;
    private Switch remainderSwitch;
    private ImageView nameWrongImgV, frequencyWrongImgV, stateWrongImgV, weekWrongImgV;
    private List<CheckBox> checkboxDayList = new ArrayList<>();
    private List<CheckBox> checkboxMonthList = new ArrayList<>();
    List<Integer> calendarDaysList = new ArrayList<>();
    HubbaModel model = HubbaModel.getInstance();
    Themehandler themehandler = new Themehandler();
    MainActivityVM mainActivityVM;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(themehandler.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_habit);
        setCurrentHabit();
        init();
        makeAListOfDayCbx();
        initSettings();
        themehandler.addThemeListener(this);
        update();
    }

    private void init() {
        habitName = findViewById(R.id.habitInputEdit);
        save = findViewById(R.id.saveButton);
        cancel = findViewById(R.id.cancelButton);
        morning = findViewById(R.id.morningButton);
        midday = findViewById(R.id.middayButton);
        evening = findViewById(R.id.eveningButton);
        night = findViewById(R.id.nightButton);
        daily = findViewById(R.id.dailyButton);
        weekly = findViewById(R.id.weeklyButton);
        monthly = findViewById(R.id.monthlyButton);
        mondayCheckbox = findViewById(R.id.monCheckbox);
        tuesdayCheckbox = findViewById(R.id.tueCheckbox);
        wednesdayCheckbox = findViewById(R.id.wedCheckbox);
        thursdayCheckbox = findViewById(R.id.thuCheckbox);
        fridayCheckbox = findViewById(R.id.friCheckbox);
        saturdayCheckbox = findViewById(R.id.satCheckbox);
        sundayCheckbox = findViewById(R.id.sunCheckbox);
        numberOfDaysTextView = findViewById(R.id.numTextView);
        timeTextView = findViewById(R.id.timeTextView);
        colonTextView = findViewById(R.id.colonTextView);
        monthTextView = findViewById(R.id.monthTextView);
        errorMsgTextView = findViewById(R.id.wrongMesTextView);
        numberOfDaysSpinner = findViewById(R.id.numSpinner);
        hourSpinner = findViewById(R.id.hourSpinner);
        minSpinner = findViewById(R.id.minSpinner);
        monthSpinner = findViewById(R.id.monthSpinner);
        remainderSwitch = findViewById(R.id.remainderSwitchEdit);
        nameWrongImgV = findViewById(R.id.nameImageView);
        frequencyWrongImgV = findViewById(R.id.frequencyImageView);
        stateWrongImgV = findViewById(R.id.stateImageView);
        weekWrongImgV = findViewById(R.id.weekImageView);
    }

    private void initSettings(){
        habitName.setText(currentHabit.getTitle());
        setFrequencyDays();
        setReminderSwitch();
    }

    private void setReminderSwitch(){
        if(currentHabit.isReminderOn()){
            remainderSwitch.setChecked(true);
            hourSpinner.setVisibility(View.VISIBLE);
            minSpinner.setVisibility(View.VISIBLE);
            timeTextView.setVisibility(View.VISIBLE);
            colonTextView.setVisibility(View.VISIBLE);

            hourSpinner.setSelection(currentHabit.getReminderTime().get(0));
            minSpinner.setSelection((currentHabit.getReminderTime().get(1))/5);
        } else{
            remainderSwitch.setChecked(false);
        }
    }

    /**
     * method to preset the frequency and days for the habit in edit habit
     */
    private void setFrequencyDays(){
        if(currentHabit.getFREQUENCY().equals(Frequency.DAILY)){
        } else if(currentHabit.getFREQUENCY().equals(Frequency.WEEKLY)){
            weekVisible();
            List<Integer> daysToBeChecked = new ArrayList<>();
            for(int check = 0; check < checkboxDayList.size(); check++){
                for(int days = 0; days < currentHabit.getDaysToDo().size(); days++){
                    if((check + 1) == currentHabit.getDaysToDo().get(days)){
                        daysToBeChecked.add(check);
                    }
                }
            }
            for(Integer integer: daysToBeChecked){
                checkboxDayList.get(integer).setChecked(true);
            }
        } else if(currentHabit.getFREQUENCY().equals(Frequency.MONTHLY)){
            monthVisible();
            monthSpinner.setSelection(currentHabit.getDaysToDo().get(0));
        }
    }

    private void update() {

        currentHabit = MainActivityVM.openHabit;

        habitName.setOnClickListener(v -> takeAwayWrongMessage());

        save.setOnClickListener(v -> {
            makeCalendarDaysList();

            currentHabit.setTitle(habitName.getText().toString());
            currentHabit.setDaysToDo(calendarDaysList);

            if(checkIfAllIsFillIn()) {
                endActivity();
            }
            else {
                errorMsgTextView.setVisibility(View.VISIBLE);
                errorMsgTextView.setText("You must fill in everything");
                errorMsgTextView.setTextColor(Color.RED);
                currentHabit.setTitle(habitName.getText().toString());
                currentHabit.setDaysToDo(calendarDaysList);
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
                currentHabit.setSTATE(State.MORNING);
            }
        });

        midday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeAwayWrongMessage();
                currentHabit.setSTATE(State.MIDDAY);
            }
        });

        evening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeAwayWrongMessage();
                currentHabit.setSTATE(State.EVENING);
            }
        });

        night.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeAwayWrongMessage();
                currentHabit.setSTATE(State.NIGHT);
            }
        });

        daily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeAwayWrongMessage();
                dayVisible();
                currentHabit.setFREQUENCY(Frequency.DAILY);
            }
        });

        weekly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeAwayWrongMessage();
                weekVisible();
                currentHabit.setFREQUENCY(Frequency.WEEKLY);
            }
        });

        monthly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeAwayWrongMessage();
                monthVisible();
                currentHabit.setFREQUENCY(Frequency.MONTHLY);
            }
        });

        remainderSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeAwayWrongMessage();
                if(remainderSwitch.isChecked()) {
                    hourSpinner.setVisibility(View.VISIBLE);
                    minSpinner.setVisibility(View.VISIBLE);
                    timeTextView.setVisibility(View.VISIBLE);
                    colonTextView.setVisibility(View.VISIBLE);
                    currentHabit.reminderEnabled();
                }
                else {
                    hourSpinner.setVisibility(View.INVISIBLE);
                    minSpinner.setVisibility(View.INVISIBLE);
                    timeTextView.setVisibility(View.INVISIBLE);
                    colonTextView.setVisibility(View.INVISIBLE);
                    currentHabit.reminderDisabled();
                }
            }
        });
    }

    /**
     * This method set everything to invisible when you click on the month-button.
     */
    private void dayVisible() {
        numberOfDaysTextView.setVisibility(View.INVISIBLE);
        numberOfDaysSpinner.setVisibility(View.INVISIBLE);
        monthTextView.setVisibility(View.INVISIBLE);
        monthSpinner.setVisibility(View.INVISIBLE);

        for(int i = 0; i < checkboxDayList.size(); i++) {
            checkboxDayList.get(i).setVisibility(View.INVISIBLE);
        }

        for (int i = 0; i< checkboxMonthList.size(); i++) {
            checkboxMonthList.get(i).setVisibility(View.INVISIBLE);
        }
    }

    /**
     * This method makes it easier to see what happens when clicking on the week-button.
     */
    private void weekVisible () {
        numberOfDaysTextView.setVisibility(View.VISIBLE);
        numberOfDaysSpinner.setVisibility(View.VISIBLE);
        monthSpinner.setVisibility(View.INVISIBLE);
        monthTextView.setVisibility(View.INVISIBLE);

        for(int i = 0; i < checkboxDayList.size(); i++) {
            checkboxDayList.get(i).setVisibility(View.VISIBLE);
        }
    }

    /**
     * This method makes it easier to see what happens when clicking on the month-button.
     */
    private void monthVisible () {
        numberOfDaysTextView.setVisibility(View.INVISIBLE);
        numberOfDaysSpinner.setVisibility(View.INVISIBLE);
        monthTextView.setVisibility(View.VISIBLE);
        monthSpinner.setVisibility(View.VISIBLE);

        for(int i = 0; i < checkboxDayList.size(); i++) {
            checkboxDayList.get(i).setVisibility(View.INVISIBLE);
        }
    }

    /**
     * This method makes a list of the checkboxes because is easier to treat them as a group
     * than individual
     */
    private void makeAListOfDayCbx() {
        checkboxDayList.add(sundayCheckbox);
        checkboxDayList.add(mondayCheckbox);
        checkboxDayList.add(tuesdayCheckbox);
        checkboxDayList.add(wednesdayCheckbox);
        checkboxDayList.add(thursdayCheckbox);
        checkboxDayList.add(fridayCheckbox);
        checkboxDayList.add(saturdayCheckbox);
    }

    /**
     * The method making a list of which weekdays the habit is to be done an depending on which
     * frequency that is select it so it can be shown on the right day.
     */
    private void makeCalendarDaysList () {

        calendarDaysList.clear();

        //When the frequency is daily every day is added in the list
        if(currentHabit.getFREQUENCY() == Frequency.DAILY) {
            for (int i = 0; i < 7; i++) {
                calendarDaysList.add(i+1);
            }
        }

        // When the frequency is weekly, the selected days are added to the list
        else if(currentHabit.getFREQUENCY() == Frequency.WEEKLY) {
            for (int i = 0; i < checkboxDayList.size(); i++) {
                if (checkboxDayList.get(i).isChecked()) {
                    calendarDaysList.add(i + 1);
                }
            }
        }

        //When the frequency is monthly, the date is added in the list
        else if(currentHabit.getFREQUENCY() == Frequency.MONTHLY) {
            calendarDaysList.add(Integer.valueOf(monthSpinner.getSelectedItem().toString()));
        }
    }

    /**
     * This method go through all the fields and looks for all information you need when
     * creating a Habit, is selected
     *  @return True if everything is correct, else return false
     */
    private boolean checkIfAllIsFillIn () {
        if(currentHabit.getFREQUENCY() == null || currentHabit.getSTATE() == null
                || currentHabit.getDaysToDo().size() == 0 || currentHabit.getTitle().equals("")) {
            if (currentHabit.getFREQUENCY() == null) {
                frequencyWrongImgV.setVisibility(View.VISIBLE);
            }

            if (currentHabit.getDaysToDo().size() == 0 && currentHabit.getFREQUENCY() == Frequency.WEEKLY) {
                weekWrongImgV.setVisibility(View.VISIBLE);
            }

            if (currentHabit.getSTATE() == null) {
                stateWrongImgV.setVisibility(View.VISIBLE);
            }
            if (currentHabit.getTitle().equals("")) {
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
        errorMsgTextView.setVisibility(View.INVISIBLE);
        frequencyWrongImgV.setVisibility(View.INVISIBLE);
        nameWrongImgV.setVisibility(View.INVISIBLE);
        stateWrongImgV.setVisibility(View.INVISIBLE);
        weekWrongImgV.setVisibility(View.INVISIBLE);
    }

    private void setCurrentHabit() {
        if(!(MainActivityVM.openHabit.getTitle().equals(""))) {
            currentHabit = MainActivityVM.openHabit;
        }
        else if(!(MenuHabitsVM.openHabit.getTitle().equals(""))) {
            currentHabit = MenuHabitsVM.openHabit;
        }
    }

    private void endActivity(){
        finish();
    }

    @Override
    public void recreateActivity() {
        recreate();
    }
}
