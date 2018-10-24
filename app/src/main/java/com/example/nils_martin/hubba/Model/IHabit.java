package com.example.nils_martin.hubba.Model;

import java.util.List;

public interface IHabit {

    void setHabitTypeState(HabitTypeState habitTypeState);

    HabitTypeState getHabitTypeState();

    void setDone();

    void isDone();

    void notDone();

    void notifyObservers();

    void upStreak(Habit habit);

    Boolean getIsDone();

    void upGroupMembersDoneCount();

    void reminderEnabled();

    void reminderDisabled();

    boolean isReminderOn();

    int getGroupMembersDoneCount();

    Habit getHabit();

    int getStreak();

    void setTitle(String string);

    String getTitle();

    void setSTATE(State state);

    void setFREQUENCY (Frequency FREQUENCY);

    State getSTATE ();

    Frequency getFREQUENCY();

    void setDaysToDo(List<Integer> daysToDo);

    List<Integer> getDaysToDo();

}