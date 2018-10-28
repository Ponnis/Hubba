package com.example.nils_martin.hubba.Model;

import java.util.Date;
import java.util.List;
import java.util.Stack;

/**
 * @author Nils-Martin Robeling
 */
public interface IHabit {

    void setHabitTypeState(IHabitTypeState habitTypeState);

    IHabitTypeState getHabitTypeState();

    void setGroupDone();

    void isDone();

    void notDone();

    void notifyObservers();

    void upStreak(Habit habit);

    Boolean getIsDone();

    void setDoneTo(boolean b);

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

    void setFREQUENCY(Frequency FREQUENCY);

    State getSTATE();

    Frequency getFREQUENCY();

    void setDaysToDo(List<Integer> daysToDo);

    void setReminderTime(List<Integer> time);

    List<Integer> getDaysToDo();

    List<Integer> getReminderTime();

    int getDaysToDoSize();

    void setDaysToDoSize(int size);

    void initDaysToDoList();

    String getTodayDate();

    String getPreviewsDayDone();
}