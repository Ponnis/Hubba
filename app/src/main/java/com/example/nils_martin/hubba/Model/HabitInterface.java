package com.example.nils_martin.hubba.Model;

import java.util.List;

public interface HabitInterface {

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

    int getGroupmembersDoneCount();

    Habit getHabit();

    int getStreak(Habit habit);

    void setTitle(String string);

    String getTitle(Habit habit);

    void setSTATE(State state);

    void setFREQUENCY (Frequency FREQUENCY);

    State getSTATE ();

    Frequency getFREQUENCY();

    void setDaysToDo(List<Integer> daysToDo);

    List<Integer> getDaysToDo();
}