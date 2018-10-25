package com.example.nils_martin.hubba.ViewModel;

import com.example.nils_martin.hubba.Model.Habit;

public interface ICreateGroupHabitVM {
    void init();

    void update();

    void dayVisible();

    void weekVisible();

    void monthVisible();

    void makeCalendarDaysList();

    boolean checkIfAllFieldsFilled();

    void takeAwayWrongMessage();

    void endActivity();

    void setToGroupHabit(Habit habit);
}
